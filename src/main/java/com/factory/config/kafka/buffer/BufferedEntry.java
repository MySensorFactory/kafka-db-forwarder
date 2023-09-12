package com.factory.config.kafka.buffer;

import lombok.Builder;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecordBase;

@Builder
@Getter
public class BufferedEntry <T extends SpecificRecordBase>{
    private T entry;
    private String key;
}
