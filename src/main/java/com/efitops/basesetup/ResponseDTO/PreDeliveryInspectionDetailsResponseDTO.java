package com.efitops.basesetup.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreDeliveryInspectionDetailsResponseDTO {

    private Long id;
    private String parameter;
    private String parameterType;
    private String specification;
    private String instrumentName;
    private UnitResponseDTO unit;
    private String tol;
    private String method;
    private String obs1;
    private String obs2;
    private String obs3;
    private String obs4;
}