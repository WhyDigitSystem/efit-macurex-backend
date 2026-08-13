package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "purchase_short_close_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseShortCloseDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseshortclosedetailsgen")
    @SequenceGenerator(
            name = "purchaseshortclosedetailsgen",
            sequenceName = "purchaseshortclosedetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchaseshortclosedetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    @Column(name = "ordered_qty", precision = 10, scale = 2)
    private BigDecimal orderedQty;

    @Column(name = "supplied_qty", precision = 10, scale = 2)
    private BigDecimal suppliedQty;

    @Column(name = "pending_qty", precision = 10, scale = 2)
    private BigDecimal pendingQty;

    @Column(name = "new_required_qty", precision = 10, scale = 2)
    private BigDecimal newRequiredQty;

    @Column(name = "short_close_qty", precision = 10, scale = 2)
    private BigDecimal shortCloseQty;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "purchaseshortclose_id")
    private PurchaseShortCloseVO purchaseShortCloseVO;
}