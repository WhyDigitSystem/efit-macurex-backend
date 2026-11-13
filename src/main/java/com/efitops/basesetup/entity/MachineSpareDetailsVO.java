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
@Table(name = "machine_spare_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineSpareDetailsVO {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "machine_spare_detailsgen")
    @SequenceGenerator(name = "machine_spare_detailsgen",sequenceName = "machine_spare_detailsseq",initialValue = 1000000001,allocationSize = 1)
    @Column(name = "machine_spare_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spare_id")
    private ItemMasterVO spareId;

    @Column(name = "spare_description")
    private String spareDescription;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "critical")
    private boolean critical;

    @Column(name = "model_no")
    private String modelNo;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "warranty_till_date")
    private LocalDate warrantyTillDate;

    @Column(name = "calibration_required")
    private String calibrationRequired;

    @Column(name = "last_calibrated_date")
    private LocalDate lastCalibratedDate;

    @ManyToOne
    @JoinColumn(name = "machine_equipments_master_id")
    @JsonBackReference
    private MachineMasterVO machineMasterVO;

}