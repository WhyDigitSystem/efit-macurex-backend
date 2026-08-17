package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;


import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documenttypemapping_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

	@Column(name = "fin_year", length = 10)
	private String finYear;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branch_code", length = 25)
	private String branchCode;

	@Column(name = "finyearidentifier", length = 50)
	private String finYearIdentifier;

	@Column(name = "concatenation", length = 25)
	private String concatenation;

	@Column(name = "last_no")
	private int lastNo = 1;

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
	@JoinColumn(name = "document_type_mapping_id")
	@JsonBackReference
	private DocumentTypeMappingVO documentTypeMappingMasterVO;
}