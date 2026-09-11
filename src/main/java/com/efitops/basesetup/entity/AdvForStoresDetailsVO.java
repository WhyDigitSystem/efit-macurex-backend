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
@Table(name = "adv_for_stores_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvForStoresDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adv_for_stores_detailsgen")
    @SequenceGenerator(
            name = "adv_for_stores_detailsgen",
            sequenceName = "adv_for_stores_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "adv_for_stores_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitMasterVO unit;

    @Column(name = "bom_qty", precision = 10, scale = 2)
    private BigDecimal bomQty;

    @Column(name = "issue_qty", precision = 10, scale = 2)
    private BigDecimal issueQty;

    @ManyToOne
    @JoinColumn(name = "adv_for_stores_id")
    @JsonBackReference
    private AdvForStoresVO advForStoresVO;
}