package com.github.cschen1205.tensorflow.web.viewmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountObj {
    private long count;
    private String error;

    public CountObj(long count){
        this.count = count;
    }
}
