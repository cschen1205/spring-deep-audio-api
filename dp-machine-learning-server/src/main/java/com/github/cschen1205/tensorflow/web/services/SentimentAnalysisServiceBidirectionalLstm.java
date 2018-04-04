package com.github.cschen1205.tensorflow.web.services;

import com.github.cschen1205.tensorflow.classifiers.sentiment.models.BidirectionalLstmSentimentClassifier;
import com.github.cschen1205.tensorflow.classifiers.sentiment.utils.ResourceUtils;
import com.github.cschen1205.tensorflow.web.viewmodels.Sentiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SentimentAnalysisServiceBidirectionalLstm implements SentimentAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceBidirectionalLstm.class);

    private BidirectionalLstmSentimentClassifier classifier = new BidirectionalLstmSentimentClassifier();

    @Override
    public Sentiment predictSentiment(String doc) {
        float[] ratings = classifier.predict(doc);
        Sentiment result = new Sentiment();
        result.setPositiveProb(ratings[0]);
        result.setNegativeProb(ratings[1]);
        result.setPositive(result.getPositiveProb() >= result.getNegativeProb());
        result.setSentence(doc);
        return result;
    }

    @Override
    public void loadModel() {
        logger.info("load bi-directional lstm trained model for sentiment analysis");
        try {
            classifier.load_model(ResourceUtils.getInputStream("tf_models/bidirectional_lstm_softmax.pb"));
            classifier.load_vocab(ResourceUtils.getInputStream("tf_models/bidirectional_lstm_softmax.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
