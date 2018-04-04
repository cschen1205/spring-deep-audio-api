package com.github.cschen1205.tensorflow.web.services;

import java.io.File;

public interface AudioClassifierService extends TrainedModelService {
    String predict(File audioFile);

}
