package com.efitops.basesetup.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_transfer_return_note_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTransferReturnNoteVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_transfer_return_note_basicgen")
    @SequenceGenerator(name = "material_transfer_return_note_basicgen", sequenceName = "material_transfer_return_note_basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "material_transfer_return_note_basic_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;


    @Column(name = "doc_id")
    private String docId;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation;

    @ManyToOne
    @JoinColumn(name = "to_location")
    private LocationVO toLocation;

    @ManyToOne
    @JoinColumn(name = "fg_item")
    private ItemMasterVO fgItem;

    @Column(name = "sch_order_no")
    private String schOrderNo; 

    @Column(name = "time")
    private LocalTime time=LocalTime.now();

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy; 

    
    @Column(name = "total_value", precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "approved_by_pm")
    private String approvedByPm ;

    @Column(name = "approved_by_qc")
    private String approvedByQc;

    @Column(name = "approved_by_stores")
    private String approvedByStores ;

    @Column(name = "narration", length = 1000)
    private String narration;


    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_name")
    private String screenName = "MaterialTransferReturnNote";

    @Column(name = "screen_code")
    private String screenCode = "MTRN";

    
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @OneToMany(mappedBy = "materialTransferReturnNoteVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MaterialTransferReturnNoteDetailsVO> itemDetails;


    @JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}