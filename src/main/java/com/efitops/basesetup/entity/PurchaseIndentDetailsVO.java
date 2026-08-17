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
@Table(name = "Indent_Detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Indent_Detailgen")
    @SequenceGenerator(name = "Indent_Detailgen", sequenceName = "Indent_Detailseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "Indent_Detail_id")
    private Long id;

    // Item is a full reference to ItemMasterVO - itemCode / itemDescription / primaryUnit / purchaseUnit 
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

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
    @JoinColumn(name = "Indent_Basic_id")
    @JsonBackReference
    private PurchaseIndentVO purchaseIndentVO;
}