package com.luv2code.emailator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class EmailMessageRequest {

    @NotBlank(message = "Field from must not be blank")
    @Email(message = "Email from should be in valid form like spring.boot@gmail.com")
    @Schema(description = "Who is sending the email", example = "john.doe@gmail.com", required = true)
    private String from;

    @NotBlank(message = "Field to must not be blank")
    @Email(message = "Email to should be in valid form like spring.boot@gmail.com")
    @Schema(description = "To whom is mail sending", example = "john.doe@gmail.com", required = true)
    private String to;

    @Schema(description = "Who also needs a copy of this email", example = "john.doe@gmail.com,spring.boot@gmail.com")
    private String cc;

    @NotBlank(message = "Field subject must not be blank")
    @Schema(description = "Subject of the email", example = "This is the subject of the email", required = true)
    private String subject;

    @NotBlank(message = "Field importance must be selected")
    @Schema(description = "Choose an importance of the email", example = "HIGH", required = true)
    private String importance;

    @Schema(description = "Content of the email", example = "This is the content of the email")
    private String content;

}
