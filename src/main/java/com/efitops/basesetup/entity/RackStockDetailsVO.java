package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rackstockdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RackStockDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackstockdetailsgen")
	@SequenceGenerator(name = "rackstockdetailsgen", sequenceName = "rackstockdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "rackstockdetailsid")
	private Long id;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "docid", length = 25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "refno", length = 25)
	private Long refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "plusorminus")
	private String plusOrMinus;
	@Column(name = "stockdate")
	private LocalDate stockDate = LocalDate.now();
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 25)
	private String branchCode;
	@Column(name = "parttype", length = 50)
	private String partType;
	@Column(name = "partno", length = 25)
	private String partno;
	@Column(name = "partdesc", length = 150)
	private String partDesc;
	@Column(name = "sourcescreencode", length = 10)
	private String sourceScreenCode;
	@Column(name = "sourcescreenname", length = 25)
	private String sourceScreenName;
	@Column(name = "remarks", length = 150)
	private String remarks;
	@Column(name = "customer", length = 150)
	private String customer;
	@Column(name = "sourceid")
	private Long sourceId;
	@Column(name = "batch", length = 25)
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "weight")
	private double weight;
	@Column(name = "location")
	private String location;
	@Column(name = "rackno")
	private String rackNo;
	@Column(name = "status", length = 10)
	private String status;
	@Column(name = "active")
	private boolean active;
	@Column(name = "modifiedby", length = 25)
	private String updatedBy;
	@Column(name = "screencode")
	private String screenCode = "RS";
	@Column(name = "screenname")
	private String screenName ="RACK STOCK DETAILS";
	@Column(name = "finyear")
	private String finYear;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
