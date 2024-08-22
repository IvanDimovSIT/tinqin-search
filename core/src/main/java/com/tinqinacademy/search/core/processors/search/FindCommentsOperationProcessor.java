package com.tinqinacademy.search.core.processors.search;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsInput;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOperation;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import com.tinqinacademy.search.core.errors.ErrorMapper;
import com.tinqinacademy.search.core.processors.BaseOperationProcessor;
import com.tinqinacademy.search.persistence.repository.SearchWordRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FindCommentsOperationProcessor extends BaseOperationProcessor implements FindCommentsOperation {
    private final SearchWordRepository searchWordRepository;

    public FindCommentsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                          Validator validator, SearchWordRepository searchWordRepository) {
        super(conversionService, errorMapper, validator);
        this.searchWordRepository = searchWordRepository;
    }

    private List<String> findCommentIdsForWord(String word) {
        return searchWordRepository.findByWord(word.toUpperCase())
                .stream()
                .map(searchWord -> searchWord.getCommentId().toString())
                .toList();
    }

    @Override
    public Either<Errors, FindCommentsOutput> process(FindCommentsInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    List<String> commentIds = findCommentIdsForWord(input.getWord());
                    FindCommentsOutput result = FindCommentsOutput.builder()
                            .commentIds(commentIds)
                            .build();

                    log.info("End process result:{}", result);
                    return result;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
