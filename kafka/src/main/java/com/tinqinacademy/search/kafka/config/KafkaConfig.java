package com.tinqinacademy.search.kafka.config;

import com.tinqinacademy.search.kafka.model.DeleteMessage;
import com.tinqinacademy.search.kafka.model.WordMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${kafka.server}")
    private String kafkaServer;

    @Bean
    public NewTopic wordsTopic() {
        return TopicBuilder
                .name("words")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic deleteTopic() {
        return TopicBuilder
                .name("delete")
                .partitions(10)
                .replicas(1)
                .build();
    }

    private Map<String, Object> createConsumerConfigs() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.tinqinacademy.search.kafka");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return config;
    }

    @Bean
    public ConsumerFactory<String, WordMessage> wordsConsumerFactory() {
        Map<String, Object> config = createConsumerConfigs();
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(WordMessage.class)));
    }

    @Bean
    public ConsumerFactory<String, DeleteMessage> deleteConsumerFactory() {
        Map<String, Object> config = createConsumerConfigs();
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(DeleteMessage.class)));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WordMessage> kafkaWordsListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, WordMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(wordsConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeleteMessage> kafkaDeleteListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeleteMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deleteConsumerFactory());
        return factory;
    }
}
