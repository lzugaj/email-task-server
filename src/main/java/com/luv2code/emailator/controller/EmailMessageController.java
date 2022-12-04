package com.luv2code.emailator.controller;

import com.luv2code.emailator.dto.EmailMessageRequest;
import com.luv2code.emailator.dto.EmailMessageResponse;
import com.luv2code.emailator.entity.EmailMessage;
import com.luv2code.emailator.mapper.EntityToResponseMapper;
import com.luv2code.emailator.mapper.RequestToEntityMapper;
import com.luv2code.emailator.service.EmailMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/email-message")
@Tag(name = "Email Message", description = "Email message API")
public class EmailMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageController.class);

    private final EmailMessageService emailMessageService;

    private final RequestToEntityMapper<EmailMessage, EmailMessageRequest> requestToEntityMapper;

    private final EntityToResponseMapper<EmailMessage, EmailMessageResponse> entityToResponseMapper;

    @Autowired
    public EmailMessageController(final EmailMessageService emailMessageService,
                                  final RequestToEntityMapper<EmailMessage, EmailMessageRequest> requestToEntityMapper,
                                  final EntityToResponseMapper<EmailMessage, EmailMessageResponse> entityToResponseMapper) {
        this.emailMessageService = emailMessageService;
        this.requestToEntityMapper = requestToEntityMapper;
        this.entityToResponseMapper = entityToResponseMapper;
    }

    @Operation(summary = "Send a new email message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Email message sent",
                    content = @Content(schema = @Schema(implementation = EmailMessageRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @PostMapping
    public ResponseEntity<HttpStatus> send(
            @Parameter(description = "Email message to send. Cannot be null or empty.", required = true)
            @Valid @RequestBody final EmailMessageRequest emailMessageRequest) {
        final EmailMessage newEmailMessage = requestToEntityMapper.map(emailMessageRequest, EmailMessage.class);
        LOGGER.debug("Successfully mapped EmailMessageRequest to EmailMessage");

        emailMessageService.send(newEmailMessage);
        LOGGER.info("Successfully send email. [from={}, to={}]", newEmailMessage.getFrom(), newEmailMessage.getTo());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Find a email message by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the email message",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmailMessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<EmailMessageResponse> findById(
            @Parameter(description = "Id of the email message to be founded. Cannot be empty.", required = true)
            @PathVariable final Long id) {
        final EmailMessage searchedEmailMessage = emailMessageService.findById(id);
        LOGGER.debug("Successfully founded searched email message. [id={}]", id);

        final EmailMessageResponse dto = entityToResponseMapper.map(searchedEmailMessage, EmailMessageResponse.class);
        LOGGER.debug("Successfully mapped EmailMessage to EmailMessageResponse");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(summary = "Find all email messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all email messages",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmailMessageResponse.class))))})
    @GetMapping
    public ResponseEntity<List<EmailMessageResponse>> findAll() {
        final List<EmailMessage> searchedEmailMessages = emailMessageService.findAll();
        LOGGER.info("Successfully founded {} emails.", searchedEmailMessages.size());

        final List<EmailMessageResponse> dtos = entityToResponseMapper.mapToList(searchedEmailMessages, EmailMessageResponse.class);
        LOGGER.debug("Successfully mapped list of EmailMessage to list of EmailMessageResponse");
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}
