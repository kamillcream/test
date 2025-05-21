package com.example.demo.domain.community.mapper;


import org.apache.ibatis.annotations.*;

import com.example.demo.domain.community.entity.*;

@Mapper
public interface RecommendationMapper {
	Recommendation findByBoardSq(@Param("boardSq") Long bardSq);
	Recommendation findByAnswerSq(@Param("answerSq") Long answerSq);
	Recommendation findByCommentSq(@Param("commentSq") Long commentSq);
	void insert(Recommendation recommendation);
    void delete(@Param("recommendationSq") Long recommendationSq);
    
}