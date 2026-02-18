package com.quoraBackend.services;

import com.quoraBackend.dto.QuestionRequestDTO;
import com.quoraBackend.dto.QuestionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IQuestionService {
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);
    public Mono<QuestionResponseDTO> getById(String id);
    public Flux<QuestionResponseDTO> findAll(String cursor , int size);
    public Mono<Void> deleteById(String id);
    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page);
    public Flux<QuestionResponseDTO> searchByTag(List<String> tag, int page, int size);
    public Flux<String> getAllTags();
    Mono<QuestionResponseDTO> deleteTag(String id, String tag);
}
