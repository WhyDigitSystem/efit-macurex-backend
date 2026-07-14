package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "settingapprovaldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingApprovalDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settingapprovaldetailsgen")
	@SequenceGenerator(name = "settingapprovaldetailsgen", sequenceName = "settingapprovaldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "settingapprovaldetailsid")
	private Long id;
	
	@Column(name = "characteristics")
	private String characteristics;
	@Column(name = "specification")
	private String specification;
	@Column(name = "methodofinspection")
	private String methodOfInspection;
	@Column(name = "lsl")
	private BigDecimal lsl;
	@Column(name = "usl")
	private BigDecimal usl;
	@Column(name = "setter1")
	private String setter1;
	@Column(name = "setter2")
	private String setter2;
	@Column(name = "setter3")
	private String setter3;
	@Column(name = "setter4")
	private String setter4;
	@Column(name = "setter5")
	private String setter5;
	@Column(name = "qulity1")
	private String qulity1;
	@Column(name = "qulity2")
	private String qulity2;
	@Column(name = "qulity3")
	private String qulity3;
	@Column(name = "qulity4")
	private String qulity4;
	@Column(name = "qulity5")
	private String qulity5;
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="settingapprovalid")
    private SettingApprovalVO settingApprovalVO; 
	
}
