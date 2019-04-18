package cn.com.pandaonline.promulgator.start;

import cn.com.pandaonline.promulgator.ShellMojo;
import cn.com.pandaonline.promulgator.push.PushParam;
import com.jcraft.jsch.JSchException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author fivewords
 * @date 2019/4/12 18:44
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.NONE)
public class Start extends ShellMojo {

    @Parameter
    private StartConfig startConfig;

    public void go() {
        StartParam startParam = getStartParam();
        command(startParam.getCmds());
    }

    StartParam getStartParam() {
        for (StartParam param : startConfig.getStartParams()) {
            if (param.getEnv().equals(getActiveEnv())) {
                return param;
            }
        }
        throw new Error("没有对应环境的Start参数!");
    }

    public void setStartConfig(StartConfig startConfig) {
        this.startConfig = startConfig;
    }
}
