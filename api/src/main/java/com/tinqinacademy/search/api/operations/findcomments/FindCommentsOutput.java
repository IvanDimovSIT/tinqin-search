package com.tinqinacademy.search.api.operations.findcomments;

import com.tinqinacademy.search.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class FindCommentsOutput implements OperationOutput {
    private List<String> commentIds;
}
