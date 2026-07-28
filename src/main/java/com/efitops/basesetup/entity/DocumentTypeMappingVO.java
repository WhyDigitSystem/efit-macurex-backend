package com.efitops.basesetup.entity;

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
@Table(name = "documenttypemappingmaster")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DocumentTypeMappingVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypemappingmastergen")
	@SequenceGenerator(name = "documenttypemappingmastergen", sequenceName = "documenttypemappingmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypemappingmaster_id")
    private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "branch")
	 private BranchVO branch;

	 @ManyToOne
	 @JoinColumn(name = "financial_year")
	 private FinancialYearVO financialYear;
	 
	 @Column(name = "org_id")
		private Long orgId;
		@Column(name = "active")
		private boolean active;
		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel=false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName="UNITMASTER";
		@Column(name = "screen_code")
		private String screenCode="UM";
		
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
		
		@OneToMany(
		        mappedBy = "documentTypeMappingMasterVO",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true)
		@JsonManagedReference
		private List<DocumentTypeMappingDetailsVO> details;
		

	}

	


