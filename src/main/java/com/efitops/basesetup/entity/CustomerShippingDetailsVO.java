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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_shipping_details")
public class CustomerShippingDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_shipping_detailsgen")
    @SequenceGenerator(
            name = "customer_shipping_detailsgen",
            sequenceName = "customer_shipping_detailsseq", initialValue = 1000000001,
            allocationSize = 1)
    private Long id;

    @Column(name = "shipping_address_type")
    private String shippingAddressType;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String email;
    
    @Column(name = "fax")
    private String fax;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_city")
    private CityVO shippingCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_state")
    private StateVO shippingState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_country")
    private CountryVO shippingCountry;

    @Column(name = "shipping_pincode")
    private String shippingPincode;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private CustomerVO customerVO;
}
