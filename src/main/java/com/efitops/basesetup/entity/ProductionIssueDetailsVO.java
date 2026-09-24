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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "production_issue_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionIssueDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_issue_detailsgen")
    @SequenceGenerator(name = "production_issue_detailsgen", sequenceName = "production_issue_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "production_issue_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;


    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit; 

    @Column(name = "available_qty", precision = 10, scale = 2)
    private BigDecimal availableQty; 

    @Column(name = "grn_no")
    private String grnNo; 

    @Column(name = "grn_date")
    private LocalDate grnDate; 

    @Column(name = "int_req_qty", precision = 10, scale = 2)
    private BigDecimal intReqQty; // Int. Req. Qty

    @Column(name = "int_pend_qty", precision = 10, scale = 2)
    private BigDecimal intPendQty; // Int. Pend. Qty

    @Column(name = "issue_qty", precision = 10, scale = 2)
    private BigDecimal issueQty; // Issue Qty

    @Column(name = "item_min_qty", precision = 10, scale = 2)
    private BigDecimal itemMinQty; // Item Min Qty

    @Column(name = "rate", precision = 10, scale = 2)
    private BigDecimal rate; // Rate

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount; // Amount

    @ManyToOne
    @JoinColumn(name = "production_issue_basic_id")
    @JsonBackReference
    private ProductionIssueVO productionIssueVO;
}