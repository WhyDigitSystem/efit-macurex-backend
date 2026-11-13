package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSheetCompRoutingResponseDetails {

	private Long id;	 
	 private String docId;
	 
	 private LocalDate docDate ;
	 
	    private List<OperationMasterResponseforPSCRDTO> operations;
}
