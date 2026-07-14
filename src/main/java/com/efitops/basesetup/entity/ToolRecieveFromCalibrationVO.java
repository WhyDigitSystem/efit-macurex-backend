package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "toolrecievefromcalibration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolRecieveFromCalibrationVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toolrecievefromcalibrationgen")
	@SequenceGenerator(name = "toolrecievefromcalibrationgen", sequenceName = "toolrecievefromcalibrationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "toolrecievefromcalibrationid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "issueno")
	private String issueNo;
	@Column(name = "issuedate")
	private String issueDate;
	@Column(name = "recievedfrom")
	private String recievedFrom;
	@Column(name = "recievedby")
	private String recievedBy;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode", length = 30)
	private String screenCode = "TRC";
	@Column(name = "screenname", length = 30)
	private String screenName = "TOOL RECIEVE FROM CALIBRATION";

	@OneToMany(mappedBy = "toolRecieveFromCalibrationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ToolRecieveFromCalibrationDetailsVO> toolRecieveFromCalibrationDetailsVO;

    @OneToMany(mappedBy = "toolRecieveFromCalibrationVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ToolRecieveFromCalibrationImagesVO> toolRecieveFromCalibrationImagesVO ;
    
	@OneToMany(mappedBy = "toolRecieveFromCalibrationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ToolRecieveFromCalibrationAttachmentVO> documents;
    
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
