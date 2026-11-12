package com.efitops.basesetup.entity;

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
@Table(name = "transfer_order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferOrderDetailVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_order_detailgen")
	@SequenceGenerator(name = "transfer_order_detailgen", sequenceName = "transfer_order_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "transfer_order_detail_id")
	private Long id;
	
	
	    @Column(name = "order_date")
	    private LocalDate orderDate;

	    @ManyToOne
	    @JoinColumn(name = "item_code")
	    private ItemMasterVO itemCode;

	    @Column(name = "item_description")
	    private String itemDescription;

	    @Column(name = "schedule_date")
	    private LocalDate scheduleDate;
	    
	    @Column(name = "qty")
	    private Double qty;

	    @Column(name = "unit")
	    private String unit;

	    @Column(name = "pur_qty")
	    private Double purQty;

	    @Column(name = "pur_unit")
	    private String purUnit;

	    @ManyToOne
	    @JoinColumn(name = "supplier_id")
	    private CustomerVO supplierId;

	    @Column(name = "supplier_name")
	    private String supplierName;

	    @Column(name = "type")
	    private String type;

	    @Column(name = "combine_with")
	    private String combineWith;

	    @Column(name = "trans_id")
	    private String transId;

	    @Column(name = "contract_no")
	    private String contractNo;

	    @ManyToOne
	    @JoinColumn(name = "transfer_order_basic_id")
	    @JsonBackReference
	    private TransferOrderVO transferOrderVO;

}
