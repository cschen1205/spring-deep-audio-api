package com.github.cschen1205.tensorflow.web.configs;

import com.github.cschen1205.objdetect.ObjectDetector;
import com.github.cschen1205.tensorflow.recommenders.models.AudioRecommender;
import com.github.cschen1205.tensorflow.recommenders.models.ImageRecommender;
import com.github.cschen1205.tensorflow.recommenders.models.KnnAudioRecommender;
import com.github.cschen1205.tensorflow.recommenders.models.KnnImageRecommender;
import com.github.cschen1205.tensorflow.search.models.AudioSearchEngine;
import com.github.cschen1205.tensorflow.search.models.AudioSearchEngineImpl;
import com.github.cschen1205.tensorflow.search.models.ImageSearchEngine;
import com.github.cschen1205.tensorflow.search.models.ImageSearchEngineInception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public AudioSearchEngine audioSearchEngine() {
        return new AudioSearchEngineImpl();
    }

    @Bean
    public AudioRecommender audioRecommender() {
        return new KnnAudioRecommender();
    }

    @Bean
    public ImageSearchEngine imageSearchEngine() {
        return new ImageSearchEngineInception();
    }

    @Bean
    public ImageRecommender imageRecommender() {
        return new KnnImageRecommender();
    }

    @Bean
    public ObjectDetector objectDetector() {
        return new ObjectDetector();
    }
}
