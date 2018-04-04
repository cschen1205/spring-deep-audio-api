package com.github.cschen1205.tensorflow.web.services;

import com.github.cschen1205.tensorflow.classifiers.audio.models.resnet.ResNetV2AudioClassifier;
import com.github.cschen1205.tensorflow.classifiers.audio.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class AudioClassifierServiceResNetV2 implements AudioClassifierService {

    private static final Logger logger = LoggerFactory.getLogger(AudioClassifierServiceResNetV2.class);

    private ResNetV2AudioClassifier classifier = new ResNetV2AudioClassifier();

    @Override
    public String predict(File audioFile) {
        return classifier.predict_audio(audioFile);
    }

    @Override
    public void loadModel() {
        logger.info("loading resnet v2 model for audio classifier");

        InputStream inputStream = ResourceUtils.getInputStream("tf_models/resnet-v2.pb");
        try {
            classifier.load_model(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
