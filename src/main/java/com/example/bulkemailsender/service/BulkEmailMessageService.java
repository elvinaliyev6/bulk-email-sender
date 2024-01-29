package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import com.example.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.example.bulkemailsender.mapper.EmailMessageMapper;
import com.example.bulkemailsender.repository.BulkEmailMessageRepository;
import com.example.bulkemailsender.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BulkEmailMessageService {
    private final BulkEmailMessageRepository bulkEmailMessageRepository;
    private final BulkEmailMessageMapper bulkEmailMessageMapper;
    private final EmailMessageService emailMessageService;

//    private final ExecutorService executorService= Executors.newFixedThreadPool(20);

    public void sendEmail(BulkEmailMessageDto bulkEmailMessageDto) {
        BulkEmailMessageEntity bulkEmailMessageEntity=bulkEmailMessageMapper
                .mapDtoToEntity(bulkEmailMessageDto);
        bulkEmailMessageRepository.save(bulkEmailMessageEntity);

        for(String to: bulkEmailMessageDto.getTo()){
            EmailMessageDto emailMessageDto=EmailMessageDto
                    .builder()
                    .body(bulkEmailMessageDto.getBody())
                    .subject(bulkEmailMessageDto.getSubject())
                    .from(bulkEmailMessageDto.getFrom())
                    .to(to)
                    .build();

            emailMessageService.sendEmail(emailMessageDto);
        }
    }
}
