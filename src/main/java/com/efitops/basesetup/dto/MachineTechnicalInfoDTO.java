package com.efitops.basesetup.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineTechnicalInfoDTO {

	private Long id;

    private Date installationDate;

    private Long powerConsumption;

    private Long consumption;

    private Long powerProduced;

    private Long capacity;

    private String unit;

    private String bedSize;

    private Long currentInAmps;

    private Long voltage;

    private String cushionTonnage;

    private String machineType;

    private Long hourlyRate;

    private Long instrumentWt;

    private String uom;

    private Date warrantyStDate;

    private Date warrantyEndDate;

    private Date lastCalibratedDate;

    private Date nextDueDate;

    private String lifeCycle;

    private String rangeInfo;

    private String errorAllowed;

    private String frequenceOfCalibration;

    private Date maintenanceDate;

	
}

