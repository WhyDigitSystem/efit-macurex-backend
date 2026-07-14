package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "pendingworkorderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingWorkOrderDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pendingworkorderdetailsgen")
	@SequenceGenerator(name = "pendingworkorderdetailsgen", sequenceName = "pendingworkorderdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pendingworkorderdetailsid")
	private Long id;
	@Column(name = "cancel")
	private String cancel;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "inouttime")
	private LocalTime inouttime = LocalTime.now();
	@Column(name = "refno", length = 25)
	private Long refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "plusorminus")
	private String plusOrMinus;
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
	@Column(name = "sourceid")
	private Long sourceId;
	@Column(name = "modifiedby", length = 25)
	private String updatedBy;
	@Column(name = "workorderno", length = 150)
	private String workOrderNo;
	@Column(name = "workorderdate", length = 150)
	private LocalDate workorderdate;
	@Column(name = "customername", length = 150)
	private String customerName;
	@Column(name = "customerpono", length = 150)
	private String customerPoNo;
	@Column(name = "customercode", length = 150)
	private String customerCode;
	@Column(name = "salesorderno", length = 150)
	private String salesOrderNo;
	@Column(name = "salesorderdate", length = 150)
	private LocalDate salesOrderDate;
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
