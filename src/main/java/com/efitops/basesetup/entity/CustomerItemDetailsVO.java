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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_item_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerItemDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "customeritemdetailsgen")
    @SequenceGenerator(
            name = "customeritemdetailsgen",
            sequenceName = "customeritemdetailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "customer_item_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private CustomerVO customerVO;
}