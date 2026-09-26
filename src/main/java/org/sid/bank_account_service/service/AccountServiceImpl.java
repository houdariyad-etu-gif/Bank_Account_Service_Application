package org.sid.bank_account_service.service;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.mappers.AccountMapper;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Override
    //mapping <- Le mapping fait partie des bonnes pratiques [des entités vers DTO]
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount= BankAccount.builder()
            .id (UUID.randomUUID().toString())
            .createdAt(new Date())
            .balance (bankAccountDTO.getBalance())
            .type(bankAccountDTO.getType())
            .currency(bankAccountDTO.getCurrency())
            .build();
        //code metier
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        //AccountMapper <- le code suivant a ne pas faire en couche service
//        BankAccountResponseDTO bankAccountResponseDTO= BankAccountResponseDTO.builder()
//                .id (savedBankAccount.getId())
//                .type(savedBankAccount.getType())
//                .createdAt (savedBankAccount.getCreatedAt())
//                .currency (savedBankAccount.getCurrency())
//                .balance (savedBankAccount.getBalance())
//                .build();
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(savedBankAccount);
        return bankAccountResponseDTO;
    }
}
