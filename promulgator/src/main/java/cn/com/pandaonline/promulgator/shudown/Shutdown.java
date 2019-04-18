package cn.com.pandaonline.promulgator.shudown;

import cn.com.pandaonline.promulgator.ShellMojo;
import cn.com.pandaonline.promulgator.start.StartConfig;
import cn.com.pandaonline.promulgator.start.StartParam;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author fivewords
 * @date 2019/4/12 18:44
 */
@Mojo(name = "shutdown", defaultPhase = LifecyclePhase.NONE)
public class Shutdown extends ShellMojo {

    @Parameter
    private ShutdownConfig shutdownConfig;

    public void go() {
        ShutdownParam shutdownParam = getShutdownParam();
        command(shutdownParam.getCmds());
    }

    ShutdownParam getShutdownParam() {
        for (ShutdownParam param : shutdownConfig.getShutdownParams()) {
            if (param.getEnv().equals(getActiveEnv())) {
                return param;
            }
        }
        throw new Error("没有对应环境的Shutdown参数!");
    }

    public void setShutdownConfig(ShutdownConfig shutdownConfig) {
        this.shutdownConfig = shutdownConfig;
    }
}
