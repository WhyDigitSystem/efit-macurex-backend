package com.efitops.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partymastaddress")
public class PartyMasterAddressVO {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partymastaddressgen")
    @SequenceGenerator(name = "partymastaddressgen", sequenceName = "partymastaddressseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "partymastaddress_id")
    private Long id;
 
    @Column(name = "addbooktype", length = 20)
    private String addBookType;
 
    @Column(name = "addbookcompany", length = 30)
    private String addBookCompany;
 
    @Column(name = "addbookadd1", length = 30)
    private String addBookAdd1;
 
    @Column(name = "addbookadd2", length = 30)
    private String addBookAdd2;
 
    @Column(name = "addbookadd3", length = 30)
    private String addBookAdd3;
 
    @Column(name = "addbookphone", length = 30)
    private String addBookPhone;
 
    @Column(name = "addbookfax", length = 30)
    private String addBookFax;
 
    @Column(name = "addbookemail", length = 100)
    private String addBookEmail;
}
