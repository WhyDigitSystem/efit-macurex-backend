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
@Table(name = "indent_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indent_detailgen")
    @SequenceGenerator(name = "indent_detailgen", sequenceName = "indent_detailseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "indent_detail_id")
    private Long id;

    // Item is a full reference to ItemMasterVO - itemCode / itemDescription 
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;
    
    @ManyToOne
    @JoinColumn(name = "primary_unit")
    private UnitMasterVO primaryUnit;
    
    @ManyToOne
    @JoinColumn(name = "purchase_unit")
    private UnitMasterVO purchaseUnit;
    
  

    @Column(name = "qtyinprimary_unit")
    private BigDecimal qtyInPrimaryUnit;
    
    @ManyToOne
    @JoinColumn(name = "conversion_Factor")
    private UomConversionVO conversionFactor;

    @Column(name = "qtyinpurchase_unit")
    private BigDecimal qtyInPurchaseUnit;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "purpose", length = 500)
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "indent_basic_id")
    @JsonBackReference
    private PurchaseIndentVO purchaseIndentVO;
}