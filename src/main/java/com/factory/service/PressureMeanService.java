package com.factory.service;

import com.factory.message.Pressure;
import com.factory.persistence.entity.MeanPressure;
import com.factory.persistence.repository.MeanPressureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PressureMeanService {

    private final MeanPressureRepository meanPressureRepository;
    private final ModelMapper modelMapper;

    public MeanPressure saveMean(final Pressure data, final String eventKey){
        var result = modelMapper.map(data, MeanPressure.class);
        result.getAuditData().setEventKey(eventKey);
        return meanPressureRepository.save(result);
    }
}
