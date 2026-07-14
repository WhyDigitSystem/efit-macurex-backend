package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partystate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyStateVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partystategen")
	@SequenceGenerator(name = "partystategen", sequenceName = "partystateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partystateid")
	private Long id;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "state")
	private String state;

	@Column(name = "gstin")
	private String gstIn;

	@Column(name = "stateno")
	private Long stateNo;

	@Column(name = "contactperson")
	private String contactPerson;

	@Column(name = "contactphoneno")
	private String contactPhoneNo; 

	@Column(name = "email")
	private String email;

	@Column(name = "statecode")
	private String stateCode;

//	@Column(name = "partyname")
//	private String partyName;
	
	
	
	@ManyToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}