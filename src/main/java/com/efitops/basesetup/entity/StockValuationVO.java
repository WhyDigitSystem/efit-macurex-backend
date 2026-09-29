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
@Table(name = "stock_value")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StockValuationVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stock_valuegen")
	@SequenceGenerator(name = "stock_valuegen",sequenceName = "stock_valueseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="stock_value_id")
	private Long id;
	
	
	   
	    @Column(name = "plus_or_minus")
	    private String plusOrMinus;
	    
	    @ManyToOne
	    @JoinColumn(name = "stock_part_no")
	    private ItemMasterVO stockPartNo;

	    @Column(name = "doc_id")
	    private String docId;
	    
	    @Column(name = "doc_date")
	    private LocalDate docDate;

	    @Column(name = "quantity")
	    private BigDecimal quantity;

	    @Column(name = "rate")
	    private BigDecimal rate;

	    @Column(name = "stockvalue")
	    private BigDecimal stockValue;

	    @Column(name = "doc_time")
	    private LocalTime docTime;
	   	    	
	    @Column(name = "lot_no")
	    private String lotNo;

	    @Column(name = "narration")
	    private String narration; 
	    
	    @ManyToOne
	    @JoinColumn(name = "locdetails_id")
	    private LocationVO locDetailsId;

	    @ManyToOne
	    @JoinColumn(name = "branch_id")
	    private BranchVO branchVO;
	    
	    
	    @Column(name = "active")
		private boolean active;

	    
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
		@Column(name = "source_screen_name")
		private String sourceScreenName;
		@Column(name = "source_screen_code")
		private String sourceScreenCode;
		
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
