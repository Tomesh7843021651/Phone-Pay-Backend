package com.example.phonePay.Controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.phonePay.Repo.BankUserRepo;
import com.example.phonePay.Repo.PhonePayUserRepo;
import com.example.phonePay.model.BankUser;
import com.example.phonePay.model.PhonePayUser;

@RestController
@CrossOrigin("http://localhost:4200/")
public class LoginRegisterController {
	@Autowired
	PhonePayUserRepo phonePayUserRepo;
	
	@Autowired
	BankUserRepo bankUserRepo;
	
	@RequestMapping("register/{number}")
	public int register(@PathVariable long number) {
		System.out.println(number);
		PhonePayUser user;
		int count = phonePayUserRepo.countByMobile(number);
		if(count ==1) {
			user = phonePayUserRepo.findByMobile(number);
			Random random = new Random();
			int otp =1000+ random.nextInt(9000);
			user.setOtp(otp);
			
			phonePayUserRepo.save(user);
			
		}
		else if(count ==0) {
			user = new PhonePayUser();
			user.setMobile(number);
			List<BankUser> list = bankUserRepo.findByMobile(number);
			
			if(list == null || list.size() ==0) {
				user.setName("user");
			}
			else {
				BankUser bankuser = list.get(0);
				user.setName( bankuser.getName());
			}
			Random random = new Random();
			int otp =1000+ random.nextInt(9000);
			user.setOtp(otp);
			phonePayUserRepo.save(user);
		}
		else {
			return -1;
		}
		
		System.out.println(user.getOtp());
		return 1;
	}
	
	
	@RequestMapping("verifyotp")
	public int verifyotp(@RequestBody long[] a) {
		long mobile = a[0];
		int otp = (int)a[1];
		int count = phonePayUserRepo.countByMobile(mobile);
		if(count !=1) {
			return -1;
		}
		PhonePayUser ph = phonePayUserRepo.findByMobile(mobile);
		if(ph.getOtp() == otp) {
			return 1;
			
		}
		return -1;
		
	}
	
	@RequestMapping("setpin/{mobile}")
	public int setPin(@PathVariable long mobile,@RequestBody int pin) {
		 
		int count = phonePayUserRepo.countByMobile(mobile);
		if(count !=1) {
			return -1;
		}
		PhonePayUser ph = phonePayUserRepo.findByMobile(mobile);
		ph.setPin(pin);
		phonePayUserRepo.save(ph);
		return 1;
	}
	
//	@RequestMapping("login")
//	public int loginFunction() {
//		
//		return 0;
//		
//	}
	
	Random random = new Random();
	int otp =1000+ random.nextInt(9000);

}
