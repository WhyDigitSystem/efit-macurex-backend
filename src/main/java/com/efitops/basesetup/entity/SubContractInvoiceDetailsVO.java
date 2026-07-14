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
@Table(name = "subcontractinvoicedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubContractInvoiceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontractinvoicedetailsgen")
	@SequenceGenerator(name = "subcontractinvoicedetailsgen", sequenceName = "subcontractinvoicedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "subcontractinvoicedetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdes")
	private String partDes;
	@Column(name = "process")
	private String process;
	@Column(name = "units")
	private String units;
	@Column(name = "quantitynos", precision = 10, scale = 2)
	private BigDecimal quantityNos;
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	@Column(name = "cgst", precision = 10, scale = 2)
	private BigDecimal cgst;
	@Column(name = "sgst", precision = 10, scale = 2)
	private BigDecimal sgst;
	@Column(name = "landedamount", precision = 10, scale = 2)
	private BigDecimal landedAmount;
	@Column(name = "quotationamount", precision = 10, scale = 2)
	private BigDecimal quotationAmount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "subcontractinvoiceid", columnDefinition = "BIGINT DEFAULT 0")
	private SubContractInvoiceVO subContractInvoiceVO;

}
