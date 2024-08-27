package com.tinqinacademy.search.kafka;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordInput;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOperation;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOutput;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordInput;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordOperation;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordOutput;
import com.tinqinacademy.search.kafka.model.DeleteMessage;
import com.tinqinacademy.search.kafka.model.WordMessage;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final AddSearchWordOperation addSearchWordOperation;
    private final DeleteWordOperation deleteWordOperation;

    @KafkaListener(id = "wordsConsumerId", topics = "words", containerFactory = "kafkaWordsListenerContainerFactory")
    public void listenWords(WordMessage message) {
        AddSearchWordInput input = AddSearchWordInput.builder()
                .commentId(message.getId())
                .word(message.getWord())
                .build();

        Either<Errors, AddSearchWordOutput> result = addSearchWordOperation.process(input);
        if (result.isLeft()) {
            log.error(result.getLeft().toString());
        }
    }

    @KafkaListener(id = "deleteConsumerId", topics = "delete", containerFactory = "kafkaDeleteListenerContainerFactory")
    public void listenDelete(DeleteMessage message) {
        DeleteWordInput input = DeleteWordInput.builder()
                .commentId(message.getId())
                .word(message.getWord())
                .build();

        Either<Errors, DeleteWordOutput> result = deleteWordOperation.process(input);
        if (result.isLeft()) {
            log.error(result.getLeft().toString());
        }
    }
}
