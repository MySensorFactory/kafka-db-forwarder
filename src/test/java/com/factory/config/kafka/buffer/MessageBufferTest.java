package com.factory.config.kafka.buffer;

import com.factory.message.Temperature;
import com.factory.message.TemperatureDataRecord;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageBufferTest {

    public static final String KEY_1 = "key1";
    public static final String KEY_2 = "key2";

    @Test
    void testAddMessage() {
        MessageBuffer<Temperature> buffer = new MessageBuffer<>(3);
        Temperature message = createAvroRecord(1);

        buffer.addMessage(KEY_1, message);

        assertEquals(1, buffer.size());
    }

    @Test
    void testExtractBatch() {
        MessageBuffer<Temperature> buffer = new MessageBuffer<>(3);
        Temperature message1 = createAvroRecord(1);
        Temperature message2 = createAvroRecord(2);

        buffer.addMessage(KEY_1, message1);
        buffer.addMessage(KEY_2, message2);

        Set<BufferedEntry<Temperature>> batch = buffer.extractBatch();

        assertEquals(0, buffer.size());
        assertEquals(2, batch.size());
    }

    @Test
    void testSize() {
        MessageBuffer<Temperature> buffer = new MessageBuffer<>(3);
        Temperature message1 = createAvroRecord(1);
        Temperature message2 = createAvroRecord(2);

        buffer.addMessage(KEY_1, message1);
        buffer.addMessage(KEY_2, message2);

        assertEquals(2, buffer.size());
    }

    @Test
    void testIsFull() {
        MessageBuffer<Temperature> buffer = new MessageBuffer<>(2);
        Temperature message1 = createAvroRecord(1);
        Temperature message2 = createAvroRecord(2);

        buffer.addMessage(KEY_1, message1);
        assertFalse(buffer.isFull());

        buffer.addMessage(KEY_2, message2);
        assertTrue(buffer.isFull());
    }

    private Temperature createAvroRecord(final float value) {
        return Temperature.newBuilder()
                .setData(TemperatureDataRecord.newBuilder()
                        .setTemperature(value)
                        .build())
                .setLabel("Label")
                .setTimestamp(ZonedDateTime.now().toEpochSecond())
                .build();
    }
}
