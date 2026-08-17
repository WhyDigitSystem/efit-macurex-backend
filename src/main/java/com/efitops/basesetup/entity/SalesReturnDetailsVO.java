package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_return_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesReturnDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_return__detailseq")
	@SequenceGenerator(name = "sales_return_detail_seq",sequenceName = "sales_return_detail_seq",allocationSize = 1)
	@Column(name = "sales_return_detail_id")
	private Long id;
	
	
    @ManyToOne
	@JoinColumn(name = "item_")
	private ItemMasterVO item;
    
//    @Column(name = "item_description")
//    private String itemDescription;
//
    @Column(name = "hsn_sac_code")
    private String hsnSacCode;
    
    
    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_percentage")
    private String taxPercentage;
    
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    @Column(name = "stock")
    private BigDecimal stock;

    @Column(name = "qty_sold")
    private BigDecimal qtySold;

    @Column(name = "received_qty")
    private BigDecimal receivedQty;
    
    @Column(name = "rate")
    private BigDecimal rate;
    
    @Column(name = "rate_selected_currency")
    private BigDecimal rateInSelectedCurrency;

    @Column(name = "amount_selected_currency")
    private BigDecimal amountInSelectedCurrency;

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
    
  //header mapping
  	@ManyToOne
  	@JoinColumn(name = "sales_return_basic_id")
  	private SalesReturnVO salesReturn;
  	
}
    
    
    
    

	
	
	 
	 
	
	
	


