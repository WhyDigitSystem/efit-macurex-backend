package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "customer_contact_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerContactDetailsVO {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customercontactdetailsseq")
    @SequenceGenerator(name = "customercontactdetailsseq", sequenceName = "customercontactdetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purpose")
    private DepartmentVO purpose;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private CustomerVO customerVO;
}
