package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDropdownResponseDTO {

    private Long customerId;
    private String customerCode;
    private String customerName;
    private String address;
    private String gstState;
    private String gstNo;
    private boolean igstApplicable;
    private String gstType;
}
