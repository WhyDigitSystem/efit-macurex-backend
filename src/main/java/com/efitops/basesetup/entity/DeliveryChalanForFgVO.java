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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliverychalanforfg")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryChalanForFgVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychalanforfggen")
	@SequenceGenerator(name = "deliverychalanforfggen", sequenceName = "deliverychalanforfgseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychalanforfgid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customeraddress")
	private String customerAddress;
	@Column(name = "sono")
	private String soNo;
	@Column(name = "sodate")
	private LocalDate soDate;
	@Column(name = "dudate")	
	private LocalDate duDate;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "vehicleno")
	private String vehicleNo;
	@Column(name = "status")
	private String status = "PENDING";

	// Summary

	@Column(name = "naration")
	private String naration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode", length = 30)
	private String screenCode = "DCFG";
	@Column(name = "screenname", length = 30)
	private String screenName = "DELIVERY CHALAN FOR FG";

	@OneToMany(mappedBy = "deliveryChalanForFgVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<DeliveryChallanForFgDetailsVO> deliveryChallanForFgDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
