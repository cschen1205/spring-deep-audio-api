package com.github.cschen1205.tensorflow.classifiers.audio.utils;

import java.io.*;

public class InputStreamUtils {
    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream mem = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer, 0, 1024)) > 0){
            mem.write(buffer, 0, len);
        }
        return mem.toByteArray();
    }

    public static byte[] getBytes(File f) {
        try(FileInputStream inStream = new FileInputStream(f)){
            long len = f.length();
            byte[] bytes = new byte[(int)len];
            inStream.read(bytes, 0, (int)len);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
