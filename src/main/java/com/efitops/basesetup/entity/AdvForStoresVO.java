package com.efitops.basesetup.entity;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adv_for_stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvForStoresVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adv_for_storesgen")
    @SequenceGenerator(
            name = "adv_for_storesgen",
            sequenceName = "adv_for_storesseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "adv_for_stores_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "doc_id")
    private String docId	;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;
    
    @ManyToOne
    @JoinColumn(name = "incoming_part_no")
    private ItemMasterVO incomingPartNo;
    
    @ManyToOne
    @JoinColumn(name = "bom")
    private BomVO bom;

    @Column(name = "time")
    private String time;


    // Common Fields

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
    private String screenName = "ADV FOR STORES";

    @Column(name = "screen_code")
    private String screenCode = "ADV";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    
    @Column(name = "remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "advForStoresVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AdvForStoresDetailsVO> advForStoresDetailsVO;

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}