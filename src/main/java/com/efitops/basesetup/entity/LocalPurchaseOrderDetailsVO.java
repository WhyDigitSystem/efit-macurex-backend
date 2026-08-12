package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "local_purchase_order_details")
public class LocalPurchaseOrderDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localpurchaseorderdetailsgen")
    @SequenceGenerator(name = "localpurchaseorderdetailsgen", sequenceName = "localpurchaseorderdetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "localpurchaseorderdetails_id")
    private Long id;

    // Indent No / Indent Date resolved via this link's parent PurchaseIndentVO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indent_details_id")
    private PurchaseIndentDetailsVO indentDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @Column(name = "customer_part_no")
    private String customerPartNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hsn_id")
    private HsnVO hsnCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_type_id")
    private ListOfValuesDetailsVO taxType;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_unit_id")
    private UnitMasterVO purchaseUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_unit_id")
    private UnitMasterVO primaryUnit;

    @Column(name = "conversion_factor")
    private BigDecimal conversionFactor;

    // Snapshotted at save-time from the linked indent line
    @Column(name = "indent_qty")
    private BigDecimal indentQty;

    // Server-calculated: indentQty minus what's already placed on other LPOs against the same indent line
    @Column(name = "pending_indent_qty")
    private BigDecimal pendingIndentQty;

    @Column(name = "po_qty_in_purchase_unit")
    private BigDecimal poQtyInPurchaseUnit;

    // Server-calculated: poQtyInPurchaseUnit * conversionFactor
    @Column(name = "qty_in_primary_unit")
    private BigDecimal qtyInPrimaryUnit;

    @Column(name = "rate_in_inr")
    private BigDecimal rateInInr;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    // Server-calculated: (poQtyInPurchaseUnit * rateInInr) * discountPercent / 100
    @Column(name = "discount_amount_inr")
    private BigDecimal discountAmountInr;

    // Server-calculated: (poQtyInPurchaseUnit * rateInInr) - discountAmountInr
    @Column(name = "amount_in_inr")
    private BigDecimal amountInInr;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localpurchaseorder_id")
    private LocalPurchaseOrderVO localPurchaseOrderVO;
}