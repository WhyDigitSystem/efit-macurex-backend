package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOtherSalesResponseDTO {
private Long id;
private String customerName;
private String customerCode;
private String customerGstNo;
private String gstApproval;

}
