package com.example.demo.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.user.dto.AddressDTO;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;

@Mapper
public interface UserMapper {

    Long selectAreaCodeSqBySigungu(String sigungu);

    int insertAddress(AddressDTO addressDTO);

    int insertUser(UserDTO userDTO);

    boolean existsByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserPhoneNum(String userPhoneNum);

    UserDTO findByUserId(@Param("userId") String userId);

    int updateRefreshToken(@Param("userSq") Long userSq, @Param("refreshToken") String refreshToken);

    UserDTO findByRefreshToken(String refreshToken);

    LoginResponseDTO findUserInfoByUserSq(@Param("userSq") Long userSq);

    void deleteRefreshTokenByUserSq(Long userSq);

}