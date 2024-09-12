package com.example.phonePay.Controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.phonePay.Repo.BankUserRepo;
import com.example.phonePay.Repo.LinkedBankAccountRepo;
import com.example.phonePay.Repo.PhonePayUserRepo;
import com.example.phonePay.Repo.TransactionsRepo;
import com.example.phonePay.model.BankUser;
import com.example.phonePay.model.LinkedBankAccount;
import com.example.phonePay.model.PhonePayUser;
import com.example.phonePay.model.Transactions;
import com.example.phonePay.model.dto.BalanceCheckDto;
import com.example.phonePay.model.dto.LinkedAccountDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RestController
@CrossOrigin("http://localhost:4200/")
public class BankController {
	@Autowired
	BankUserRepo bankUserRepo;
	@Autowired
	LinkedBankAccountRepo linkedBankAccountRepo;
	
	@Autowired
	PhonePayUserRepo phonePayUserRepo;
	@Autowired
	TransactionsRepo transactionsRepo;
	
	 @RequestMapping("getBankDetails")
	    public List<BankUser> getAllBankName(@RequestBody long[] str){
	     
		 List<BankUser> list= bankUserRepo.findByMobile((str[0]));
	        List<BankUser> list2=new ArrayList<>();
	        for(BankUser bankUser:list){
	            int count=linkedBankAccountRepo.countByBankUserId(bankUser.getId());
	            if(count==0) list2.add(bankUser);
	        }
	        return list2;
		 
		 //return bankUserServices.getAllBankDetails(mobNo);
	    }
	 
	 @GetMapping("setUser/{id}")
	    public int setUser(@PathVariable int id){
	     
		 BankUser bankUser=bankUserRepo.findById(id).get();
	        int count=phonePayUserRepo.countByMobile(bankUser.getMobile());
	        if(count!=1) return -1;
	        PhonePayUser phonePayUser=phonePayUserRepo.findByMobile(bankUser.getMobile());

	        int count1=linkedBankAccountRepo.countByBankUserId(bankUser.getId());
	        if(count1==0) {
	            LinkedBankAccount linkedBankAccount = new LinkedBankAccount();
	            linkedBankAccount.setBankUserId(bankUser.getId());
	            linkedBankAccount.setPhonePayUserId(phonePayUser.getId());
	            linkedBankAccount.setMobile(phonePayUser.getMobile());
	            int userCount= linkedBankAccountRepo.countByPhonePayUserId(phonePayUser.getId());
	            if(userCount==0) {
	                linkedBankAccount.setIsPrimary(1);
	            }
	            Random random = new Random();
	            int otp = 1000 + random.nextInt(9000);
	            linkedBankAccount.setOtp(otp);
	            linkedBankAccountRepo.save(linkedBankAccount);
	            System.out.println(otp);
	            return 1;
	        }
	        else{
	            return -1;
	        }
	    

	    
		 
		 //return linkedBankAccountServices.setUser(id);
	    
}

	 @PostMapping("linkBankAccount")
	 	public int linkBankAccount(@RequestBody LinkedBankAccount linkedBankAccount){
		 
		 int count=linkedBankAccountRepo.countByPhonePayUserIdAndBankUserId(linkedBankAccount.getPhonePayUserId(),linkedBankAccount.getBankUserId());
	        System.out.println(count);
	        if(count ==0 ) return -1;

	        else{
	            PhonePayUser phonePayUser=phonePayUserRepo.findById(linkedBankAccount.getPhonePayUserId()).get();
	            LinkedBankAccount link=linkedBankAccountRepo.findByPhonePayUserIdAndBankUserId(linkedBankAccount.getPhonePayUserId(),linkedBankAccount.getBankUserId());
	            if(linkedBankAccount.getOtp()!=link.getOtp()) return -2;
	           BankUser bankUser=bankUserRepo.findById(linkedBankAccount.getBankUserId()).get();
	           link.setUpiId(bankUser.getMobile() + "@" + bankUser.getBankName() + ".ibl");
	             link.setIsDeleted(0);
	            linkedBankAccountRepo.save(link);
	        }
	      return 1;
		 
		 //return linkedBankAccountServices.linkAccount(linkedBankAccount);
}
	 
	 @GetMapping("getLinkedList")
	    public List<LinkedAccountDTO> getLinkedList(@RequestBody long mobNo){
	     
		 int count=linkedBankAccountRepo.countByMobile(mobNo);
	        if(count==0) return new ArrayList<>();

	        List<LinkedBankAccount> list=linkedBankAccountRepo.findByMobile(mobNo);
	        List<LinkedAccountDTO> ll=new ArrayList<>();
	        for(LinkedBankAccount value:list){
	            int id= value.getBankUserId();
	            BankUser bb=bankUserRepo.findById(id).get();
	            LinkedAccountDTO response=new LinkedAccountDTO();
	            response.setUpiId(value.getUpiId());
	            response.setBankName(bb.getBankName());
	            response.setIfsc(bb.getIfsccode());
	            response.setAccountNumber(bb.getAccountNumber());
	            ll.add(response);
	        }
	        return ll;
		 
		 //return linkedBankAccountServices.getLinkedListUser(mobNo);
	    }
	 
	 
	 @GetMapping("get/bank/balance/{id}")
	    public List<BalanceCheckDto> checkBankBalance(@PathVariable int id){
	       
		 List<LinkedBankAccount> ll=linkedBankAccountRepo.findByPhonePayUserIdAndIsDeleted(id,0);
	        List<BalanceCheckDto> list=new ArrayList<>();
	        for(LinkedBankAccount l:ll){
	            BalanceCheckDto bb=new BalanceCheckDto();
	            BankUser bankUser=bankUserRepo.findById(l.getBankUserId()).get();
	            bb.setBankName(bankUser.getBankName());
	            bb.setBankBalance(bankUser.getBalance());
	            list.add(bb);
	        }
	        return list;
		 
		 // return bankUserServices.checkBalace(id);
	    }
	 
	 
	 
	 
//	 @GetMapping("get/user/{id}")
//	    public List<String> getuserInfo(@PathVariable int id){
//	       
//		 LinkedBankAccount ll=linkedBankAccountRepo.findByPhonePayUserIdAndIsPrimary(id,1);
//	        BankUser bb=bankUserRepo.findById(ll.getBankUserId()).get();
//	        List<String> list=new ArrayList<>();
//	        list.add(ll.getUpiId());
//	        list.add(Long.toString(ll.getMobile()));
//	        list.add(bb.getBankName());
//	        list.add(bb.getUserEmail());
//	        return list;
//		 
//		 // return phonePayUserServices.getInfo(id);
//	    }
	 
	 
//	  @GetMapping("getPhonePayUser/{id}")
//	    @CrossOrigin
//	    public PhonePayUser getPhonePayUser(@PathVariable int id){
//	       
//		  int count=phonePayUserRepo.countById(id);
//	        if(count==0) return null;
//	        return phonePayUserRepo.findById(id).get();
//		  
//		  // return phonePayUserServices.getPhonePayUser(id);
//	    }
	  
//	  @GetMapping("get/all/transactions/{id}")
//	    public List<Transactions> getAllTransactions(@PathVariable int id){
//	        
//		  return transactionsRepo.findByPhonePayUserId(id);
//		  
//		  //return phonePayUserServices.getAllTransactions(id);
//	    }
	 
	 
	 
	 
}

