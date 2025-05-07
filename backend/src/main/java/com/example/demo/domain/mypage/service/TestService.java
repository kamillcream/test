package com.example.demo.domain.mypage.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mypage.dto.request.RegisterRequest;
import com.example.demo.domain.mypage.entity.User;
import com.example.demo.domain.mypage.mapper.UserMapper;
import com.example.demo.domain.mypage.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TestService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public User register(RegisterRequest registerRequest){
        return userRepository.save(new User(registerRequest.getUserId(), registerRequest.getPassword()));
    }

    @Transactional
    public int updateUser(RegisterRequest registerRequest, int id) {
        User user = findUserByIdOrElseThrow(id);

        user.setUserId(registerRequest.getUserId());
        user.setPassword(registerRequest.getPassword());

        return userMapper.updateUser(user);
    }

    public User findUserByIdOrElseThrow(int id){
        return userRepository.findById(id).orElseThrow();
    }


}
