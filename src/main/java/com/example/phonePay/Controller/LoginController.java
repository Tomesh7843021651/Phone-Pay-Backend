package com.example.phonePay.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

@RestController
@CrossOrigin("http://localhost:4200/")
public class LoginController {

int userOtp;
	
	@Autowired
	PhonePayUserRepo phonepayuserRepo;
	
	@Autowired
	BankUserRepo bankUserRepo;
	
	@Autowired
	TransactionsRepo transactionsRepo;
	
	@Autowired
	LinkedBankAccountRepo linkedBankAccountRepo;
	    
	@GetMapping("getUserOtp")
	    public int retrunOtp()
	    {
	    	return  userOtp;
	    }

//	    @RequestMapping("login/{mobile}")
//	    @CrossOrigin
//	    public int login(@PathVariable long mobile) {
//	        int count = phonepayuserRepo.countByMobile(mobile);
//
//	        if (count != 1) {
//	            return -1;  // Mobile number not registered
//	        }
//
//	        // If the mobile number is registered, generate and send OTP
//	        PhonePayUser user =phonepayuserRepo.findByMobile(mobile);
//	        Random obj = new Random();
//	        int otp = 1000 + obj.nextInt(9000);
//	        user.setOtp(otp);
//	        phonepayuserRepo.save(user);
//userOtp=otp;
//	        System.out.println("OTP sent to " + mobile + ": " + userOtp);
//
//	        return 1;  // OTP sent successfully
//	    }
	    
//	    @GetMapping("/login/{mobNo}/{pass}")
//	    @CrossOrigin
//	    public int signIn(@PathVariable long mobile,@PathVariable String password){
//	        
//	    	int count=phonepayuserRepo.countByMobile(mobile);
//	        if(count!=1) return -1;
//	        PhonePayUser phonePayUser=phonepayuserRepo.findByMobile(mobile);
//	        if(!Objects.equals(phonePayUser.getPassword(), password))  return -1;
//	        return phonePayUser.getId();
//	    	
//	    	//return phonePayUserServices.signIn(mobNo,pass);
//	    }


	    
	    
	    @RequestMapping("verifyLogin/{mobile}/{otp}")
	    public int verifyLogin(@PathVariable long mobile, @PathVariable int otp) {
	        int count = phonepayuserRepo.countByMobile(mobile);
	        
	        if (count != 1) {
	            return -1;  // Mobile number not registered
	        }
	        
	      PhonePayUser user = phonepayuserRepo.findByMobile(mobile);
	        
	        if (user.getOtp() == otp) {
	            return 1;  // OTP verified successfully, proceed to dashboard
	        } else {
	            return -1;  // Incorrect OTP
	        }
	    }
	    
	    
	    //other methods
	    
	    @GetMapping("user/register/{mobNumber}")
	    public boolean userRegister(@PathVariable("mobNumber") long mobileNumber){
	       
	    	int count=bankUserRepo.countByMobile(mobileNumber);
	        int count1=phonepayuserRepo.countByMobile(mobileNumber);
	        if(count1!=0) return false;
	        else if(count>0){
	            List<BankUser> bankUser= bankUserRepo.findByMobile(mobileNumber);
	            BankUser newBankUser=bankUser.get(0);
	            PhonePayUser phonePayUser=new PhonePayUser();
	            phonePayUser.setName(newBankUser.getName());
	            phonePayUser.setMobile(newBankUser.getMobile());
	            Random random=new Random();
	            int otp=1000+random.nextInt(9000);
	            phonePayUser.setOtp(otp);
	            phonepayuserRepo.save(phonePayUser);
	            System.out.println(otp);
	        }
	        else{
	            PhonePayUser phonePayUser=new PhonePayUser();
	            phonePayUser.setName("user");
	            phonePayUser.setMobile(mobileNumber);
	            Random random=new Random();
	            int otp=1000+random.nextInt(9000);
	            phonePayUser.setOtp(otp);
	            phonepayuserRepo.save(phonePayUser);
	            System.out.println(otp);
	        }

	          return true;
	    	
	    	// return phonePayUserServices.userRegister(mobileNumber);
	    }
	    
	    @GetMapping("user/verify/{mobNo}/{otp}")
	    @CrossOrigin
	    public boolean verifyOTP(@PathVariable("mobNo") long mobNo,@PathVariable("otp") int otp){
	        
	    	 PhonePayUser phonePayUser=phonepayuserRepo.findByMobile(mobNo);
	         if(phonePayUser==null) return false;
	         if(phonePayUser.getOtp() == otp) return true;
	         return false;
	    	
	    	//return phonePayUserServices.verifyOTP(mobNo,otp);
	    }
	    
	    @PostMapping("setPass/setPin")
	    @CrossOrigin
	    public int setPassword(@RequestBody String[] str){
	       
	    	  PhonePayUser phonePayUser=phonepayuserRepo.findByMobile(Long.parseLong(str[0]));
	          if(phonePayUser==null) return -1;
	          phonePayUser.setPassword(str[1]);
	          phonePayUser.setPin(Integer.valueOf(str[2]));
	          phonepayuserRepo.save(phonePayUser);
	          return phonePayUser.getId();
	    	
	    	// return phonePayUserServices.setPassword(str);
	    }
	    
	    @RequestMapping("login")
	    @CrossOrigin
//	    public int login(@PathVariable long mobileNo,@PathVariable String pass){
	    	public int signIn(@RequestBody String[] str){
	    	System.out.println(str[0]);
	      
	    	 int count=phonepayuserRepo.countByMobile(Long.parseLong(str[0]));
	         if(count!=1) return -1;
	         PhonePayUser phonePayUser=phonepayuserRepo.findByMobile(Long.parseLong(str[0]));
	         if(!Objects.equals(phonePayUser.getPassword(), str[1]))  return -1;
	         return phonePayUser.getId();
	    	
	    	//  return phonePayUserServices.signIn(mobNo,pass);
	    }
	    @GetMapping("getPhonePayUser/{id}")
	    @CrossOrigin
	    public PhonePayUser getPhonePayUser(@PathVariable int id){
	        
	    	int count=phonepayuserRepo.countById(id);
	        if(count==0) return null;
	        return phonepayuserRepo.findById(id).get();
	    	
	    	//return phonePayUserServices.getPhonePayUser(id);
	    }
	    
	    @RequestMapping("get/all/transactions/{id}")
	    public List<Transactions> getAllTransactions(@PathVariable int id){
	        return transactionsRepo.findByPhonePayUserId(id);
	    }
	    
	    
	    @RequestMapping("get/user")
	    public List<String> getuserInfo(@RequestBody int[] id){
	    	System.out.println("hellos");
	       System.out.println(id);
	    	 LinkedBankAccount ll=linkedBankAccountRepo.findByPhonePayUserIdAndIsPrimary((id[0]),1);
	         BankUser bb=bankUserRepo.findById(ll.getBankUserId()).get();
	         List<String> list=new ArrayList<>();
	         list.add(ll.getUpiId());
	         list.add(Long.toString(ll.getMobile()));
	         list.add(bb.getBankName());
	         list.add(bb.getUserEmail());
	         return list;
	    	
	    	// return phonePayUserServices.getInfo(id);
	    }
	}


              
