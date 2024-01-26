package com.example.bulkemailsender.repository;

import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import com.example.bulkemailsender.entity.EmailMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BulkEmailMessageRepository extends JpaRepository<BulkEmailMessageEntity,Long> {

    List<BulkEmailMessageEntity> findAllByStatus(EmailMessageStatus emailMessageStatus);

}
