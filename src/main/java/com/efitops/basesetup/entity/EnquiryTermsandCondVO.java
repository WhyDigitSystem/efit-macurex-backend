package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquirytermsandcond")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryTermsandCondVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirytermsandcondgen")
    @SequenceGenerator(name = "enquirytermsandcondgen",sequenceName = "enquirytermsandcondseq",initialValue = 1000000001,allocationSize = 1)
    @Column(name = "enquirytermsandcond_id")
    private Long id;

   
    @Column(name = "additional_investment")
    private String additionalInvestment;
    @Column(name = "additional_man_power")
    private String additionalManPower;
    @Column(name = "likely_time_frame")
    private LocalDate likelyTimeFrame;
    @Column(name = "expected_delivery_sample")
    private LocalDate expectedDeliverySample;
    @Column(name = "pilot_batch")
    private String pilotBatch;
    @Column(name = "regular_production")
    private String regularProduction;
    @Column(name = "initial_review_comments")
    private String initialReviewComments;
    @Column(name = "detail_delivery")
    private String detailDelivery;
    @Column(name = "statutory_regulatory_req")
    private String statutoryRegulatoryReq;
    @Column(name = "follow_up")
    private String followUp;
    @Column(name = "conclusion")
    private String conclusion;
    @Column(name = "remarks")
    private String remarks;
    
   
    
    @ManyToOne
    @JoinColumn(name = "enquiry_id")
    @JsonBackReference
    private EnquiryVO enquiryVO;
}