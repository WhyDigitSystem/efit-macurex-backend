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
@Table(name = "local_purchase_order_tax_details")
public class LocalPurchaseOrderTaxDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localpurchaseordertaxdetailsgen")
    @SequenceGenerator(name = "localpurchaseordertaxdetailsgen", sequenceName = "localpurchaseordertaxdetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "localpurchaseordertaxdetails_id")
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "tax_percent")
    private BigDecimal taxPercent;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localpurchaseorder_id")
    private LocalPurchaseOrderVO localPurchaseOrderVO;
}