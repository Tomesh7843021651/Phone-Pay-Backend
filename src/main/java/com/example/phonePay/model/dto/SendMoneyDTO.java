package com.example.phonePay.model.dto;

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
public class SendMoneyDTO {

		@Id
		@GeneratedValue(strategy =  GenerationType.IDENTITY)
		 int Id;
		String receiverUpiId;
		double sendAmount;
		int senderBankId;
	    int senderId;
	    int upiPin;

}
