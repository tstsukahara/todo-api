package com.example.todoapi.service.sample;

import com.example.todoapi.repository.sample.SampleRecord;
import com.example.todoapi.repository.sample.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SampleService {

    private final SampleRepository repository;

    public SampleEntity find() {
        SampleRecord record = repository.select();
        return new SampleEntity(record.getContent());
    }
}
