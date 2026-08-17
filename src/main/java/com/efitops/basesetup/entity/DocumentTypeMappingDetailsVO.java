package com.efitops.basesetup.entity;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documenttypemapping_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeMappingDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documenttypemappingdetailsgen")
	@SequenceGenerator(name = "documenttypemappingdetailsgen", sequenceName = "documenttypemappingdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documenttypemappingdetails_id")
	private Long id;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "screen_code")
	private String screenCode;

	@Column(name = "doc_code")
	private String docCode;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name = "last_no")
	private Long lastNo = 1L;

	@Column(name = "fin_year", length = 10)
	private String finYear;

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

	@ManyToOne
	@JoinColumn(name = "documenttypemappingmaster_id")
	@JsonBackReference
	private DocumentTypeMappingVO documentTypeMappingMasterVO;
}