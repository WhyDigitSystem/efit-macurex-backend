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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recievefromsubcontractdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecieveFromSubContractDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recievefromsubcontractdetailsgen")
	@SequenceGenerator(name = "recievefromsubcontractdetailsgen", sequenceName = "recievefromsubcontractdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "recievefromsubcontractdetailsid")
	private Long id;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "issueqty",precision = 10, scale = 2)
	private BigDecimal issueQty;
	@Column(name = "recieveqty",precision = 10, scale = 2)
	private BigDecimal recieveQty;
	@Column(name = "pendingqty",precision = 10, scale = 2)
	private BigDecimal pendingQty;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne
	@JoinColumn(name = "recievefromsubcontractid")
	@JsonBackReference
	private RecieveFromSubcontractVO recieveFromSubcontractVO;

}
