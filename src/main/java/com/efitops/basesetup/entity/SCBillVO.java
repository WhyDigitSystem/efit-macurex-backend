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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sc_bill_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SCBillVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_bill_basicgen")
	@SequenceGenerator(name = "sc_bill_basicgen", sequenceName = "sc_bill_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sc_bill_basic_id")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	 
	 @Column(name = "doc_id")
	 private String docId;

	 @Column(name = "doc_date")
	 private LocalDate docDate = LocalDate.now();
	 
	 
	 @ManyToOne
	 @JoinColumn(name = "department")
	 private DepartmentVO department;
	 
	 @ManyToOne
	 @JoinColumn(name = "vendor_name")
	 private CustomerVO vendorName;

	    @Column(name = "vendor_id")
	    private String vendorId;

	    @Column(name = "gst_state")
	    private String gstState;

	    @Column(name = "is_gst_applicable")
	    private Boolean isGstApplicable;

	    @Column(name = "vendor_invoice_no")
	    private String vendorInvoiceNo;

	    @Column(name = "vendor_dc_no")
	    private String vendorDcNo;

	    @Column(name = "gstn_no")
	    private String gstnNo;

	    @Column(name = "vendor_invoice_date")
	    private LocalDate vendorInvoiceDate;

	    @Column(name = "service_name")
	    private String serviceName;

	    @Column(name = "grn_no")
	    private String grnNo;

	    @Column(name = "hsn_sac_code")
	    private String hsnSacCode;

	    @Column(name = "contract_no")
	    private String contractNo;

	    @Column(name = "tax_type")
	    private String taxType;

	    @Column(name = "tax_percentage")
	    private BigDecimal taxPercentage;

	    @Column(name = "belongs_to")
	    private String belongsTo;
	    
	    
	    //SUMMARY
	    
	    private BigDecimal freight;

	    private BigDecimal totalAmount;

	    private BigDecimal totalBasic;

	    private String tdsApplicable;

	    private BigDecimal tdsPercentage;

	    private BigDecimal acceptedVal;

	    private BigDecimal rejectedVal;

	    private BigDecimal tdsAmount;

	    private String amountInWords;

	    private String remarks;
	    
	    
	    //default feilds
	    
	    
	    @Column(name = "active")
		private boolean active;
	    
	    @Column(name = "org_id")
		private Long orgId;
	    
	    @Column(name = "financial_year")
	    private String financialYear;

		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel = false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName = "SCBILL";
		@Column(name = "screen_code")
		private String screenCode = "SCB";
		
		
		@OneToMany(mappedBy = "sCBillVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<SCBillDetailVO> sCBillDetailVO = new ArrayList<>();
		
		@OneToMany(mappedBy = "sCBillVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<SCBillTaxGridVO> sCBillTaxGridVO = new ArrayList<>();
		
		@JsonGetter("activeStatus")
		public String getActiveStatus() {
			return active ? "Active" : "In-Active";
		}

		@JsonGetter("cancelStatus")
		public String getCancelStatus() {
			return cancel ? "T" : "F";
		}

		@Embedded
		private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
		
		



	 
	

}
