package com.tinqinacademy.search.core.processors.search;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordInput;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOperation;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOutput;
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

import java.util.UUID;

@Service
@Slf4j
public class AddSearchWordOperationProcessor extends BaseOperationProcessor implements AddSearchWordOperation {
    private final SearchWordRepository searchWordRepository;

    public AddSearchWordOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                           Validator validator, SearchWordRepository searchWordRepository) {
        super(conversionService, errorMapper, validator);
        this.searchWordRepository = searchWordRepository;
    }

    void addSearchWord(AddSearchWordInput input) {
        SearchWord searchWord = SearchWord.builder()
                .id(UUID.randomUUID())
                .word(input.getWord().toUpperCase())
                .commentId(UUID.fromString(input.getCommentId()))
                .build();

        SearchWord savedSearchWord = searchWordRepository.save(searchWord);
        log.info("Saved SearchWord: {}", savedSearchWord);
    }

    @Override
    public Either<Errors, AddSearchWordOutput> process(AddSearchWordInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    addSearchWord(input);

                    AddSearchWordOutput result = AddSearchWordOutput.builder()
                            .build();
                    log.info("End process result:{}", result);
                    return result;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
