package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolRectificationDTO {

	private Long id;

	private Long branch;

	private Long department;

	private String breakdownNo;

	private LocalDate breakdownDate;

	private Long attendBy;

	private String time;

	private String machineToolNo;

	private LocalDateTime rectificationTime;

	private String description;

	private String cause;

	private String maintenanceType;

	private String actionTaken;

	private String natureOfProblem;

	private Long carriedOutBy;

	private String timeTakenForRectification;

	private String location;

	private String sparesUsed;

	private Long preparedBy;

	private Long approvedBy;

	private String remarks;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;

}
