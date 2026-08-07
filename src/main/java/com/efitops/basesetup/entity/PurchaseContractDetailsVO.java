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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_contract_details")
public class PurchaseContractDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasecontractdetailsgen")
    @SequenceGenerator(name = "purchasecontractdetailsgen", sequenceName = "purchasecontractdetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasecontractdetails_id")
    private Long id;

    // Item Code / Item Description -> both resolved from the same ItemMaster record (like Quotation item lines)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    // HSN/SAC Code -> auto-pulled from the selected Item's hsnCode, stored here for history/audit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hsn_id")
    private HsnVO hsnCode;

    // Tax Type -> List Of Values (e.g. SGST/CGST/IGST/EXEMPT) master
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_type_id")
    private ListOfValuesDetailsVO taxType;

    // Tax (%) -> auto-pulled from TaxDefinition master for the selected Tax Type, editable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_definition_id")
    private TaxDefinitionVO taxDefinition;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    // Unit -> auto-pulled from Item's primary unit, but can be overridden
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    // entered by user
    @Column(name = "rate_in_currency")
    private BigDecimal rateInCurrency;

    // entered by user
    @Column(name = "sgst_rate")
    private BigDecimal sgstRate;

    // calculated = rateInCurrency * sgstRate / 100
    @Column(name = "sgst_amount")
    private BigDecimal sgstAmount;

    // entered by user
    @Column(name = "cgst_rate")
    private BigDecimal cgstRate;

    // calculated = rateInCurrency * cgstRate / 100
    @Column(name = "cgst_amount")
    private BigDecimal cgstAmount;

    // entered by user
    @Column(name = "igst_rate")
    private BigDecimal igstRate;

    // calculated = rateInCurrency * igstRate / 100
    @Column(name = "igst_amount")
    private BigDecimal igstAmount;

    // entered by user, defaults to header Valid From if blank
    @Column(name = "valid_from")
    private LocalDate validFrom;

    // entered by user, defaults to header Valid To if blank
    @Column(name = "valid_to")
    private LocalDate validTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchasecontract_id")
    private PurchaseContractVO purchaseContractVO;
}