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
@Table(name = "engineeringchangenoticeregisterattachment")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EngineeringChangeNoticeRegisterAttachmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "engineeringchangenoticeregisterattachmentgen")
	@SequenceGenerator(name = "engineeringchangenoticeregisterattachmentgen", sequenceName = "engineeringchangenoticeregisterattachmentseq", initialValue = 1000000001, allocationSize = 1)

	@Column(name = "engineeringchangenoticeregisterattachmentid")
	private Long id;

	@Column(name = "filename")
	private String filename;
	@Column(name = "filepath")
	private String filePath;
	@Column(name = "filetype")
	private String fileType;
	@Column(name = "filesize")
	private Long fileSize;
	@Column(name = "uploadon")
	private LocalDateTime uploadOn;

	@ManyToOne
	@JoinColumn(name = "engineeringchangenoticeregisterid")
	@JsonBackReference
	private EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO;

}
