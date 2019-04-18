package cn.com.pandaonline.promulgator.quick;

import cn.com.pandaonline.promulgator.SuperMojo;
import cn.com.pandaonline.promulgator.push.Push;
import cn.com.pandaonline.promulgator.push.PushConfig;
import cn.com.pandaonline.promulgator.shudown.Shutdown;
import cn.com.pandaonline.promulgator.shudown.ShutdownConfig;
import cn.com.pandaonline.promulgator.start.Start;
import cn.com.pandaonline.promulgator.start.StartConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * @author fivewords
 * @date 2019/4/16 17:08
 */
@Mojo(name = "quick", defaultPhase = LifecyclePhase.NONE)
public class Quick extends SuperMojo {

    /**
     * 项目路径
     */
    @Parameter(required = true)
    private String projectPath;

    @Parameter
    private PushConfig pushConfig;
    @Parameter
    private StartConfig startConfig;
    @Parameter
    private ShutdownConfig shutdownConfig;

    static final String CLEAN = "cmd /c mvn clean";
    static final String PACKAGE = "cmd /c mvn package -DskipTests";

    public void go() throws Exception {

    }

    /**
     * clean
     * package
     * shutdown
     * push
     * start
     */
    public void execute() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(CLEAN, null, new File(projectPath));
            String is = IOUtils.toString(process.getInputStream(), "GBK");
            String err = IOUtils.toString(process.getErrorStream(), "GBK");
            if (StringUtils.isNotEmpty(err)) {
                System.out.println(err);
                throw new Error("Clean异常");
            } else {
                System.out.println(is);
            }
            Thread.sleep(1000);
            System.out.println("Clean 命令执行完成");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Process process = runtime.exec(PACKAGE, null, new File(projectPath));
            String is = IOUtils.toString(process.getInputStream(), "GBK");
            String err = IOUtils.toString(process.getErrorStream(), "GBK");
            if (StringUtils.isNotEmpty(err)) {
                System.out.println(err);
                throw new Error("Package异常");
            } else {
                System.out.println(is);
            }
            Thread.sleep(1000);
            System.out.println("Package 命令执行完成");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Shutdown shutdown = new Shutdown();
        shutdown.setShutdownConfig(shutdownConfig);
        shutdown.setClients(getClients());
        shutdown.setActiveEnv(getActiveEnv());
        shutdown.execute();

        Push push = new Push();
        push.setPushConfig(pushConfig);
        push.setClients(getClients());
        push.setActiveEnv(getActiveEnv());
        push.execute();

        Start start = new Start();
        start.setStartConfig(startConfig);
        start.setClients(getClients());
        start.setActiveEnv(getActiveEnv());
        start.execute();

    }

}
