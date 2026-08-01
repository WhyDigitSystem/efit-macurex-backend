package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.entity.EmployeeMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesCustomerResponseDTO {

	private Long customerId;

	private String customerName;

	private String customerType;

    private GSTStateResponseDTO gstState;

	private boolean igstApplicable;

	private String gstnNo;
}
