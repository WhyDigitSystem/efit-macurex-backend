package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "import_purchase_bill_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportPurchaseDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "import_purchase_bill_detailsgen")
	@SequenceGenerator(name = "import_purchase_bill_detailsgen", sequenceName = "import_purchase_bill_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "import_purchase_bill_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "challan_qty")
	private BigDecimal challanQty;
	
	@Column(name = "grn_qty")
	private BigDecimal grnQty;
	
	@Column(name = "accpt_qty")
	private BigDecimal accptQty;
	
	@Column(name = "shortage_qty")
	private BigDecimal shortageQty;
	
	

}
