package com.example.phonePay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Setter@Getter@ToString@NoArgsConstructor@AllArgsConstructor
public class LinkedBankAccount {
		@Id
		@GeneratedValue(strategy =  GenerationType.IDENTITY)
	int id;
	int accountNumber;
	double balance;
	String ifscode;
	long mobile;
	String name;
	//other ones
	int phonePayUserId;
	int bankUserId;
	int isDeleted;
	int isPrimary;
	int otp;
	String upiId;
	 int upiPin;
}
