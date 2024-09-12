package com.example.phonePay.Controller;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.phonePay.Repo.BankUserRepo;
import com.example.phonePay.Repo.LinkedBankAccountRepo;
import com.example.phonePay.Repo.PhonePayUserRepo;
import com.example.phonePay.Repo.TransactionsRepo;
import com.example.phonePay.model.BankUser;
import com.example.phonePay.model.LinkedBankAccount;
import com.example.phonePay.model.Transactions;
import com.example.phonePay.model.dto.ResponseVerifyUserDTO;
import com.example.phonePay.model.dto.SendMoneyDTO;

@RestController
@CrossOrigin("http://localhost:4200/")
public class PaymentController {
	@Autowired
	LinkedBankAccountRepo linkedBankAccountRepo;
	
	@Autowired
	BankUserRepo bankUserRepo;
	
	@Autowired
	PhonePayUserRepo phonePayUserRepo;
	
	@Autowired
	TransactionsRepo transactionsRepo;
	
	 @GetMapping("verifyUpiId/{upiId}")
	    public ResponseVerifyUserDTO verifyUPI(@PathVariable String upiId ){
	     
		 int count=linkedBankAccountRepo.countByUpiId(upiId);
	        if(count>1) return null;
	        if(count==1){
	            LinkedBankAccount ll=linkedBankAccountRepo.findByUpiId(upiId);
	            BankUser bankUser=bankUserRepo.findById(ll.getBankUserId()).get();
	            ResponseVerifyUserDTO response=new ResponseVerifyUserDTO();
	            response.setUserName(bankUser.getName());
	            response.setUserBank(bankUser.getBankName());
	            return response;
	        }
	        else{
	           int cc=linkedBankAccountRepo.countByUpiId(upiId);
	           if(cc==0) return null;
	           LinkedBankAccount ll=linkedBankAccountRepo.findByUpiIdAndIsDeletedAndIsPrimary(upiId,0,1);
	            BankUser bankUser=bankUserRepo.findById(ll.getBankUserId()).get();
	            ResponseVerifyUserDTO response=new ResponseVerifyUserDTO();
	            response.setUserName(bankUser.getName());
	            response.setUserBank(bankUser.getBankName());
	            return response;

	        }
		 
		 // return paymentServices.verifyUPI(upiId);
	    }

	    @PostMapping("sendMoney")
	    public int sendMoney(@RequestBody SendMoneyDTO sendMoneyDTO){
	       
	    	  //check pin
	        LinkedBankAccount ll= linkedBankAccountRepo.findByPhonePayUserIdAndIsPrimaryAndIsDeleted(sendMoneyDTO.getSenderId(),1,0);

	        System.out.println(sendMoneyDTO.getUpiPin()+"  "+ll.getUpiPin()+" "+sendMoneyDTO.getSenderId());
	         if(!Objects.equals(sendMoneyDTO.getUpiPin(), ll.getUpiPin())) return -1;
	         // check balance
	         BankUser bb=bankUserRepo.findById(ll.getBankUserId()).get();

	         if(bb.getBalance()<sendMoneyDTO.getSendAmount()) 
	        	 return -2;

	         //sender transaction
	         Transactions tt=new Transactions();
	         tt.setPhonePayUserId(sendMoneyDTO.getSenderId());
	         tt.setBankId(bb.getId());
	         tt.setBalance(bb.getBalance());
	         tt.setCrdp("Debited");
	         tt.setAfterBalance(bb.getBalance()-sendMoneyDTO.getSendAmount());
	         tt.setCommit("send to "+sendMoneyDTO.getReceiverUpiId());
	         tt.setCurrDate(new Date());
	         transactionsRepo.save(tt);

	         //sender db update
	         bb.setBalance(tt.getAfterBalance());
	         bankUserRepo.save(bb);

	         //receiver Transaction
	         int count=linkedBankAccountRepo.countByUpiId(sendMoneyDTO.getReceiverUpiId());
	         if(count>1) return -3;
	         if(count==1){
	             LinkedBankAccount receiver=linkedBankAccountRepo.findByUpiId(sendMoneyDTO.getReceiverUpiId());
	             BankUser bankUser=bankUserRepo.findById(receiver.getBankUserId()).get();
	             tt=new Transactions();
	             tt.setPhonePayUserId(receiver.getPhonePayUserId());
	             tt.setBankId(receiver.getBankUserId());
	             tt.setCrdp("Credited");
	             tt.setBalance(sendMoneyDTO.getSendAmount());
	             tt.setAfterBalance(bankUser.getBalance()+sendMoneyDTO.getSendAmount());
	             tt.setCommit("Recieved to "+ll.getUpiId());
	             tt.setCurrDate(new Date());
	             transactionsRepo.save(tt);

	             bankUser.setBalance(tt.getAfterBalance());
	             bankUserRepo.save(bankUser);
	             return 1;
	         }
	         else{
	             int cc=linkedBankAccountRepo.countByUpiId(sendMoneyDTO.getReceiverUpiId());
	             if(cc==0) return -1;
	             LinkedBankAccount receiver=linkedBankAccountRepo.findByUpiIdAndIsDeletedAndIsPrimary(sendMoneyDTO.getReceiverUpiId(),0,1);
	             BankUser bankUser=bankUserRepo.findById(receiver.getBankUserId()).get();
	             tt=new Transactions();
	             tt.setPhonePayUserId(receiver.getPhonePayUserId());
	             tt.setBankId(receiver.getBankUserId());
	             tt.setCrdp("Credited");
	             tt.setBalance(sendMoneyDTO.getSendAmount());
	             tt.setAfterBalance(bankUser.getBalance()+sendMoneyDTO.getSendAmount());
	             tt.setCommit("Recieved to "+ll.getUpiId());
	             tt.setCurrDate(new Date());
	             transactionsRepo.save(tt);
	             bankUser.setBalance(tt.getAfterBalance());
	             bankUserRepo.save(bankUser);
	            return 1;
	         }
	     }
	    	
	    	// System.out.println(sendMoneyDTO.toString());
	        //return paymentServices.sendMoneyDTO(sendMoneyDTO);
	    }

	


