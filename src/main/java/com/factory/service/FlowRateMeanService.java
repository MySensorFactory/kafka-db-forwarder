package com.factory.service;

import com.factory.message.FlowRate;
import com.factory.persistence.entity.MeanFlowRate;
import com.factory.persistence.repository.MeanFlowRateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowRateMeanService {

    private final MeanFlowRateRepository meanFlowRateRepository;
    private final ModelMapper modelMapper;

    public MeanFlowRate saveMean(final FlowRate data, final String eventKey){
        var result = modelMapper.map(data, MeanFlowRate.class);
        result.getAuditData().setEventKey(eventKey);
        return meanFlowRateRepository.save(result);
    }
}
