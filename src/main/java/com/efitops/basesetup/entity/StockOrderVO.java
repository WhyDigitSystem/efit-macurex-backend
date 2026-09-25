package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockOrderVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_ordergen")
    @SequenceGenerator(
            name = "stock_ordergen",
            sequenceName = "stock_orderseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "stock_order_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "total_amount", precision = 15, scale = 5)
    private BigDecimal totalAmount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_name")
    private String screenName = "STOCK ORDER";

    @Column(name = "screen_code")
    private String screenCode = "SO";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "stockOrderVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<StockOrderDetailsVO> details = new ArrayList<>();

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
