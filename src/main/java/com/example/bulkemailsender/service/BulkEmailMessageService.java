package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import com.example.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.example.bulkemailsender.repository.BulkEmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class BulkEmailMessageService {
//    private final EmailMessageService emailMessageService;
    private final BulkEmailMessageRepository bulkEmailMessageRepository;
    private final BulkEmailMessageMapper bulkEmailMessageMapper;
//    private final ExecutorService executorService= Executors.newFixedThreadPool(20);

    public void sendEmail(BulkEmailMessageDto bulkEmailMessageDto) {
        BulkEmailMessageEntity bulkEmailMessageEntity=bulkEmailMessageMapper
                .mapDtoToEntity(bulkEmailMessageDto);
        bulkEmailMessageEntity.setStatus(EmailMessageStatus.PENDING);
        bulkEmailMessageRepository.save(bulkEmailMessageEntity);

//        executorService.submit(()->{
//            emailMessageService.sendEmail(bulkEmailMessageDto);
//        });
    }
}
