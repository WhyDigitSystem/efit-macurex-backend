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
@Table(name = "fgissuetopacking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FgIssueToPackingVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fgissuetopackinggen")
	@SequenceGenerator(name = "fgissuetopackinggen", sequenceName = "fgissuetopackingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "fgissuetopackingid")
	private Long id;	
	@Column(name ="docid")
	private String docId;
	@Column(name ="docdate")
	private LocalDate docDate=LocalDate.now();
	
	@Column(name ="fromdept")
	private String fromDept;
	@Column(name ="todept")
	private String toDept;
	@Column(name ="routecardno")
	private String routeCardNo;
	
	
	@Column(name ="approvedby")
	private String approvedBy;
	@Column(name ="remarks")
	private String remarks;
	@Column(name ="narration")
	private String narration;
	
	@Column(name ="createdby")
	private String createdBy;
	@Column(name ="modifiedby")
	private String updatedBy;
	@Column(name ="cancelremarks")
	private String cancelRemarks;
	@Column(name ="cancel")
	private boolean cancel=false;
	@Column(name ="active")
	private boolean active=true;
	@Column(name ="orgid")
	private Long orgId;
	@Column(name = "screencode", length = 30)
	private String screenCode = "FITP";
	@Column(name = "screenname", length = 30)
	private String screenName = "FG ISSUE TO PACKING";
	

	@OneToMany(mappedBy ="fgIssueToPackingVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<FgIssueToPackingDetailsVO> fgIssueToPackingDetailsVO;
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
