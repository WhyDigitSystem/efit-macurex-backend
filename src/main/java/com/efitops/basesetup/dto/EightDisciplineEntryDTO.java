package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EightDisciplineEntryDTO {
	
	
	    private Long id;

	    private String docId;

	    private LocalDate docDate;

	    private String complaintType;

	    private Long customer;

	    private Long complaintNo;

	    private Long customerName;

	    private Long itemCode;

	    private String itemDescription;

	    private Long rootCauseNo;

	    private LocalDate rootCauseDate;

	    private LocalDate dateOpened;

	    private LocalDate targetDate;

	    private String remarks;

	    private boolean active;

	    private Long orgId;

	    private String financialYear;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    private List<EightDiscipline1DetailDTO> eightDiscipline1DetailDTO;
	    
	    private List<EightDiscipline2DetailDTO> eightDiscipline2DetailDTO;
	    
	    private List<EightDiscipline3DetailDTO> eightDiscipline3DetailDTO;
	    
	    private List<EightDiscipline4DetailDTO> eightDiscipline4DetailDTO;
	    
	    private List<EightDiscipline5DetailDTO> eightDiscipline5DetailDTO;
	    
	    private List<EightDiscipline6DetailDTO> eightDiscipline6DetailDTO;
	    
	    private List<EightDiscipline7DetailDTO> eightDiscipline7DetailDTO;
	    
	    private List<EightDiscipline8DetailDTO> eightDiscipline8DetailDTO;








}
