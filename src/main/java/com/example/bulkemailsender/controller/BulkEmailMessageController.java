package com.example.bulkemailsender.controller;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.service.BulkEmailMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bulk-email-sender")
public class BulkEmailMessageController {
    private final BulkEmailMessageService bulkEmailMessageService;

    @PostMapping
    public void sendEmail(@RequestBody
                          BulkEmailMessageDto bulkEmailMessageDto){
        bulkEmailMessageService.sendEmail(bulkEmailMessageDto);
    }
}
