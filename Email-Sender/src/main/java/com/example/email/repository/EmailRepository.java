package com.example.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.email.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
