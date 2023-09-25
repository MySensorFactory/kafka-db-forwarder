package com.factory.service;

import com.factory.config.kafka.buffer.BufferedEntry;
import com.factory.message.NoiseAndVibration;
import com.factory.persistence.data.entity.CompressorState;
import com.factory.persistence.data.repository.CompressorStateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompressorStateService {

    private final CompressorStateRepository compressorStateRepository;
    private final ModelMapper modelMapper;

    public void saveCompressorState(final Set<BufferedEntry<NoiseAndVibration>> batch) {
        var result = batch.stream().map(e -> {
                            var mean = modelMapper.map(e.getEntry(), CompressorState.class);
                            mean.getAuditData().setEventKey(e.getKey());
                            return mean;
                        }
                )
                .toList();
        compressorStateRepository.saveAll(result);
    }
}
