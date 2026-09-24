package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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
@Table(name = "reconcile_consumption_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileConsumptionStockVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reconcile_consumption_stockgen")
    @SequenceGenerator(
            name = "reconcile_consumption_stockgen",
            sequenceName = "reconcile_consumption_stockseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "reconcile_consumption_stock_id",
            columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "reconcile_date")
    private LocalDate reconcileDate;

    @ManyToOne
    @JoinColumn(name = "shop_floor")
    private LocationVO shopFloor;

    @ManyToOne
    @JoinColumn(name = "fg_item")
    private ItemMasterVO fgItem;

    @ManyToOne
    @JoinColumn(name = "rm_location")
    private LocationVO rmLocation;

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
    private String screenName = "RECONCILE CONSUMPTION STOCK";

    @Column(name = "screen_code")
    private String screenCode = "RCS";

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @OneToMany(
            mappedBy = "reconcileConsumptionStockVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<ReconcileConsumptionStockDetailsVO> details =
            new ArrayList<>();

    @JsonGetter("activeStatus")
	public String getActiveStatus() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancelStatus")
	public String getCancelStatus() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
