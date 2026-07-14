package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.sql.Date;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bom")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BomVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bomgen")
	@SequenceGenerator(name = "bomgen", sequenceName = "bomseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bomid")
	private Long id;
	@Column(name = "docid", length = 150)
	private String docid;
	@Column(name="docdate")
	private LocalDate docdate= LocalDate.now();
	@Column(name="producttype")
	private String productType;
	@Column(name="productcode")
	private String productCode;
	@Column(name="productname")
	private String productName;
	@Column(name="uom")
	private String uom;
	@Column(name="qty")
	private BigDecimal qty;
	@Column(name="revision")
	private boolean revision; 
	@Column(name="current")
	private boolean current;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
	
    @Column(name = "finyear", length = 5)
    private String finYear;
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "screencode",length = 30)
	private String screenCode ="BOM";
	@Column(name = "screenname",length = 30)
	private String screenName="BOM";
	
	@OneToMany(mappedBy = "bomVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<BomDetailsVO> bomDetailsVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonGetter("revision")
	public String getRevision() {
		return revision ? "YES" : "NO";
	}
	@JsonGetter("current")
	public String getCurrent() {
		return current ? "YES" : "NO";
	}
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
