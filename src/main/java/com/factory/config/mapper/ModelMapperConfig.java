package com.factory.config.mapper;

import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import com.factory.persistence.data.entity.AuditData;
import com.factory.persistence.data.entity.CompressorState;
import com.factory.persistence.data.entity.MeanFlowRate;
import com.factory.persistence.data.entity.MeanGasComposition;
import com.factory.persistence.data.entity.MeanPressure;
import com.factory.persistence.data.entity.MeanTemperature;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        mapper.addConverter(createLongZonedDateTimeConverter());
        mapper.addConverter(createMeanPressurePressureConverter(mapper));
        mapper.addConverter(createTemperatureMeanTemperatureConverter(mapper));
        mapper.addConverter(createFlowRateMeanFlowRateConverter(mapper));
        mapper.addConverter(createGasCompositionMeanGasCompositionConverter(mapper));
        mapper.addConverter(createNoiseAndVibrationCompressorStateConverter(mapper));
        return mapper;
    }

    private static Converter<Long, ZonedDateTime> createLongZonedDateTimeConverter() {
        return new AbstractConverter<>() {
            @Override
            public ZonedDateTime convert(final Long input) {
                if (Objects.nonNull(input)) {
                    Instant instant = Instant.ofEpochSecond(input);
                    return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
                }
                return null;
            }
        };
    }

    private static Converter<Pressure, MeanPressure> createMeanPressurePressureConverter(final ModelMapper modelMapper) {
        return new AbstractConverter<>() {
            @Override
            protected MeanPressure convert(final Pressure source) {
                var result = new MeanPressure();
                var auditData = getAuditData(source.getTimestamp(), source.getLabel().toString(), modelMapper);
                result.setAuditData(auditData);
                result.setValue(Double.valueOf(source.getData().getPressure()));
                return result;
            }
        };
    }

    private static Converter<Temperature, MeanTemperature> createTemperatureMeanTemperatureConverter(final ModelMapper modelMapper) {
        return new AbstractConverter<>() {
            @Override
            protected MeanTemperature convert(final Temperature source) {
                var result = new MeanTemperature();
                var auditData = getAuditData(source.getTimestamp(), source.getLabel().toString(), modelMapper);
                result.setAuditData(auditData);
                result.setValue(Double.valueOf(source.getData().getTemperature()));
                return result;
            }
        };
    }

    private static Converter<FlowRate, MeanFlowRate> createFlowRateMeanFlowRateConverter(final ModelMapper modelMapper) {
        return new AbstractConverter<>() {
            @Override
            protected MeanFlowRate convert(final FlowRate source) {
                var result = new MeanFlowRate();
                var auditData = getAuditData(source.getTimestamp(), source.getLabel().toString(), modelMapper);
                result.setAuditData(auditData);
                result.setValue(Double.valueOf(source.getData().getFlowRate()));
                return result;
            }
        };
    }

    private static Converter<GasComposition, MeanGasComposition> createGasCompositionMeanGasCompositionConverter(final ModelMapper modelMapper) {
        return new AbstractConverter<>() {
            @Override
            protected MeanGasComposition convert(final GasComposition source) {
                var result = new MeanGasComposition();
                var auditData = getAuditData(source.getTimestamp(), source.getLabel().toString(), modelMapper);
                result.setAuditData(auditData);
                var data = source.getData();
                result.setCo2(data.getCo2().doubleValue());
                result.setH2(data.getH2().doubleValue());
                result.setN2(data.getN2().doubleValue());
                result.setNh3(data.getNh3().doubleValue());
                result.setO2(data.getO2().doubleValue());
                return result;
            }
        };
    }

    private static Converter<NoiseAndVibration, CompressorState> createNoiseAndVibrationCompressorStateConverter(final ModelMapper modelMapper) {
        return new AbstractConverter<>() {
            @Override
            protected CompressorState convert(final NoiseAndVibration source) {
                var result = new CompressorState();
                var auditData = getAuditData(source.getTimestamp(), source.getLabel().toString(), modelMapper);
                result.setAuditData(auditData);
                result.setNoiseLevel(source.getNoiseData().getLevel().doubleValue());
                result.setVibrationAmplitude(source.getVibrationData().getAmplitude().doubleValue());
                result.setVibrationFrequency(source.getVibrationData().getFrequency().doubleValue());
                return result;
            }
        };
    }

    private static AuditData getAuditData(final Long timestamp, final String label, final ModelMapper modelMapper) {
        var auditData = new AuditData();
        auditData.setTimestamp(modelMapper.map(timestamp, ZonedDateTime.class));
        auditData.setLabel(label);
        return auditData;
    }
}
