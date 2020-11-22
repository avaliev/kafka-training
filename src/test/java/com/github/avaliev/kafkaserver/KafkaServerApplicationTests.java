package com.github.avaliev.kafkaserver;

import com.github.avaliev.kafkaserver.dto.InfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaServerApplicationTests {


    @Autowired
    WorkerService workerService;

    @Test
    void contextLoads() {
        InfoDto dto=new InfoDto(1,"airat Hello!");
        workerService.send(dto);
    }

}
