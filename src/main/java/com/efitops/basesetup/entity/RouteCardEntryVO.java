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
import javax.persistence.Lob;
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
@Table(name = "routecardentry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardEntryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routecardentrygen")
	@SequenceGenerator(name = "routecardentrygen", sequenceName = "routecardentryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routecardentryid")
	private Long id;

	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "wono")
	private String woNo;
	@Column(name = "wodate")
	private LocalDate woDate;
	@Column(name = "fgpartname")
	private String fgPartName;
	@Column(name = "fgpartdesc")
	private String fgPartDesc;
	@Column(name = "fgqty")
	private int fgQty;
	@Column(name = "batchqty")
	private int batchQty;
	@Column(name = "rmtype")
	private String rmType;
	@Column(name = "rmsize")
	private String rmSize;
	@Column(name = "rmbatchno")
	private String rmbatchNo;
	@Column(name = "rmqty")
	private int rmQty;
	@Column(name = "narration")
	private String narration;
	@Column(name = "status")
	private String status;
	@Column(name = "invoice")
	private String invoice;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "qty")
	private int qty;
	@Column(name = "stockqty")
	private int stockQty;

	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;

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
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "screencode", length = 5)
	private String screenCode = "RCE";
	@Column(name = "screenname", length = 30)
	private String screenName = " ROUTECARDENTRY";

	@OneToMany(mappedBy = "routeCardEntryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<RouteCardEntryDetailsVO> routeCardEntryDetailsVO;

	@OneToMany(mappedBy = "routeCardEntryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<RouteCardEngDeptVO> routeCardEngDeptVO;

	@OneToMany(mappedBy = "routeCardEntryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<RouteCardClosureVO> routeCardClosureVO;
	
	@OneToMany(mappedBy = "routeCardEntryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<RouteCardEntryAttachmentVO> documents;


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
