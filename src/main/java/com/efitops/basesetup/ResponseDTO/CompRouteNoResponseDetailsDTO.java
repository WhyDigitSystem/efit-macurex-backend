package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompRouteNoResponseDetailsDTO {
	private Long id;
	private String docId;
	private LocalDate docDate;

}
