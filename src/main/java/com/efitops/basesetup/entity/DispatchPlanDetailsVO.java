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
@Table(name = "dispatchplandetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispatchPlanDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dispatchplandetailsgen")
	@SequenceGenerator(name = "dispatchplandetailsgen", sequenceName = "dispatchplandetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dispatchplandetailsid")
	private Long id;
	
	@Column(name="item")
	private String item;
	@Column(name="itemdesc")
	private String itemDesc;
	@Column(name="unit")
	private String unit;
	@Column(name="orderqty")
	private BigDecimal orderQty;
	@Column(name="deliveryqty")
	private BigDecimal deliveryQty;
	@Column(name="remarks")
	private String remarks;
	

	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="dispatchplanid")
    private DispatchPlanVO dispatchPlanVO; 
}
