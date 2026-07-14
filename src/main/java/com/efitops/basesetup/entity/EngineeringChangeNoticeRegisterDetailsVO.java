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
@Table(name = "engineeringchangeNoticeregisterdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineeringChangeNoticeRegisterDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "engineeringchangeNoticeregisterdetailsgen")
	@SequenceGenerator(name = "engineeringchangeNoticeregisterdetailsgen", sequenceName = "engineeringchangeNoticeregisterdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "engineeringchangeNoticeregisterdetailsid")
	private Long id;
	
	@Column(name = "intecnno")
    private String intEcNno;

    @Column(name = "customer")
    private String customer;

    @Column(name = "encrefno")
    private String encRefNo;

    @Column(name = "partname")
    private String partName;

    @Column(name = "oldrevdate")
    private LocalDate oldRevDate;
    
    @Column(name = "daterev")
    private LocalDate dateRev;
    
    @Column(name = "detailsofrevision")
    private String detailsOfRevision;
    
    @Column(name = "reasonforrevision")
    private String reasonForRevision;
    
    
    @Column(name = "oldrev")
    private String oldRev;
    
    @Column(name = "verified")
    private String verified;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "slno")
    private String slNo;
    
	@ManyToOne
	@JoinColumn(name="engineeringchangenoticeregisterid")
	@JsonBackReference
	private EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO;

}
