package com.tinqinacademy.search.api.operations.deleteword;


import com.tinqinacademy.search.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteWordInput implements OperationInput {
    @UUID
    @NotBlank
    private String commentId;
    @NotBlank
    private String word;
}
