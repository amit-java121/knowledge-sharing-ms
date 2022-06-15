package com.knowledgesharing.ms.datatransfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KnowledgeSharingDto {

    private Long id;

    @NotBlank(message = "the field \"title\" is needed!")
    private String title;

    @NotBlank(message = "the field \"author\" is needed!")
    private String author;

    private Long likes;
    private Long views;

    @NotBlank(message = "the field \"link\" is needed!")
    private String link;

    private String date;
}
