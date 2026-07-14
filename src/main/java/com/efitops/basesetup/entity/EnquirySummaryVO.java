package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enquirysummary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnquirySummaryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquirysummarygen")
	@SequenceGenerator(name = "enquirysummarygen", sequenceName = "enquirysummaryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "enquirysummaryid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "anyadditionalinverstment")
	private BigDecimal anyAdditionalInverstment;
	@Column(name = "additionalmanpower")
	private String additionalManPower;
	@Column(name = "timeframe")
	private String timeFrame;
	@Column(name = "expectedtimefordeliverysample")
	private LocalDate expectedTimeForDeliverySample;
	@Column(name = "regularproduction")
	private String regularProduction;
	@Column(name = "initialreviewcomments")
	private String initialReviewComments;
	@Column(name = "detailreview")
	private String detailReview;
	@Column(name = "conclusion")
	private String conclusion;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "enquiryid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	EnquiryVO enquiryVO;

}
