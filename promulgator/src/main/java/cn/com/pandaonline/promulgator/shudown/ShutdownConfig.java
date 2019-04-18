package cn.com.pandaonline.promulgator.shudown;

import java.util.List;

/**
 * @author fivewords
 * @date 2019/4/16 15:51
 */
public class ShutdownConfig {

    private List<ShutdownParam> shutdownParams;

    public List<ShutdownParam> getShutdownParams() {
        return shutdownParams;
    }

    public void setShutdownParams(List<ShutdownParam> shutdownParams) {
        this.shutdownParams = shutdownParams;
    }
}
