package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
	 private ToolCategoryDetailVO machineInstrumentCategory;

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
     
     @Column(name = "installation_date")
     private LocalDate installationDate;

     @Column(name = "power_consumption")
     private BigDecimal powerConsumption;

     @Column(name = "consumption")
     private BigDecimal consumption;

     @Column(name = "power_produced")
     private BigDecimal powerProduced;

     @Column(name = "technical_specification")
     private String technicalSpecification;

     @Column(name = "capacity")
     private BigDecimal capacity;

     @ManyToOne
     @JoinColumn(name = "unit")
     private ItemMasterVO unit;

     @Column(name = "bed_size_mm")
     private BigDecimal bedSizeMm;

     @Column(name = "current_in_amps")
     private BigDecimal currentInAmps;

     @Column(name = "voltage")
     private BigDecimal voltage;

     @Column(name = "cushion_tonnage")
     private BigDecimal cushionTonnage;

     @Column(name = "parallelity")
     private BigDecimal parallelity;

     @ManyToOne
     @JoinColumn(name = "machine_type")
     private ListOfValuesDetailsVO machineType;

     @Column(name = "hourly_rate")
     private BigDecimal hourlyRate;

     @Column(name = "machine_instrument_weight")
     private BigDecimal machineInstrumentWeight;

     @ManyToOne
     @JoinColumn(name = "uom")
     private UnitMasterVO uom;

     @Column(name = "warranty_start_date")
     private LocalDate warrantyStartDate;

     @Column(name = "warranty_end_date")
     private LocalDate warrantyEndDate;

     @Column(name = "last_calibrated_date")
     private LocalDate lastCalibratedDate;

     @Column(name = "next_due_date")
     private LocalDate nextDueDate;

     @Column(name = "life_cycle_year")
     private String lifeCycleYear;

     @Column(name = "machine_range")
     private String range;
     
     @Column(name = "error_allowed")
     private String errorAllowed;

     @Column(name = "frequency_of_calibration")
     private String frequencyOfCalibration;

     @Column(name = "instrument_cost")
     private BigDecimal instrumentCost;
     
     @Column(name = "calibration_cost")
     private BigDecimal calibrationCost;

     @Column(name = "calibration_agency")
     private String calibrationAgency;

     @Column(name = "certificate_no")
     private String certificateNo;

     @Column(name = "shut_height_mm")
     private BigDecimal shutHeightMm;

     @Column(name = "stroke_mm")
     private BigDecimal strokeMm;

     @Column(name = "cushion")
     private BigDecimal cushion;

     @Column(name = "hp")
     private BigDecimal hp;

     @Column(name = "hc_no")
     private String hcNo;

     @Column(name = "range_size")
     private BigDecimal rangeSize;

     @Column(name = "leastcount")
     private BigDecimal leastcount;

     @Column(name = "go_size")
     private BigDecimal goSize;

     @Column(name = "no_go_size")
     private BigDecimal noGoSize;

     @Column(name = "ram_size")
     private BigDecimal ramSize;

     @Column(name = "throat_depth")
     private BigDecimal throatDepth;

     @Column(name = "throat_gap")
     private BigDecimal throatGap;

     @Column(name = "maintenance_date")
     private LocalDate maintenanceDate;
     
     
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
