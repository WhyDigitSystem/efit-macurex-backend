package com.efitops.basesetup.entity;

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
@Table(name = "tool_master_component_out_put_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterComponentOutPutDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_master_component_out_put_detailsgen")
	@SequenceGenerator(name = "tool_master_component_out_put_detailsgen", sequenceName = "tool_master_component_out_put_detailsseq", initialValue = 1000000002, allocationSize = 1)
	@Column(name = "tool_master_component_out_put_details_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@ManyToOne
	@JoinColumn(name = "tool_master_basic_id")
	@JsonBackReference
	private ToolMasterVO toolMasterVO;


}
