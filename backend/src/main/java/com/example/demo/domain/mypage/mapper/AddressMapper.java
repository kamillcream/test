package com.example.demo.domain.address.mapper;

import com.example.demo.domain.address.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
    Long selectAreaCodeSqBySigungu(String sigungu);
    void insertAddress(AddressDTO addressDTO);
}
