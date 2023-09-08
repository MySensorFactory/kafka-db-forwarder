package com.factory.service;

import com.factory.message.NoiseAndVibration;
import com.factory.persistence.entity.CompressorState;
import com.factory.persistence.repository.CompressorStateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompressorStateService {

    private final CompressorStateRepository compressorStateRepository;
    private final ModelMapper modelMapper;

    public CompressorState saveCompressorState(final NoiseAndVibration data, final String eventKey) {
        var compressorState = modelMapper.map(data, CompressorState.class);
        compressorState.getAuditData().setEventKey(eventKey);
        return compressorStateRepository.save(compressorState);
    }
}
