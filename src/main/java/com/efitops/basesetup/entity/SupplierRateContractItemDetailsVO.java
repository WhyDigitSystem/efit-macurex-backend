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
@Table(name = "supplier_rate_contract_item_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractItemDetailsVO {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "supplier_rate_contract_item_detailsgen"
    )
    @SequenceGenerator(
            name = "supplier_rate_contract_item_detailsgen",
            sequenceName = "supplier_rate_contract_item_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "supplier_rate_contract_item_details_id")
    private Long id;


    // Incoming Item Code

    @ManyToOne
    @JoinColumn(name = "incoming_item_code")
    private ItemMasterVO incomingItemCode;


    @Column(name = "incoming_item_description")
    private String incomingItemDescription;


    // Purchase Unit

    @ManyToOne
    @JoinColumn(name = "purchase_unit")
    private UnitMasterVO purchaseUnit;


    @Column(name = "plating_type")
    private String platingType;


    @Column(name = "thickness")
    private BigDecimal thickness;


    @Column(name = "rate")
    private BigDecimal rate;


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


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "tool_amortization_rate")
    private BigDecimal toolAmortizationRate;


    // Header Mapping

    @ManyToOne
    @JoinColumn(name = "supplier_rate_contract_id")
    @JsonBackReference
    private SupplierRateContractVO supplierRateContractVO;

}