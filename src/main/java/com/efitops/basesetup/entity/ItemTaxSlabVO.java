package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "itemtaxslab")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemTaxSlabVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemtaxslabgen")
	@SequenceGenerator(name = "itemtaxslabgen", sequenceName = "itemtaxslabseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemtaxslabid")
	private Long id;
	
	@Column(name = "taxslab")
	private String taxSlab;

	@Column(name = "taxeffectivefrom")
	private LocalDate taxEffectiveFrom;
	
	@ManyToOne
	@JoinColumn(name = "itemid")
	@JsonBackReference
	private ItemVO itemVO;
	

}
