package com.github.cschen1205.tensorflow.recommenders;

import com.github.cschen1205.tensorflow.recommenders.models.AudioUserHistory;
import com.github.cschen1205.tensorflow.recommenders.models.KnnAudioRecommender;
import com.github.cschen1205.tensorflow.search.models.AudioSearchEntry;
import com.github.cschen1205.tensorflow.commons.FileUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class KnnAudioRecommenderDemo {
    public static void main(String[] args) {
        AudioUserHistory userHistory = new AudioUserHistory();

        List<String> audioFiles = FileUtils.getAudioFilePaths();
        Collections.shuffle(audioFiles);

        for(int i=0; i < 40; ++i){
            String filePath = audioFiles.get(i);
            userHistory.logAudio(filePath);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        KnnAudioRecommender recommender = new KnnAudioRecommender();
        if(!recommender.loadIndexDbIfExists()) {
            recommender.indexAll(FileUtils.getAudioFiles());
            recommender.saveIndexDb();
        }

        System.out.println(userHistory.head(10));

        int k = 10;
        List<AudioSearchEntry> result = recommender.recommends(userHistory.getHistory(), k);

        for(int i=0; i < result.size(); ++i){
            AudioSearchEntry entry = result.get(i);
            System.out.println("Search Result #" + (i+1) + ": " + entry.getPath());
        }




    }
}
