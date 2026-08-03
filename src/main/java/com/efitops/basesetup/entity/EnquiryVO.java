package com.efitops.basesetup.entity;

import java.time.LocalDate;
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
@Table(name = "enquiry")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnquiryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirygen")
	@SequenceGenerator(name = "enquirygen", sequenceName = "enquiryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "enquiry_id")
	private Long id;

	@Column(name = "enquiry_no")
	private String enquiryNo;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne
	@JoinColumn(name = "party_id")
	private CustomerVO partyid;

	@ManyToOne
	@JoinColumn(name = "contact_name")
	private CustomerContactDetailsVO contactName;

	@Column(name = "enquiry_type")
	private String enquiryType;
	@Column(name = "enquiry_date")
	private LocalDate enquiryDate;
	@Column(name = "party_name")
	private String partyName;
	@Column(name = "party_ref_no")
	private String partyRefNo;
	@Column(name = "party_ref_date")
	private LocalDate partyRefDate;
	@Column(name = "enquiry_due_date")
	private LocalDate enquiryDueDate;
	@Column(name = "contact_email")
	private String contactEmail;
	@Column(name = "status")
	private String status;
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
	@Column(name = "screen_name")
	private String screenName = "ENQUIRY";
	@Column(name = "screen_code")
	private String screenCode = "EQN";

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

	@OneToMany(mappedBy = "enquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<EnquiryDetailsVO> enquiryDetails;

	@OneToMany(mappedBy = "enquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<EnquiryTermsandCondVO> enquiryTermsandCond;

	@OneToMany(mappedBy = "enquiryVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<EnquiryAttachmentVO> enquiryAttachment;

}
