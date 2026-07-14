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
@Table(name = "purchaseshortclosedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseShortCloseDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseshortclosedetailsgen")
	@SequenceGenerator(name = "purchaseshortclosedetailsgen", sequenceName = "purchaseshortclosedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseshortclosedetailsid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "item", length = 150)
	private String item;
	@Column(name = "itemdesc")
	private String itemDesc;
	@Column(name = "uom")
	private String uom;
	@Column(name = "receivedqty", precision = 10, scale = 2)
	private BigDecimal receivedQty;
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;
	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;
	@Column(name = "shortageqty", precision = 10, scale = 2)
	private BigDecimal shortageQty;
	
	@ManyToOne
	@JoinColumn(name = "purchaseshortcloseid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private PurchaseShortCloseVO purchaseShortCloseVO;

}
