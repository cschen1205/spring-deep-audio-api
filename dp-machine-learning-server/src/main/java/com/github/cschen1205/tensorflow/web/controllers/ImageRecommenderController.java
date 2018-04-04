package com.github.cschen1205.tensorflow.web.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.cschen1205.tensorflow.commons.FileUtils;
import com.github.cschen1205.tensorflow.recommenders.models.ImageRecommender;
import com.github.cschen1205.tensorflow.recommenders.models.ImageUserHistory;
import com.github.cschen1205.tensorflow.search.models.ImageSearchEntry;
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
public class ImageRecommenderController {

    private static final Logger logger = LoggerFactory.getLogger(ImageRecommenderController.class);

    @Autowired
    private ImageRecommender recommender;

    @RequestMapping(value="/api/image/user-history-samples.json", method = RequestMethod.GET)
    public void downloadUserHistorySamples(HttpServletResponse response) {
        ImageUserHistory userHistory = new ImageUserHistory();

        List<String> imageFiles = FileUtils.getImageFilePaths();
        Collections.shuffle(imageFiles);

        for(int i=0; i < 40; ++i){
            String filePath = imageFiles.get(i);
            userHistory.logImage(filePath);
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

    @RequestMapping(value = "/api/recommend/image", method = RequestMethod.POST)
    public @ResponseBody
    List<ImageSearchEntry> uploadBinaryObj(@RequestParam("file") MultipartFile file,
                                           @RequestParam("pageSize") int pageSize)
            throws ServletException, IOException {

        Map<String, Object> result = new HashMap<>();

        try {
            byte[] bytes = file.getBytes();
            logger.info("json bytes received: {}", bytes.length);
            String json = new String(bytes);

            ImageUserHistory history = JSON.parseObject(json, ImageUserHistory.class);

            return recommender.recommends(history.getHistory(), pageSize);
        }catch(IOException ex) {
            logger.error("Failed to process the uploaded image", ex);
            return new ArrayList<>();
        }
    }
}
