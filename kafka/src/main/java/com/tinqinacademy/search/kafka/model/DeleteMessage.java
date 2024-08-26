package com.tinqinacademy.search.kafka.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteMessage {
    private String word;
    private String id;
}
