package com.luv2code.emailator.service.impl;

import com.luv2code.emailator.entity.EmailMessage;
import com.luv2code.emailator.repository.EmailMessageRepository;
import com.luv2code.emailator.service.EmailMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailMessageServiceImpl implements EmailMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageServiceImpl.class);

    private final Clock clock;

    private final EmailMessageRepository emailMessageRepository;

    @Autowired
    public EmailMessageServiceImpl(final Clock clock, final EmailMessageRepository emailMessageRepository) {
        this.clock = clock;
        this.emailMessageRepository = emailMessageRepository;
    }

    @Override
    @Transactional
    public void send(final EmailMessage emailMessage) {
        LOGGER.debug("Start process of sending email");
        emailMessage.setSendAt(LocalDateTime.now(clock));
        emailMessageRepository.save(emailMessage);
    }

    @Override
    public EmailMessage findById(final Long id) {
        return emailMessageRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find EmailMessage by given id. [id={}]", id);
                    return new EntityNotFoundException(
                            String.format("Cannot find EmailMessage by given id. [id=%s]", id)
                    );
                });
    }

    @Override
    public List<EmailMessage> findAll() {
        LOGGER.debug("Start process of searching email messages in database.");
        return emailMessageRepository.findAll();
    }
}
