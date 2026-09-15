package com.efitops.basesetup.entity;

import java.time.LocalTime;

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
@Table(name = "set_up_approval_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "set_up_approval_detailsgen")
	@SequenceGenerator(name = "set_up_approval_detailsgen", sequenceName = "set_up_approval_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "set_up_approval_details_id")
    private Long id;
	
	@Column(name = "operation_no")
	private String operationNo;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "specification")
	private String Specification;
	
	@Column(name = "details1")
	private String details1;
	
	@Column(name = "details2")
	private String details2;
	
	@Column(name = "details3")
	private String details3;
	
	@Column(name = "details4")
	private String details4;
	
	@Column(name = "details5")
	private String details5;
	
	@Column(name = "details6")
	private String details6;
	
	@Column(name = "details7")
	private String details7;
	
	@Column(name = "details8")
	private String details8;
	
	@Column(name = "details9")
	private String details9;
	
	@Column(name = "details10")
	private String details10;
	
	@Column(name = "time")
	private LocalTime time;
	
	@Column(name = "remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "set_up_approval_basic_id")
	@JsonBackReference
	private SetUpApprovalVO setUpApprovalVO;
	

}
