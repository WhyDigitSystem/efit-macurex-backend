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
@Table(name = "transfer_order_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferOrderVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_order_basicgen")
	@SequenceGenerator(name = "transfer_order_basicgen", sequenceName = "transfer_order_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "transfer_order_basic_id")
	private Long id;
	
	
	 @ManyToOne
	 @JoinColumn(name = "order_type")
	 private ListOfValuesDetailsVO orderType;

	 @Column(name = "doc_id")
	 private String docId;

	 @Column(name = "doc_date")
	 private LocalDate docDate = LocalDate.now();
	 
	 
	    @Column(name = "active")
		private boolean active;
	    
	    @Column(name = "org_id")
		private Long orgId;
	    
	    @Column(name = "financial_year")
	    private String financialYear;

		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel = false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName = "TRANSFERORDER";
		@Column(name = "screen_code")
		private String screenCode = "TO";
		
		@OneToMany(mappedBy = "transferOrderVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<TransferOrderDetailVO> transferOrderDetailVO = new ArrayList<>();
		
		
		
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
