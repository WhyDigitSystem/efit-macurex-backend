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
@Table(name = "tool_category_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ToolCategoryDetailVO {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_category_detailgen")
	    @SequenceGenerator(name = "tool_category_detailgen", sequenceName = "tool_category_detailseq", initialValue = 1000000001, allocationSize = 1)
	    @Column(name = "tool_category_detail_id")
	    private Long id;
	 
	    @Column(name = "category")
	    private String category;
	    
	    @ManyToOne
	    @JoinColumn(name = "tool_category_basic_id")
	    @JsonBackReference
	    private ToolCategoryVO toolCategoryVO;
	    
	    

}
