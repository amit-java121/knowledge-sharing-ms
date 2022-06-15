package com.knowledgesharing.ms.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeSharingDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private Long likes;
    private Long views;

    @NotNull
    private String link;

    private String date;
}
