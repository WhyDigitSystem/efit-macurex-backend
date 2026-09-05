package com.efitops.basesetup.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tool_category_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class ToolCategoryVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_category_basicgen")
	@SequenceGenerator(name = "tool_category_basicgen", sequenceName = "tool_category_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "tool_category_basic_id")
	private Long id;
	
	  @Column(name = "apllicable_for")
	  private String apllicableFor;
	  
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
		@Column(name = "screen_name")
		private String screenName = "TOOLCATEGORY";
		@Column(name = "screen_code")
		private String screenCode = "TC";
		
		@OneToMany(mappedBy = "toolCategoryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<ToolCategoryDetailVO> toolCategoryDetailVO = new ArrayList<>();
		
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
