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
@Table(name = "issue_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuesDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "issue_detailgen")
    @SequenceGenerator(name = "issue_detailgen",sequenceName = "issue_detailseq",initialValue = 1000000001, allocationSize = 1)
    @Column(name = "issue_detail_id")
    private Long id;

    @ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

    @Column(name = "qty_available")
    private BigDecimal qtyAvailable;

    @Column(name = "indent_qty")
    private BigDecimal indentQty;

    @Column(name = "previously_issued_qty")
    private BigDecimal previouslyIssuedQty;

    @Column(name = "pending_qty")
    private BigDecimal pendingQty;

    @Column(name = "qty")
    private BigDecimal qty;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "amount")
    private BigDecimal amount;
    
    
    @ManyToOne
	@JsonBackReference
	@JoinColumn(name = "issue_basic_id")
    private IssuesVO issuesVO;
    
    
}