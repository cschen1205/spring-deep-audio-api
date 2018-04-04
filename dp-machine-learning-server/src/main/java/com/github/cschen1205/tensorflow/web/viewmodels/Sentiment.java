package com.github.cschen1205.tensorflow.web.viewmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sentiment {
    private boolean positive;
    private double positiveProb;
    private double negativeProb;
    private String sentence;
}
