package com.example.bulkemailsender.mapper;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.data.EmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BulkEmailMessageMapper {

    BulkEmailMessageDto mapEntityToDto(BulkEmailMessageEntity emailMessageEntity);

    BulkEmailMessageEntity mapDtoToEntity(BulkEmailMessageDto emailMessageDto);

}
