package com.github.cschen1205.tensorflow.web.controllers;

import com.github.cschen1205.tensorflow.web.entities.PdfMediaEntity;
import com.github.cschen1205.tensorflow.web.services.PdfMediaService;
import com.github.cschen1205.tensorflow.web.utils.HttpResponseHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api("Pdf Download")
@Controller
public class PdfController {

    @Autowired
    private PdfMediaService pdfMediaService;

    @RequestMapping(value="/api/download-pdf", method= RequestMethod.GET)
    public void downloadPdf(@RequestParam("documentId") String documentId, HttpServletResponse response){
        PdfMediaEntity entity = pdfMediaService.findByDocumentId(documentId);
        if(entity != null){
            try {
                HttpResponseHelper.sendBinaryData(response, entity.getModel());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            HttpResponseHelper.sendDefaultPdf(response);
        }
    }
}
