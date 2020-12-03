package com.example.practice_batch.job;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.example.practice_batch.job.PayPagingJobConfiguration.JOB_NAME;


/**
 * Created by jojoldu@gmail.com on 2018. 9. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class PayPagingJobConfiguration {

    public static final String JOB_NAME = "payPagingJob";

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

        JpaPagingItemReader<Pay2> reader = new JpaPagingItemReader<Pay2>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT p FROM Pay2 p WHERE p.successStatus = 0");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("payPagingReader");

        return reader;
    }


    @Bean
    @StepScope
    public ItemProcessor<Pay2, Pay2> payPagingProcessor() {
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
        return writer;
    }

}