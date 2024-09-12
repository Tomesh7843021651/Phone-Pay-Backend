package com.example.phonePay.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonePay.model.LinkedBankAccount;
import com.example.phonePay.model.dto.LinkedAccountDTO;

public interface LinkedBankAccountRepo extends JpaRepository<LinkedBankAccount, Integer>{
	List<LinkedBankAccount> findByMobile(long mobile);
	
	int countByMobile(long mobile);

	
	//other ones
	 int countByPhonePayUserId(int id);

	    int countByBankUserIdAndOtp(int userId, int i);

	    int countByBankUserId(int userId);

	    int countByPhonePayUserIdAndBankUserId(int phonePayUserId, int bankUserId);

	    
	    //other
	    LinkedBankAccount findByPhonePayUserIdAndBankUserId(int phonePayUserId, int bankUserId);


	    int countByUpiId(String upiId);

	    LinkedBankAccount findByUpiId(String upiId);


	    LinkedBankAccount findByUpiIdAndIsDeletedAndIsPrimary(String upiId, int i,int j);

	    LinkedBankAccount findByUpiIdAndIsDeleted(String receiverUpiId, int i);

	    LinkedBankAccount findByPhonePayUserId(int senderId);

	    LinkedBankAccount findByPhonePayUserIdAndIsPrimaryAndIsDeleted(int senderId, int i, int i1);

	    LinkedBankAccount findByPhonePayUserIdAndIsPrimary(int id,int i);

	    List<LinkedBankAccount> findByPhonePayUserIdAndIsDeleted(int id, int i);
	  

	    //services 
//	    int setUser(int id);
//
//	    int linkAccount(LinkedBankAccount linkedBankAccount);
//
//	     List  <LinkedAccountDTO> getLinkedListUser(long mobNo);


		
}
