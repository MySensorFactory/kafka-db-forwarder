package com.factory.service;

import com.factory.config.kafka.buffer.BufferedEntry;
import com.factory.message.GasComposition;
import com.factory.persistence.data.entity.MeanGasComposition;
import com.factory.persistence.data.repository.MeanGasCompositionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GasCompositionMeanService {

    private final MeanGasCompositionRepository meanGasCompositionRepository;
    private final ModelMapper modelMapper;

    public void saveMean(final Set<BufferedEntry<GasComposition>> batch) {
        var result = batch.stream().map(e -> {
                            var mean = modelMapper.map(e.getEntry(), MeanGasComposition.class);
                            mean.getAuditData().setEventKey(e.getKey());
                            return mean;
                        }
                )
                .toList();
        meanGasCompositionRepository.saveAll(result);
    }
}
