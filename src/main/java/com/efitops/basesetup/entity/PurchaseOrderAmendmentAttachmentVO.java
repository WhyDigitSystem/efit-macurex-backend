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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchaseorder_amendment_attachment")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseOrderAmendmentAttachmentVO {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseorder_amendment_attachmentgen")
	    @SequenceGenerator(name = "purchaseorder_amendment_attachmentgen", sequenceName = "purchaseorder_amendment_attachmentseq", initialValue = 1000000001, allocationSize = 1)
	    @Column(name = "purchaseorder_amendment_attachment_id")
	    private Long id;

	    @Column(name = "name")
	    private String name; // original file name

	    @Column(name = "file_name")
	    private String fileName; // UUID-prefixed stored name

	    @Column(name = "file_path")
	    private String filePath;

	    @Column(name = "file_size")
	    private Long fileSize;

	    @Column(name = "upload_on")
	    private LocalDateTime uploadOn;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "purchaseorder_amendment_basic_id")
	    @JsonBackReference
	    private PurchaseOrderAmendmentVO purchaseOrderAmendmentVO;

}


