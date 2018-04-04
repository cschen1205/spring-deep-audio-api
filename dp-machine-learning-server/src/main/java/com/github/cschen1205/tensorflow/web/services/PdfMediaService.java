package com.github.cschen1205.tensorflow.web.services;


import com.github.cschen1205.tensorflow.web.entities.PdfMediaEntity;

public interface PdfMediaService {
    long savePdf(byte[] bytes, String username, String documentId);
    PdfMediaEntity findByDocumentId(String documentId);

}
