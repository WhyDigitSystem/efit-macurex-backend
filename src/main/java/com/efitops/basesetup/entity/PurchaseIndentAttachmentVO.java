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
@Table(name = "indent_Attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentAttachmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indent_Attachmentgen")
    @SequenceGenerator(name = "indent_Attachmentgen", sequenceName = "indent_Attachmentseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "indent_Attachment_id")
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
    @JoinColumn(name = "Indent_Basic_id")
    @JsonBackReference
    private PurchaseIndentVO purchaseIndentVO;
}