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
@Table(name = "machine_tool_breakdown_attachment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolBreakdownAttachmentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_tool_breakdown_attachmentgen")
	@SequenceGenerator(name = "machine_tool_breakdown_attachmentgen", sequenceName = "machine_tool_breakdown_attachmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machine_tool_breakdown_attachment_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "file_size")
	private Long fileSize;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "upload_on")
	private LocalDateTime uploadOn;

	@ManyToOne
	@JoinColumn(name = "machine_tool_breakdown_basic_id")
	@JsonBackReference
	private MachineToolBreakdownVO machineToolBreakdownVO;

}
