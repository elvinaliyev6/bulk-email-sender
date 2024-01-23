package com.example.bulkemailsender.repository;

import com.example.bulkemailsender.entity.BulkEmailMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkEmailMessageRepository extends JpaRepository<BulkEmailMessageEntity,Long> {

}
