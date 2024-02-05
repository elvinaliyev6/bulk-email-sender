package com.example.bulkemailsender.data;

import com.example.bulkemailsender.entity.EmailMessagePriority;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessageDto {

    private String from;
    private String to;
    private String subject;
    private String body;
    private EmailMessagePriority emailMessagePriority;

}
