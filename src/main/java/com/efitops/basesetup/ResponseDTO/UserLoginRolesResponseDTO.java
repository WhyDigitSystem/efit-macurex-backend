package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRolesResponseDTO {

    private Long id;

    private Long roleId;

    private String role;

    private LocalDate startDate;

    private LocalDate endDate;


}