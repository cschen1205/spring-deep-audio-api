package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.classifiers.audio.models.resnet.ResNetV2AudioClassifier;
import com.github.cschen1205.tensorflow.web.utils.HttpResponseHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Audio Media")
@Controller
public class AudioMediaController {

    @RequestMapping(value = "/api/media/audio/download-audio", method = RequestMethod.GET)
    public void downloadAudio(@RequestParam("name") String name, HttpServletResponse response)
            throws IOException {
        HttpResponseHelper.sendAudioFile(response, "music_samples/" + name + ".au");
    }

    @RequestMapping(value="/api/media/audio/download-list", method = RequestMethod.GET)
    public @ResponseBody List<String> getAudioList() {
        List<String> result = new ArrayList<>();
        String[] labels = ResNetV2AudioClassifier.labels;
        int[] indices = new int[] {3, 5, 6};
        for(String label : labels){
            for(Integer index : indices) {
                String name = label + ".0000" + index;
                result.add(name);
            }
        }

        return result;
    }
}
