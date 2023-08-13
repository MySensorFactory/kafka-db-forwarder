package com.factory.kafka.config;

import com.factory.persistence.entity.CountedWords;
import com.factory.persistence.repository.CountedWordsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.ZonedDateTime;

@Configuration
@EnableKafka
@Slf4j
@RequiredArgsConstructor
public class FactoryKafkaConfiguration {

    private final CountedWordsRepository countedWordsRepository;

    @Bean
    @ConfigurationProperties("kafka")
    public KafkaConfig kafkaConfig() {
        return new KafkaConfig();
    }

    @KafkaListener(id = "${kafka.listenerId}",
            topics = "${kafka.resultTopic}",
            autoStartup = "${listen.auto.start:true}",
            concurrency = "${listen.concurrency:3}")
    public void listen(Long data) {
        log.info("Saving result: {}", data);
        var result = new CountedWords();
        result.setLettersCount(data);
        result.setTimestamp(ZonedDateTime.now());
        countedWordsRepository.save(result);
    }
}
