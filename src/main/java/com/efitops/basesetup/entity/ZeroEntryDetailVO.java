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
@Table(name = "zero_entry_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ZeroEntryDetailVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zero_entry_detailgen")
	@SequenceGenerator(name = "zero_entry_detailgen", sequenceName = "zero_entry_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "zero_entry_detail_id")
	private Long id;
	
	  @Column(name = "part_no")
	  private String partNo;

      @Column(name = "part_name")
	  private String partName;

      @Column(name = "failure_qty")
	  private Long failureQty;

      @Column(name = "reason")
	  private String reason;
         
      @ManyToOne
	  @JoinColumn(name = "zero_km_failure_entry_basic_id")
	  @JsonBackReference
	  private ZeroKmFailureEntryVO zeroKmFailureEntryVO;
	    

}
