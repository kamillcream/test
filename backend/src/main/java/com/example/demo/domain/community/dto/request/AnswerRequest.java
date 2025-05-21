package com.example.demo.domain.community.dto.request;

import com.example.demo.domain.community.dto.*;

import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class AnswerRequest{
    private Long userSq;
    private Long boardSq;
    private String answerTtl;
    private String answerDescriptionEdt;
    private List<String> normalTags;
    private List<SkillTagDTO> skillTags;
	
}