package com.luv2code.emailator.mock;

import com.luv2code.emailator.entity.EmailMessage;
import com.luv2code.emailator.entity.enums.ImportanceType;

import java.time.Clock;
import java.time.LocalDateTime;

public class EmailMessageMock {

    public static EmailMessage createEmailMessage(final Long id, final String from, final String to,
                                                  final String cc, final String subject, final ImportanceType importance,
                                                  final String content) {
        return EmailMessage.builder()
                .id(id)
                .from(from)
                .to(to)
                .cc(cc)
                .subject(subject)
                .importance(importance)
                .content(content)
                .build();
    }
}
