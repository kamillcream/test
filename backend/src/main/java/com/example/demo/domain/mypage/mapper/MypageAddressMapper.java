package com.example.demo.domain.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.mypage.dto.AddressDTO;

@Mapper
public interface MypageAddressMapper {

    // 1. 시도명으로 상위 지역 코드 조회 (parent_code가 NULL인 항목 중에서 찾음)
    Long selectParentAreaCodeBySido(String sido);

    // 2. 시군구명 + 상위 지역 코드로 정확 지역코드 조회
    Long selectAreaCodeBySigunguAndParent(@Param("sigungu") String sigungu,
                                          @Param("parentCode") Long parentCode);

    // 3. 주소 INSERT
    void insertAddress(AddressDTO addressDTO);
}