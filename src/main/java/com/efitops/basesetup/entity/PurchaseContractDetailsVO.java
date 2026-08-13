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
@Table(name = "purchase_contract_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseContractDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasecontractdetailsgen")
    @SequenceGenerator(
            name = "purchasecontractdetailsgen",
            sequenceName = "purchasecontractdetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasecontractdetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "hsn_id")
    private HsnVO hsnCode;

    @ManyToOne
    @JoinColumn(name = "tax_type_id")
    private ListOfValuesDetailsVO taxType;

    @ManyToOne
    @JoinColumn(name = "tax_definition_id")
    private TaxDefinitionVO taxDefinition;

    @Column(name = "tax_percent", precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    @Column(name = "rate_in_currency", precision = 10, scale = 2)
    private BigDecimal rateInCurrency;

    @Column(name = "sgst_rate", precision = 10, scale = 2)
    private BigDecimal sgstRate;

    @Column(name = "sgst_amount", precision = 10, scale = 2)
    private BigDecimal sgstAmount;

    @Column(name = "cgst_rate", precision = 10, scale = 2)
    private BigDecimal cgstRate;

    @Column(name = "cgst_amount", precision = 10, scale = 2)
    private BigDecimal cgstAmount;

    @Column(name = "igst_rate", precision = 10, scale = 2)
    private BigDecimal igstRate;

    @Column(name = "igst_amount", precision = 10, scale = 2)
    private BigDecimal igstAmount;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchasecontract_id")
    private PurchaseContractVO purchaseContractVO;
}