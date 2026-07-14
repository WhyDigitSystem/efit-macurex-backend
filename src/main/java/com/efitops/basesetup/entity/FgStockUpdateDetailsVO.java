package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fgstockupdatedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FgStockUpdateDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fgstockupdatedetailsgen")
	@SequenceGenerator(name = "fgstockupdatedetailsgen", sequenceName = "fgstockupdatedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "fgstockupdatedetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "part")
	private String part;

	@Column(name = "partdesc")
	private String partDesc;

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	
	@Column(name = "actualqty", precision = 10, scale = 2)
	private BigDecimal actualQty;
	
	@Column(name = "availableqty", precision = 10, scale = 2)
	private BigDecimal availableQty;

	@Column(name = "unit")
	private String unit;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;


	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@OneToOne
	@JoinColumn(name = "finalfgpartstockupdateid")
	@JsonBackReference
	private FinalFgPartStockUpdateVO finalFgPartStockUpdateVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
