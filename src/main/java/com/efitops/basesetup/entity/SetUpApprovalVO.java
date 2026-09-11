package com.efitops.basesetup.entity;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "set_up_approval_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "set_up_approval_basicgen")
	@SequenceGenerator(name = "set_up_approval_basicgen", sequenceName = "set_up_approval_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "set_up_approval_basic_id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name  = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
//	shift master
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "process_sheet_no")
	private String processSheetNo;
	
	
	
	
	

}
