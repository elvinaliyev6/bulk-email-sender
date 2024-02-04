package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import com.example.bulkemailsender.mapper.EmailMessageMapper;
import com.example.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageKafkaScheduler {
    private final EmailMessageSender emailMessageSender;
    private final EmailMessageRepository emailMessageRepository;
    private final EmailMessageMapper emailMessageMapper;
    private final ExecutorService executorService= Executors.newFixedThreadPool(20);
    private Semaphore semaphore=new Semaphore(1000);

    @KafkaListener(topics = "emailMessageTopic",groupId = "emailMessageTopic")
    public void listen(Long messageId) throws InterruptedException {
        EmailMessageEntity entity=emailMessageRepository
                .findById(messageId).orElseThrow();
        EmailMessageDto dto=emailMessageMapper.mapEntityToDto(entity);

        semaphore.acquire();
        executorService.submit(()->{
            try{
                emailMessageSender.sendEmail(dto);
                entity.setStatus(EmailMessageStatus.SENT);
            }catch (Exception e){
                log.error(e.getMessage(),e);
                entity.setStatus(EmailMessageStatus.FAILED);
            }finally {
                emailMessageRepository.save(entity);
                semaphore.release();
            }
        });
    }
}
