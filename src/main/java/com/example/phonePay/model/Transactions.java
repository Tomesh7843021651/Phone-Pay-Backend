package com.example.phonePay.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor


public class Transactions {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	
	 
	    Integer Id;
	Double afterBalance;
	Double balance;
	Integer bankId;
	String commit;
	String crdp;
	Date  currDate;
    Integer phonePayUserId;

}
