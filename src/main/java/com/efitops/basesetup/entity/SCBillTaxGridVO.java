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
@Table(name = "sc_bill_tax_grid")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SCBillTaxGridVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_bill_tax_gridgen")
	@SequenceGenerator(name = "sc_bill_tax_gridgen", sequenceName = "sc_bill_tax_gridseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sc_bill_tax_grid_id")
	private Long id;
	
    @Column(name = "particulars")
    private String particulars;

    @Column(name = "gl_account_name")
    private String glAccountName;

    @Column(name = "accepted_qty_amount")
    private BigDecimal acceptedQtyAmount;

    @Column(name = "revised_amount")
    private BigDecimal revisedAmount;

    @ManyToOne
    @JoinColumn(name = "sc_bill_basic_id")
    @JsonBackReference
    private SCBillVO sCBillVO;

}
