package com.um.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.um.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	boolean existsByEmail(String email);

	boolean existsByUserPhone(String userPhone);

	Optional<User> findByEmail(String email);
}
