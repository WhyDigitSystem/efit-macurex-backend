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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "putaway")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutawayVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "putawaygen")
	@SequenceGenerator(name = "putawaygen", sequenceName = "putawayseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawayid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "vehicleno")
	private String vehicleNo;
	@Column(name = "fromlocation")
	private String fromLocation;
	@Column(name = "tolocation")
	private String toLocation;
	@Column(name = "goodstype")
	private String goodsType;
	@Column(name = "dcno")
	private String dcNo;
	@Column(name = "narration")
	private String narration;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "status")
	private String status;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 5)
	private String screenCode = "PUT";
	@Column(name = "screenname", length = 30)
	private String screenName = "PUTAWAY";
	@Column(name = "totalputawayQty", precision = 10, scale = 2)
	private BigDecimal totalPutawayQty;

	@OneToMany(mappedBy = "putawayVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PutawayDetailsVO> putawayDetailsVO;

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
