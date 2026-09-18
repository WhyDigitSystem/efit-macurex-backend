package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialSampleInspectionDetailResponseDTO {

    private Long id;

    private String parametersToBeChecked;

    private String parameterType;

    private String specification;

    private String tolerance;

    private UnitMasterResponseDTO uom;

    private String sampling1;

    private String sampling2;

    private String sampling3;

    private String sampling4;

    private String sampling5;

    private String remarks;

    private Long initialSampleInspectionVO;
}