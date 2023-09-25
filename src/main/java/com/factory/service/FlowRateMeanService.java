package com.factory.service;

import com.factory.config.kafka.buffer.BufferedEntry;
import com.factory.message.FlowRate;
import com.factory.persistence.data.entity.MeanFlowRate;
import com.factory.persistence.data.repository.MeanFlowRateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FlowRateMeanService {

    private final MeanFlowRateRepository meanFlowRateRepository;
    private final ModelMapper modelMapper;

    public void saveMean(final Set<BufferedEntry<FlowRate>> batch) {
        var result = batch.stream().map(e -> {
                            var mean = modelMapper.map(e.getEntry(), MeanFlowRate.class);
                            mean.getAuditData().setEventKey(e.getKey());
                            return mean;
                        }
                )
                .toList();
        meanFlowRateRepository.saveAll(result);
    }
}
