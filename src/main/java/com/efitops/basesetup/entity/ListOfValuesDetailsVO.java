package com.efitops.basesetup.entity;

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
@Table(name = "listofvaluesDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfValuesDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listofvaluesDetailsgen")
	@SequenceGenerator(name = "listofvaluesDetailsgen", sequenceName = "listofvaluesDetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "listofvaluesDetails_id")
	private Long id;
	@Column(name = "s_no")
	private Long sNo;
	@Column(name = "value_code")
	private String valueCode;
	@Column(name = "value_description")
	private String valueDescription;
	@Column(name = "active")
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "listofvaluesid")
	@JsonBackReference
	private ListOfValuesVO listOfValuesVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
