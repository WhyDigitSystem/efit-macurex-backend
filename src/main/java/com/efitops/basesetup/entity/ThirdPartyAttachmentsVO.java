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
@Table(name = "thirdpartyattachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyAttachmentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thirdpartyattachmentsgen")
	@SequenceGenerator(name = "thirdpartyattachmentsgen", sequenceName = "thirdpartyattachmentsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "thirdpartyattachmentsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "fileName")
	private String filename;

	@Column(name = "filePath")
	private String filePath;

	@Column(name = "fileType")
	private String fileype;

	@Column(name = "fileSize")
	private Long filesize;

	@Column(name = "itemid")
	private String itemId;

	@Column(name = "uploadOn")
	private LocalDateTime uploadOn;
	
	@Column(name="filenames")
	private String fileNames;

	@ManyToOne
	@JoinColumn(name = "thirdpartyinspectionid")
	@JsonBackReference
	private ThirdPartyInspectionVO thirdPartyInspectionVO;

}
