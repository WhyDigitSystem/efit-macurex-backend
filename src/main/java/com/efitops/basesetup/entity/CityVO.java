package com.efitops.basesetup.entity;

import javax.persistence.Column;
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
@Table(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citygen")
	@SequenceGenerator(name = "citygen", sequenceName = "cityseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "city_id")
	private Long id;

	@Column(name = "code")
	private String cityCode;
	
	
	@ManyToOne
	@JoinColumn(name = "country")
	private CountryVO country;
	
	@Column(name = "city")
	private String cityName;
	
	
	@ManyToOne
	@JoinColumn(name = "state")
	private StateVO state;
	
	@Column(name = "active")
	private boolean active;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
    @Column(name = "screen_code", length = 5)
    private String screenCode = "CT";

    @Column(name = "screen_name", length = 25)
    private String screenName = "CITY";

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
