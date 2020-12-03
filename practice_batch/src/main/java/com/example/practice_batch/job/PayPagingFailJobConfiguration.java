package com.example.practice_batch.job;

import com.example.practice_batch.entity.Pay;
import com.example.practice_batch.entity.Pay2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = PayPagingFailJobConfiguration.JOB_NAME)
public class PayPagingFailJobConfiguration {

    public static final String JOB_NAME ="payPagingFailJob";

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    private final int chunkSize = 10;

    @Bean
    public Job payPagingJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(payPagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step payPagingStep() {
        return stepBuilderFactory.get("payPagingStep")
                .<Pay2, Pay2>chunk(chunkSize)
                .reader(payPagingReader())
                .processor(payPagingProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Pay2> payPagingReader() {
        log.info("payPagingReader start ");
        return new JpaPagingItemReaderBuilder<Pay2>()
                .queryString("SELECT p FROM Pay2 p WHERE p.successStatus = 0")
                .pageSize(chunkSize)
                .entityManagerFactory(entityManagerFactory)
                .name("payPagingReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Pay2, Pay2> payPagingProcessor() {

        log.info("payPagingProcessor start ");
        return item -> {
            item.success();
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Pay2> writer() {
        JpaItemWriter<Pay2> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        log.info(writer.toString());
        return writer;
    }
}