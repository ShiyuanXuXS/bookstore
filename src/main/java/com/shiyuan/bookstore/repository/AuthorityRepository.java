package com.shiyuan.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiyuan.bookstore.entity.Authority;
import com.shiyuan.bookstore.entity.AuthorityPK;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {

}
