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
@Table(name = "sales_contract_attach")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractAttachVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_contract_attachgen")
    @SequenceGenerator(
            name = "sales_contract_attachgen",
            sequenceName = "sales_contract_attachseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "sales_contract_attach_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salescontract_id")
    @JsonBackReference
    private SalesContractVO salesContract;

    @Column(name = "pdf_attached")
    private String pdfAttached;
}	
