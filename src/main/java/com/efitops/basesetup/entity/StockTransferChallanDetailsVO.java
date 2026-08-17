package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "stock_transfer_chellan_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_transfer_chellan_detailgen")
	@SequenceGenerator(name = "stock_transfer_chellan_detailgen", sequenceName = "stock_transfer_chellan_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stock_transfer_chellan_detail_id")
    private Long id;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "item")
	    private ItemMasterVO item;

	    @Column(name = "tax_type")
	    private String taxType;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "tax_percentage")
	    private GSTRateMasterVO taxPercentage;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "unit")
	    private UnitMasterVO unit;
	    
	    @Column(name = "stock")
	    private String stock;

	    @Column(name = "quantity")
	    private BigDecimal quantity;

	    @Column(name = "rate")
	    private BigDecimal rate;
	    
	    @Column(name = "total_assessable_value")
	    private BigDecimal totalAssessableValue;
	    
	    @Column(name = "amount_in_rs")
	    private BigDecimal amountInRs;
	    
//	    @ManyToOne
//	    @JoinColumn(name = "gst_rate")
//	    private GSTRateMasterVO gstRate;

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
	    
//	    @Column(name="final_amount")
//	    private BigDecimal finalAmount;

	    @ManyToOne
	    @JoinColumn(name = "stock_transfer_chellan_basic_id")
	    @JsonBackReference
	    private StockTransferChallanVO stockTransferChallanVO;


}
