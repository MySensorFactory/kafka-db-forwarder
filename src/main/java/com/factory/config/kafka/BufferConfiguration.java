package com.factory.config.kafka;

import com.factory.config.kafka.buffer.MessageBuffer;
import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BufferConfiguration {

    @Value("${kafka.config.pressure.bufferSize:10}")
    private Integer pressureBufferSize;

    @Value("${kafka.config.temperature.bufferSize:10}")
    private Integer temperatureBufferSize;

    @Value("${kafka.config.flowRate.bufferSize:10}")
    private Integer flowRateBufferSize;

    @Value("${kafka.config.gasComposition.bufferSize:10}")
    private Integer gasCompositionBufferSize;

    @Value("${kafka.config.compressorState.bufferSize:10}")
    private Integer compressorStateBufferSize;

    @Bean
    public MessageBuffer<Pressure> pressureMessageBuffer() {
        return new MessageBuffer<>(pressureBufferSize);
    }

    @Bean
    public MessageBuffer<Temperature> temperatureMessageBuffer() {
        return new MessageBuffer<>(temperatureBufferSize);
    }

    @Bean
    public MessageBuffer<FlowRate> flowRateMessageBuffer() {
        return new MessageBuffer<>(flowRateBufferSize);
    }

    @Bean
    public MessageBuffer<GasComposition> gasCompositionMessageBuffer() {
        return new MessageBuffer<>(gasCompositionBufferSize);
    }

    @Bean
    public MessageBuffer<NoiseAndVibration> compressorStateMessageBuffer() {
        return new MessageBuffer<>(compressorStateBufferSize);
    }
}
