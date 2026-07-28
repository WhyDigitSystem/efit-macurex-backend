package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "dailyexchangerate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyExchangeRateVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailyexchangerategen")
	@SequenceGenerator(name = "dailyexchangerategen", sequenceName = "dailyexchangerateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dailyexchangerate_id")
	private Long dailyExchangeRateId;
	
	@Column(name = "currency_symbol", length = 10)
    private String currencySymbol;

    @Column(name = "currency_name", length = 100)
    private String currencyName;

    @Column(name = "exchange_rate", precision = 18, scale = 4)
    private BigDecimal exchangeRate;

    @Column(name = "exchange_from_date")
    private String exchangeFromDate;

    @Column(name = "exchange_to_date")
    private String exchangeToDate;
    
	
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
    private String financialYear;
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
