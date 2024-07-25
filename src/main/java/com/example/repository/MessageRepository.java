package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :id")
    int updateMessageById(int id, String messageText);


    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId = :id")
    int deleteMessagesById(int id);

    List<Message> findAllByPostedBy(int id);
}
