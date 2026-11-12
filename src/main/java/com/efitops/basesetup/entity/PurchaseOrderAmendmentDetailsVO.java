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
@Table(name = "purchaseorder_amendment_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseOrderAmendmentDetailsVO {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseorder_amendment_detailgen")
	    @SequenceGenerator(name = "purchaseorder_amendment_detailgen", sequenceName = "purchaseorder_amendment_detailseq", initialValue = 1000000001, allocationSize = 1)
	    @Column(name = "purchaseorder_amendment_detail_id")
	    private Long id;
	 
	    @ManyToOne
	    @JoinColumn(name = "item")
	    private ItemMasterVO item;
	    
	    @ManyToOne
	    @JoinColumn(name = "unit")
	    private UnitMasterVO unit;
	    
	    @Column(name = "old_qty")
		private Long oldQty;
	    
	    @Column(name = "new_qty")
		private Long newQty;
	    
	    @Column(name = "old_rate")
		private Long oldRate;
	    
	    @Column(name = "new_rate")
		private Long newRate;
	    
	    @Column(name = "olddelivery_date")
	    private LocalDate oldDeliveryDate;

	    @Column(name = "newdelivery_date")
	    private LocalDate newDeliveryDate;
	   
	    @ManyToOne
	    @JoinColumn(name = "purchaseorder_amendment_basic_id")
	    @JsonBackReference
	    private PurchaseOrderAmendmentVO purchaseOrderAmendmentVO;
	    

	    

}
