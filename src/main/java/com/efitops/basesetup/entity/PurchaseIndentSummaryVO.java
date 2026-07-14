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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseindentsummary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseIndentSummaryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchaseindentsummarygen")
	@SequenceGenerator(name = "purchaseindentsummarygen", sequenceName = "purchaseindentsummaryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseindentsummaryid")
	
	private Long id;
	@Column(name ="verifiedby")
	private String verifiedBy;
	@Column(name ="cancelremarks")
	private String cancelRemarks;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="purchaseindentid")
    private PurchaseIndentVO purchaseIndentVO; 
	
}