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
@Table(name = "productionplan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productionplangen")
	@SequenceGenerator(name = "productionplangen", sequenceName = "productionplanseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "productionplanid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate= LocalDate.now();
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "wosono")
	private String woSoNo;
	@Column(name = "wosodate")
	private LocalDate woSoDate;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "part")
	private String part;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "productionqty")
	private String productionQty;
	@Column(name = "productionstartdate")
	private LocalDate productionStartDate;
	@Column(name = "productionenddate")
	private LocalDate productionEndDate;
	@Column(name = "rawmaterial")
	private String rawMaterial;
	@Column(name = "rawmaterialdesc")
	private String rawMaterialDesc;
	@Column(name = "narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;
//	@Column(name = "branch", length = 30)
//	private String branch;
//	@Column(name = "branchcode", length = 10)
//	private String branchCode;
//	@Column(name = "finyear", length = 10)
//	private String finYear;
	@Column(name = "createdby", length = 30)
	private String createdBy;
	@Column(name = "modifiedby", length = 30)
	private String modifiedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "PP";
	@Column(name = "screenname", length = 30)
	private String screenName = "PRODUCTION PLAN";

	@OneToMany(mappedBy = "productionPlanVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProductionPlanDetailsVO> productionPlanDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

}
