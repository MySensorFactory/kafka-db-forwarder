package com.factory.config.kafka;

import com.factory.config.kafka.buffer.MessageBuffer;
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

import java.util.Collection;

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

    private final MessageBuffer<Pressure> pressureMessageBuffer;
    private final MessageBuffer<Temperature> temperatureMessageBuffer;
    private final MessageBuffer<FlowRate> flowRateMessageBuffer;
    private final MessageBuffer<GasComposition> gasCompositionMessageBuffer;
    private final MessageBuffer<NoiseAndVibration> noiseAndVibrationMessageBuffer;


    @ConditionalOnProperty(
            value = "kafka.config.pressure.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.pressure.listenerId}",
            topics = "#{'${kafka.config.pressure.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.pressure.auto-start:true}",
            concurrency = "${kafka.config.pressure.concurrency:3}",
            groupId = "${kafka.config.pressure.groupId}")
    public void listen(@Payload final Pressure message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        pressureMessageBuffer.addMessage(key, message);
        if (pressureMessageBuffer.isFull()) {
            var batch = pressureMessageBuffer.extractBatch();
            debug(batch);
            pressureMeanService.saveMean(batch);
        }
    }


    @ConditionalOnProperty(
            value = "kafka.config.temperature.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.temperature.listenerId}",
            topics = "#{'${kafka.config.temperature.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.temperature.auto-start:true}",
            concurrency = "${kafka.config.temperature.concurrency:3}",
            groupId = "${kafka.config.temperature.groupId}")
    public void listen(@Payload final Temperature message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        temperatureMessageBuffer.addMessage(key, message);
        if (temperatureMessageBuffer.isFull()) {
            var batch = temperatureMessageBuffer.extractBatch();
            debug(batch);
            temperatureMeanService.saveMean(batch);
        }
    }

    @ConditionalOnProperty(
            value = "kafka.config.flowRate.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.flowRate.listenerId}",
            topics = "#{'${kafka.config.flowRate.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.flowRate.auto-start:true}",
            concurrency = "${kafka.config.flowRate.concurrency:3}",
            groupId = "${kafka.config.flowRate.groupId}")
    public void listen(@Payload final FlowRate message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        flowRateMessageBuffer.addMessage(key, message);
        if (flowRateMessageBuffer.isFull()) {
            var batch = flowRateMessageBuffer.extractBatch();
            debug(batch);
            flowRateMeanService.saveMean(batch);
        }
    }

    @ConditionalOnProperty(
            value = "kafka.config.gasComposition.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.gasComposition.listenerId}",
            topics = "#{'${kafka.config.gasComposition.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.gasComposition.auto-start:true}",
            concurrency = "${kafka.config.gasComposition.concurrency:3}",
            groupId = "${kafka.config.gasComposition.groupId}")
    public void listen(@Payload final GasComposition message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        gasCompositionMessageBuffer.addMessage(key, message);
        if (gasCompositionMessageBuffer.isFull()) {
            var batch = gasCompositionMessageBuffer.extractBatch();
            debug(batch);
            gasCompositionMeanService.saveMean(batch);
        }
    }

    @ConditionalOnProperty(
            value = "kafka.config.compressorState.enabled",
            havingValue = "true")
    @KafkaListener(id = "${kafka.config.compressorState.listenerId}",
            topics = "#{'${kafka.config.compressorState.inputTopics}'.split(',')}",
            autoStartup = "${kafka.config.compressorState.auto-start:true}",
            concurrency = "${kafka.config.compressorState.concurrency:3}",
            groupId = "${kafka.config.compressorState.groupId}")
    public void listen(@Payload final NoiseAndVibration message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        noiseAndVibrationMessageBuffer.addMessage(key, message);
        if (noiseAndVibrationMessageBuffer.isFull()) {
            var batch = noiseAndVibrationMessageBuffer.extractBatch();
            debug(batch);
            compressorStateService.saveCompressorState(batch);
        }
    }

    private <T> void debug(final Collection<T> data) {
        log.debug("Saving result: {}", data);
    }
}
