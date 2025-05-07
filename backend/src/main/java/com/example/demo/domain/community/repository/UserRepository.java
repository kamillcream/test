package com.example.demo.domain.community.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.community.entity.User;
import com.example.demo.domain.community.mapper.UserMapper;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserMapper {
}
