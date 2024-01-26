package com.example.bulkemailsender.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulkEmailMessageDto {

    private String from;
    private String to;
    private String subject;
    private String body;

}
