package com.example.phonePay.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonePay.model.BankUser;
import com.example.phonePay.model.dto.BalanceCheckDto;

public interface BankUserRepo extends JpaRepository<BankUser, Integer>{
	
	List<BankUser> findByMobile(long mobile);
	
	int countByMobile(long mobile);
	
	//services
	
	  //List<BankUser> getAllBankDetails(long mobile);

	  //  List<BalanceCheckDto> checkBankBalance(int id);
	
	
   
}
