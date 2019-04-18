package cn.com.pandaonline.promulgator;

/**
 * 客户端信息
 *
 * @author fivewords
 * @date 2019/4/16 11:01
 */
public class Client {

    // 环境
    private String env;
    private String host;
    private int port = 22;
    private String username;
    private String password;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
