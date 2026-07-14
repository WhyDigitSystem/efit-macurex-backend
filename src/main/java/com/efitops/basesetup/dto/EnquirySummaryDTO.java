package com.efitops.basesetup.dto;



import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquirySummaryDTO {
	private BigDecimal anyAdditionalInverstment;
	private String additionalManPower;
	private String timeFrame;
	private LocalDate expectedTimeForDeliverySample;
	private String regularProduction;
	private String initialReviewComments;
	private String detailReview;
	private  String conclusion;
	private String remarks;
}
