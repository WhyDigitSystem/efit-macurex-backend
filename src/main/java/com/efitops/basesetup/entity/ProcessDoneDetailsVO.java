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
@Table(name = "processdonedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDoneDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processdonedetailsgen")
	@SequenceGenerator(name = "processdonedetailsgen", sequenceName = "processdonedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "processdonedetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "process")
	private String process;
	@Column(name = "status")
	private String status;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "processdoneid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private ProcessDoneVO processDoneVO;

}
