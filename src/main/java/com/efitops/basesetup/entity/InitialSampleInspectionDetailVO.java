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
@Table(name = "initial_sample_inspection_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialSampleInspectionDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initial_sample_inspection_detailgen")
	@SequenceGenerator(name = "initial_sample_inspection_detailgen", sequenceName = "initial_sample_inspection_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initial_sample_inspection_detail_id")
	private Long id;
	
    @Column(name = "parameters_to_be_checked")
    private String parametersToBeChecked;

    @Column(name = "parameter_type")
    private String parameterType;

    @Column(name = "specification")
    private String specification;

    @Column(name = "tolerance")
    private String tolerance;

    @ManyToOne
    @JoinColumn(name = "uom")
    private UnitMasterVO uom;

    @Column(name = "sampling1")
    private String sampling1;

    @Column(name = "sampling2")
    private String sampling2;

    @Column(name = "sampling3")
    private String sampling3;

    @Column(name = "sampling4")
    private String sampling4;

    @Column(name = "sampling5")
    private String sampling5;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "initial_sample_inspection_basic_id")
    @JsonBackReference
    private InitialSampleInspectionVO initialSampleInspectionVO;
    
    
    
}