package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DocumentTypeMappingDetailsDTO {

	    private Long id;
        private String screenName;
        private String screenCode;
        private String docCode;
        private String prefix;
        private boolean active;
	}


