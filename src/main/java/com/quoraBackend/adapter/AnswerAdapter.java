package com.quoraBackend.adapter;

import com.quoraBackend.dto.AnswerRequestDTO;
import com.quoraBackend.dto.AnswerResponseDTO;
import com.quoraBackend.models.Answer;

import java.time.LocalDateTime;

public class AnswerAdapter {
    public static Answer toEntity(AnswerRequestDTO dto , String questionId){
        return Answer.builder()
                .questionId(questionId)
                .content(dto.getContent())
                .authorId(dto.getAuthorId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    public static AnswerResponseDTO toAnswerResponseDTO(Answer entity){
        return AnswerResponseDTO.builder()
                .id(entity.getId())
                .questionId(entity.getQuestionId())
                .content(entity.getContent())
                .authorId(entity.getAuthorId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
