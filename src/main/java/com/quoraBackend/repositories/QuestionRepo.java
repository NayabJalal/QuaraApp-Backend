package com.quoraBackend.repositories;

import com.quoraBackend.models.Questions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionRepo extends ReactiveMongoRepository<Questions , String> {
    Mono<Questions> findById(String id);
    Flux<Questions> findAll();
    Mono<Void> deleteById(String id);
    @Query("{'$or': [ {'title': { $regex: ?0 ,  $options: 'i'} } , {'content': {$regex: ?0 , $options: 'i' } } ] }")
    Flux<Questions> findByTitleOrContentContainingIgnoreCase(String searchTerm, Pageable pageable);
    Flux<Questions> findByCreatedAtGreaterThanOrderByCreatedAtAsc(LocalDateTime cursor , Pageable pageable);
    Flux<Questions> findTop10ByOrderByCreatedAtAsc();
    @Query(value = "{}", fields = "{ 'tags' : 1 }")
    Flux<Questions> findAllTagsOnly();
    Flux<Questions> findByTagsIn(List<String> tags, Pageable pageable);
    @Query("{ '_id': ?0 }")
    @Update("{ '$pull': { 'tags': ?1 } }")
    Mono<Long> removeTagById(String id, String tag); //If tag exists → it removes , If not → nothing happens
}
