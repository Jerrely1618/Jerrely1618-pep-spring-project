package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Optional<Account> getAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }
    public Account addAccount(Account account){
        return accountRepository.save(account);
    }
    public Optional<Account> getAccountById(int accountId ){
        return accountRepository.findById(accountId);

    }
}
