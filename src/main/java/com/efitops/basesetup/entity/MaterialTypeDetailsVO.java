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
@Table(name = "materialtypedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialTypeDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialtypedetailsgen")
	@SequenceGenerator(name = "materialtypedetailsgen", sequenceName = "materialtypedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "materialtypedetailsid")
	private Long id;
	@Column(name = "itemsubgroup")
	private String itemSubGroup;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "materialtypeid")
	MaterialTypeVO materialTypeVO;
}