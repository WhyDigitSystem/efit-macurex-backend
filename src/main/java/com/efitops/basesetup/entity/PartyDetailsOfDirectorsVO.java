package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDetailsOfDirectorsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partydetailsgen")
	@SequenceGenerator(name = "partydetailsgen", sequenceName = "partydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partydetailsid")
	private Long id;
	@Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "phone")
    private String phone; 

    @Column(name = "email")
    private String email;
	
	@ManyToOne
	@JoinColumn(name = "partymasterid")
	@JsonBackReference
	private PartyMasterVO partyMasterVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();



}