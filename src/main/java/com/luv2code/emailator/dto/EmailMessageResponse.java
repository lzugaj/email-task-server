package com.luv2code.emailator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailMessageResponse {

    @Schema(description = "Id of the email", example = "234")
    private Long id;

    @Schema(description = "Who is sending the email", example = "john.doe@gmail.com")
    private String from;

    @Schema(description = "To whom is mail sending", example = "john.doe@gmail.com")
    private String to;

    @Schema(description = "Who also needs a copy of this email", example = "john.doe@gmail.com,spring.boot@gmail.com")
    private String cc;

    @Schema(description = "Subject of the email", example = "This is the subject of the email")
    private String subject;

    @Schema(description = "Importance of the email", example = "HIGH")
    private String importance;

    @Schema(description = "Content of the email", example = "This is the content of the email")
    private String content;

}
