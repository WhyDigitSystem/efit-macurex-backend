package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionDetailsDTO {

    private String parameterType;

    private String parameter;

    private String tol;

    private LocalDate date;

    private String time1;

    private String obs1;

    private String obs2;

    private String obs3;

    private String obs4;

    private String obs5;

    private String deviationObserved;

    private String correctiveAction;

    private String remarks;
}