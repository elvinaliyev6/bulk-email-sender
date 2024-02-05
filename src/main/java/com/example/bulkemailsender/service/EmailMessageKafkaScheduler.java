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
    private final Semaphore semaphoreLow=new Semaphore(30);
    private final Semaphore semaphoreMedium=new Semaphore(100);
    private final Semaphore semaphoreHigh=new Semaphore(3000);

    @KafkaListener(topics = "emailMessageTopicLow",groupId = "emailMessageTopic")
    public void emailMessageTopicLow(Long messageId) throws InterruptedException {
        semaphoreLow.acquire();
        sendEmailMessage(messageId,semaphoreLow);
    }

    @KafkaListener(topics = "emailMessageTopicMedium",groupId = "emailMessageTopic")
    public void emailMessageTopicMedium(Long messageId) throws InterruptedException {
        semaphoreMedium.acquire();
        sendEmailMessage(messageId,semaphoreMedium);
    }

    @KafkaListener(topics = "emailMessageTopicHigh",groupId = "emailMessageTopic")
    public void emailMessageTopicHigh(Long messageId) throws InterruptedException {
       semaphoreHigh.acquire();
        sendEmailMessage(messageId,semaphoreHigh);

    }

    private void sendEmailMessage(Long messageId,Semaphore semaphore) throws InterruptedException {
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
