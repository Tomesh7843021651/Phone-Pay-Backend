package com.example.phonePay.Repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonePay.model.BankUser;
import com.example.phonePay.model.Transactions;
import com.example.phonePay.model.dto.BalanceCheckDto;
import com.example.phonePay.model.dto.ResponseVerifyUserDTO;
import com.example.phonePay.model.dto.SendMoneyDTO;

public interface TransactionsRepo extends JpaRepository<Transactions, Integer> {

    List<Transactions> findByPhonePayUserId(int id);
    
    
    //services
//    ResponseVerifyUserDTO verifyUPI(String upiId);
//
//
//    int sendMoneyDTO(SendMoneyDTO sendMoneyDTO);
//    
//    
//    List<BankUser> getAllBankDetails(long mobNo);
//
//    List<BalanceCheckDto> checkBankBalance(int id);


}
