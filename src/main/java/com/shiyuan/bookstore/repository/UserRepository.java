package com.shiyuan.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiyuan.bookstore.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
