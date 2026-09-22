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
@Table(name = "production_bulk_issues_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssuesDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_bulk_issues_detailsgen")
    @SequenceGenerator(
            name = "production_bulk_issues_detailsgen",
            sequenceName = "production_bulk_issues_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "production_bulk_issues_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "available_qty", precision = 15, scale = 5)
    private BigDecimal availableQty;

    @Column(name = "indent_req_qty", precision = 15, scale = 5)
    private BigDecimal indentReqQty;

    @Column(name = "indent_pending_qty", precision = 15, scale = 5)
    private BigDecimal indPendingQty;

    @Column(name = "issue_qty", precision = 15, scale = 5)
    private BigDecimal issueQty;

    @Column(name = "rate", precision = 15, scale = 5)
    private BigDecimal rate;

    @Column(name = "amount", precision = 15, scale = 5)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "production_bulk_issues_id")
    @JsonBackReference
    private ProductionBulkIssuesVO productionBulkIssuesVO;
}