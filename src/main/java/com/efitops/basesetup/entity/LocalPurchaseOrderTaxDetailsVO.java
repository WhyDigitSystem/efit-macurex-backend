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
@Table(name = "local_purchase_order_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalPurchaseOrderTaxDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localpurchaseordertaxdetailsgen")
    @SequenceGenerator(
            name = "localpurchaseordertaxdetailsgen",
            sequenceName = "localpurchaseordertaxdetailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "localpurchaseordertaxdetails_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "tax_percent", precision = 10, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "localpurchaseorder_id")
    private LocalPurchaseOrderVO localPurchaseOrderVO;
}