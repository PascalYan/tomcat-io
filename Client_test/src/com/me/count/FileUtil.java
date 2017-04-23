package com.me.count;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Created by young on 2017/4/23.
 */
public class FileUtil {
    public static PrintStream getPrintStream(String fileName,boolean append){
        try {
            return new PrintStream(new FileOutputStream(fileName,append));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
