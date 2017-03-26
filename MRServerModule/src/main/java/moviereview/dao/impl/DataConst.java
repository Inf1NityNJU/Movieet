package moviereview.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Kray on 2017/3/21.
 */
public class DataConst {

    static final String SEPARATOR = "--------------------";
    static final int INFO_IN_ONE_FILE = 1000;

    //local
    public static final String FILE_LOCATION = "/Users/Kray/Desktop/MovieSmallCache";
    public static final String PYTHON_FILE_LOCATION = "/Users/Kray/Desktop/PythonHelper";
    //    server
//    public static final String FILE_LOCATION = "/mydata/moviereview/MovieSmallCache";
//    public static final String PYTHON_FILE_LOCATION = "/mydata/moviereview/PythonHelper";


    /**
     * get File index by data number
     *
     * @param number data number
     * @return file index
     */
    static int getFileIndex(int number) {
        return (number - 1) / DataConst.INFO_IN_ONE_FILE;
    }

}
