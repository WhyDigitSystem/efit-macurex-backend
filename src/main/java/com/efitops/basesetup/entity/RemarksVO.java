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
@Table(name = "remarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarksVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remarksgen")
	@SequenceGenerator(name = "remarksgen", sequenceName = "remarksseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "remarks_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "indicate_1")
	private String indicate1;

	@Column(name = "indicate_2")
	private String indicate2;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "engineering_change_note_basic_id")
	EngineeringChangeNoteVO engineeringChangeNoteVO;

}
