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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier_rate_contract_amendment_item_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractAmendmentItemDetailsVO {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "supplier_rate_contract_amendment_item_detailsgen"
    )
    @SequenceGenerator(
            name = "supplier_rate_contract_amendment_item_detailsgen",
            sequenceName = "supplier_rate_contract_amendment_item_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "supplier_rate_contract_amendment_item_details_id")
    private Long id;

    // Item Code
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    // Unit
    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    // Old Rate
    @Column(name = "old_rate")
    private BigDecimal oldRate;

    // New Rate
    @Column(name = "new_rate")
    private BigDecimal newRate;

    // Header Reference
    @ManyToOne
    @JoinColumn(name = "supplier_rate_contract_amendment_id")
    @JsonBackReference
    private SupplierRateContractAmendmentVO supplierRateContractAmendmentVO;
}

