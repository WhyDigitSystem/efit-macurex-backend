package com.efitops.basesetup.entity;

import java.time.LocalTime;

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
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shiftdet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shiftdetgen")
	@SequenceGenerator(name = "shiftdetgen", sequenceName = "shiftdetseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "shiftdetid")
	private Long id;

    @JsonFormat(pattern = "HH:mm")
    @Column(name = "timeinhours", length = 20)
    private String timingInHours;
    
    @ManyToOne
	@JoinColumn(name = "shiftmastid")
	@JsonBackReference
	private ShiftVO shiftVO;
}