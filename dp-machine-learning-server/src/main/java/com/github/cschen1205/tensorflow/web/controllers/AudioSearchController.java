package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.search.models.AudioSearchEngine;
import com.github.cschen1205.tensorflow.search.models.AudioSearchEntry;
import com.github.cschen1205.tensorflow.web.utils.FileUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Audio Search Engine")
@Controller
public class AudioSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ImageClassifierController.class);

    @Autowired
    private AudioSearchEngine audioSearchEngine;

    @RequestMapping(value = "/api/search/audio", method = RequestMethod.POST)
    public @ResponseBody
    List<AudioSearchEntry> searchAudioFile(@RequestParam("pageIndex") int pageIndex,
                                        @RequestParam("pageSize") int pageSize,
                                        @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {


        try {
            byte[] bytes = file.getBytes();
            logger.info("audio bytes received: {}", bytes.length);

            File audioFile = FileUtils.createTempFile(bytes);

            List<AudioSearchEntry> result = audioSearchEngine.query(audioFile, pageIndex, pageSize, false);

            FileUtils.deleteFile(audioFile);

            return result;
        }catch(IOException ex) {
            logger.error("Failed to process the audio query", ex);

            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/api/index/audio", method = RequestMethod.POST)
    public @ResponseBody
    AudioSearchEntry searchAudioFile(
            @RequestParam("filename") String filename,
                                           @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {


        try {
            byte[] bytes = file.getBytes();
            logger.info("audio bytes received: {}", bytes.length);

            File audioFile = FileUtils.storeUploadedFile(bytes, filename);

            return audioSearchEngine.index(audioFile);
        }catch(IOException ex) {
            logger.error("Failed to process the audio query", ex);

            return AudioSearchEntry.createAlert(ex.toString());
        }
    }
}
