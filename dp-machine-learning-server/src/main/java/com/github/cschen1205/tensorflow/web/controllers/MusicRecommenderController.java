package com.github.cschen1205.tensorflow.web.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.cschen1205.tensorflow.recommenders.models.AudioRecommender;
import com.github.cschen1205.tensorflow.recommenders.models.AudioUserHistory;
import com.github.cschen1205.tensorflow.search.models.AudioSearchEntry;
import com.github.cschen1205.tensorflow.commons.FileUtils;
import com.github.cschen1205.tensorflow.web.utils.HttpResponseHelper;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Api(tags = "Music Recommender")
@Controller
public class MusicRecommenderController {

    private static final Logger logger = LoggerFactory.getLogger(MusicRecommenderController.class);

    @Autowired
    private AudioRecommender recommender;

    @RequestMapping(value="/api/music/user-history-samples.json", method = RequestMethod.GET)
    public void downloadUserHistorySamples(HttpServletResponse response) {
        AudioUserHistory userHistory = new AudioUserHistory();

        List<String> audioFiles = FileUtils.getAudioFilePaths();
        Collections.shuffle(audioFiles);

        for(int i=0; i < 40; ++i){
            String filePath = audioFiles.get(i);
            userHistory.logAudio(filePath);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String json = JSON.toJSONString(userHistory, SerializerFeature.BrowserCompatible);
        byte[] bytes = json.getBytes();

        try {
            HttpResponseHelper.sendBinaryData(response, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/api/recommend/music", method = RequestMethod.POST)
    public @ResponseBody
    List<AudioSearchEntry> uploadBinaryObj(@RequestParam("file") MultipartFile file,
                                           @RequestParam("pageSize") int pageSize)
            throws ServletException, IOException {

        Map<String, Object> result = new HashMap<>();

        try {
            byte[] bytes = file.getBytes();
            logger.info("json bytes received: {}", bytes.length);
            String json = new String(bytes);

            AudioUserHistory history = JSON.parseObject(json, AudioUserHistory.class);

            return recommender.recommends(history.getHistory(), pageSize);
        }catch(IOException ex) {
            logger.error("Failed to process the uploaded image", ex);
            return new ArrayList<>();
        }
    }
}
