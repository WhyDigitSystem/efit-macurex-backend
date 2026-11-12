package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EightDiscipline2DetailResponseDTO {

    private Long id;

    private String problem;

    private LocalDate dateOfComplaint;

    private String repeatDiscrepancy;

    private String prevGDControlNo;

    private LocalDate date;

    private String reason;

}	