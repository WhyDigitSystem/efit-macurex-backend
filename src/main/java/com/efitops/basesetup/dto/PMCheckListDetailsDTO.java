package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.ActivityMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PMCheckListDetailsDTO {

	private Long category;

	private Long activity;

	private String checkingPoints;

	private String parameter;

	private String specification;

	private String generalDevObs;

	private String remediesRemarks;

	private BigDecimal noOfHrs;

	private String frequency;

}
