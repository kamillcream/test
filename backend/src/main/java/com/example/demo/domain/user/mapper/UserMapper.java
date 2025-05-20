package com.example.demo.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.user.dto.AddressDTO;
import com.example.demo.domain.user.dto.CompanyProfileDTO;
import com.example.demo.domain.user.dto.UserDTO;

@Mapper
public interface UserMapper {

    Long selectAreaCodeSqBySigungu(String sigungu);

    int insertAddress(AddressDTO addressDTO);

    int insertUser(UserDTO userDTO);

    boolean existsByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserPhoneNum(String userPhoneNum);

    int insertCompanyProfile(CompanyProfileDTO dto);
}
