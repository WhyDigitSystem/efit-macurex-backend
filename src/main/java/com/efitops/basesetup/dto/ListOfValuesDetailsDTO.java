package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfValuesDetailsDTO {
	private String valueCode;
	private String valueDescription;
	private boolean active;
}

