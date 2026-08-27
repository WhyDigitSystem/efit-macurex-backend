package com.efitops.basesetup.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_contract_attachment")
public class PurchaseContractAttachmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_contract_attachmentgen")
    @SequenceGenerator(name = "purchase_contract_attachmentgen", sequenceName = "purchase_contract_attachmentseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_contract_attachment_id")
    private Long id;

    // original file name typed/shown to the user ("File Name")
    @Column(name = "name")
    private String name;

    // unique name of the file actually saved on disk (UUID + original name)
    @Column(name = "file_name")
    private String fileName;

    // full physical path on disk where the file is stored
    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "upload_on")
    private LocalDateTime uploadOn;

    @ManyToOne
    @JoinColumn(name = "purchase_contract_basic_id")
    @JsonBackReference
    private PurchaseContractVO purchaseContractVO;
}