package com.quoraBackend.controllers;

import com.quoraBackend.dto.QuestionRequestDTO;
import com.quoraBackend.dto.QuestionResponseDTO;
import com.quoraBackend.services.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final IQuestionService questionService;

    @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO){

        return questionService.createQuestion(questionRequestDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully : " + response))
                .doOnError(error -> System.out.println("Error creating question: "+error));
    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getById(@PathVariable String id){
        return questionService.getById(id)
                .doOnSuccess(response -> System.out.println("Question fetched successfully: " + response))
                .doOnError(error -> System.out.println("Error fetching question: " + error));
    }

    @GetMapping
    public Flux<QuestionResponseDTO> getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.findAll(cursor, size)
                .doOnError(error -> System.out.println("Error fetching questions : "+ error))
                .doOnComplete(() -> System.out.println("Question fetching successfully"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id){
        return questionService.deleteById(id)
                .doOnSuccess(v -> System.out.println("Successfully deleted the Question "+id))
                .doOnError(error -> System.out.println("Failed to delete id "+ id));
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
        @RequestParam String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        return questionService.searchQuestions(query,page,size);
    }

    @GetMapping("/tags/all")
    public Mono<List<String>> getAllTags() {
        return questionService.getAllTags()
                .collectList();
    }

    @GetMapping("/tags")
    public Flux<QuestionResponseDTO> getByTag(
            @RequestParam List<String> tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return questionService.searchByTag(tag,page,size);
    }

    @DeleteMapping("/{id}/tags")
    public Mono<QuestionResponseDTO> deleteTag(
            @PathVariable String id,
            @RequestParam String tag
    ) {
        return questionService.deleteTag(id, tag);
    }
}
