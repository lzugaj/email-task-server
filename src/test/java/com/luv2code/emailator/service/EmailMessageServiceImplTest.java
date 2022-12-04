package com.luv2code.emailator.service;

import com.luv2code.emailator.entity.EmailMessage;
import com.luv2code.emailator.entity.enums.ImportanceType;
import com.luv2code.emailator.mock.EmailMessageMock;
import com.luv2code.emailator.repository.EmailMessageRepository;
import com.luv2code.emailator.service.impl.EmailMessageServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmailMessageServiceImplTest {

    private EmailMessageService emailMessageService;

    @Mock
    private EmailMessageRepository emailMessageRepository;

    private EmailMessage firstEmailMessage;
    private EmailMessage secondEmailMessage;
    private EmailMessage thirdEmailMessage;

    private List<EmailMessage> emailMessages;

    @BeforeEach
    public void setup() {
        emailMessageService = new EmailMessageServiceImpl(Clock.fixed(Instant.parse("2022-12-03T12:00:00Z"), ZoneOffset.UTC), emailMessageRepository);

        firstEmailMessage = EmailMessageMock.createEmailMessage(1L, "john.doe@gmail.com", "osolote09@maill.dev", "hotboypimp@weinzed.org,sergeyh1@btcmod.com", "Test 1", ImportanceType.NORMAL, "Test 1 message");
        secondEmailMessage = EmailMessageMock.createEmailMessage(2L, "osolote09@maill.dev", "osolote09@maill.dev", null, "Test 2", ImportanceType.HIGH, "Test 2 message");
        thirdEmailMessage = EmailMessageMock.createEmailMessage(3L, "ndplots@schoolmother.us", "midniteeyes@gmailni.com", "yyhenry@gmailvn.net", "Test 3", ImportanceType.LOW, "Test 3 message");

        emailMessages = List.of(firstEmailMessage, secondEmailMessage, thirdEmailMessage);
    }

    @Test
    @DisplayName(value = "Test send EmailMessage, expect ok")
    public void should_Send_Email_Message() {
        // Given
        BDDMockito.when(emailMessageRepository.save(firstEmailMessage)).thenReturn(firstEmailMessage);

        // When
        emailMessageService.send(firstEmailMessage);

        // Then
        Mockito.verify(emailMessageRepository).save(firstEmailMessage);
    }

    @Test
    @DisplayName(value = "Test find EmailMessage by id, expect ok")
    public void should_Find_EmailMessage_By_Id() {
        //Given
        final Long searchedId = secondEmailMessage.getId();
        BDDMockito.given(emailMessageRepository.findById(searchedId)).willReturn(Optional.ofNullable(secondEmailMessage));

        //When
        final EmailMessage searchedEmailMessage = emailMessageService.findById(searchedId);

        //Then
        Assertions.assertEquals(2, searchedEmailMessage.getId());
    }

    @Test
    @DisplayName(value = "Test find EmailMessage by id, expected failed")
    public void should_Throw_Exception_When_Id_Not_Found() {
        //Given
        final Long notFoundId = 4L;

        //When
        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> emailMessageService.findById(notFoundId)
        );

        final String expectedMessage = String.format("Cannot find EmailMessage by given id. [id=%s]", notFoundId);
        final String actualMessage = exception.getMessage();

        //Then
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName(value = "Test find all EmailMessages, expected ok")
    public void should_Find_All_EmailMessages() {
        //Given
        BDDMockito.given(emailMessageRepository.findAll()).willReturn(emailMessages);

        //When
        final List<EmailMessage> searchedEmailMessages = emailMessageService.findAll();

        //Then
        Assertions.assertEquals(3, searchedEmailMessages.size());
    }
}
