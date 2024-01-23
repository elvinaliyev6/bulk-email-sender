package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.example.bulkemailsender.repository.BulkEmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BulkEmailMessageService {
    private final EmailMessageService emailMessageService;
    private final BulkEmailMessageRepository bulkEmailMessageRepository;

    @Autowired
    private BulkEmailMessageMapper bulkEmailMessageMapper;

    public void sendEmail(BulkEmailMessageDto bulkEmailMessageDto) {
        BulkEmailMessageEntity bulkEmailMessageEntity=bulkEmailMessageMapper
                .mapDtoToEntity(bulkEmailMessageDto);
        bulkEmailMessageRepository.save(bulkEmailMessageEntity);

        emailMessageService.sendEmail(bulkEmailMessageDto);
    }
}
