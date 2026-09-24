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
@Table(name = "bulk_issue_indent_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkIssueIndentDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bulk_issue_indent_detailsgen")
    @SequenceGenerator(
            name = "bulk_issue_indent_detailsgen",
            sequenceName = "bulk_issue_indent_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(
            name = "bulk_issue_indent_details_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @Column(name = "req_qty", precision = 15, scale = 5)
    private BigDecimal reqQty;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "purpose")
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "bulk_issue_indent_id")
    @JsonBackReference
    private BulkIssueIndentVO bulkIssueIndentVO;
}
