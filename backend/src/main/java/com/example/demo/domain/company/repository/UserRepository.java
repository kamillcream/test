package com.example.demo.domain.company.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.company.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
