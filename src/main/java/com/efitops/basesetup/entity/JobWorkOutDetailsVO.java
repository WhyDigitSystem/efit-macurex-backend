package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.Date;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobworkoutdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobWorkOutDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobworkoutdetailsgen")
	@SequenceGenerator(name = "jobworkoutdetailsgen", sequenceName = "jobworkoutdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "jobworkoutdetailsid")
	private Long id;
	@Column(name = "part", length = 150)
	private String part;
	@Column(name="partdesc")
	private String partDesc;
	@Column(name="process")
	private String process;
	@Column(name="taxcode")
	private String taxCode;
	@Column(name="quantitynos",precision = 10,scale = 2)
	private BigDecimal quantityNos;
	@Column(name="dueon")
	private Date dueOn;
	@Column(name="rate",precision = 10,scale = 2)

	private BigDecimal rate; 
	@Column(name="grossamt",precision = 10,scale = 2)
	private BigDecimal grossAmt;
	@Column(name="discount",precision = 10,scale = 2)
	private BigDecimal discount;
	@Column(name="discountamount",precision = 10,scale = 2)
	private BigDecimal discountAmount;
	@Column(name="netamount",precision = 10,scale = 2)
	private BigDecimal netAmount;
	@Column(name="sgst",precision = 10,scale = 2)
	private BigDecimal sgst;
	@Column(name="cgst",precision = 10,scale = 2)
	private BigDecimal cgst;
	@Column(name="igst",precision = 10,scale = 2)
	private BigDecimal igst;
	@Column(name="taxamt",precision = 10,scale = 2)
	private BigDecimal taxAmt;
	@Column(name="amount",precision = 10,scale = 2)
	private BigDecimal amount;
		
	@Column(name = "screencode", length = 5)
	private String screenCode = "WJO";
	@Column(name = "screenname", length = 30)
	private String screenName = "JOB WORKOUT ORDER";
	
	@ManyToOne
	@JoinColumn(name = "jobworkoutid")
	@JsonBackReference
	private JobWorkOutVO jobWorkOutVO;

	

}
