package com.tinqinacademy.search.core.errors;


import com.tinqinacademy.search.api.errors.Errors;

public interface ErrorMapper {
    Errors map(Throwable throwable);
}
