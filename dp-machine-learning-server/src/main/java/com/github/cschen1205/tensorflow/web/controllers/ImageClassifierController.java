package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.web.services.ImageClassifierService;
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

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "Image Classifier")
@Controller
public class ImageClassifierController {

    @Autowired
    private ImageClassifierService imageClassifierService;

    private static final Logger logger = LoggerFactory.getLogger(ImageClassifierController.class);

    @RequestMapping(value = "/api/classifiers/image", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> classifyImage(@RequestParam("description") String description,
                                      @RequestParam("id") String id,
                                      @RequestParam("filename") String filename,
                                      @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {

        Map<String, Object> result = new HashMap<>();

        try {
            byte[] bytes = file.getBytes();
            logger.info("image bytes received: {}", bytes.length);

            InputStream in = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(in);

            String predicted_label = imageClassifierService.predict(image);

            logger.info("predicted: {}", predicted_label);

            result.put("predicted", predicted_label);
            result.put("description", description);
            result.put("id", id);
            result.put("success", true);
            result.put("error", "");

            return result;
        }catch(IOException ex) {
            logger.error("Failed to process the uploaded image", ex);
            result.put("success", false);
            result.put("id", "");
            result.put("predicted", "none");
            result.put("description", description);
            result.put("error", ex.getMessage());
            return result;
        }
    }
}
