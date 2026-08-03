package com.efitops.basesetup.entity;

import java.time.LocalDate;

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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquirydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnquiryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirydetailsgen")
	@SequenceGenerator(name = "enquirydetailsgen", sequenceName = "enquirydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "enquirydetails_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "item_code")
	 private ItemMasterVO itemcode;
	 
	 @Column(name = "item_description")
	 private String  itemdescription;
	 @Column(name = "annual_quantity")
	 private Integer annualquantity;
	 @Column(name = "dlry_date")
	 private LocalDate   dlrydate;
	 @Column(name = "needrd_approval")
	 private String  needrdapproval;
	 @Column(name = "quote_duedate")
	 private LocalDate   quoteduedate;
	 @Column(name = "remarks")
	 private String  remarks;
	 
    
     @ManyToOne
	 @JoinColumn(name = "enquiry_id")
	 @JsonBackReference
	 private EnquiryVO enquiryVO;
     
	}
	 
	 
	 
	 
	 
	 

	
	
	
	


