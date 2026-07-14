package com.efitops.basesetup.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
	@Table(name = "sampleimageresponse")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor

	public class SampleImageResponseVO {
		
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sampleimageresponsegen")
		@SequenceGenerator(name = "sampleimageresponsegen", sequenceName = "sampleimageresponseseq", initialValue = 1000000001, allocationSize = 1)

		@Column(name = "sampleimageresponseid")
		
		private Long id;
		
		@Column(name = "filename")
		 private String fileName;
		 
		@Column(name = "profileImage")
		 private String profileImage;
		
		@ManyToOne
		@JsonBackReference
		@JoinColumn(name = "sampleapprovalid")
		SampleApprovalVO sampleApprovalVO;

	}







