package com.factory.config.mapper;

import com.factory.message.FlowRate;
import com.factory.message.GasComposition;
import com.factory.message.NoiseAndVibration;
import com.factory.message.Pressure;
import com.factory.message.Temperature;
import com.factory.persistence.data.entity.SensorData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface SensorDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensorType", constant = "pressure")
    @Mapping(target = "auditData.label", source = "source.label")
    @Mapping(target = "auditData.eventKey", source = "eventKey")
    @Mapping(target = "auditData.timestamp", source = "source.timestamp", qualifiedByName = "longToZonedDateTime")
    @Mapping(target = "data", source = "source", qualifiedByName = "pressureToJsonb")
    SensorData pressureToSensorData(Pressure source, String eventKey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensorType", constant = "temperature")
    @Mapping(target = "auditData.label", source = "source.label")
    @Mapping(target = "auditData.eventKey", source = "eventKey")
    @Mapping(target = "auditData.timestamp", source = "source.timestamp", qualifiedByName = "longToZonedDateTime")
    @Mapping(target = "data", source = "source", qualifiedByName = "temperatureToJsonb")
    SensorData temperatureToSensorData(Temperature source, String eventKey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensorType", constant = "flowRate")
    @Mapping(target = "auditData.label", source = "source.label")
    @Mapping(target = "auditData.eventKey", source = "eventKey")
    @Mapping(target = "auditData.timestamp", source = "source.timestamp", qualifiedByName = "longToZonedDateTime")
    @Mapping(target = "data", source = "source", qualifiedByName = "flowRateToJsonb")
    SensorData flowRateToSensorData(FlowRate source, String eventKey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensorType", constant = "gasComposition")
    @Mapping(target = "auditData.label", source = "source.label")
    @Mapping(target = "auditData.eventKey", source = "eventKey")
    @Mapping(target = "auditData.timestamp", source = "source.timestamp", qualifiedByName = "longToZonedDateTime")
    @Mapping(target = "data", source = "source", qualifiedByName = "gasCompositionToJsonb")
    SensorData gasCompositionToSensorData(GasComposition source, String eventKey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sensorType", constant = "compressorState")
    @Mapping(target = "auditData.label", source = "source.label")
    @Mapping(target = "auditData.eventKey", source = "eventKey")
    @Mapping(target = "auditData.timestamp", source = "source.timestamp", qualifiedByName = "longToZonedDateTime")
    @Mapping(target = "data", source = "source", qualifiedByName = "noiseAndVibrationToJsonb")
    SensorData noiseAndVibrationToSensorData(NoiseAndVibration source, String eventKey);

    @Named("longToZonedDateTime")
    default ZonedDateTime longToZonedDateTime(Long timestamp) {
        if (timestamp != null) {
            return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        }
        return null;
    }

    @Named("pressureToJsonb")
    default Map<String, Object> pressureToJsonb(Pressure source) {
        Map<String, Object> data = new HashMap<>();
        data.put("pressure", source.getData().getPressure());
        return data;
    }

    @Named("temperatureToJsonb")
    default Map<String, Object> temperatureToJsonb(Temperature source) {
        Map<String, Object> data = new HashMap<>();
        data.put("temperature", source.getData().getTemperature());
        return data;
    }

    @Named("flowRateToJsonb")
    default Map<String, Object> flowRateToJsonb(FlowRate source) {
        Map<String, Object> data = new HashMap<>();
        data.put("flowRate", source.getData().getFlowRate());
        return data;
    }

    @Named("gasCompositionToJsonb")
    default Map<String, Object> gasCompositionToJsonb(GasComposition source) {
        Map<String, Object> data = new HashMap<>();
        var gasData = source.getData();
        data.put("H2", gasData.getH2());
        data.put("N2", gasData.getN2());
        data.put("NH3", gasData.getNh3());
        data.put("O2", gasData.getO2());
        data.put("CO2", gasData.getCo2());
        return data;
    }

    @Named("noiseAndVibrationToJsonb")
    default Map<String, Object> noiseAndVibrationToJsonb(NoiseAndVibration source) {
        Map<String, Object> data = new HashMap<>();
        data.put("noiseLevel", source.getNoiseData().getLevel());
        data.put("vibrationAmplitude", source.getVibrationData().getAmplitude());
        data.put("vibrationFrequency", source.getVibrationData().getFrequency());
        return data;
    }

    default String map(CharSequence charSequence){
        return charSequence.toString();
    }
}