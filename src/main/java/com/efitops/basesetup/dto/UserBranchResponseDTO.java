package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBranchResponseDTO {

	private Long id;
//    private Long branchId;
    private String branch;
    private String branchCode;
}
