package moviereview.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Kray on 2017/3/16.
 */
public class ShellUtil {

    /**
     * 直接运行 Shell 脚本
     *
     * @param filePath Shell 脚本路径
     * @return 控制台输出
     */
    public static String getResultOfShellFromFile(String filePath) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            CommandLine commandline = new CommandLine(new File(filePath));
            DefaultExecutor exec = new DefaultExecutor();
            exec.setExitValues(null);
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            return outputStream.toString("utf8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 直接运行 Shell 命令
     *
     * @param command Shell 命令
     * @return 控制台输出
     */
    public static String getResultOfShellFromCommand(String command) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            CommandLine commandLine = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setExitValues(null);
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine);
            String out = outputStream.toString("utf8");
            return out;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
