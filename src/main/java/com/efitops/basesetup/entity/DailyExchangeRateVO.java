package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dailyexrate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyExchangeRateVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailyexrategen")
	@SequenceGenerator(name = "dailyexrategen", sequenceName = "dailyexrateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailyexrate_id")
	private Long dailyExRateId;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "month")
	private String month;
	
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
	
    @Column(name = "financial_year", length = 5)
    private String finYear;
	@Column(name = "screen_code", length = 30)
	private String screenCode = "DER";
	@Column(name = "screen_name", length = 30)
	private String screenName = "dailyExchangeRate";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


	
	

}
