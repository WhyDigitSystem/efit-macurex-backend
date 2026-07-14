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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workorderterms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderTermsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workordertermsgen")
	@SequenceGenerator(name = "workordertermsgen", sequenceName = "workordertermsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "workordertermsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "template")
	private String template;
	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "workorderid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	WorkOrderVO workOrderVO;

}
