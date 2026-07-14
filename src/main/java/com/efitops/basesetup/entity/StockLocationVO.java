package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stocklocation")	
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StockLocationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stocklocationgen")
	@SequenceGenerator(name = "stocklocationgen", sequenceName = "stocklocationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stocklocationid")
	private Long id;
	
	@Column(name ="locationcode")
	private String locationCode;
	@Column(name ="locationname")
	private String locationName;
	@Column(name ="company")
	private String company;
	@Column(name ="branch")
	private String branch;
	@Column(name ="startdate")
	private LocalDate startDate;
	@Column(name ="closeddate")
	private LocalDate closedDate;
	@Column(name ="orgid")
	private Long orgId;
	private boolean active;
	private boolean cancel;
	@Column(name ="createdby")
	private String createdBy;
	@Column(name ="modifiedby")
	private String updatedBy;
	@Column(name = "cancelremarks")
    private String cancelRemarks;
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
}
