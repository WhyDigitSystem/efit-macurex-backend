package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gst")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gstgen")
	@SequenceGenerator(name = "gstgen", sequenceName = "gstseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gstid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "gstslab")
	private String gstSlab;
	@Column(name = "gstpercentage", precision = 10, scale = 2)
	private BigDecimal gstPercentage;
	@Column(name = "igstpercentage", precision = 10, scale = 2)
	private BigDecimal igstPercentage;
	@Column(name = "cgstpercentage", precision = 10, scale = 2)
	private BigDecimal cgstPercentage;
	@Column(name = "sgstpercentage", precision = 10, scale = 2)
	private BigDecimal sgstPercentage;
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

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
