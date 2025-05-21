package com.example.demo.domain.community.dto.request;

import com.example.demo.domain.community.dto.*;

import lombok.*;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardRequest{
    private Long userSq;
    private String boardTtl;
    private String boardDescriptionEdt;
    private Long boardAdoptStatusCd;
    private List<String> normalTags;
    private List<SkillTagDTO> skillTags;
	
}