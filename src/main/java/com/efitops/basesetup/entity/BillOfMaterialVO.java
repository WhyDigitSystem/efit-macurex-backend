package com.efitops.basesetup.entity;


import java.time.LocalDate;
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
@Table(name = "bill_of_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterialVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_of_materialgen")
    @SequenceGenerator(name = "bill_of_materialgen", sequenceName = "bill_of_materialseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "bill_of_material_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "type_of_bom")
    private String typeOfBom;

    @Column(name = "type_of_item")
    private String typeOfItem;

    @Column(name = "fg_sfg_item_code")
    private Long fgSfgItemCode;

    @Column(name = "fg_sfg_item_description")
    private String fgSfgItemDescription;

    @Column(name = "revision_no")
    private Integer revisionNo = 1;

    @Column(name = "specifications")
    private String specifications;

    // "Fill Details of" and "Fill Details of Item" dropdowns
    @Column(name = "fill_details_of")
    private String fillDetailsOf;

    @Column(name = "fill_details_of_item")
    private String fillDetailsOfItem;

    @Column(name = "wef")
    private LocalDate wef;

    // "FG Reference To Profit" dropdown
    @Column(name = "fg_reference_to_profit")
    private String fgReferenceToProfit;

    @Column(name = "fmanbou")
    private String fmanbou;

    // ---------- Common Fields ----------
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
    private String screenName = "BillOfMaterial";

    @Column(name = "screen_code")
    private String screenCode = "BOM";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchVO branch;


    @OneToMany(mappedBy = "billOfMaterialVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BillOfMaterialDetailsVO> billOfMaterialDetailsVO;

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
