package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "documents_changes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsChangesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documents_changesgen")
	@SequenceGenerator(name = "documents_changesgen", sequenceName = "documents_changesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documents_changes_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "sop_no")
	private String sopNo;

	@Column(name = "station_no")
	private String stationNo;

	@Column(name = "completion_date")
	private LocalDate completionDate;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "engineering_change_note_basic_id")
	EngineeringChangeNoteVO engineeringChangeNoteVO;

}
