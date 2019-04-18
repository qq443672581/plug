package cn.com.pandaonline.promulgator.push;

import com.jcraft.jsch.SftpProgressMonitor;

/**
 * 文件上传进度
 *
 * @author fivewords
 * @date 2019/4/16 13:48
 */
public class SftpProgress implements SftpProgressMonitor {

    private long count;
    private long count_;
    private long next;
    private long step;

    public void init(int mode, String src, String dest, long count) {
        this.count = count;
        this.step = count / 5;
        this.next = this.step;
    }

    public boolean count(long l) {
        count_ += l;
        if (count_ > next) {
            System.out.println(String.format("已上传%.2f", (float) count_ / count * 100) + "%");
            next = next + step;
        }
        return true;
    }

    public void end() {
        System.out.println("上传完成!");
    }

}
