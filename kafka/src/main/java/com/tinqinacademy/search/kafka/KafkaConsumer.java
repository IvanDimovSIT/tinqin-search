package com.tinqinacademy.search.kafka;

import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.addsearchwords.AddSearchWordInput;
import com.tinqinacademy.search.api.operations.addsearchwords.AddSearchWordOperation;
import com.tinqinacademy.search.api.operations.addsearchwords.AddSearchWordOutput;
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

    @KafkaListener(id = "consumerId", topics = "words", containerFactory = "kafkaListenerContainerFactory")
    public void listen(WordMessage message) {
        AddSearchWordInput input = AddSearchWordInput.builder()
                .commentId(message.getId())
                .word(message.getWord())
                .build();

        Either<Errors, AddSearchWordOutput> result = addSearchWordOperation.process(input);
        if (result.isLeft()) {
            log.error(result.getLeft().toString());
        }
    }
}
