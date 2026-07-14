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
@Table(name = "salesorderterms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderTermsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "salesordertermsgen")
	@SequenceGenerator(name = "salesordertermsgen", sequenceName = "salesordertermsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesordertermsid")
	private Long id;
	@Column(name = "terms")
	private String terms;
	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "salesid")
	@JsonBackReference
	private SalesVO salesVO;

}
