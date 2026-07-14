package com.efitops.basesetup.dto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryDetailsDTO {
	private String partCode;
	private String partDescription;
	private String drawingNo;
	private String revisionNo;
	private String unit;
	private Long requireQty;
	private LocalDate deliveryDate;
	private String remarks;
}
