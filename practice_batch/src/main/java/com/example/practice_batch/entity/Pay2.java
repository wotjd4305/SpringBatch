package com.example.practice_batch.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pay2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private Long successStatus;

    public Pay2(Long amount, Long successStatus) {
        this.amount = amount;
        this.successStatus = successStatus;
    }

    public void success(){
        this.successStatus = 1L;
    }
}