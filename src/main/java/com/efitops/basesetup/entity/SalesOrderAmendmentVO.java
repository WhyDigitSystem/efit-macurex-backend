package com.efitops.basesetup.entity;

import java.time.LocalDate;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salesorderamendment")
public class SalesOrderAmendmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_amendment_seq")
    @SequenceGenerator(name = "sales_order_amendment_seq", sequenceName = "sales_order_amendment_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

    @Column(name = "so_amendment_no")
    private String soAmendmentNo;

    @Column(name = "s_o_no")
    private String soNumber;

    @Column(name = "amendment_date")
    private LocalDate amendmentDate;

    @Column(name = "party_po_amendment_no")
    private String partyPoAmendmentNo;

    @Column(name = "sales_order_date")
    private LocalDate salesOrderDate;

    @Column(name = "party_po_amendment_date")
    private LocalDate partyPoAmendmentDate;
    
    @Column(name = "summary")
    private LocalDate summary;
    
    @Column(name = "po_no")
    private String poNo;

    @Column(name = "revision_no")
    private Integer revisionNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "active")
    private Boolean active;
    
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

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


}