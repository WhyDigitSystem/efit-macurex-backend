package com.efitops.basesetup.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDTO {
	
	private Long id;
	private String shiftName;
	private String shiftType;
	private String shiftCode;
	private LocalDateTime fromHour;
	private LocalDateTime toHour;
	private String timing;
	private Long orgId;
	private String createdBy;
	private boolean active;
	
	 
	private List<ShiftDetailsDTO> ShiftDetailsDTO; 
	
	private List<ShiftBreakTimingDetailsDTO> shiftBreakTimingDetailsDTO; 
	
}