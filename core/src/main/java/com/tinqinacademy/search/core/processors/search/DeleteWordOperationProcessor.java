package com.tinqinacademy.search.core.processors.search;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordInput;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordOperation;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordOutput;
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

import java.util.UUID;

@Service
@Slf4j
public class DeleteWordOperationProcessor extends BaseOperationProcessor implements DeleteWordOperation {
    private final SearchWordRepository searchWordRepository;

    public DeleteWordOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                        Validator validator, SearchWordRepository searchWordRepository) {
        super(conversionService, errorMapper, validator);
        this.searchWordRepository = searchWordRepository;
    }

    @Override
    public Either<Errors, DeleteWordOutput> process(DeleteWordInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    UUID commentId = UUID.fromString(input.getCommentId());
                    searchWordRepository.deleteAllByWordAndCommentId(input.getWord(), commentId);

                    DeleteWordOutput result = DeleteWordOutput.builder()
                            .build();

                    log.info("End process result:{}", result);
                    return result;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
