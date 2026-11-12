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
@Table(name = "stock_transfer_grn_attached_po_copy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferGrnFileUploadDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_transfer_grn_attached_po_copygen")
	@SequenceGenerator(name = "stock_transfer_grn_attached_po_copygen", sequenceName = "stock_transfer_grn_attached_po_copyseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stock_transfer_grn_attached_po_copy_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "file_size")
	private Long fileSize;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "upload_on")
	private LocalDateTime uploadOn;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "stock_transfer_grn_basic_id")
	private StockTransferGrnVO stockTransferGrnVO;
}