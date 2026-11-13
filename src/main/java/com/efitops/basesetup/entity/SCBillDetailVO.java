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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sc_bill_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SCBillDetailVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_bill_detailgen")
	@SequenceGenerator(name = "sc_bill_detailgen", sequenceName = "sc_bill_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sc_bill_detail_id")
	private Long id;

	    @Column(name = "sc_grn_no")
	    private String scGrnNo;

	    @ManyToOne
	    @JoinColumn(name = "incoming_item_code")
	    private ItemMasterVO incomingItemCode;

	    @Column(name = "incoming_item_description")
	    private String incomingItemDescription;

	    @ManyToOne
	    @JoinColumn(name = "unit")
	    private UnitMasterVO unit;

	    @Column(name = "challan_qty")
	    private BigDecimal challanQty;

	    @Column(name = "received_qty")
	    private BigDecimal receivedQty;

	    @Column(name = "shortage_qty")
	    private BigDecimal shortageQty;

	    @Column(name = "grn_accepted_qty")
	    private BigDecimal grnAcceptedQty;

	    @Column(name = "rejected_qty")
	    private BigDecimal rejectedQty;

	    @Column(name = "rate")
	    private BigDecimal rate;

	    @Column(name = "amount")
	    private BigDecimal amount;

	    @Column(name = "sgst_rate")
	    private BigDecimal sgstRate;

	    @Column(name = "sgst_amount")
	    private BigDecimal sgstAmount;

	    @Column(name = "cgst_rate")
	    private BigDecimal cgstRate;

	    @Column(name = "cgst_amount")
	    private BigDecimal cgstAmount;

	    @Column(name = "igst_rate")
	    private BigDecimal igstRate;

	    @Column(name = "igst_amount")
	    private BigDecimal igstAmount;

	    @Column(name = "supplier_dc_no")
	    private String supplierDcNo;

	    @Column(name = "supplier_dc_date")
	    private LocalDate supplierDcDate;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "sc_bill_basic_id")
	    @JsonBackReference
	    private SCBillVO sCBillVO;


}
