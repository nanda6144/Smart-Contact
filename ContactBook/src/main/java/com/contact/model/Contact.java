package com.contact.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Contact_list")
public class Contact{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cid")
	private int cId;
	private String cName;
	private String nName;
	
	@Column(name = "c_phone_number")
	private long cPhoneNumber;
	@Column(name = "cEmail")
	private String cEmail;
	private String relation;
    private String birthDay;
    
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cuid",
	referencedColumnName="uid")
	private User user;
	
	
}
