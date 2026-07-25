package com.efitops.basesetup.entity;

import java.math.BigDecimal;

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
@Table(name = "supitems")
public class PartySubItemVO {

	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supitemsgen")
	    @SequenceGenerator(name = "supitemsgen", sequenceName = "supitemsseq", initialValue = 1000000001, allocationSize = 1)
	    @Column(name = "partysupitems_id")
	    private Long id;
	 
	    @Column(name = "itemmid", length = 50)
	    private String itemmid;
	 
	    @Column(name = "itemdescs", length = 180)
	    private String itemDescs;
	 
	    @Column(name = "sunit", length = 10)
	    private String sUnit;
	 
	    @Column(name = "qty", precision = 10, scale = 2)
	    private BigDecimal qty;
}
