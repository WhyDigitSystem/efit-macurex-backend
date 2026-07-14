package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dailypatrolinspectiondocuments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPatrolInspectionDocumentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailypatrolinspectiondocumentsgen")
	@SequenceGenerator(name = "dailypatrolinspectiondocumentsgen", sequenceName = "dailypatrolinspectiondocumentsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailypatrolinspectiondocumentsid")
	private Long id;

	@Column(name = "documentname")
	private String documentName;

	@Column(name = "documenttype")
	private String documentType;

	@Lob
	@Column(name = "documentdata")
	private byte[] documentData; // ✅ BLOB

	@ManyToOne
	@JoinColumn(name = "dailypatrolinspectionid")
	@JsonBackReference
	private DailyPatrolInspectionVO dailyPatrolInspectionVO;
}
