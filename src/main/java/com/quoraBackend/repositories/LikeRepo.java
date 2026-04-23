package com.quoraBackend.repositories;

import com.quoraBackend.models.Like;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends ReactiveMongoRepository<Like ,String> {
}
