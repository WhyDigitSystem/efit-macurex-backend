package com.efitops.basesetup.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquiryattachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryAttachmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquiryattachmentgen")
	@SequenceGenerator(name = "enquiryattachmentgen", sequenceName = "enquiryattachmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "enquiryattachment_id")
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
	@JoinColumn(name = "enquiry_id")
	@JsonBackReference
	private EnquiryVO enquiryVO;

}