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
@Table(name = "itemothers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOthersVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemothersgen")
	@SequenceGenerator(name = "itemothersgen", sequenceName = "itemothersseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemothers_id",columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "technical_specification")
	private String technicalSpecification;

	@Column(name = "supplier_part_no")
	private String supplierPartNo;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "itemmaster_id")
//	ItemMasterVO itemMasterVO;

}
