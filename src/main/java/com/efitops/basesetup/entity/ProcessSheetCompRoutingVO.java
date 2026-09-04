package com.efitops.basesetup.entity;

import java.math.BigDecimal;
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
@Table(name = "process_sheet_comp_routing_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_sheet_comp_routing_basicgen")
	@SequenceGenerator(name = "process_sheet_comp_routing_basicgen", sequenceName = "process_sheet_comp_routing_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "process_sheet_comp_routing_basic_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "branch")
	 private BranchVO branch;
	 
	 @Column(name = "doc_id")
	 private String docId;
	 
	 @Column(name = "doc_date")
	 private LocalDate docDate = LocalDate.now();

	 @ManyToOne
	 @JoinColumn(name = "fg_sfg_item_type")
	 private ListOfValuesDetailsVO fgSfgItemType;

	 @ManyToOne
	 @JoinColumn(name = "fg_sfg_item_code")
	 private ItemMasterVO fgSfgItemCode;
	 
	 @Column(name = "item_description")
	 private String itemDescription;

	 @Column(name = "bom_id")
	 private String bomId;

	 
	 @Column(name = "active")
	 private boolean active;
	 
	 //summary
	 
	 @Column(name = "total_mc_value")
	 private BigDecimal totalMcValue;

	 @Column(name = "total_labour_value")
	 private BigDecimal totalLabourValue;

	 @Column(name = "total_tool_fixture_value")
	 private BigDecimal totalToolFixtureValue;

	 @Column(name = "total_consumables_value")
	 private BigDecimal totalConsumablesValue;

	 @Column(name = "total_operation_value")
	 private BigDecimal totalOperationValue;

	 @ManyToOne
	 @JoinColumn(name = "prepared_by")
	 private EmployeeMasterVO preparedBy;
	   
	    @Column(name = "org_id")
	    private Long orgId;
       
	    @Column(name = "created_by")
        private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel = false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName = "PROCESSSHEETCOMPROUTING";
		@Column(name = "screen_code")
		private String screenCode = "PSCR";
		

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
		
		
		@OneToMany(mappedBy = "purchaseOrderAmendmentVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<PurchaseOrderAmendmentDetailsVO> details = new ArrayList<>();

		
	 
	 
	

}
