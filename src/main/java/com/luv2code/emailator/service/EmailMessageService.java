package com.luv2code.emailator.service;

import com.luv2code.emailator.entity.EmailMessage;

import java.util.List;

public interface EmailMessageService {

    void send(final EmailMessage emailMessage);

    EmailMessage findById(final Long id);

    List<EmailMessage> findAll();

}
