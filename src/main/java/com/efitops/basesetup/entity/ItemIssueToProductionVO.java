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
@Table(name = "itemisstoprod")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemisstoprodgen")
	@SequenceGenerator(name = "itemisstoprodgen", sequenceName = "itemisstoprodseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemisstoprodid")
	private Long id;

	@Column(name = "docid")
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "routecardno")
	private String routeCardNo;

	@Column(name = "workorder")
	private String workorder;

	@Column(name = "fgitemid")
	private String fgItemId;

	@Column(name = "fgitemdesc")
	private String fgItemDesc;

	@Column(name = "fgqty")
	private int fgQty;

	@Column(name = "fromlocation")
	private String fromLocation;
	
	@Column(name = "tolocation")
	private String toLocation;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "preparedby")
	private String preparedBy;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;		
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;

	@Column(name = "screencode", length = 5)
	private String screenCode = "IITP";
	@Column(name = "screenname", length = 30)
	private String screenName = "ITEM ISSUE TO PRODUCTION";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@OneToMany(mappedBy = "itemIssueToProductionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemIssueToProductionDetailsVO> itemIssueToProductionDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
