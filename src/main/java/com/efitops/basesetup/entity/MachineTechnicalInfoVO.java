package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "machine_technicalinfo_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineTechnicalInfoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "machine_technicalinfo_detailgen")
    @SequenceGenerator( name = "machine_technicalinfo_detailgen",sequenceName = "machine_technicalinfo_detailseq",initialValue = 1000000001,allocationSize = 1)
    @Column(name = "machine_technicalinfo_detail_id")
    private Long id;


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
    
    @ManyToOne
    @JoinColumn(name = "machine_equipments_master_id")
    @JsonBackReference
    private MachineMasterVO machineMasterVO;
    
}