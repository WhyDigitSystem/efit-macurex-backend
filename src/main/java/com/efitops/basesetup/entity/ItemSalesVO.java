package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemsales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSalesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemsalesgen")
	@SequenceGenerator(name = "itemsalesgen", sequenceName = "itemsalesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemsales_id",columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "item_blocked")
	private String itemBlocked;

	@Column(name = "minimum_selling_price", precision = 10, scale = 2)
	private BigDecimal minimumSellingPrice;

	@Column(name = "sales_account")
	private BigDecimal salesAccount;

	@Column(name = "lead_time_to_despatch", precision = 10, scale = 2)
	private BigDecimal leadTimeToDespatch;

	@Column(name = "customer_part_no")
	private String customerPartNo;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "itemmaster_id")
	ItemMasterVO itemMasterVO;

}
