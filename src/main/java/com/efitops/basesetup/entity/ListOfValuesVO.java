package com.efitops.basesetup.entity;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listofvalues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfValuesVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listofvaluesgen")
	@SequenceGenerator(name = "listofvaluesgen", sequenceName = "listofvaluesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "listofvalues_id")
	private Long id;
	@Column(name = "list_code")
	private String listCode;
	@Column(name = "list_description")
	private String listDescription;
	@Column(name = "cancel")
	private boolean cancel;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String  updatedBy;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name="orgid")
	private Long orgId;
	@Column(name="active")
    private boolean active;
	
	@OneToMany(mappedBy = "listOfValuesVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ListOfValuesDetailsVO> listOfValuesDetailsVO;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
//	@JsonGetter("active")
//	public String getActive() {
//		return active ? "Active" : "In-Active";
//	}
}
