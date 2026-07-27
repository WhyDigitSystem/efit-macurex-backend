package com.efitops.basesetup.entity;

import java.time.LocalDateTime;

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
@Table(name = "itemdrawing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDrawingVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemdrawinggen")
	@SequenceGenerator(name = "itemdrawinggen", sequenceName = "itemdrawingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemdrawing_id",columnDefinition = "BIGINT DEFAULT 0")
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

	@Lob
	@Column(name = "item_attachment_url", columnDefinition = "LONGBLOB")
	private byte[] itemAttachmentUrl;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "itemmaster_id")
	ItemMasterVO itemMasterVO;

}
