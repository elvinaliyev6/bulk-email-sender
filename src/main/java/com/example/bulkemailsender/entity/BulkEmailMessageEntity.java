package com.example.bulkemailsender.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class BulkEmailMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"from\"")
    private String from;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "\"to\"", joinColumns = @JoinColumn(name = "bulk_email_message_id"))
    @Column(name = "\"to\"")
    private List<String> to;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailMessagePriority emailMessagePriority;

    @Enumerated(EnumType.STRING)
    private EmailMessageStatus status;
}
