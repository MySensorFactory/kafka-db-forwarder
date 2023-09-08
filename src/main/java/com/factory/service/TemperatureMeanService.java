package com.factory.service;

import com.factory.message.Temperature;
import com.factory.persistence.entity.MeanTemperature;
import com.factory.persistence.repository.MeanTemperatureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureMeanService {

    private final MeanTemperatureRepository meanTemperatureRepository;
    private final ModelMapper modelMapper;

    public MeanTemperature saveMean(final Temperature data, final String eventKey){
        var result = modelMapper.map(data, MeanTemperature.class);
        result.getAuditData().setEventKey(eventKey);
        return meanTemperatureRepository.save(result);
    }
}
