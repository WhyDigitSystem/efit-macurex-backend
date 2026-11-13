package com.efitops.basesetup.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "set_up_approval_parameters_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalParametersDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "set_up_approval_parameters_detailsgen")
	@SequenceGenerator(name = "set_up_approval_parameters_detailsgen", sequenceName = "set_up_approval_parameters_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "set_up_approval_parameters_details_id")
    private Long id;
	
	@Column(name = "parameters")
	private String parameters;
	
	@Column(name = "parameter_type")
	private String parameterType;
	
	@Column(name = "tol")
	private String tol;
	
	@ManyToOne
	@JoinColumn(name = "set_up_approval_basic_id")
	@JsonBackReference
	private SetUpApprovalVO setUpApprovalVO;
}
