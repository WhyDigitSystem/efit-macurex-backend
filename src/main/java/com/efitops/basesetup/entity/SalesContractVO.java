package com.efitops.basesetup.entity;

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
@Table(name = "sales_contract")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_contractgen")
    @SequenceGenerator(name = "sales_contractgen",
            sequenceName = "sales_contractseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "salescontract_id")
    private Long id;


    @Column(name = "customer_contract_no")
    private String customerContractNo;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "with_quotation")
    private String withQuotation;

    @Column(name = "invoice_type")
    private String invoiceType;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;

    @Column(name = "quotation_no")
    private String quotationNo;
    
    @Column(name = "quotation_date")
    private LocalDate quotationDate;
    
    @Column(name = "customer_purchase_order_no")
    private String customerPoNo;

    @Column(name = "customer_purchase_order_date")
    private LocalDate customerPoDate;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "post_rate")
    private String postRate;

    @Column(name = "is_igst_applicable")
    private String isIgstApplicable;


    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

	@Column(name = "screen_code", length = 30)
	private String screenCode = "SAC";
	@Column(name = "screen_name", length = 30)
	private String screenName = "SALESCONTRACT";
	
	
	@OneToMany(mappedBy = "salesContract",
	        cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesContractDetailsVO> salesContractDetailsVO = new ArrayList<>();
	
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
