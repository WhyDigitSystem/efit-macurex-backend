package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentgen")
	@SequenceGenerator(name = "studentgen", sequenceName = "studentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "studentid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	
	@Column(name="name")
	private String name;
	@Column(name="age")
	private String age;
	
	@OneToMany(mappedBy = "studentVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<StudentAddressVO>studentAddressVO;

}
