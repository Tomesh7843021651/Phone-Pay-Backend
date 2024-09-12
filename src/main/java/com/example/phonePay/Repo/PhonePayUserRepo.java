package com.example.phonePay.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonePay.model.PhonePayUser;
import com.example.phonePay.model.Transactions;

public interface PhonePayUserRepo extends JpaRepository<PhonePayUser, Integer>{
	
	PhonePayUser findByMobile(long mobile);
	int countByMobile(long mobile);
	
	
	int countById(int id);
	
	//services
	
//	  boolean userRegister(String mobileNumber);
//
//	    boolean verifyOTP( String mobNo,int otp);
//
//	    int setPassword(String[] str);
//
//	    int signIn(String mobileNo, String password);
//
//	    PhonePayUser getPhonePayUser(int id);
//
//	    List<Transactions> getAllTransactions(int id);
//
//	    List<String> getInfo(int id);

}
