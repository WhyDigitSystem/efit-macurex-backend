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
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currencygen")
	@SequenceGenerator(name = "currencygen", sequenceName = "currencyseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "currency_id")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryVO country;
	
	
	@Column(name = "main_currency")
	private String mainCurrency;
	@Column(name = "currency")
	private String currency;
	@Column(name = "sub_currency")
	private String subCurrency;
	@Column(name = "main_currencysymbol")
	private String mainCurrencySymbol;
	@Column(name = "sub_symbol")
	private String subSymbol;
	@Column(name = "currency_representation")
	private String currencyRepresentation;
	@Column(name = "currency_integer")
	private String currencyInteger;
	@Column(name = "currency_decimal")
	private String currencyDecimal;
	@Column(name = "currency_description")
	private String currencyDescription;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName="CURRENCY";
	@Column(name = "screen_code")
	private String screenCode="CUR";
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
