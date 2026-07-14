package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.ExportPackingListDetailsVO;
import com.efitops.basesetup.entity.ExportPackingListTermsVO;
import com.efitops.basesetup.entity.ExportPackingListVO;
import com.efitops.basesetup.entity.ExportPackingShippingListVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportPackingListTermsDTO {

	private String term;
	private String description;
	
}
