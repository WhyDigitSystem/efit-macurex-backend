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
@Table(name = "purchaseindentdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseindentdetailsgen")
    @SequenceGenerator(name = "purchaseindentdetailsgen", sequenceName = "purchaseindentdetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchaseindentdetails_id")
    private Long id;

    // Item is a full reference to ItemMasterVO - itemCode / itemDescription /
    // primaryUnit / purchaseUnit are all pulled from here, never stored again.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "qty_in_primary_unit")
    private BigDecimal qtyInPrimaryUnit;

    @Column(name = "conversion_factor")
    private BigDecimal conversionFactor;

    @Column(name = "qty_in_purchase_unit")
    private BigDecimal qtyInPurchaseUnit;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "purpose", length = 500)
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "purchaseindent_id")
    @JsonBackReference
    private PurchaseIndentVO purchaseIndentVO;
}