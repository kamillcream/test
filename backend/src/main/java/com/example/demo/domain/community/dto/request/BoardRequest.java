package com.example.demo.domain.community.dto.request;

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
	
}