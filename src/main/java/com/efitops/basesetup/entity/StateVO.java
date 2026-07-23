package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stategen")
	@SequenceGenerator(name = "stategen",sequenceName = "stateseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="state_id")
	private Long id;
	
	@Column(name="code")
	private String stateCode;
	@Column(name="state")
	private String stateName;
     
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryVO country;
	
	@Column(name="region")
    private String region;
	@Column(name="state_number")
	 private String stateNumber;
	@Column(name="active")
    private boolean active;
//	@Column(name="userid")
//    private String userId;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="modified_by")
	private String updatedBy;
    @Column(name="orgid")
	private Long orgId;
    @Column(name="cancel")
	private boolean cancel;
    @Column(name="cancelRemarks")
	private String cancelRemarks;
    
    @Column(name = "screen_code", length = 5)
    private String screenCode = "ST";

    @Column(name = "screen_name", length = 25)
    private String screenName = "STATE";
    
    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
    
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
