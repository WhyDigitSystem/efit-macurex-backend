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
@Table(name = "bill_of_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterialVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_of_materialgen")
	@SequenceGenerator(name = "bill_of_materialgen", sequenceName = "bill_of_materialseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bill_of_material_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "type_of_bom")
	private ListOfValuesDetailsVO typeOfBom;

	@Column(name = "type_of_item")
	private String typeOfItem;

	@ManyToOne
	@JoinColumn(name = "fg_itme")
	private ItemMasterVO fgItem;
	

	@Column(name = "revision_no")
	private int revisionNo;

	@Column(name = "specifications")
	private String specifications;

	@Column(name = "fill_details_of")
	private String fillDetailsOf;
	@ManyToOne
	@JoinColumn(name = "fill_details_of_item")
	private ItemMasterVO fillDetailsOfItem;


	@Column(name = "wef")
	private LocalDate wef;

	@Column(name = "fg_reference_to_profit")
	private String fgReferenceToProfit;

	@Column(name = "manufacturing")
	private String manufacturing;

	
	@Column(name = "remarks")
	private String remarks;

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
	private String screenName = "BillOfMaterial";

	@Column(name = "screen_code")
	private String screenCode = "BOM";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "billOfMaterialVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<BillOfMaterialDetailsVO> billOfMaterialDetailsVO;

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
