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
@Table(name = "machine_equipments_master")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MachineMasterVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_equipments_mastergen")
	@SequenceGenerator(name = "machine_equipments_mastergen", sequenceName = "machine_equipments_masterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machine_equipments_master_id")
	private Long id;
	
	
	 @ManyToOne
	 @JoinColumn(name = "branch")
	 private BranchVO branch;
	 
	 @ManyToOne
	 @JoinColumn(name = "department")
	 private DepartmentVO department;
	 
	 @ManyToOne
	 @JoinColumn(name = "type")
	 private ListOfValuesDetailsVO type;
	 
	 @Column(name = "machine_instrument_no")
	 private String machineInstrumentNo;
	   
	 @Column(name = "machine_instrument_name")
	 private String machineInstrumentName;
	   
	 @Column(name = "calibration_required")
	 private String calibrationRequired;

     @ManyToOne
	 @JoinColumn(name = "location")
	 private LocationVO location;
     
	 @Column(name = "process_no")
	 private String processNo;

     @ManyToOne
	 @JoinColumn(name = "machine_instrument_category")
	 private ListOfValuesDetailsVO machineInstrumentCategory;

     @Column(name = "section")
	 private String section;

	 @Column(name = "model")
	 private String model;

	 @Column(name = "serial_no")
	 private String serialNo;

	 @Column(name = "status")
	 private String status;

	 @Column(name = "manufactured_by")
	 private String manufacturedBy;

     @ManyToOne
	 @JoinColumn(name = "made_in")
	 private CountryVO madeIn;

     @ManyToOne
	 @JoinColumn(name = "purchased_from")
	 private CustomerVO purchasedFrom;

     @Column(name = "mode_of_purchase")
	 private String modeOfPurchase;
 
     @Column(name = "machine_instrument_incharge")
     private String machineInstrumentIncharge;

     @Column(name = "machine_instrument_used_for")
	 private String machineInstrumentUsedFor;

     @Column(name = "pm_checklist_no")
	 private String pmChecklistNo;

     @Column(name = "remarks")
	 private String remarks;

     @Column(name = "make")
	 private String make;
     
     
     //image
     
     @Column(name = "machineinstrument_imagename")
	 private String machineInstrumentImageName;
     
     @Column(name = "machine_or_instrument")
	 private String machineOrInstrument;

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
 	private String screenName = "MACHINEMASTER";
 	@Column(name = "screen_code")
 	private String screenCode = "MM";
 	
 	
 	@OneToMany(mappedBy = "machineMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MachineTechnicalInfoVO> machineTechnicalInfoVO = new ArrayList<>();
 	
 	
 	@OneToMany(mappedBy = "machineMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MachineSpareDetailsVO> machineSpareDetailsVO = new ArrayList<>();
 	
 	
 	@OneToMany(mappedBy = "machineMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MachineHistoryVO> machineHistoryVO = new ArrayList<>();
 	
 	
 	@OneToMany(mappedBy = "machineMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MachineMasterAttachmentVO> machineMasterAttachmentVO = new ArrayList<>();




 	
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
