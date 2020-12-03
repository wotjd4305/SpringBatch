package com.example.practice_batch.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pay3 {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-DD");

    //Mysql
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    //오라클
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_MY_SEQ")
    @SequenceGenerator(name="S_MY_SEQ", sequenceName = "tmp_seq3", allocationSize = 1)
    private Long id;


    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

    public Pay3(Long amount, String txName, String txDateTime) {
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER);
    }

    public Pay3(Long amount, String txName, LocalDateTime txDateTime) {
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }

    public Pay3(Long id, Long amount, String txName, String txDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER);
    }
}