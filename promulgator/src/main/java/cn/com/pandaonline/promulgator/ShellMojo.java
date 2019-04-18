package cn.com.pandaonline.promulgator;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * shell请求处理的
 *
 * @author fivewords
 * @date 2019/4/16 14:35
 */
public abstract class ShellMojo extends SuperMojo {

    public void command(String... cmds) {
        if (null == cmds || cmds.length == 0) {
            throw new Error("cmd命令不能为空!");
        }

        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = getShell().getOutputStream();
            inputStream = getShell().getInputStream();

            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println(StringUtils.join(cmds, ";"));
            printWriter.flush();
            Thread.sleep(1000);

            if (inputStream.available() > 0) {
                byte[] data = new byte[inputStream.available()];
                int nLen = inputStream.read(data);
                if (nLen < 0) {
                    throw new Error("network error.");
                }
                String temp = new String(data, 0, nLen, "UTF-8");
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
