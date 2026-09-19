package com.efitops.basesetup.entity;

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
@Table(name = "process_validation_entry_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessValidationEntryDetailsVO {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "process_validation_entry_detailsgen"
    )
    @SequenceGenerator(
            name = "process_validation_entry_detailsgen",
            sequenceName = "process_validation_entry_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(
            name = "process_validation_entry_details_id",
            columnDefinition = "BIGINT DEFAULT 0"
    )
    private Long id;

    @Column(name = "parameter_1")
    private String parameter1;

    @Column(name = "parameter_2")
    private String parameter2;

    @Column(name = "parameter_3")
    private String parameter3;

    @Column(name = "parameter_4")
    private String parameter4;

    @Column(name = "parameter_5")
    private String parameter5;

    @Column(name = "parameter_6")
    private String parameter6;

    @Column(name = "parameter_7")
    private String parameter7;

    @ManyToOne
    @JoinColumn(name = "process_validation_entry_id")
    private ProcessValidationEntryVO processValidationEntryVO;
}