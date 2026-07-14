package com.efitops.basesetup.entity;

import java.sql.Date;
import java.time.LocalDate;
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

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toolissueentryattachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ToolIssueEntryAttachmentVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toolissueentryattachmentgen")
	@SequenceGenerator(name = "toolissueentryattachmentgen", sequenceName = "toolissueentryattachmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "toolissueentryattachmentid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	
	@Column(name = "fileName")
	private String filename;
	
	@Column(name = "filePath")
	private String filePath;
	
	@Column(name = "fileType")
	private String fileype;
	
	@Column(name = "fileSize")
	private Long filesize;
	
	@Column(name = "uploadOn")
	private LocalDateTime uploadOn;
	
//	private ToolIssueEntryVO toolIssueEntry;
	
	@ManyToOne
	@JoinColumn(name = "toolIssueEntryid")
	@JsonBackReference
	private ToolIssueEntryVO toolIssueEntryVO;
	
	

	


}
