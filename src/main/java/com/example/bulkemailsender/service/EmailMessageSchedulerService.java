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

    private final EmailMessageService emailMessageService;
    private final BulkEmailMessageRepository emailMessageRepository;
    private final BulkEmailMessageMapper emailMessageMapper;

    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        try{
            List<BulkEmailMessageEntity> emailMessageEntities = emailMessageRepository
                    .findAllByStatus(EmailMessageStatus.PENDING);

            for (BulkEmailMessageEntity entity : emailMessageEntities) {
                entity.setStatus(EmailMessageStatus.SENDING);
                emailMessageRepository.save(entity);
            }

            for (BulkEmailMessageEntity entity : emailMessageEntities) {
                BulkEmailMessageDto dto = emailMessageMapper.mapEntityToDto(entity);

                try{
                    emailMessageService.sendEmail(dto);
                    entity.setStatus(EmailMessageStatus.SENT);
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                    entity.setStatus(EmailMessageStatus.FAILED);
                }finally {
                    emailMessageRepository.save(entity);
                }
            }

        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }

}
