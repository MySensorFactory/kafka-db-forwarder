package com.factory.config.kafka.buffer;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MessageBuffer <T extends SpecificRecordBase> {
    private final Set<BufferedEntry<T>> buffer;
    private final int capacity;

    public MessageBuffer(final int capacity) {
        this.capacity = capacity;
        this.buffer = Collections.synchronizedSet(new HashSet<>());
    }

    public void addMessage(final String key, final T message){
        buffer.add(new BufferedEntry<>(message,key));
    }

    public Set<BufferedEntry<T>> extractBatch() {
        var result = new HashSet<>(buffer);
        buffer.clear();
        return result;
    }

    public int size(){
        return buffer.size();
    }

    public boolean isFull(){
        return buffer.size() >= capacity;
    }
}
