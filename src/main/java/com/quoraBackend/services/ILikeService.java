package com.quoraBackend.services;

import com.quoraBackend.dto.LikeRequestDTO;
import com.quoraBackend.dto.LikeResponseDTO;
import reactor.core.publisher.Mono;

public interface ILikeService {
    Mono<LikeResponseDTO> createLike(LikeRequestDTO likeRequestDTO);

    Mono<LikeResponseDTO> CountLikesByTargetIdAndTargetType(String targetId , String targetType);

    Mono<LikeResponseDTO> CountDisLikesByTargetIdAndTargetType(String targetId , String targetType);

    Mono<LikeResponseDTO> toggleLike(String targetId , String targetType , Boolean isLike);

}
