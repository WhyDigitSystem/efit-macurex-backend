package com.efitops.basesetup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificationdesignation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDesignationVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationdesignationgen")
	@SequenceGenerator(name = "notificationdesignationgen", sequenceName = "notificationdesignationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "notificationdesignationid")
	private Long id;

	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedy")
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "designationname")
	private String designationname;
	@Column(name = "designationcode")
	private String designationcode;
	
	@Column(name = "screencode", length = 30)
	private String screenCode = "ND";
	@Column(name = "screenname", length = 30)
	private String screenName = "NOTIFICATION DESIGNATION";
	
	@Column(name = "branch", length = 25)
	private String branch;
//	@Column(name = "count")
//	private int count;
	
	
	@Column(name = "docid")
	private String docId;
	
	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;
	
	@OneToMany(mappedBy = "notificationDesignationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<NotificationDesignationDetailsVO> notificationDesignationDetailsVO;

}
