package com.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.train.model.User;

public interface AdminRepository extends JpaRepository<User, Integer>{

}
