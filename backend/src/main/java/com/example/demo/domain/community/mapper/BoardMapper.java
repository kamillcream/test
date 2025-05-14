package com.example.demo.domain.community.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.community.entity.*;
import java.util.*;

@Mapper
public interface BoardMapper {
     Board findByIdBoard(@Param("boardSq") Long boardSq, @Param("boardTypeCd") Long boardTypeCd);
     List<Board> findAll(Long boardTypeCd);
     void insert(Board board);
     void update(Board board);
     void delete(Long boardSq);
     void addViewCnt(Long boardSq);
     
}
