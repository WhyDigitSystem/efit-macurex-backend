package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "subcontractquotationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractQuotationDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractquotationdetailsgen")
	@SequenceGenerator(name = "subcontractquotationdetailsgen", sequenceName = "subcontractquotationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractquotationdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "part")
	private String part;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "process")
	private String process;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;
	@Column(name = "discountamount", precision = 10, scale = 2)
	private BigDecimal discountAmount;
	@Column(name = "tax", precision = 10, scale = 2)
	private BigDecimal tax;
	@Column(name = "quotationamount", precision = 10, scale = 2)
	private BigDecimal quotationAmount;
//	@Column(name="afterdiscountamount",precision = 10,scale = 2)
//	private BigDecimal afterDiscountAmount;
//	@Column(name="afterquotationamount",precision = 10,scale = 2)
//	private BigDecimal afterQuotationAmount;
	@Column(name = "deliverydate")
	private LocalDate deliveryDate;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "subcontractquotationid", columnDefinition = "BIGINT DEFAULT 0")
	private SubContractQuotationVO subContractQuotationVO;

}
