package com.quoraBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDTO {
    private String id;

    private String questionId;

    private String content;

    private String authorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
