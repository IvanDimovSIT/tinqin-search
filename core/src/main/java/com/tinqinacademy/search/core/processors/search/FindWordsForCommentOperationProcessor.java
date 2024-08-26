package com.tinqinacademy.search.core.processors.search;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOutput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentInput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOperation;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOutput;
import com.tinqinacademy.search.core.errors.ErrorMapper;
import com.tinqinacademy.search.core.processors.BaseOperationProcessor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FindWordsForCommentOperationProcessor extends BaseOperationProcessor
        implements FindWordsForCommentOperation {

    public FindWordsForCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                                 Validator validator) {
        super(conversionService, errorMapper, validator);
    }

    @Override
    public Either<Errors, FindWordsForCommentOutput> process(FindWordsForCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    //log.info("End process result:{}", result);
                    return result;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
