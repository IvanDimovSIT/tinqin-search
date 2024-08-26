package com.tinqinacademy.search.api.operations.findwordsforcomment;

import com.tinqinacademy.search.api.base.OperationInput;
import com.tinqinacademy.search.api.base.OperationOutput;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class FindWordsForCommentOutput implements OperationOutput {
    private Set<String> word;
}
