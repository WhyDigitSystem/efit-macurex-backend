package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "drawing_attachment_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class DrawingAttachmentsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drawing_attachment_basicgen")
	@SequenceGenerator(name = "drawing_attachment_basicgen", sequenceName = "drawing_attachment_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "drawing_attachment_basic_id")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "type_of_item")
	 private ListOfValuesDetailsVO typeOfItem;

	 @ManyToOne
	 @JoinColumn(name = "fg_part_no")
	 private ItemMasterVO fgPartNo;

	 @Column(name = "fg_part_description")
	 private String fgPartDescription;
	 
	 
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
		private String screenName = "DRAWINGATTACHMENTS";
		@Column(name = "screen_code")
		private String screenCode = "DA";
		
		
		@OneToMany(mappedBy = "drawingAttachmentsVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonManagedReference
		private List<DrawingAttachmentDetailVO> drawingAttachmentDetailVO = new ArrayList<>();
		
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
