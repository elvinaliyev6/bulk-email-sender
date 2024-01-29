package com.example.bulkemailsender.controller;

import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.service.EmailMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email-message")
public class EmailMessageController {
    private final EmailMessageService emailMessageService;

    @PostMapping
    public void sendEmail(@RequestBody
                          EmailMessageDto emailMessageDto){
        emailMessageService.sendEmail(emailMessageDto);
    }
}
