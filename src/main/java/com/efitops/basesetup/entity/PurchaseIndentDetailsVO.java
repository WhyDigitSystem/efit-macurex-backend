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
@Table(name = "purchaseindentdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseIndentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseindentdetailsgen")
    @SequenceGenerator(
            name = "purchaseindentdetailsgen",
            sequenceName = "purchaseindentdetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchaseindentdetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "qty_in_primary_unit", precision = 10, scale = 2)
    private BigDecimal qtyInPrimaryUnit;

    @Column(name = "conversion_factor", precision = 10, scale = 2)
    private BigDecimal conversionFactor;

    @Column(name = "qty_in_purchase_unit", precision = 10, scale = 2)
    private BigDecimal qtyInPurchaseUnit;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "purpose", length = 500)
    private String purpose;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchaseindent_id")
    private PurchaseIndentVO purchaseIndentVO;
}