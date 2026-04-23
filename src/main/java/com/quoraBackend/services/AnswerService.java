package com.quoraBackend.services;

import com.quoraBackend.adapter.AnswerAdapter;
import com.quoraBackend.dto.AnswerRequestDTO;
import com.quoraBackend.dto.AnswerResponseDTO;
import com.quoraBackend.events.ViewCountEvent;
import com.quoraBackend.models.Answer;
import com.quoraBackend.producers.KafkaEventProducer;
import com.quoraBackend.repositories.AnswersRepo;
import com.quoraBackend.repositories.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService{

    private final AnswersRepo answersRepo;
    private final QuestionRepo questionRepo;
    private final KafkaEventProducer kafkaEventProducer;

    @Override
    public Mono<AnswerResponseDTO> createAnswer(String questionId, AnswerRequestDTO answerRequestDTO) {
        return questionRepo.findById(questionId)
                .flatMap(questions -> {
                    Answer answer = AnswerAdapter.toEntity(answerRequestDTO, questionId);
                    return answersRepo.save(answer);
                })
                .map(AnswerAdapter::toAnswerResponseDTO)
                .doOnSuccess(response -> System.out.println("Answer created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating answer: "+error));
    }

    @Override
    public Mono<AnswerResponseDTO> getAnswerById(String questionId, String answerId) {
        return answersRepo.findByIdAndQuestionId(answerId, questionId)
                .map(AnswerAdapter::toAnswerResponseDTO)
                .doOnSuccess(response -> {
                    if (response != null){
                        System.out.println("Answer retrieved successfully: " + response);
                        ViewCountEvent viewCountEvent = new ViewCountEvent(answerId, "answer", LocalDateTime.now()); // Increment view count by 1
                        kafkaEventProducer.publishViewCountEvent(viewCountEvent); // Publish view count event to Kafka
                    }
                })
                .doOnError(error -> System.out.println("Error retrieving answer: "+error));
    }

    @Override
    public Flux<AnswerResponseDTO> getAnswersByQuestion(String questionId) {
        return answersRepo.findByQuestionId(questionId)
                .map(AnswerAdapter::toAnswerResponseDTO)
                .doOnComplete(() -> System.out.println("All answers retrieved successfully for questionId: " + questionId))
                .doOnError(error -> System.out.println("Error retrieving answers for questionId " + questionId + ": " + error));
    }

    @Override
    public Mono<AnswerResponseDTO> updateAnswer(String questionId, String answerId, AnswerRequestDTO answerRequestDTO) {
        return answersRepo.findByIdAndQuestionId(answerId, questionId)
                .flatMap(existingAnswer -> {
                    existingAnswer.setContent(answerRequestDTO.getContent());
                    existingAnswer.setUpdatedAt(LocalDateTime.now());
                    return answersRepo.save(existingAnswer);
                })
                .map(AnswerAdapter::toAnswerResponseDTO)
                .doOnSuccess(response -> System.out.println("Answer updated successfully: " + response))
                .doOnError(error -> System.out.println("Error updating answer with id " + answerId + ": " + error));
    }

    @Override
    public Mono<Void> deleteAnswer(String questionId, String answerId) {
        return answersRepo.deleteByIdAndQuestionId(answerId, questionId)
                .doOnSuccess(unused -> System.out.println("Answer deleted successfully with id: " + answerId))
                .doOnError(error -> System.out.println("Error deleting answer with id " + answerId + ": " + error));
    }
}
