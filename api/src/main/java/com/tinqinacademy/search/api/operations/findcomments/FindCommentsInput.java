package com.tinqinacademy.search.api.operations.findcomments;

import com.tinqinacademy.search.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class FindCommentsInput implements OperationInput {
    @NotBlank
    private String word;
}
