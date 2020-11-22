package com.github.avaliev.kafkaserver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avaliev.kafkaserver.dto.InfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkerService {

    private final KafkaTemplate<Long, InfoDto> kafkaStarshipTemplate;
    private final ObjectMapper objectMapper;



    @Autowired
    public WorkerService(KafkaTemplate<Long, InfoDto> kafkaStarshipTemplate, ObjectMapper objectMapper) {
        this.kafkaStarshipTemplate = kafkaStarshipTemplate;
        this.objectMapper = objectMapper;
    }


    public void send(InfoDto dto) {
        kafkaStarshipTemplate.send("server.airat", dto);
    }


    @KafkaListener(id = "airat", topics = {"server.airat"}, containerFactory = "singleFactory")
    public void consume(InfoDto dto) {
        log.info("=> consumed {}", writeValueAsString(dto));
    }

    private String writeValueAsString(InfoDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
