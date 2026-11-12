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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "docket_invoice_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docket_invoice_basicgen")
	@SequenceGenerator(name = "docket_invoice_basicgen", sequenceName = "docket_invoice_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "docket_invoice_basic_id")
    private Long id;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate=LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "transport_id")
	private TransportMasterVO transport;

	
	@Column(name = "bill_no")
	private String billNo;
	
	@Column(name = "bill_date")
	private LocalDate billDate;
	
	@Column(name = "total_amount")
	private int totalAmount;
	
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name = "financial_year")
	 private String  financialYear;
	
	@Column(name = "screen_name")
	private String screenName="DOCKET INVOICE DETAILS";
	@Column(name = "screen_code")
	private String screenCode="DID";
	
	

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	 @OneToMany(
	            mappedBy = "docketInvoiceVO",
	            cascade = CascadeType.ALL)
	 @JsonManagedReference
	    private List<DocketInvoiceDetailsVO> details =
	            new ArrayList<>();


	
	
	

}
