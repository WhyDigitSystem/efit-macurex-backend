package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialStageInspectionDetailDTO {

    private Long id;

    private String operationNo;

    private String parametersToBeChecked;

    private String specification;

    private String sampling1;

    private String sampling2;

    private String sampling3;

    private String sampling4;

    private String sampling5;

    private String time;

    private Long operatorName;

    private LocalDate operationDate;

    private String remarks;

    private Long initialStageInspectionVO;

}