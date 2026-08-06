package com.efitops.basesetup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "despatch_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstructionDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "despatchdetailgen")
	@SequenceGenerator(name = "despatchdetailgen", sequenceName = "despatchdetailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "despatch_detail_id")
	private Long id;
	
	@Column(name = "ord_accp_contr_no")
	private String ordAccpContrNo;
	
	@Column(name = "date")
	private String date;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemMasterVO item;
	
	@Column(name = "pdi")
	private String pdi;
	
	@Column(name = "pdi_date")
	private String pdiDate;
	
	@Column(name = "schdule_month")
	private  String schduleMonth;
	
	@Column(name = "planned_qty")
	private String plannedQty;
	
	@Column(name = "pending_qty")
	private String pendingQty;
	
	@Column(name = "available_qty")
	private String availableQty;
	
	@Column(name = "descQty")
	private String descQty;
	
	@Column(name = "no_of_package")
	private String noOfPackage;
	
	@Column(name = "package_type")
	private String packageType;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="despatch_basic_id")
	    private DespatchInstructionVO despatchInstructionVO;
	
	

}
