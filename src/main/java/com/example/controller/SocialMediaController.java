package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


 @RestController
public class SocialMediaController {
    
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account user){
        if(accountService.getAccountByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.status(409).build();
        }
        if(user.getUsername().length() > 0 && user.getPassword().length() > 4){
            Account addedAccount = accountService.addAccount(user);
            return ResponseEntity.status(200).body(addedAccount);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account user){
        Optional<Account> foundAccount = accountService.getAccountByUsername(user.getUsername());
        if(foundAccount.isPresent() && foundAccount.get().getPassword().equals(user.getPassword())){
            return ResponseEntity.status(200).body(foundAccount.get());
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message msg){
        if(accountService.getAccountById(msg.getPostedBy()).isEmpty()){
            return ResponseEntity.status(400).build();
        }
        String messageText = msg.getMessageText();
        if(messageText.length() > 0 && messageText.length() < 255){
            Message addedMessage = messageService.addMessage(msg);
            return ResponseEntity.status(200).body(addedMessage);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId){
        Optional<Message> selectedMessage = messageService.getMessageById(messageId);
        if(selectedMessage.isPresent()){
            return ResponseEntity.status(200).body(selectedMessage.get());
        }
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody HashMap<String,String> body){
        if(messageService.getMessageById(messageId).isEmpty()){
            return ResponseEntity.status(400).build();
        }
        String newMessage = body.get("messageText");
        if(newMessage.length() > 0 && newMessage.length() < 255){
            int rowsAffected = messageService.updateMessageById(messageId, newMessage);
            return ResponseEntity.status(200).body(rowsAffected);
        }
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        int rowsAffected = messageService.deleteMessageById(messageId);
        if(rowsAffected > 0){
            return ResponseEntity.status(200).body(rowsAffected);
        } else {
            return ResponseEntity.status(200).build();
        }
    }


    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId){
        List<Message> messages = messageService.getMessagesByPostedBy(accountId);
        return ResponseEntity.status(200).body(messages);
    }
}
