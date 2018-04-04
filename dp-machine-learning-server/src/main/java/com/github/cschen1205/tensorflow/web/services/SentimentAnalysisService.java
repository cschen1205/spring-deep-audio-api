package com.github.cschen1205.tensorflow.web.services;

import com.github.cschen1205.tensorflow.web.viewmodels.Sentiment;

public interface SentimentAnalysisService extends TrainedModelService {
    Sentiment predictSentiment(String doc);
}
