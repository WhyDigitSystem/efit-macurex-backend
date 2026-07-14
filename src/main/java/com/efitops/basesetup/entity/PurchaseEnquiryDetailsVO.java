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
@Table(name = "purchaseenquirydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEnquiryDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseenquirydetailsgen")
	@SequenceGenerator(name = "purchaseenquirydetailsgen", sequenceName = "purchaseenquirydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseenquirydetailid")
	private Long id;
	
	@Column(name = "item")
    private String item;

    @Column(name = "itemdesc")
    private String itemDesc;

    @Column(name = "unit")
    private String Unit;

    @Column(name = "qtyrequired")
    private BigDecimal qtyRequired;

    @Column(name = "remarks")
    private String remarks;

	@ManyToOne
	@JoinColumn(name="purchaseenquiryid")
	@JsonBackReference
	private PurchaseEnquiryVO purchaseEnquiryVO;

}
