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
@Table(name = "gate_outward_entry_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GateOutwardEntryDetailVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gate_outward_entry_detailgen")
	@SequenceGenerator(name = "gate_outward_entry_detailgen", sequenceName = "gate_outward_entry_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gate_outward_entry_detail_id")
	private Long id;
	
	    @ManyToOne
	    @JoinColumn(name = "item_code")
	    private ItemMasterVO itemCode;

	    @Column(name = "item_description")
	    private String itemDescription;
	    
	    @Column(name = "tool_machine_instrument_no")
	    private String toolMachineInstrumentNo;

	    @Column(name = "tool_machine_instrument_no_desc")
	    private String toolMachineInstrumentNoDesc;
	    
	    @ManyToOne
	    @JoinColumn(name = "unit")
	    private UnitMasterVO unit;

	    @Column(name = "quantity")
	    private BigDecimal quantity;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "gate_outward_Entry_basic")
	    @JsonBackReference
	    private GateOutwardEntryVO gateOutwardEntryVO;
	

}
