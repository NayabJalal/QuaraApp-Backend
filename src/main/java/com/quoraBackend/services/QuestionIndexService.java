package com.quoraBackend.services;

import com.quoraBackend.models.QuestionElasticDocument;
import com.quoraBackend.models.Questions;
import com.quoraBackend.repositories.QuestionDocumentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService{

    private final QuestionDocumentRepo questionDocumentRepo;

    @Override
    public void createQuestionIndex(Questions questions) {
        QuestionElasticDocument document = QuestionElasticDocument.builder()
                .id(questions.getId())
                .title(questions.getTitle())
                .content(questions.getContent())
                .build();
        questionDocumentRepo.save(document);
    }
}
