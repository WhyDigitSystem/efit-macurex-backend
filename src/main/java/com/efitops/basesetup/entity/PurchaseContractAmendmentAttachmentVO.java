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
@Table(name = "pcamd4")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseContractAmendmentAttachmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "pcamdattachmentgen")
    @SequenceGenerator(
            name = "pcamdattachmentgen",
            sequenceName = "pcamdattachmentseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "pcamdattachment_id")
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
    @JoinColumn(name = "pcamdbasic_id")
    @JsonBackReference
    private PurchaseContractAmendmentVO purchaseContractAmendmentVO;
}


