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
@Table(name = "partyacc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingOfPartyToAccVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyaccgen")
	@SequenceGenerator(name = "partyaccgen", sequenceName = "partyaccseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partyacc_id")
	private Long id;
	
	@Column(name = "doc_id")
	private Long docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate;
	
	@Column(name = "as_on_date")
	private LocalDate asOnDate;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private ListOfValuesVO category;
	
	
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
	private String screenCode = "MPA";
	@Column(name = "screenname", length = 30)
	private String screenName = "Mapping Of Party To Account";

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
	
	@OneToMany(mappedBy = "mappingOfPartyToAccVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MappingDetailsVO> mappingDetailsVO;

}
