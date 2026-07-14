package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exportpackinglist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportPackingListVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exportpackinglistgen")
	@SequenceGenerator(name = "exportpackinglistgen", sequenceName = "exportpackinglistseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "exportpackinglistid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customercode")
	private String customerCode;
	@Column(name = "customeraddress")
	private String customerAddress;
	@Column(name = "salesorderno")
	private String salesOrderNo;
	@Column(name = "deliveryplace")
	private String deliveryPlace;
	@Column(name = "countryoforgingoods")
	private String countryOfOrginGoods;
	@Column(name = "noofpackage")
	private int noOfPackage;
	@Column(name = "typeofpackage")
	private String typeOfPackage;
	@Column(name = "destinationcountry")
	private String destinationCountry;
	@Column(name = "status")
	private String status="PENDING";
	@Column(name = "lutno")
	private String lutNo;
	
	@Column(name = "totalquantity")
	private BigDecimal totalQuantity;
	@Column(name = "totalgrossweight")
	private BigDecimal totalGrossWeight;
	@Column(name = "boxtype")
	private String boxType;
	@Column(name = "boxdimention")
	private String boxDimention;
	@Column(name = "boxquantity")
	private int boxQuantity;
	@Column(name = "narration")
	private String narration;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch",length = 30)
	private String branch;
	@Column(name = "branchcode",length = 10)
	private String branchCode;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "screencode", length = 30)
	private String screenCode = "EPL";
	@Column(name = "screenname", length = 30)
	private String screenName = "EXPORTPACKINGLIST";
	
	@OneToMany(mappedBy = "exportPackingListVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ExportPackingListDetailsVO> exportPackingListDetailsVO;
	
	@OneToMany(mappedBy = "exportPackingListVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ExportPackingShippingListVO> exportPackingShippingListVO;
	
	@OneToMany(mappedBy = "exportPackingListVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ExportPackingListTermsVO> exportPackingListTermsVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
