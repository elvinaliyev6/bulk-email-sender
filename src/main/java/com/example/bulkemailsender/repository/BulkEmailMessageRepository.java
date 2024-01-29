package com.example.bulkemailsender.repository;

import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkEmailMessageRepository extends JpaRepository<BulkEmailMessageEntity,Long> {

    List<EmailMessageEntity> findAllByStatus(EmailMessageStatus emailMessageStatus);

}
