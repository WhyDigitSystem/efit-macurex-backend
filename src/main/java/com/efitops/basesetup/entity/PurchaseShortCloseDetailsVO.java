package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_short_close_details")
public class PurchaseShortCloseDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseshortclosedetailsgen")
    @SequenceGenerator(name = "purchaseshortclosedetailsgen", sequenceName = "purchaseshortclosedetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchaseshortclosedetails_id")
    private Long id;

    // Item Description comes from here, same as every other details grid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemMasterVO item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private UnitMasterVO unit;

    @Column(name = "ordered_qty")
    private BigDecimal orderedQty;

    @Column(name = "supplied_qty")
    private BigDecimal suppliedQty;

    // Ordered Qty - Supplied Qty, floored at 0 - always server-calculated
    @Column(name = "pending_qty")
    private BigDecimal pendingQty;

    @Column(name = "new_required_qty")
    private BigDecimal newRequiredQty;

    // Pending Qty - New Required Qty, floored at 0 - always server-calculated
    @Column(name = "short_close_qty")
    private BigDecimal shortCloseQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseshortclose_id")
    private PurchaseShortCloseVO purchaseShortCloseVO;
}