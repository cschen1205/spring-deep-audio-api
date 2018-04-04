package com.github.cschen1205.tensorflow.web.services;

import com.github.cschen1205.tensorflow.classifiers.images.models.inception.InceptionImageClassifier;
import com.github.cschen1205.tensorflow.classifiers.images.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageClassifierServiceInception implements ImageClassifierService {
    private static final Logger logger = LoggerFactory.getLogger(ImageClassifierServiceInception.class);
    private InceptionImageClassifier classifier = new InceptionImageClassifier();
    public ImageClassifierServiceInception(){

    }

    @Override
    public String predict(BufferedImage image) {
        return classifier.predict_image(image);
    }

    @Override
    public void loadModel() {

        logger.info("loading inception model for image classifier");

        try {
            classifier.load_model(ResourceUtils.getInputStream("tf_models/tensorflow_inception_graph.pb"));
            classifier.load_labels(ResourceUtils.getInputStream("tf_models/imagenet_comp_graph_label_strings.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
