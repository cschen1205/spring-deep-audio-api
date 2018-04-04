package com.github.cschen1205.tensorflow.web.services;



import com.github.cschen1205.tensorflow.web.models.PdfSearchEntry;

import java.util.List;
import java.util.Map;

public interface SearchService {
    void indexAsync(String documentId, String username);
    long countByQuery(String keyword, Map<String, String> filters);
    List<PdfSearchEntry> pageByQuery(String keyword, Map<String, String> filters, int pageIndex, int pageSize, Map<String, String> sorting);
}
