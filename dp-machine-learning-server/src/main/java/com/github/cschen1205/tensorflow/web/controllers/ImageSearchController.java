package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.search.models.ImageSearchEngine;
import com.github.cschen1205.tensorflow.search.models.ImageSearchEntry;
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

@Api(tags = "Image Search Engine")
@Controller
public class ImageSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ImageClassifierController.class);

    @Autowired
    private ImageSearchEngine imageSearchEngine;

    @RequestMapping(value = "/api/search/image", method = RequestMethod.POST)
    public @ResponseBody
    List<ImageSearchEntry> searchImageFile(@RequestParam("pageIndex") int pageIndex,
                                        @RequestParam("pageSize") int pageSize,
                                        @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {


        try {
            byte[] bytes = file.getBytes();
            logger.info("image bytes received: {}", bytes.length);

            File imageFile = FileUtils.createTempFile(bytes);

            List<ImageSearchEntry> result = imageSearchEngine.query(imageFile, pageIndex, pageSize, false);

            FileUtils.deleteFile(imageFile);

            return result;
        }catch(IOException ex) {
            logger.error("Failed to process the image query", ex);

            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/api/index/image", method = RequestMethod.POST)
    public @ResponseBody
    ImageSearchEntry searchImageFile(
            @RequestParam("filename") String filename,
                                           @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {


        try {
            byte[] bytes = file.getBytes();
            logger.info("image bytes received: {}", bytes.length);

            File imageFile = FileUtils.storeUploadedFile(bytes, filename);

            return imageSearchEngine.index(imageFile);
        }catch(IOException ex) {
            logger.error("Failed to process the image query", ex);

            return ImageSearchEntry.createAlert(ex.toString());
        }
    }
}
