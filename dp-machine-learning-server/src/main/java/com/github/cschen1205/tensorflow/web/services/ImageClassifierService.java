package com.github.cschen1205.tensorflow.web.services;

import java.awt.image.BufferedImage;

public interface ImageClassifierService extends TrainedModelService {

    String predict(BufferedImage image);
}
