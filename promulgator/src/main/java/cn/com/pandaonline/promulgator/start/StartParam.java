package cn.com.pandaonline.promulgator.start;

/**
 * @author fivewords
 * @date 2019/4/16 14:36
 */
public class StartParam {

    private String env;
    private String[] cmds;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String[] getCmds() {
        return cmds;
    }

    public void setCmds(String[] cmds) {
        this.cmds = cmds;
    }
}
