package com.luv2code.emailator.entity;

import com.luv2code.emailator.entity.enums.ImportanceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "email_message")
public class EmailMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email_form", nullable = false)
    private String from;

    @Column(name = "email_to", nullable = false)
    private String to;

    @Column(name = "cc")
    private String cc;

    @Column(name = "subject")
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "importance", nullable = false)
    private ImportanceType importance;

    @Column(name = "content")
    private String content;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

}
