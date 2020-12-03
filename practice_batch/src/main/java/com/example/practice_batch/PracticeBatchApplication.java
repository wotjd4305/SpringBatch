package com.example.practice_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing // 배치 실행 설정
@SpringBootApplication
public class PracticeBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeBatchApplication.class, args);
    }

}
