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
@Table(name = "import_purchase_bill_tax_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillTaxDetailsVO {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "import_purchase_bill_tax_details_seq")
	    @SequenceGenerator(
	            name = "import_purchase_bill_tax_details_seq",
	            sequenceName = "import_purchase_bill_tax_details_seq",
	            initialValue = 1000000001,
	            allocationSize = 1)
	    private Long id;

	    @Column(name = "particulars")
	    private String particulars;

	    @Column(name = "tax", precision = 10, scale = 2)
	    private BigDecimal tax;

	    @Column(name = "taxval1", precision = 18, scale = 4)
	    private BigDecimal taxval1;

	    @Column(name = "tax_amount", precision = 18, scale = 4)
	    private BigDecimal taxAmount;

	    @Column(name = "db_cr")
	    private String dbCr;

	    @Column(name = "gl_subledger")
	    private String glSubledger;

	    @ManyToOne
	    @JoinColumn(name = "purchasebill_id")
	    @JsonBackReference
	    private PurchaseBillVO purchaseBillVO;
	    
}
