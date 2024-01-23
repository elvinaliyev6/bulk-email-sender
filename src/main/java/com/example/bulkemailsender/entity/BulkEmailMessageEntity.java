package com.example.bulkemailsender.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BulkEmailMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"from\"")
    private String from;
    @Column(name = "\"to\"")
    private String to;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;

}
