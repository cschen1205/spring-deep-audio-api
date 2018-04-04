package com.github.cschen1205.tensorflow.web.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfSearchEntry {
    private String documentId;
    private String content;
    private String username;
    private long updatedAt;
    private long createdAt;
}
