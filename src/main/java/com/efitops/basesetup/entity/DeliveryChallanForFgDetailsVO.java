package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "deliverychallanforfgdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryChallanForFgDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychallanforfgdetailsgen")
	@SequenceGenerator(name = "deliverychallanforfgdetailsgen", sequenceName = "deliverychallanforfgdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychallanforfgdetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "itemno")
	private String itemNo;
	@Column(name = "itemdescription")
	private String itemDescription;
	@Column(name = "quantity", precision = 10, scale = 2)
	private BigDecimal quantity;
	@Column(name = "unit")
	private String unit;
	@Column(name = "salesorderno")
	private String salesOrderNo;
	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "deliverychalanforfgid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	DeliveryChalanForFgVO deliveryChalanForFgVO;
}
