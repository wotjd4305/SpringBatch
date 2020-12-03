package com.example.practice_batch;

import com.example.practice_batch.entity.Pay2;
import com.example.practice_batch.job.PayPagingJobConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jojoldu@gmail.com on 2018. 9. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"job.name=" + PayPagingJobConfiguration.JOB_NAME})
public class PayPagingJobConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PayRepository payRepository;

    @Test
    public void 같은조건을읽고_업데이트할때() throws Exception {
        //given
        for (long i = 0; i < 50; i++) {
            payRepository.save(new Pay2(i, 0L));
        }

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
        assertThat(payRepository.findAllSuccess().size(), is(50));

    }
}