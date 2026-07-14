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
@Table(name = "thirdpartyattach")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdPartyAttachmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thirdpartyattachgen")
    @SequenceGenerator(name = "thirdpartyattachgen", sequenceName = "thirdpartyattachseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "thirdpartyattachid")
    private Long id;

    @Column(name = "itemid")
    private String itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thirdpartyinspectionid")
    @JsonBackReference
    private ThirdPartyInspectionVO thirdPartyInspectionVO;
}
