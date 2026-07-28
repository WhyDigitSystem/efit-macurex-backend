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
@Table(name = "taxdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDefinitionDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxdetailsgen")
	@SequenceGenerator(name = "taxdetailsgen", sequenceName = "taxdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "taxdetails_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tax_type")
	private ListOfValuesVO taxType; 
	
	@ManyToOne
	@JoinColumn(name = "tax_name")
	private ListOfValuesVO taxName; 
	
	@Column(name = "add_less")
	private String addLess;
	
	@Column(name = "tax_Percent")
	private Double taxPercent;
	
	@Column(name = "tax_id")
	private String taxId;
	
	@Column(name = "formula")
	private String formula;
	
	@Column(name = "post_to_finance")
	private String postToFinance;
	
	@Column(name = "db_cr")
	private String dbCr;
	
	@Column(name = "gl_acc_name")
	private String glAccountName;
	
	@Column(name = "print")
	private String print;
	
	@Column(name = "tax_post")
	private String taxPost;
	
	@Column(name = "screencode", length = 30)
	private String screenCode = "TDD";
	@Column(name = "screenname", length = 30)
	private String screenName = "TaxDefinitionDetails";
	
	
	@ManyToOne
	@JoinColumn(name = "taxbasic_id")
	@JsonBackReference
	private TaxDefinitionVO taxDefinitionVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
		

}
