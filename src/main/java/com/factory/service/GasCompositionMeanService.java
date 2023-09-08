package com.factory.service;

import com.factory.message.GasComposition;
import com.factory.persistence.entity.MeanGasComposition;
import com.factory.persistence.repository.MeanGasCompositionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GasCompositionMeanService {

    private final MeanGasCompositionRepository meanGasCompositionRepository;
    private final ModelMapper modelMapper;

    public MeanGasComposition saveMean(final GasComposition data, final String eventKey){
        var result = modelMapper.map(data, MeanGasComposition.class);
        result.getAuditData().setEventKey(eventKey);
        return meanGasCompositionRepository.save(result);
    }
}
