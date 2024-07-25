package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message msg){
        return messageRepository.save(msg);
    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    public Optional<Message> getMessageById(int id){
        return messageRepository.findById(id);
    }
    public int updateMessageById(int id, String message){
        return messageRepository.updateMessageById(id,message);
    }
    public List<Message> getMessagesByPostedBy(int id){
        return messageRepository.findAllByPostedBy(id);
    }
    public int deleteMessageById(int id){
        return messageRepository.deleteMessagesById(id);
    }
}
