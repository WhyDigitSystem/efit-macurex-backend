package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "deliverychallan_subcontracting_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanSubcontractingDetailsVO {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "deliverychallan_subcontracting_detailsgen"
    )
    @SequenceGenerator(
        name = "deliverychallan_subcontracting_detailsgen",
        sequenceName = "deliverychallan_subcontracting_detailsseq",
        allocationSize = 1,
        initialValue = 1000000001
    )
    @Column(name = "deliverychallan_subcontracting_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "outgoing_item")
    private ItemMasterVO outgoingItem;

    @Column(name = "stock")
    private BigDecimal stock;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @ManyToOne
    @JoinColumn(name = "from_location")
    private LocationVO fromLocation;

    @Column(name = "available_stock")
    private BigDecimal availableStock;

    @Column(name = "issue_qty")
    private BigDecimal issueQty;

    @Column(name = "unit_rate")
    private BigDecimal unitRate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "deliverychallan_subcontracting_id")
    @JsonBackReference
    private DeliveryChallanSubcontractingVO deliveryChallanSubcontracting;
}