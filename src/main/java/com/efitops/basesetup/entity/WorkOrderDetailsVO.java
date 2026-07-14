package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workorderdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workorderdetailsgen")
	@SequenceGenerator(name = "workorderdetailsgen", sequenceName = "workorderdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "workorderdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partname")
	private String partName;
	@Column(name = "drawingno")
	private String drawingNo;
	@Column(name = "revisionno")
	private String revisionNo;
	@Column(name = "uom")
	private String uom;
	@Column(name = "ordqty", precision = 10, scale = 3)
	private BigDecimal ordQty;
	@Column(name = "freeqty", precision = 10, scale = 3)
	private BigDecimal freeQty;
	@Column(name = "availablestockqty", precision = 10, scale = 3)
	private BigDecimal availableStockQty;
	@Column(name = "requiredqty", precision = 10, scale = 3)
	private BigDecimal requiredQty;

	@ManyToOne
	@JoinColumn(name = "workorderid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	WorkOrderVO workOrderVO;

}
