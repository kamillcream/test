package com.example.demo.domain.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long normalTagSq;
    private Long boardSq;
    private Long answerSq;
    private String normalTagNm;
    private Integer normalTagTypeCd;
}
