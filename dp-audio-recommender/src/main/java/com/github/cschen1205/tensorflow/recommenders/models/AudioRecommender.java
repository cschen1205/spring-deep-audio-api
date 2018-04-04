package com.github.cschen1205.tensorflow.recommenders.models;

import com.github.cschen1205.tensorflow.search.models.AudioSearchEngine;
import com.github.cschen1205.tensorflow.search.models.AudioSearchEntry;

import java.util.List;

public interface AudioRecommender extends AudioSearchEngine {
    List<AudioSearchEntry> recommends(List<AudioMemo> userHistory, int k);
}
