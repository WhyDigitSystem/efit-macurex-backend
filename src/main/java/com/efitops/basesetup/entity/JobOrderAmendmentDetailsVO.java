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
@Table(name = "job_order_amendment_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderAmendmentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_order_amendment_details_gen")
    @SequenceGenerator(
            name = "job_order_amendment_details_gen",
            sequenceName = "job_order_amendment_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "job_order_amendment_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "old_qty")
    private BigDecimal oldQty;

    @Column(name = "new_qty")
    private BigDecimal newQty;

    @ManyToOne
    @JoinColumn(name = "job_order_amendment_id")
    @JsonBackReference
    private JobOrderAmendmentVO jobOrderAmendment;

}
