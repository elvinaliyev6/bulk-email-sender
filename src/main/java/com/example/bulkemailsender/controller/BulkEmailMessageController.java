package com.example.bulkemailsender.controller;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.example.bulkemailsender.repository.BulkEmailMessageRepository;
import com.example.bulkemailsender.service.BulkEmailMessageService;
import com.example.bulkemailsender.service.EmailMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bulk-email-message")
public class BulkEmailMessageController {
    private final EmailMessageService emailMessageService;
    private final BulkEmailMessageService bulkEmailMessageService;


    @PostMapping
    public void sendEmail(@RequestBody
                          BulkEmailMessageDto bulkEmailMessageDto){
        bulkEmailMessageService.sendEmail(bulkEmailMessageDto);

    }
}
