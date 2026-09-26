package org.sid.bank_account_service.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.sid.bank_account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    //annotation @QueryMapping <- pour dire que c'est une methode qui va traiter une requette GraphQL
    @QueryMapping
    //quand on a une requette ou le client demande accountsList on va executer la methode accountsList
    public List<BankAccount> accountsList() {
        return bankAccountRepository.findAll();
    }
    @QueryMapping
    public BankAccount bankAccountById(@Argument String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
    }
    //on ajoute :"@MutationMapping"-> pour les operations de mutation [ajout,modification,suppression]
    @MutationMapping
    public BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount) {
        return accountService.addAccount(bankAccount);
    }
}

//METHODE 1 : CREER UNE CLASSE DTO -> {classe avec les getters et setters}
//@Data @NoArgsConstructor @AllArgsConstructor
//class BankAccountDTO {
//    private String type;
//    private Double balance;
//    private String currency;
//}

//METHODE 2 : CREER UNE CLASSE DTO -> {en utilisant le type : record}
//record BankAccountDTO(Double balance, String type, String currency) {}