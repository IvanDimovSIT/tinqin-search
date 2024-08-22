package com.tinqinacademy.search.persistence.model;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Document("search_word")
public class SearchWord {
    @Id
    private UUID id;
    private UUID commentId;
    private String word;
}
