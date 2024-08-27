package com.tinqinacademy.search.api.operations.findwordsforcomment;

import com.tinqinacademy.search.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class FindWordsForCommentInput implements OperationInput {
    @UUID
    @NotNull
    private String commentId;
}
