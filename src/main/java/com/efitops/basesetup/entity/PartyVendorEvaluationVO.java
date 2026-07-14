package com.efitops.basesetup.entity;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partyvendorevaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyVendorEvaluationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyvendorevaluationgen")
	@SequenceGenerator(name = "partyvendorevaluationgen", sequenceName = "partyvendorevaluationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partyvendorevaluationid")
	private Long id;
	
	@Column(name = "boughvendor")
	private String boughVendor;

	@Column(name = "basicvenselected")
	private String basicVenSelected;

	@Column(name = "justification")
	private String justification;

	@Column(name = "slapoints")
	private String slaPoints;

	@Column(name = "commagreedterm")
	private String commAgreedTerm;

	@OneToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
