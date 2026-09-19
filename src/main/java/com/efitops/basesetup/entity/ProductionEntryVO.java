package com.efitops.basesetup.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "production_entry_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionEntryVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_entry_basicgen")
    @SequenceGenerator(name = "production_entry_basicgen", sequenceName = "production_entry_basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "production_entry_basic_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId; 

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now(); 
    
    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "shift_time_from")
    private LocalTime shiftTimeFrom; 
    
    @Column(name = "shift_time_to")
    private LocalTime shiftTimeTo;

    @Column(name = "shift")
    private String shift; // Shift

    @ManyToOne
    @JoinColumn(name = "fg_item_code")
    private ItemMasterVO fgItemCode; // FG Item Code

    @Column(name = "fg_item_description")
    private String fgItemDescription; // FG Item Description

    @ManyToOne
    @JoinColumn(name = "location")
    private LocationVO location; // Location

    @Column(name = "production_qty", precision = 10, scale = 2)
    private BigDecimal productionQty; // Production QTY

    @Column(name = "sch_order_no")
    private String schOrderNo; // Sch.Order No.

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy; // Prepared By

    @Column(name = "process_sheet_no")
    private String processSheetNo; // Process Sheet No

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private EmployeeMasterVO approvedBy; // Approved By

    @ManyToOne
    @JoinColumn(name = "bom_id")
    private BillOfMaterialVO bomId; // Bomid

    // Production Summary Fields
    @Column(name = "total_labour_cost", precision = 10, scale = 2)
    private BigDecimal totalLabourCost;

    @Column(name = "total_machine_cost", precision = 10, scale = 2)
    private BigDecimal totalMachineCost;

    @Column(name = "total_tool_cost", precision = 10, scale = 2)
    private BigDecimal totalToolCost;

    @Column(name = "total_consumables_cost", precision = 10, scale = 2)
    private BigDecimal totalConsumablesCost;

    @Column(name = "narration", length = 1000)
    private String narration;

    // Standard Audit Fields
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
    private String screenName = "ProductionEntry";

    @Column(name = "screen_code")
    private String screenCode = "PE";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

//    @OneToMany(mappedBy = "productionEntryVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<ProductionEntryDetailsVO> productionEntryDetailsVO;
//
//    @OneToMany(mappedBy = "productionEntryVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<ToolDetailsVO> toolDetailsVO;
//
//    @OneToMany(mappedBy = "productionEntryVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<StoppageReasonVO> stoppageReasonVO;
//
//    @OneToMany(mappedBy = "productionEntryVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<ReworkReasonVO> reworkReasonVO;
//
//    @OneToMany(mappedBy = "productionEntryVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<ScrapDetailsVO> scrapDetailsVO;

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