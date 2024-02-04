package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import com.example.bulkemailsender.mapper.EmailMessageMapper;
import com.example.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageSchedulerService {

    private final EmailMessageSender emailMessageSender;
    private final EmailMessageRepository emailMessageRepository;
    private final EmailMessageMapper emailMessageMapper;
    private final ExecutorService executorService=Executors.newFixedThreadPool(20);
    private Semaphore semaphore=new Semaphore(1000);

    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        try{
            List<EmailMessageEntity> emailMessageEntities = emailMessageRepository
                    .findAllByStatus(EmailMessageStatus.PENDING);

            for (EmailMessageEntity entity : emailMessageEntities) {
                entity.setStatus(EmailMessageStatus.SENDING);
                emailMessageRepository.save(entity);
            }

            for (EmailMessageEntity entity : emailMessageEntities) {
                EmailMessageDto dto = emailMessageMapper.mapEntityToDto(entity);

                executorService.submit(()->{
                    try{
                        emailMessageSender.sendEmail(dto);
                        entity.setStatus(EmailMessageStatus.SENT);
                    }catch (Exception e){
                        log.error(e.getMessage(),e);
                        entity.setStatus(EmailMessageStatus.FAILED);
                    }finally {
                        emailMessageRepository.save(entity);
                    }
                });
            }

        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }

}
