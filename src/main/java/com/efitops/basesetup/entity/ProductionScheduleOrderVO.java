package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "production_schedule_order_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionScheduleOrderVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_schedule_order_basicgen")
	@SequenceGenerator(name = "production_schedule_order_basicgen", sequenceName = "production_schedule_order_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "production_schedule_order_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "order_type")
	private String orderType;

	@Column(name = "lc_po_no")
	private String lcPoNo;

	@Column(name = "lc_po_date")
	private LocalDate lcPoDate;

	@ManyToOne
	@JoinColumn(name = "fg_itme")
	private ItemMasterVO fgItem;

	@ManyToOne
	@JoinColumn(name = "comp_route_no")
	private ProcessSheetCompRoutingVO compRouteNo;

	@Column(name = "batch_qty", precision = 15, scale = 5)
	private BigDecimal batchQty;

	@Column(name = "total_qty", precision = 15, scale = 5)
	private BigDecimal totalQty;

	@Column(name = "short_close")
	private String shortClose;

	@ManyToOne
	@JoinColumn(name = "bom")
	private BillOfMaterialVO bom;

	@Column(name = "schedule_start_date")
	private LocalDate scheduleStartDate;

	@Column(name = "schedule_end_date")
	private LocalDate scheduleEndDate;

	// Common fields

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
	private String screenName = "ProductionScheduleOrder";

	@Column(name = "screen_code")
	private String screenCode = "PSO";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "productionScheduleOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProductionScheduleOrderDetailsVO> productionScheduleOrderDetailsVO;

	@OneToMany(mappedBy = "productionScheduleOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ScheduleDetailsVO> scheduleDetailsVO;

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
