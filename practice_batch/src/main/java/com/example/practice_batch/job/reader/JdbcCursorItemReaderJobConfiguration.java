//package com.example.practice_batch.job;
//
//import com.example.practice_batch.entity.Pay;
//import lombok.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import javax.sql.DataSource;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class JdbcCursorItemReaderJobConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final DataSource dataSource; // DataSource DI
//
//    private static final int chunkSize = 10;
//
//    @Bean
//    public Job jdbcCursorItemReaderJob() {
//        return jobBuilderFactory.get("jdbcCursorItemReaderJob")
//                .start(jdbcCursorItemReaderStep())
//                .build();
//    }
//
//    @Bean
//    public Step jdbcCursorItemReaderStep() {
//        //전자의 Pay는 반환 타입, 후자는 매개 파라미터 타입
//        return stepBuilderFactory.get("jdbcCursorItemReaderStep")
//                .<Pay, Pay>chunk(chunkSize)
//                .reader(jdbcCursorItemReader())
//                .writer(jdbcCursorItemWriter())
//                .build();
//    }
//
//    @Bean
//    public JdbcCursorItemReader<Pay> jdbcCursorItemReader() {
//        log.info("jdbcCursorItemReader start ");
//        return new JdbcCursorItemReaderBuilder<Pay>()
//                .fetchSize(chunkSize)
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
//                .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
//                .name("jdbcCursorItemReader")
//                .build();
//    }
//
//    //처리할게 없으면 Process 필없음.
//    private ItemWriter<Pay> jdbcCursorItemWriter() {
//        log.info("jdbcCursorItemWriter start ");
//        return list -> {
//            for (Pay pay: list) {
//                log.info("Current Pay={}", pay);
//            }
//        };
//    }
//}