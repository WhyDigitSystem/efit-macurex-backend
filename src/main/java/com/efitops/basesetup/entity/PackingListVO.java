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
@Table(name = "packinglist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packinglistgen")
	@SequenceGenerator(name = "packinglistgen", sequenceName = "packinglistseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "packinglistid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customeraddress")
	private String customerAddress;
	@Column(name = "salesorderno")
	private String salesOrderNo;
	@Column(name = "salesorderdate")
	private LocalDate salesOrderDate;
	@Column(name = "supplydate")
	private LocalDate supplyDate;
	@Column(name = "deliveryplace")
	private String deliveryPlace;
	@Column(name = "noofpackage")
	private int noOfPackage;
	@Column(name = "vendorcode")
	private String vendorCode;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 30)
	private String createdBy;
	@Column(name = "modifiedby", length = 30)
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 30)
	private String screenCode = "LPL";
	@Column(name = "screenname", length = 30)
	private String screenName = "PACKING LIST";

	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "narration")
	private String narration;
	@Column(name = "totalqty",precision = 10, scale = 2)
	private BigDecimal totalQty;
	@Column(name = "totalcrossweight",precision = 10, scale = 2)
	private BigDecimal totalCrossWeight;

	@OneToMany(mappedBy = "packingListVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PackingListDetailsVO> packingListDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
