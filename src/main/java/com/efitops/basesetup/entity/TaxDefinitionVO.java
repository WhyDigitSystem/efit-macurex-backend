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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "taxbasic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDefinitionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxbasicgen")
	@SequenceGenerator(name = "taxbasicgen", sequenceName = "taxbasicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "taxbasic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "module")
	private ListOfValuesVO module;
	
	@Column(name = "tax_no")
	private Long taxNo;
	
	@Column(name = "tax_description")
	private String taxDescription;
	
	@Column(name = "doc_date")
	private LocalDate docDate;
	
	@Column(name = "effective_date")
	private LocalDate effectiveDate;
	
	@Column(name = "fill_copy_of")
	private String fillCopyOF;
	
	@Column(name = "print_name")
	private String PrintName;
	
	
	
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by", length = 25)
	private String createdBy;
	@Column(name = "modify_by", length = 25)
	private String updatedBy;
	@Column(name = "cancel_remarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel=false;
	
	
	
    @Column(name = "finyear", length = 5)
    private String finYear;
	@Column(name = "screencode", length = 30)
	private String screenCode = "TD";
	@Column(name = "screenname", length = 30)
	private String screenName = "Tax Definition Master";

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
	
	@OneToMany(mappedBy = "taxDefinitionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<TaxDefinitionDetailsVO> taxDefinitionDetailsVO;


}
