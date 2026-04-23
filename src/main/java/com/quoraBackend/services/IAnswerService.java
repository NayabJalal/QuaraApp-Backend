package com.quoraBackend.services;

import com.quoraBackend.dto.AnswerRequestDTO;
import com.quoraBackend.dto.AnswerResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    Mono<AnswerResponseDTO> createAnswer(String questionId, AnswerRequestDTO answerRequestDTO);

    Mono<AnswerResponseDTO> getAnswerById(String questionId , String answerId);

    Flux<AnswerResponseDTO> getAnswersByQuestion(String questionId);

    Mono<AnswerResponseDTO> updateAnswer(String questionId , String answerId, AnswerRequestDTO answerRequestDTO);

    Mono<Void> deleteAnswer(String questionId , String answerId);
}
