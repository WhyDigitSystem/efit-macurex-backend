package com.efitops.basesetup.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tool_master_attachment")
public class ToolMasterAttachementVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_master_attachmentgen")
    @SequenceGenerator(name = "tool_master_attachmentgen", sequenceName = "tool_master_attachmentseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "tool_master_attachment_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "upload_on")
    private LocalDateTime uploadOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_master_basic_id")
    private ToolMasterVO toolMasterVO;

}