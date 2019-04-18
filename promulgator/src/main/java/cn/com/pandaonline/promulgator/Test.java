package cn.com.pandaonline.promulgator;

import cn.com.pandaonline.promulgator.push.SftpProgress;
import com.jcraft.jsch.*;

import java.util.Properties;
import java.util.Vector;

/**
 * @author fivewords
 * @date 2019/4/16 13:05
 */
public class Test {
    static String separator = "/";

    public static void main(String[] args) {
        ChannelSftp sftp = null;
        ChannelShell shell = null;
        Session sshSession = null;
        try {
            JSch jsch = new JSch();
            sshSession = jsch.getSession("root", "10.16.0.58", 22);
            sshSession.setPassword("jkl;'");
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            // shell
            sftp = (ChannelSftp) sshSession.openChannel("sftp");
            shell = (ChannelShell) sshSession.openChannel("shell");
            shell.connect();
            sftp.connect();

            if (true && hasFile(sftp, "/data/test", "ss-1.0-SNAPSHOT.jar")) {
                throw new RuntimeException(String.format("文件路径[%s]已经存在此文件[%s]", "123", "123"));
            }
            sftp.put("D:\\soft\\project\\service\\small_software\\target\\ss-1.0-SNAPSHOT.jar", "/data/test" + separator + "ss-1.0-SNAPSHOT.jar", new SftpProgress());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                }
            }
            if (shell != null) {
                if (shell.isConnected()) {
                    shell.disconnect();
                }
            }
            if (sshSession != null) {
                if (sshSession.isConnected()) {
                    sshSession.disconnect();
                }
            }
        }
    }

    static boolean hasFile(ChannelSftp sftp, String dirPath, String fileName) throws SftpException {
        Vector<ChannelSftp.LsEntry> ls = sftp.ls(dirPath);
        for (ChannelSftp.LsEntry entry : ls) {
            if (entry.getFilename().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

}
