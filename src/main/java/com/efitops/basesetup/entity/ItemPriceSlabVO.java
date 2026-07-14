package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itempriceslab")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPriceSlabVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itempriceslabgen")
	@SequenceGenerator(name = "itempriceslabgen", sequenceName = "itempriceslabseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itempriceslabid")
	private Long id;
	
	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "priceeffectivefrom")
	private LocalDate priceEffectiveFrom;
	
	@ManyToOne
	@JoinColumn(name = "itemid")
	@JsonBackReference
	private ItemVO itemVO;
	
}
