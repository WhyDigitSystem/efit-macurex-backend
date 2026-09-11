package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentsgen")
	@SequenceGenerator(name = "documentsgen", sequenceName = "documentsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documents_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "drawing")
	private String drawing;

	@Column(name = "part_no")
	private String partNo;

	@Column(name = "issue")
	private String issue;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "engineering_change_note_basic_id")
	EngineeringChangeNoteVO engineeringChangeNoteVO;

}
