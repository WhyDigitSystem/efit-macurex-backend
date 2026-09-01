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
@Table(name = "direct_purchase_file_upload_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectPurchaseFileUploadDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direct_purchase_file_upload_detailsgen")
	@SequenceGenerator(name = "direct_purchase_file_upload_detailsgen", sequenceName = "direct_purchase_file_upload_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "direct_purchase_file_upload_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "name")
	private String name;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "file_size")
	private Long fileSize;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "upload_on")
	private LocalDateTime uploadOn;

	@ManyToOne
	@JoinColumn(name = "direct_purchase_basic_id")
	@JsonBackReference
	private DirectPurchaseVO directPurchaseVO;

}