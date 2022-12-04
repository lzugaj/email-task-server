package com.luv2code.emailator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.emailator.dto.EmailMessageRequest;
import com.luv2code.emailator.mock.EmailMessageRequestMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles({"controller", "test"})
public class EmailMessageIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("201 - POST /email-message")
    void should_Save_EmailMessage() throws Exception {
        final EmailMessageRequest request = EmailMessageRequestMock.createEmailMessageRequest("john.doe@gmail.com", "osolote09@gmail.com", "hotboypimp@weinzed.org,sergeyh1@btcmod.com", "Test 1", "NORMAL", "Test 1 message");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/email-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("400 - POST /email-message")
    void should_Throw_Exception_When_Required_Attribute_Is_Null() throws Exception {
        final EmailMessageRequest request = EmailMessageRequestMock.createEmailMessageRequest(null, "osolote09@gmail.com", "hotboypimp@weinzed.org,sergeyh1@btcmod.com", "Test 1", "NORMAL", "Test 1 message");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/email-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Field from must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/email-message"));
    }

    @Test
    @DisplayName("400 - POST /email-message")
    void should_Throw_Exception_When_Required_Attribute_Is_Empty() throws Exception {
        final EmailMessageRequest request = EmailMessageRequestMock.createEmailMessageRequest("osolote09@gmail.com", "", "hotboypimp@weinzed.org,sergeyh1@btcmod.com", "Test 1", "NORMAL", "Test 1 message");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/email-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Field to must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/email-message"));
    }

    @Test
    @DisplayName("400 - POST /email-message")
    void should_Throw_Exception_When_Email_Is_Not_In_Valid_Format() throws Exception {
        final EmailMessageRequest request = EmailMessageRequestMock.createEmailMessageRequest("osolote09@gmail.com", "joh.doe.com", "hotboypimp@weinzed.org,sergeyh1@btcmod.com", "Test 1", "NORMAL", "Test 1 message");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/email-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Email to should be in valid form like spring.boot@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/email-message"));
    }

    @Test
    @DisplayName("200 - GET /email-message/100")
    public void should_Find_EmailMessage_By_Id() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/email-message/{id}", 100)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.from").value("john.doe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.to").value("franck.miller12@gmail.com"));
    }

    @Test
    @DisplayName("404 - GET /email-message/109")
    public void should_Throw_Exception_When_Entity_Was_Not_Founded_In_Database() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/email-message/{id}", 109)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatusCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cannot find EmailMessage by given id. [id=109]"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/v1/email-message/109"));
    }

    @Test
    @DisplayName("200 - GET /email-message")
    public void should_Find_All_EmailMessages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/email-message")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
