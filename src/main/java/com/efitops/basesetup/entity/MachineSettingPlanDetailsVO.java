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
@Table(name = "machine_setting_plan_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineSettingPlanDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_setting_plan_detailsgen")
    @SequenceGenerator(
            name = "machine_setting_plan_detailsgen",
            sequenceName = "machine_setting_plan_detailsseq",
            initialValue = 1000000001,
            allocationSize = 1)
    @Column(name = "machine_setting_plan_details_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "machine_setting_plan_id")
    @JsonBackReference
    private MachineSettingPlanVO machineSettingPlanVO;
}