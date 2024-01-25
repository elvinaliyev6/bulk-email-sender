package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import com.example.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.example.bulkemailsender.repository.BulkEmailMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageSchedulerService {

    private final BulkEmailMessageService emailMessageService;
    private final BulkEmailMessageRepository emailMessageRepository;
    private final BulkEmailMessageMapper emailMessageMapper;

    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        try{
            List<BulkEmailMessageEntity> emailMessageEntities = emailMessageRepository
                    .findAllByEmailMessageStatus(EmailMessageStatus.PENDING);

            for (BulkEmailMessageEntity entity : emailMessageEntities) {
                entity.setStatus(EmailMessageStatus.SENDING);
                emailMessageRepository.save(entity);
            }

            for (BulkEmailMessageEntity entity : emailMessageEntities) {
                entity.setStatus(EmailMessageStatus.SENDING);

                BulkEmailMessageDto dto = emailMessageMapper.mapEntityToDto(entity);

                try{
                    emailMessageService.sendEmail(dto);
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
            }

        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }

}
