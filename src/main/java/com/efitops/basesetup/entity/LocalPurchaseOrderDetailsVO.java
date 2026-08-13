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
@Table(name = "local_purchase_order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalPurchaseOrderDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localpurchaseorderdetailsgen")
    @SequenceGenerator(
            name = "localpurchaseorderdetailsgen",
            sequenceName = "localpurchaseorderdetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "localpurchaseorderdetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "indent_details_id")
    private PurchaseIndentDetailsVO indentDetails;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @Column(name = "customer_part_no")
    private String customerPartNo;

    @ManyToOne
    @JoinColumn(name = "hsn_id")
    private HsnVO hsnCode;

    @ManyToOne
    @JoinColumn(name = "tax_type_id")
    private ListOfValuesDetailsVO taxType;

    @Column(name = "tax_percent", precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @ManyToOne
    @JoinColumn(name = "purchase_unit_id")
    private UnitMasterVO purchaseUnit;

    @ManyToOne
    @JoinColumn(name = "primary_unit_id")
    private UnitMasterVO primaryUnit;

    @Column(name = "conversion_factor", precision = 10, scale = 2)
    private BigDecimal conversionFactor;

    @Column(name = "indent_qty", precision = 10, scale = 2)
    private BigDecimal indentQty;

    @Column(name = "pending_indent_qty", precision = 10, scale = 2)
    private BigDecimal pendingIndentQty;

    @Column(name = "po_qty_in_purchase_unit", precision = 10, scale = 2)
    private BigDecimal poQtyInPurchaseUnit;

    @Column(name = "qty_in_primary_unit", precision = 10, scale = 2)
    private BigDecimal qtyInPrimaryUnit;

    @Column(name = "rate_in_inr", precision = 10, scale = 2)
    private BigDecimal rateInInr;

    @Column(name = "discount_percent", precision = 10, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "discount_amount_inr", precision = 10, scale = 2)
    private BigDecimal discountAmountInr;

    @Column(name = "amount_in_inr", precision = 10, scale = 2)
    private BigDecimal amountInInr;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "localpurchaseorder_id")
    private LocalPurchaseOrderVO localPurchaseOrderVO;
}