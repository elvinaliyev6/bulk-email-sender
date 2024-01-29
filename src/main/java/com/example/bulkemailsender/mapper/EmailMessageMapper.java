package com.example.bulkemailsender.mapper;

import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMessageMapper {

    EmailMessageDto mapEntityToDto(EmailMessageEntity emailMessageEntity);

    EmailMessageEntity mapDtoToEntity(EmailMessageDto emailMessageDto);

}
