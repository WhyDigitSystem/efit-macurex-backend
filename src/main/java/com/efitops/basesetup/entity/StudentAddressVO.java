package com.efitops.basesetup.entity;

import java.util.List;

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
@Table(name = "studentaddress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAddressVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentaddressgen")
	@SequenceGenerator(name = "studentaddressgen", sequenceName = "studentaddressseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "studentaddressid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	private String address1;
	private String address2;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="studentid")
	private StudentVO studentVO;
}
