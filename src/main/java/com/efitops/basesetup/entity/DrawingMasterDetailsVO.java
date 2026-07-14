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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drawingmaster1")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingMasterDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "drawingmaster1gen")
	@SequenceGenerator(name = "drawingmaster1gen", sequenceName = "drawingmaster1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "drawingmaster1id")
	private Long id;
	@Column(name ="filenames")
	private String fileNames;
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
	
	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;
	
	@ManyToOne
	@JoinColumn(name="drawingmasterid")
	@JsonBackReference
	private DrawingMasterVO drawingMasterVO;
}



