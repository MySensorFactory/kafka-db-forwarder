package com.factory.service;

import com.factory.config.kafka.buffer.BufferedEntry;
import com.factory.message.Temperature;
import com.factory.persistence.data.entity.MeanTemperature;
import com.factory.persistence.data.repository.MeanTemperatureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TemperatureMeanService {

    private final MeanTemperatureRepository meanTemperatureRepository;
    private final ModelMapper modelMapper;

    public void saveMean(final Set<BufferedEntry<Temperature>> batch) {
        var result = batch.stream().map(e -> {
                            var mean = modelMapper.map(e.getEntry(), MeanTemperature.class);
                            mean.getAuditData().setEventKey(e.getKey());
                            return mean;
                        }
                )
                .toList();
        meanTemperatureRepository.saveAll(result);
    }
}
