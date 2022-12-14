package com.luv2code.emailator.repository;

import com.luv2code.emailator.entity.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long> {

    List<EmailMessage> findAllByOrderBySendAtDesc();

}
