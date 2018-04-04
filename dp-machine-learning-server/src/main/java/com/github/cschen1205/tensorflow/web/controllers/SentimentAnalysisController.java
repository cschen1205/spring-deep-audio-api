package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.web.services.SentimentAnalysisService;
import com.github.cschen1205.tensorflow.web.viewmodels.Sentiment;
import com.github.cschen1205.tensorflow.web.viewmodels.SentimentRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "Sentiment Analysis")
@Controller
public class SentimentAnalysisController {

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @RequestMapping(value="/api/classifiers/sentiment", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    Sentiment predictSentiment(@RequestBody SentimentRequest request) {
        return sentimentAnalysisService.predictSentiment(request.getSentence());
    }
}
