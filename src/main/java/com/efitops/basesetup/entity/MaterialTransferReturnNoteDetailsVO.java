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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_transfer_return_note_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTransferReturnNoteDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_transfer_return_note_detailsgen")
	@SequenceGenerator(name = "material_transfer_return_note_detailsgen", sequenceName = "material_transfer_return_note_detailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "material_transfer_return_note_details_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;

	@ManyToOne
	@JoinColumn(name = "unit")
	private UnitMasterVO unit;

	@Column(name = "available_qty", precision = 10, scale = 2)
	private BigDecimal availableQty;

	@Column(name = "qty", precision = 10, scale = 2)
	private BigDecimal qty;

	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@Column(name = "value", precision = 10, scale = 2)
	private BigDecimal value;

	@Column(name = "reason_for_rejection_transfer")
	private String reasonForRejectionTransfer;

	@ManyToOne
	@JoinColumn(name = "supplier")
	private CustomerVO supplier;

	@ManyToOne
	@JoinColumn(name = "material_transfer_return_note_basic_id")
	@JsonBackReference
	private MaterialTransferReturnNoteVO materialTransferReturnNoteVO;
}