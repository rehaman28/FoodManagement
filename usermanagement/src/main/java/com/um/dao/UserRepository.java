package com.um.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
}