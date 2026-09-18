package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialSampleInspectionDetailDTO {

    private Long id;

    private String parametersToBeChecked;

    private String parameterType;

    private String specification;

    private String tolerance;

    private Long uom;

    private String sampling1;

    private String sampling2;

    private String sampling3;

    private String sampling4;

    private String sampling5;

    private String remarks;

    private Long initialSampleInspectionVO;
}