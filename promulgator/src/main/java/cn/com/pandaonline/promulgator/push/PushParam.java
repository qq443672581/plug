package cn.com.pandaonline.promulgator.push;

/**
 * 客户端信息
 *
 * @author fivewords
 * @date 2019/4/16 11:01
 */
public class PushParam {

    // 环境
    private String env;

    // 是否强制替换 如果文件存在的话
    private boolean replace = false;

    private String filePath;
    private String fileName;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
