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
@Table(name = "proforma_order_delivery_schedule_shortclose_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDeliveryScheduleShortCloseVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proforma_order_delivery_schedule_shortclose_basicgen")
	@SequenceGenerator(name = "proforma_order_delivery_schedule_shortclose_basicgen", sequenceName = "proforma_order_delivery_schedule_shortclose_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "proforma_order_delivery_schedule_shortclose_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "supplier_code")
	private CustomerVO supplierCode;

	@Column(name = "type")
	private String type;
;
	@Column(name = "purchase_order_schedule_no")
	private String purchaseOrderScheduleNo;

	@Column(name = "reference_for_short_close")
	private String referenceForShortClose;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "narration")
	private String narration;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "PURCHASEORDERDELIVERSCHEDULESHORTCLOSE";

	@Column(name = "screen_code")
	private String screenCode = "PODSSC";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "purchaseOrderDeliveryScheduleShortCloseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderDeliveryScheduleShortCloseDetailsVO> purchaseOrderDeliveryScheduleShortCloseDetailsVO;

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
