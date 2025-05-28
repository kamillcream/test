package com.example.demo.domain.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.domain.mypage.dto.AddressDTO;

@Mapper
public interface MypageAddressMapper {
    Long selectAreaCodeSqBySigungu(String sigungu);
    void insertAddress(AddressDTO addressDTO);  
    
}
