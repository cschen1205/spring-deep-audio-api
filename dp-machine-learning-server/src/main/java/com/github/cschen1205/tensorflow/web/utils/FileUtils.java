package com.github.cschen1205.tensorflow.web.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {
    public static void createFolderIfNotExists(String folderPath) {
        File f = new File(folderPath);
        if(f.exists()){
            f.mkdir();
        }
    }

    public static File createTempFile(byte[] bytes) throws IOException {
        FileUtils.createFolderIfNotExists("/tmp");

        String filePath = "/tmp/" + UUID.randomUUID().toString() + ".au";
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bytes);
        fos.close();

        return new File(filePath);
    }

    public static void deleteFile(File audioFile) {
        try {
            audioFile.delete();
        }catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public static File storeUploadedFile(byte[] bytes, String filename) throws IOException {
        FileUtils.createFolderIfNotExists("uploaded_audio_samples");

        String filePath = "uploaded_audio_samples/" + UUID.randomUUID().toString() + ".au";
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bytes);
        fos.close();

        return new File(filePath);
    }
}
