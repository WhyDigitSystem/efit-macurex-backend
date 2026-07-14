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
@Table(name = "toolisstocalibdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolsIssueToCalibrationDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toolisstocalibdetailsgen")
	@SequenceGenerator(name = "toolisstocalibdetailsgen", sequenceName = "toolisstocalibdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "toolisstocalibdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "instrumentid")
	private String instrumentId;
	@Column(name = "instrumentname")
	private String instrumentName;
	@Column(name = "instrumentdesc")
	private String instrumentDesc;
	@Column(name = "issqty", precision = 10, scale = 2)
	private BigDecimal issQty;
	@Column(name = "unit", precision = 10, scale = 2)
	private String unit;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "toolissuetocalibrationid", columnDefinition = "BIGINT DEFAULT 0")
	private ToolsIssueToCalibrationVO toolsIssueToCalibrationVO;
	

}
