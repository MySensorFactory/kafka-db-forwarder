package com.factory.service;

import com.factory.config.kafka.buffer.BufferedEntry;
import com.factory.message.Pressure;
import com.factory.persistence.data.entity.MeanPressure;
import com.factory.persistence.data.repository.MeanPressureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PressureMeanService {

    private final MeanPressureRepository meanPressureRepository;
    private final ModelMapper modelMapper;

    public void saveMean(final Set<BufferedEntry<Pressure>> batch) {
        var result = batch.stream().map(e -> {
                            var mean = modelMapper.map(e.getEntry(), MeanPressure.class);
                            mean.getAuditData().setEventKey(e.getKey());
                            return mean;
                        }
                )
                .toList();
        meanPressureRepository.saveAll(result);
    }
}
