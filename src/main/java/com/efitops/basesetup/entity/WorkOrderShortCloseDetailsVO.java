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
@Table(name = "workordershortclosedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderShortCloseDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workordershortclosedetailsgen")
	@SequenceGenerator(name = "workordershortclosedetailsgen", sequenceName = "workordershortclosedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "workordershortclosedetailsid", columnDefinition = "BIGINT DEFAULT 0")
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
	@Column(name = "orderqty", precision = 10, scale = 2)
	private BigDecimal orderQty;
	@Column(name = "shortageqty", precision = 10, scale = 2)
	private BigDecimal shortageQty;

	@ManyToOne
	@JoinColumn(name = "workordershortcloseid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	WorkOrderShortCloseVO workOrderShortCloseVO;

}
