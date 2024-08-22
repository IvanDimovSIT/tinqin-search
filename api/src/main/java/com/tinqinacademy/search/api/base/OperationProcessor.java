package com.tinqinacademy.search.api.base;


import com.tinqinacademy.search.api.errors.Errors;
import io.vavr.control.Either;

public interface OperationProcessor<I extends OperationInput, O extends OperationOutput> {
    Either<Errors, O> process(I input);
}
