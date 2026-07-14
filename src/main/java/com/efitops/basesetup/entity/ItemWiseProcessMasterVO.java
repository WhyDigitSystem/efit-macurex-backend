package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemwiseprocess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemWiseProcessMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemwiseprocessgen")
	@SequenceGenerator(name = "itemwiseprocessgen", sequenceName = "itemwiseprocessseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemwiseprocessid")
	private Long id;
	

	@Column(name = "docid")
	private String docId;
	
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "processtype")
	private String processType;
	
	@Column(name = "item")
	private String item; 

	@Column(name = "itemdesc")
	private String itemDesc;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "branchcode", length = 20)
	private String branchCode;
    @Column(name = "finyear", length = 5)
    private String finYear;
	
	private String screenCode="IWP";
	@Column(name = "screenname",length = 30)
	private String screenName="ITEMWISE PROCESS MASTER";
	
	
	@OneToMany(mappedBy = "itemWiseProcessMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemWiseProcessDetailsVO> itemWiseProcessDetailsVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
