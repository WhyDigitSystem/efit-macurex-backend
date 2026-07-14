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
@Table(name = "finalfgpartstockupdate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalFgPartStockUpdateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finalfgpartstockupdategen")
	@SequenceGenerator(name = "finalfgpartstockupdategen", sequenceName = "finalfgpartstockupdateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "finalfgpartstockupdateid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "routecardno")
	private String routeCardNo;
	@Column(name = "workorderno")
	private String workOrderNo;
	@Column(name = "fromlocation")
	private String fromLocation;
	@Column(name = "tolocation")
	private String toLocation;
	@Column(name = "narration")
	private String narration;
	
	@Column(name = "part")
	private String part;

	@Column(name = "partdesc")
	private String partDesc;

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal  qty;

	@Column(name = "unit")
	private String unit;

	@Column(name = "rate")
	private BigDecimal rate;

	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name="status")
	private String status;
	
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
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "screencode", length = 30)
	private String screenCode = "FFGPSU";
	@Column(name = "screenname", length = 30)
	private String screenName = "FinalFgPartStockUpdate";
	
	@OneToMany(mappedBy = "finalFgPartStockUpdateVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<FgStockUpdateDetailsVO> fgStockUpdateDetailsVO;

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
