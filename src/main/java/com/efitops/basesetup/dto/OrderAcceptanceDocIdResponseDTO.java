package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAcceptanceDocIdResponseDTO {

	private Long orderAccptanceId;

	private String docId;

	private LocalDate docDate;
}
