package com.ty.presentationApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.presentationApp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);

}
