package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.web.services.AudioClassifierService;
import com.github.cschen1205.tensorflow.web.utils.FileUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "Audio Classifier")
@Controller
public class AudioClassifierController {

    private static final Logger logger = LoggerFactory.getLogger(AudioClassifierController.class);

    @Autowired
    private AudioClassifierService audioClassifierService;

    @RequestMapping(value = "/api/classifiers/audio", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> uploadBinaryObj(@RequestParam("description") String description,
                                        @RequestParam("id") String id,
                                        @RequestParam("filename") String filename,
                                        @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {

        Map<String, Object> result = new HashMap<>();

        try {
            byte[] bytes = file.getBytes();
            logger.info("audio bytes received: {}", bytes.length);

            File audioFile = FileUtils.createTempFile(bytes);
            String predicted_label = audioClassifierService.predict(audioFile);

            FileUtils.deleteFile(audioFile);

            logger.info("predicted: {}", predicted_label);

            result.put("predicted", predicted_label);
            result.put("description", description);
            result.put("filename", filename);
            result.put("id", id);
            result.put("success", true);
            result.put("error", "");

            return result;
        }catch(IOException ex) {
            logger.error("Failed to process the uploaded image", ex);
            result.put("success", false);
            result.put("description", description);
            result.put("predicted", "none");
            result.put("filename", filename);
            result.put("id", "");
            result.put("error", ex.getMessage());
            return result;
        }
    }
}
