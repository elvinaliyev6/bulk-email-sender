package com.example.bulkemailsender.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkEmailMessageDto {

    private String from;
    private List<String> to;
    private String subject;
    private String body;

}
