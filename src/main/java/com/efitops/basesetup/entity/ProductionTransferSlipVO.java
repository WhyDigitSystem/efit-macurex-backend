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
@Table(name = "production_transfer_slip_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTransferSlipVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_transfer_slip_basicgen")
	@SequenceGenerator(name = "production_transfer_slip_basicgen", sequenceName = "production_transfer_slip_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "production_transfer_slip_basicid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;

	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation;

	@ManyToOne
	@JoinColumn(name = "scrap_to_location")
	private LocationVO scrapToLocation;

	@ManyToOne
	@JoinColumn(name = "fg_part_no")
	private ItemMasterVO fgPartNo;

	@ManyToOne
	@JoinColumn(name = "sfg_part_no")
	private ItemMasterVO sfgPartNo;

	@Column(name = "sfg_description")
	private String sfgDescription;

	@Column(name = "sch_order_no")
	private String schOrderNo;

	@Column(name = "bom")
	private String bom;

	@Column(name = "sch_dates")
	private LocalDate schDates;

	@Column(name = "alter_input_item")
	private String alterInputItem;

	@Column(name = "item_type")
	private String itemType;

	@Column(name = "issue_qty", precision = 10, scale = 2)
	private BigDecimal issueQty;

	// Based on "Unit"
	@Column(name = "unit")
	private String unit;

	// Based on "Value"
	@Column(name = "value", precision = 10, scale = 2)
	private BigDecimal value;

	// Based on "Rate"
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "ProductionTransferSlip";

	@Column(name = "screen_code")
	private String screenCode = "PTS";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "total_value", precision = 10, scale = 2)
	private BigDecimal totalValue;

	@OneToMany(mappedBy = "productionTransferSlipVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProductionTransferSlipDetailsVO> productionTransferSlipDetailsVO;

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