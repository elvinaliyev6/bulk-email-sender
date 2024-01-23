package com.example.bulkemailsender.mapper;

import com.example.bulkemailsender.data.BulkEmailMessageDto;
import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BulkEmailMessageMapper {

    BulkEmailMessageDto mapEntityToDto(BulkEmailMessageEntity bulkEmailMessageEntity);

    BulkEmailMessageEntity mapDtoToEntity(BulkEmailMessageDto bulkEmailMessageDto);

}
