package com.example.demo.domain.mypage.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.mapper.MypageAddressMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressRepository {
    private final MypageAddressMapper addressMapper;
    
    public void insertAddress(com.example.demo.domain.mypage.dto.AddressDTO addressDTO) {
        addressMapper.insertAddress(addressDTO);
    }
}

