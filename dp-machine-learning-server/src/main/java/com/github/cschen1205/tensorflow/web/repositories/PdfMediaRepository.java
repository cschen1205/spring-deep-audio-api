package com.github.cschen1205.tensorflow.web.repositories;

import com.github.cschen1205.tensorflow.web.entities.PdfMediaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfMediaRepository extends CrudRepository<PdfMediaEntity, Long> {
    PdfMediaEntity findFirstByDocumentId(String documentId);

}
