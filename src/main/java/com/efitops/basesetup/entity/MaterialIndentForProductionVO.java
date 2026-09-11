package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "material_indent_for_production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialIndentForProductionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_indent_for_productiongen")
	@SequenceGenerator(name = "material_indent_for_productiongen", sequenceName = "material_indent_for_productionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "material_indent_for_production_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;

	@Column(name = "sch_order_no")
	private String schOrderNo;

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "fg_item")
	private ItemMasterVO fgItem;

	@Column(name = "sch_qty", precision = 10, scale = 2)
	private BigDecimal schQty;

	@Column(name = "scheduled_date")
	private LocalDate scheduledDate;

	@Column(name = "indent_time")
	private LocalTime indentTime = LocalTime.now();

	@ManyToOne
	@JoinColumn(name = "to_location")
	private LocationVO toLocation;

	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "active")
	private boolean active ;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "MaterialIndentForProduction";

	@Column(name = "screen_code")
	private String screenCode = "MIP";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "materialIndentForProductionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MaterialIndentForProductionDetailsVO> materialIndentForProductionDetailsVO;

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