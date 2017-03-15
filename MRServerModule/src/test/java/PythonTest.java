import moviereview.model.Review;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kray on 2017/3/12.
 */
public class PythonTest {

//    private final String PREFIX = "./src/main/resources/python";

    @Test
    public void testPython() {

        try {
            /*
                arg1: Py文件 ->绝对<-路径
                  -i: 输入文件->绝对<-路径
             */
            String command = "/bin/bash -c ./src/main/resources/python/freeq.py -i /Users/Kray/Desktop/MovieSmallCache/tempResult.txt";

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            CommandLine commandline = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setExitValues(null);
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            String out = outputStream.toString("utf8");
            String error = errorStream.toString("utf8");

            System.out.println("out " + out);

            ArrayList<Map<String, Integer>> result = new ArrayList<Map<String, Integer>>();
            String[] pairs = out.split("\n");
            System.out.println(pairs.length);

            Map<String, Integer> map = new HashMap<String, Integer>();
            for (String pair : pairs) {
                pair = pair.trim();
                String[] pairSplit = pair.split(" ");
                map.put(pairSplit[1], Integer.parseInt(pairSplit[0]));
            }

            System.out.println(map.keySet());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testWriter() throws Exception {

        BufferedWriter output = null;
        try {
            File file = new File("/Users/Kray/Desktop/MovieSmallCache/tempResult.txt");
            System.out.println(file.getPath());
            output = new BufferedWriter(new FileWriter(file));

            for (String str : new String[]{"1","2","3","4"}) {
                output.write(str);
                output.write("\n");
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) output.close();
        }
    }
}
