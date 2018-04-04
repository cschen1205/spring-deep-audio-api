package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.objdetect.ObjectDetector;
import com.github.cschen1205.objdetect.models.DetectedObj;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ObjectDetectorController {

    @Autowired
    private ObjectDetector objectDetector;

    private static final Logger logger = LoggerFactory.getLogger(ObjectDetectorController.class);

    @RequestMapping(value="/api/detect-objects/in-image", method = RequestMethod.POST)
    public @ResponseBody
    List<DetectedObj> detectObjectsInImage(
                                        @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {


        try {
            byte[] bytes = file.getBytes();
            logger.info("image bytes received: {}", bytes.length);

            InputStream in = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(in);

            List<DetectedObj> result = objectDetector.detectObjects(image);

            logger.info("predicted objects: {}", result.size());

            return result;
        }catch(IOException ex) {
            logger.error("Failed to process the uploaded image", ex);

            return new ArrayList<>();
        }
    }
}
