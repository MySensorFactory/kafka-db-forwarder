package com.factory.config.kafka;

import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import com.factory.service.CompressorStateService;
import com.factory.service.FlowRateMeanService;
import com.factory.service.GasCompositionMeanService;
import com.factory.service.PressureMeanService;
import com.factory.service.TemperatureMeanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@EnableKafka
@Slf4j
@RequiredArgsConstructor
public class FactoryKafkaConfiguration {

    private final FlowRateMeanService flowRateMeanService;
    private final TemperatureMeanService temperatureMeanService;
    private final PressureMeanService pressureMeanService;
    private final GasCompositionMeanService gasCompositionMeanService;
    private final CompressorStateService compressorStateService;

    @ConditionalOnProperty(
            value = "kafka.config.pressure.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.pressure.listenerId}",
            topics = "#{'${kafka.config.pressure.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.pressure.auto-start:true}",
            concurrency = "${kafka.config.pressure.concurrency:3}",
            groupId = "${kafka.config.pressure.groupId}")
    public void listen(@Payload final Pressure data, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        debug(data);
        pressureMeanService.saveMean(data, key);
    }


    @ConditionalOnProperty(
            value = "kafka.config.temperature.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.temperature.listenerId}",
            topics = "#{'${kafka.config.temperature.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.temperature.auto-start:true}",
            concurrency = "${kafka.config.temperature.concurrency:3}",
            groupId = "${kafka.config.temperature.groupId}")
    public void listen(@Payload final Temperature data, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        debug(data);
        temperatureMeanService.saveMean(data, key);
    }

    @ConditionalOnProperty(
            value = "kafka.config.flowRate.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.flowRate.listenerId}",
            topics = "#{'${kafka.config.flowRate.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.flowRate.auto-start:true}",
            concurrency = "${kafka.config.flowRate.concurrency:3}",
            groupId = "${kafka.config.flowRate.groupId}")
    public void listen(@Payload final FlowRate data, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        debug(data);
        flowRateMeanService.saveMean(data, key);
    }

    @ConditionalOnProperty(
            value = "kafka.config.gasComposition.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.gasComposition.listenerId}",
            topics = "#{'${kafka.config.gasComposition.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.gasComposition.auto-start:true}",
            concurrency = "${kafka.config.gasComposition.concurrency:3}",
            groupId = "${kafka.config.gasComposition.groupId}")
    public void listen(@Payload final GasComposition data, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        debug(data);
        gasCompositionMeanService.saveMean(data, key);
    }

    @ConditionalOnProperty(
            value = "kafka.config.compressorState.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.compressorState.listenerId}",
            topics = "#{'${kafka.config.compressorState.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.compressorState.auto-start:true}",
            concurrency = "${kafka.config.compressorState.concurrency:3}",
            groupId = "${kafka.config.compressorState.groupId}")
    public void listen(@Payload final NoiseAndVibration data, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        debug(data);
        compressorStateService.saveCompressorState(data, key);
    }

    private <T> void debug(final T data) {
        log.debug("Saving result: {}", data);
    }
}
