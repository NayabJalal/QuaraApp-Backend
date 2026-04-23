package com.quoraBackend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "answers")
public class Answer {
    @Id
    private String id;

    @NotBlank(message = "Content is required")
    @Size(min = 10 , max = 100, message = "Title must be between 10 and 100 characters!!") //@Size annotation is used to validate the length of a String or the number of elements in a Collection, Map, or Array
    private String content;

    @Indexed
    private String questionId;

    private String authorId;

    @CreatedDate //automatically captures the timestamp when an entity is first saved, Add @EnableJpaAuditing
    private LocalDateTime createdAt;

    @LastModifiedDate
    //automatically updates a field with the current timestamp whenever the entity is updated in the database.
    private LocalDateTime updatedAt;
}
