package com.quoraBackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 10 , max = 100, message = "Title must be between 10 and 100 characters!!") //@Size annotation is used to validate the length of a String or the number of elements in a Collection, Map, or Array
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10 , max = 1000, message = "Content must be between 10 and 100 characters!!") //@Size annotation is used to validate the length of a String or the number of elements in a Collection, Map, or Array
    private String content;
}
