package com.quoraBackend.repositories;

import com.quoraBackend.models.QuestionElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface QuestionDocumentRepo extends ElasticsearchRepository<QuestionElasticDocument,String> {

    List<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title,String content);

}
