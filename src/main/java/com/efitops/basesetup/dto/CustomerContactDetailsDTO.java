package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerContactDetailsDTO {

//	private Long id;

    // Department Master ID
    private Long purpose;

    private String contactName;

    private String designation;

    private String phone;

    private String email;

    private String website;
}
