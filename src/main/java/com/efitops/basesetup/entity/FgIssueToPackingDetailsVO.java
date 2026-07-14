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
@Table(name = "fgissuetopackingdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FgIssueToPackingDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fgissuetopackingdetailsgen")
	@SequenceGenerator(name = "fgissuetopackingdetailsgen", sequenceName = "fgissuetopackingdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "fgissuetopackingdetailsid")
	private Long id;
	@Column(name ="partname")
	private String partName;
	@Column(name ="partdesc")
	private String partDesc;
	@Column(name = "totalqty", precision = 10, scale = 2)
	private BigDecimal totalQty;
	@Column(name = "issueqty", precision = 10, scale = 2)
	private BigDecimal issueQty;
	
	@ManyToOne
	@JoinColumn(name="fgissuetopackingid")
	@JsonBackReference
	private FgIssueToPackingVO fgIssueToPackingVO;
}
