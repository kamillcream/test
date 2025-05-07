package com.example.demo.domain.community.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.demo.domain.community.dto.request.RegisterRequest;
import com.example.demo.domain.community.entity.User;
import com.example.demo.domain.community.mapper.UserMapper;
import com.example.demo.domain.community.repository.UserRepository;

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
