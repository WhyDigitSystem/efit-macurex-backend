package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detailssubmissiontobankdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DetailsSubmissionToBankDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detailssubmissiontobankdetailsgen")
	@SequenceGenerator(name = "detailssubmissiontobankdetailsgen", sequenceName = "detailssubmissiontobankdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "detailssubmissiontobankdetailsid")
	private Long id;
	@Column(name = "documentname")
	private String documentName;
	@Column(name = "status")
	private String status;
	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;

	@ManyToOne
	@JoinColumn(name = " detailssubmissiontobankid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private DetailsSubmissionToBankVO detailsSubmissionToBankVO;
}
