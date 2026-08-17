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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_order_short_close_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderShortCloseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_short_close_basicgen")
	@SequenceGenerator(name = "sales_order_short_close_basicgen", sequenceName = "sales_order_short_close_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sales_order_short_close_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@ManyToOne
	@JoinColumn(name = "sale_order_no")
	private OrderAcceptanceVO saleOrderNo;


	// Common Fields

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
	private String screenName = "SALESORDERSHORTCLOSE";

	@Column(name = "screen_code")
	private String screenCode = "SOS";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "salesOrderShortCloseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesOrderShortCloseDetailsVO> salesOrderShortCloseDetailsVO;

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