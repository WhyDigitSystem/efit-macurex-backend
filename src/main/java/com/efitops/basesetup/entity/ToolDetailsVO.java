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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tool_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_detailsgen")
    @SequenceGenerator(name = "tool_detailsgen", sequenceName = "tool_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "tool_details_id")
    private Long id;

    @Column(name = "tool_no")
    private String toolNo;

    @Column(name = "tool_name")
    private String toolName;

    @Column(name = "strokes", precision = 10, scale = 2)
    private BigDecimal strokes;

    @Column(name = "strokes_rate", precision = 10, scale = 2)
    private BigDecimal strokesRate;

    @Column(name = "tool_value", precision = 10, scale = 2)
    private BigDecimal toolValue;
    
    @ManyToOne
    @JoinColumn(name = "production_entry_basic_id")
    @JsonBackReference
    private ProductionEntryVO productionEntryVO;
}