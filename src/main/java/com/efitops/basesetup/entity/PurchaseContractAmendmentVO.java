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
@Table(name = "pcamdbasic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseContractAmendmentVO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pcamdbasic_seq")
    @SequenceGenerator(name = "pcamdbasic_seq", sequenceName = "pcamdbasic_seq",initialValue = 1000000001, allocationSize = 1)
	
    @Column(name = "pcamdbasic_id")
    private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "branch")
	 private BranchVO branch;
	 
	 @Column(name = "belongs_to")
	 private String belongsTo;
	 
	 @Column(name = "doc_id")
	 private String docId;
	 
	 @Column(name = "doc_date")
	 private LocalDate docDate;
	 
	 @ManyToOne
	 @JoinColumn(name = "customer")
	 private CustomerVO customer;
	 
	
	 
	 @Column(name = "contract_no")
	 private String contractNo;

	 @Column(name = "contract_date")
	 private LocalDate contractDate;
	 
	 @Column(name = "revision_no")
	 private int revisionNo;
	 
	 @Column(name = "ref_no")
	 private String refNo;

	 @Column(name = "ref_date")
	 private LocalDate refDate;
	 
	 @Column(name = "active")
	 private boolean active;
	 
	 
	// ================= Summary =================

	 @Column(name = "freight_type")
	 private String freightType;

	 @Column(name = "packing_type")
	 private String packingType;

	 @Column(name = "insurance_amount")
	 private BigDecimal insuranceAmount;
	 
	 @Column(name = "mode_of_despatch")
	 private String modeOfDespatch;

	 @Column(name = "tax_description")
	 private String taxDescription;

	 @Column(name = "prepared_by")
	 private String preparedBy;

	 @Column(name = "authorised_by")
	 
	 private String authorisedBy;

	 @Column(name = "remarks")
	 private String remarks;
	    

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
		private String screenName = "PURCHASEORDERAMENDMENT";
		@Column(name = "screen_code")
		private String screenCode = "POA";
		
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
        
        @OneToMany(
                mappedBy = "purchaseContractAmendmentVO",
                cascade = CascadeType.ALL
        )
        @JsonManagedReference
        private List<PurchaseContractAmendmentDetailsVO>
                purchaseContractAmendmentDetails = new ArrayList<>();


        @OneToMany(
                mappedBy = "purchaseContractAmendmentVO",
                cascade = CascadeType.ALL
        )
        @JsonManagedReference
        private List<PurchaseContractAmendmentAttachmentVO>
                purchaseContractAmendmentAttachment = new ArrayList<>();
        

	    
	    
	 
	 

}
