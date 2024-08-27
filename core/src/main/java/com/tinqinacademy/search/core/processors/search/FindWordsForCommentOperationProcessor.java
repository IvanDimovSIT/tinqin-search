package com.tinqinacademy.search.core.processors.search;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentInput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOperation;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOutput;
import com.tinqinacademy.search.core.errors.ErrorMapper;
import com.tinqinacademy.search.core.processors.BaseOperationProcessor;
import com.tinqinacademy.search.persistence.model.SearchWord;
import com.tinqinacademy.search.persistence.repository.SearchWordRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FindWordsForCommentOperationProcessor extends BaseOperationProcessor
        implements FindWordsForCommentOperation {
    private final SearchWordRepository searchWordRepository;

    public FindWordsForCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                                 Validator validator, SearchWordRepository searchWordRepository) {
        super(conversionService, errorMapper, validator);
        this.searchWordRepository = searchWordRepository;
    }

    private Set<String> findWordsForComment(UUID commentId) {
        return searchWordRepository
                .findByCommentId(commentId)
                .stream()
                .map(SearchWord::getWord)
                .collect(Collectors.toSet());
    }

    @Override
    public Either<Errors, FindWordsForCommentOutput> process(FindWordsForCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    Set<String> words = findWordsForComment(UUID.fromString(input.getCommentId()));

                    FindWordsForCommentOutput result = FindWordsForCommentOutput.builder()
                            .words(words)
                            .build();

                    log.info("End process result:{}", result);
                    return result;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
