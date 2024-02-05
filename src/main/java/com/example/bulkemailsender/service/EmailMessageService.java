package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessagePriority;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import com.example.bulkemailsender.mapper.EmailMessageMapper;
import com.example.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailMessageService {
    //    private final EmailMessageService emailMessageService;
    private final EmailMessageRepository emailMessageRepository;
    private final EmailMessageMapper emailMessageMapper;
//    private final ExecutorService executorService= Executors.newFixedThreadPool(20);

    private final KafkaTemplate<Long, Long> kafkaTemplate;

    public void sendEmail(EmailMessageDto emailMessageDto) {
        EmailMessageEntity emailMessageEntity = emailMessageMapper
                .mapDtoToEntity(emailMessageDto);
        emailMessageEntity.setStatus(EmailMessageStatus.PENDING);
        emailMessageRepository.save(emailMessageEntity);

//        executorService.submit(()->{
//            emailMessageService.sendEmail(bulkEmailMessageDto);
//        });
        EmailMessagePriority priority = emailMessageEntity
                .getEmailMessagePriority();
        switch (priority) {
            case LOW:
                kafkaTemplate
                        .send("emailMessageTopicLow", emailMessageEntity.getId());
                break;
            case MEDIUM:
                kafkaTemplate
                        .send("emailMessageTopicMedium", emailMessageEntity.getId());
                break;
            case HIGH:
                kafkaTemplate
                        .send("emailMessageTopicHigh", emailMessageEntity.getId());
                break;
        }

    }
}
