package com.factory.service;

import com.factory.config.kafka.buffer.BufferedEntry;
import com.factory.config.mapper.SensorDataMapper;
import com.factory.message.*;
import com.factory.persistence.data.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SensorDataService {
    private final SensorDataRepository sensorDataRepository;
    private final SensorDataMapper sensorDataMapper;

    public void savePressureData(Set<BufferedEntry<Pressure>> batch) {
        var sensorDataList = batch.stream()
                .map(entry -> sensorDataMapper.pressureToSensorData(entry.getEntry(), entry.getKey()))
                .toList();
        sensorDataRepository.saveAll(sensorDataList);
    }

    public void saveTemperatureData(Set<BufferedEntry<Temperature>> batch) {
        var sensorDataList = batch.stream()
                .map(entry -> sensorDataMapper.temperatureToSensorData(entry.getEntry(), entry.getKey()))
                .toList();
        sensorDataRepository.saveAll(sensorDataList);
    }

    public void saveFlowRateData(Set<BufferedEntry<FlowRate>> batch) {
        var sensorDataList = batch.stream()
                .map(entry -> sensorDataMapper.flowRateToSensorData(entry.getEntry(), entry.getKey()))
                .toList();
        sensorDataRepository.saveAll(sensorDataList);
    }

    public void saveGasCompositionData(Set<BufferedEntry<GasComposition>> batch) {
        var sensorDataList = batch.stream()
                .map(entry -> sensorDataMapper.gasCompositionToSensorData(entry.getEntry(), entry.getKey()))
                .toList();
        sensorDataRepository.saveAll(sensorDataList);
    }

    public void saveCompressorStateData(Set<BufferedEntry<NoiseAndVibration>> batch) {
        var sensorDataList = batch.stream()
                .map(entry -> sensorDataMapper.noiseAndVibrationToSensorData(entry.getEntry(), entry.getKey()))
                .toList();
        sensorDataRepository.saveAll(sensorDataList);
    }
}