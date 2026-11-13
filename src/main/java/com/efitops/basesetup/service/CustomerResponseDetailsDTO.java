package com.efitops.basesetup.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDetailsDTO {
	private Long id;
	private String customerName;
	private String customerCode;

}
