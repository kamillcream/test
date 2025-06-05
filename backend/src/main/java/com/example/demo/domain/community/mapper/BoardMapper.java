package com.example.demo.domain.community.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.community.entity.*;
import java.util.*;

@Mapper
public interface BoardMapper {
     Board findByIdBoard(@Param("boardSq") Long boardSq, @Param("boardTypeCd") Long boardTypeCd);
     List<Board> findAll(@Param("boardTypeCd") Long boardTypeCd,
        @Param("boardAdoptStatusCd") Long boardAdoptStatusCd,
        @Param("searchType") String searchType,
        @Param("keyword") String keyword,
        @Param("skillTags") List<Long> skillTags,
        @Param("sortType") String sortType,
        @Param("size") Long size,
        @Param("offset") Long offset);
     Long findAllCnt(@Param("boardTypeCd") Long boardTypeCd,
    	        @Param("boardAdoptStatusCd") Long boardAdoptStatusCd,
    	        @Param("searchType") String searchType,
    	        @Param("keyword") String keyword,
    	        @Param("skillTags") List<Long> skillTags,
    	        @Param("sortType") String sortType,
    	        @Param("size") Long size,
    	        @Param("offset") Long offset);
     void insert(Board board);
     void update(Board board);
     void delete(@Param("userSq") Long userSq, @Param("boardSq") Long boardSq);
     void addViewCnt(@Param("boardSq") Long boardSq);
     void updateCommentCnt(@Param("boardSq") Long boardSq);
     void updateRecommendCnt(@Param("boardSq") Long boardSq);
     
}