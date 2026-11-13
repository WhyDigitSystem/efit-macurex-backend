package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bom_correction_request_note_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomCorrectionRequestNoteDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bom_correction_request_note_detailsgen")
    @SequenceGenerator(
            name = "bom_correction_request_note_detailsgen",
            sequenceName = "bom_correction_request_note_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "bom_correction_request_note_details_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "part_no")
    private ItemMasterVO partNo;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "bom_qty", precision = 15, scale = 5)
    private BigDecimal bomQty;

    @Column(name = "added_removed")
    private String addedRemoved;

    @ManyToOne
    @JoinColumn(name = "bom_correction_request_note_id")
    private BomCorrectionRequestNoteVO bomCorrectionRequestNoteVO;
}