import moviereview.dao.impl.DataConst;
import moviereview.dao.impl.MovieDaoImpl;
import moviereview.model.Review;
import moviereview.model.ReviewIMDB;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;

/**
 * Created by Kray on 2017/3/12.
 */
public class PythonTest {

    @Autowired
    private MovieDaoImpl movieDao;

    private static final String PYTHON_FILE_LOCATION = "/Users/Kray/Desktop/PythonHelper";

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

            for (String str : new String[]{"1", "2", "3", "4"}) {
                output.write(str);
                output.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) output.close();
        }
    }

    @Test
    public void testIMDB() throws Exception {
        System.out.println(ShellUtil.getResultOfShellFromCommand("python " + PYTHON_FILE_LOCATION + "/MovieIMDBGetter.py " + "B000ZLFALS"));
//        System.out.println(ShellUtil.getResultOfShellFromFile(PYTHON_FILE_LOCATION + "/IMDBGetter.sh"));
//        System.out.println(ShellUtil.getResultOfShellFromCommand( "ls -l"));
    }

    @Test
    public void testJsonTransform() throws Exception {
        String stringResult = ShellUtil.getResultOfShellFromCommand("python3 " + PYTHON_FILE_LOCATION + "/MovieIMDBGetter.py " + "B000ZLFALS");
        JSONObject jsonObject = new JSONObject(stringResult);
        System.out.println(jsonObject);
    }

    @Test
    public void testIMDBReview() throws Exception {
        //todo
        ArrayList<Review> reviews = new ArrayList<>();
        String imdbID = "tt0942903";

        String stringResult = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBReviewGetter.py " + imdbID);
        try {
            JSONArray jsonArray = new JSONArray(stringResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ReviewIMDB reviewIMDB = GsonUtil.parseJson(jsonArray.get(i).toString(), ReviewIMDB.class);

                    System.out.println(i);

                    Review review = new Review("asd", reviewIMDB);
                    reviews.add(review);

                    System.out.println(review.getHelpfulness());
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            System.out.println(reviews.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
