package cn.com.pandaonline.promulgator.push;

import cn.com.pandaonline.promulgator.SuperMojo;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Vector;

/**
 * 上传文件到
 *
 * @author fivewords
 * @date 2019/4/12 18:44
 */
@Mojo(name = "push", defaultPhase = LifecyclePhase.NONE)
public class Push extends SuperMojo {

    @Parameter
    private PushConfig pushConfig;

    public void go() throws SftpException {
        PushParam pushParam = getPushParam();
        if (!new File(pushConfig.getJarPath()).exists()) {
            throw new Error(String.format("要上传的文件[%s]不存在", pushConfig.getJarPath()));
        }

        if (!pushParam.isReplace() && hasFile(getFtp(), pushParam.getFilePath(), pushParam.getFileName())) {
            throw new RuntimeException(String.format("文件路径[%s]已经存在此文件[%s]", pushParam.getFilePath(), pushParam.getFileName()));
        }
        getFtp().put(pushConfig.getJarPath(), pushParam.getFilePath() + SEPARATOR + pushParam.getFileName(), new SftpProgress());

    }

    PushParam getPushParam() {
        for (PushParam param : pushConfig.getPushParams()) {
            if (param.getEnv().equals(getActiveEnv())) {
                return param;
            }
        }
        throw new Error("没有对应环境的Push参数!");
    }

    boolean hasFile(ChannelSftp sftp, String dirPath, String fileName) throws SftpException {
        Vector<ChannelSftp.LsEntry> ls = sftp.ls(dirPath);
        for (ChannelSftp.LsEntry entry : ls) {
            if (entry.getFilename().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public void setPushConfig(PushConfig pushConfig) {
        this.pushConfig = pushConfig;
    }
}
