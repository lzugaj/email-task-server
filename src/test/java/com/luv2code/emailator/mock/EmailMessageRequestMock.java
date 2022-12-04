package com.luv2code.emailator.mock;

import com.luv2code.emailator.dto.EmailMessageRequest;

public class EmailMessageRequestMock {

    public static EmailMessageRequest createEmailMessageRequest(final String from, final String to, final String cc,
                                                                final String subject, final String importance, final String content) {
        return EmailMessageRequest.builder()
                .from(from)
                .to(to)
                .cc(cc)
                .subject(subject)
                .importance(importance)
                .content(content)
                .build();
    }
}
