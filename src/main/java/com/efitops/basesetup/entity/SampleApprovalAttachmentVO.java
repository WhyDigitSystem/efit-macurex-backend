package com.efitops.basesetup.entity;

import java.time.LocalDateTime;

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
@Table(name = "sampleapprovalattachment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleApprovalAttachmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sampleapprovalattachmentgen")
	@SequenceGenerator(name = "sampleapprovalattachmentgen", sequenceName = "sampleapprovalattachmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "sampleapprovalattachmentid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "filename")
	private String fileName;

	@Column(name = "filepath")
	private String filePath;

	@Column(name = "fileype")
	private String fileType;

	@Column(name = "filesize")
	private Long fileSize;

	@Column(name = "uploadOn")
	private LocalDateTime uploadOn;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "sampleapprovalid")
	private SampleApprovalVO sampleApprovalVO;

	

}
