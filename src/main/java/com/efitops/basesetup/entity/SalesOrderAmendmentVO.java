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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_order_amendment_basic")
public class SalesOrderAmendmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_amendment_basic_seq")
    @SequenceGenerator(name = "sales_order_amendment_basic_seq", sequenceName = "sales_order_amendment_basic_seq",initialValue = 1000000001, allocationSize = 1)
	
    @Column(name = "sales_order_amendment_id")
    private Long id;

    @ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "salesorder_no")
    private String salesOrderNumber;

    @Column(name = "docdate")
    private LocalDate docDate;

    @Column(name = "party_po_amendment_no")
    private String partyPoAmendmentNo;

    @Column(name = "salesorder_date")
    private LocalDate salesOrderDate;

    @Column(name = "party_po_amendment_date")
    private LocalDate partyPoAmendmentDate;
    
    
    @Column(name = "po_no")
    private String poNo;

    @Column(name = "revision_no")
    private int revisionNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "remarks")
    private String remarks;

   
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "org_id")
	private Long orgId;
	
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "SALESORDERAMENDMENT";
	@Column(name = "screen_code")
	private String screenCode = "SOA";

	@JsonGetter("activeStatus")
	public String getActiveStatus() {
	    return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancelStatus")
	public String getCancelStatus() {
	    return cancel ? "T" : "F";
	}
	
	@OneToMany(mappedBy = "salesOrderAmendmentVO",
	        cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesOrderAmendmentDetailsVO> details = new ArrayList<>();


	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


}