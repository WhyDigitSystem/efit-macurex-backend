package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContactDetailsResponseDTO {

    private Long id;

    private DepartmentResponseDTO purpose;

    private String contactName;
    private String designation;
    private String phone;
    private String email;
    private String website;
}
