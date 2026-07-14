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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchasequotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseQuotationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchasequotationgen")
	@SequenceGenerator(name = "purchasequotationgen", sequenceName = "purchasequotationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchasequotationid")
	private Long id;
	@Column(name="docid")
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name="customername")
	private String customerName;
	@Column(name="customercode")
	private String customerCode;
	@Column(name="workorderno")
	private String workOrderNo;
	@Column(name="enquiryno")
	private String enquiryNo;
	@Column(name="enquirydate")
	private LocalDate enquiryDate;
	@Column(name="suppliername")
	private String supplierName;
	@Column(name="supplierid")
	private String supplierId;
	@Column(name="validtill")
	private LocalDate validTill;
	@Column(name="kindattention")
	private String kindAttention;
	@Column(name="taxcode")
	private String taxCode;
	@Column(name="contactperson")
	private String contactPerson;
	@Column(name="contactno")
	private Long contactNo;
	@Column(name="qstatus")
	private String qStatus;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String updatedBy; 
	@Column(name="orgid")
	private Long orgId;
	@Column(name="active")
	private boolean active=true;
	@Column(name="cancel")
	private boolean cancel=false;
	@Column(name="screencode")
	private String screenCode="PQ";
	@Column(name="screenname")
	private String screenName="PURCHASEQUOTATION";
	
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "finyear", length = 5)
	private String finYear;
	@Column(name = "count")
	private int count;
	@Column(name = "iterations")
	private String iterations;
	
	//summary
	@Column(name="grossamount")
	private BigDecimal grossAmount;
	@Column(name="netamount")
	private BigDecimal netAmount;
	@Column(name="totaldiscount")
	private BigDecimal totalDiscount;
	@Column(name="narration")
	private String narration;
	@Column(name="amountinwords")
	private String amountInWords;
	
    
    @OneToMany(mappedBy ="purchaseQuotationVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseQuotationDetailsVO> purchaseQuotationDetailsVO;
    
    @OneToMany(mappedBy ="purchaseQuotationVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVO;
    
	@OneToMany(mappedBy = "purchaseQuotationVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<PurchaseQuotationImagesVO> documents;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
