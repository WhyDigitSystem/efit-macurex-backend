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
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_order_attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOrderAttachmentVO {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "job_order_attachmentgen"
    )
    @SequenceGenerator(
            name = "job_order_attachmentgen",
            sequenceName = "job_order_attachmentseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "job_order_attachment_id", columnDefinition = "BIGINT DEFAULT 0")
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
    @JoinColumn(name = "job_order_basic_id")
    @JsonBackReference
    private JobOrderVO jobOrderVO;

}

