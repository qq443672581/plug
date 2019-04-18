package cn.com.pandaonline.promulgator;

import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author fivewords
 * @date 2019/4/16 14:04
 */
public abstract class SuperMojo extends AbstractMojo {

    public static String SEPARATOR = "/";

    /**
     * 激活的环境
     */
    @Parameter(required = true)
    private String activeEnv;

    /**
     * 客户端地址
     */
    @Parameter(required = true)
    private List<Client> clients;

    public Client getClient() {
        Map<String, Client> cs = new HashMap<String, Client>();
        for (Client client : clients) {
            if (StringUtils.isEmpty(client.getEnv())) {
                throw new Error(String.format("运行环境不能为空"));
            }
            if (null != cs.get(client.getEnv())) {
                throw new Error(String.format("重复的运行环境[%s]", client.getEnv()));
            }
            cs.put(client.getEnv(), client);
        }
        Client client = cs.get(activeEnv);
        if (null == client) {
            throw new Error(String.format("不存在的运行环境[%s]", activeEnv));
        }
        return client;
    }

    public String getActiveEnv() {
        return activeEnv;
    }

    public void setActiveEnv(String activeEnv) {
        this.activeEnv = activeEnv;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    Session sshSession = null;
    ChannelShell shell = null;
    ChannelSftp ftp = null;

    public void open() throws JSchException {
        Client client = getClient();

        JSch jsch = new JSch();
        sshSession = jsch.getSession(client.getUsername(), client.getHost(), client.getPort());
        sshSession.setPassword(client.getPassword());
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();

        shell = (ChannelShell) sshSession.openChannel("shell");
        shell.setPty(true);
        shell.connect();

        ftp = (ChannelSftp) sshSession.openChannel("sftp");
        ftp.connect();
    }

    public ChannelShell getShell() {
        return shell;
    }

    public ChannelSftp getFtp() {
        return ftp;
    }

    public void close() {
        if (shell != null) {
            if (shell.isConnected()) {
                shell.disconnect();
            }
        }
        if (ftp != null) {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
            }
        }
    }

    public abstract void go() throws Exception;

    public void execute() {
        try {
            open();
            go();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


}
