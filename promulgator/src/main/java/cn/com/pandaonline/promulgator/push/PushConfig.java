package cn.com.pandaonline.promulgator.push;

import java.util.List;

/**
 * @author fivewords
 * @date 2019/4/16 14:09
 */
public class PushConfig {

    private String jarPath;

    private List<PushParam> pushParams;

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public List<PushParam> getPushParams() {
        return pushParams;
    }

    public void setPushParams(List<PushParam> pushParams) {
        this.pushParams = pushParams;
    }
}
