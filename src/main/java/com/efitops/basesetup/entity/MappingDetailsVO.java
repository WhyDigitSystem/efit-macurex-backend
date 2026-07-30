package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partyaccdeatils")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyaccdeatilsgen")
	@SequenceGenerator(name = "partyaccdeatilsgen", sequenceName = "partyaccdeatilsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partyaccdeatils_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "party_id")
	private CustomerVO partyId;
	
	@Column(name = "account_name")
	private String accountName;
	
	@ManyToOne
	@JoinColumn(name = "partyacc_id")
	@JsonBackReference
	private MappingOfPartyToAccVO mappingOfPartyToAccVO;
	
}
