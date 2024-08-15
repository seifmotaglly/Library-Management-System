package com.library.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Token;

public interface TokenDao extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}
