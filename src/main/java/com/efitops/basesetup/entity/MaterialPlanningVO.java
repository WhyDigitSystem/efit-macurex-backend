package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_planning")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanningVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_planninggen")
    @SequenceGenerator(
            name = "material_planninggen",
            sequenceName = "material_planningseq",
            
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "material_planning_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "mrp_type")
    private String mrpType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_name")
    private String screenName = "MATERIAL PLANNING";

    @Column(name = "screen_code")
    private String screenCode = "MRP";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;
    
    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Embedded
   	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}