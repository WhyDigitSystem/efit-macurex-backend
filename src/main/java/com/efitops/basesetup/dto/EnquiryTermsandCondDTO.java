package com.efitops.basesetup.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnquiryTermsandCondDTO {
	
	private Long id;
    private String additionalInvestment;
    private String additionalManPower;
    private LocalDate likelyTimeFrame;
    private LocalDate expectedDeliverySample;
    private String pilotBatch;
    private String regularProduction;
    private String initialReviewComments;
    private String detailDelivery;
    private String statutoryRegulatoryReq;
    private String followUp;
    private String conclusion;
    private String remarks;

}
