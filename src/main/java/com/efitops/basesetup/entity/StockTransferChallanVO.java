package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_transfer_chellan_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_transfer_chellan_basicgen")
	@SequenceGenerator(name = "stock_transfer_chellan_basicgen", sequenceName = "stock_transfer_chellan_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stock_transfer_chellan_basic_id")
    private Long id;
	
	@Column(name = "doc_id")
	private String docID;
	
	@Column(name = "doc_date")
	private LocalDate docDate;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	
	@ManyToOne
	@JoinColumn(name = "types")
	private ListOfValuesDetailsVO types;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;
	
	@Column(name = "time_of_transfer")
	private LocalTime timeOfTranfer;
	
	@Column(name = "stock_posting")
	private String stockPosting;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "no_of_packages")
	private int noOfPackages;
	
	@Column(name = "other_packages")
	private int otherPackages;
	
	@Column(name = "import_local")
	private String importLocal;
	
	
	@Column(name = "active")
	private boolean active;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updated_By;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name = "total_insurance")
	private double totalInsurance;
	@Column(name = "total_freight")
	private double totalFreight;
	@Column(name = "total_ass_val")
	private double totalAssVal;
	@Column(name = "mode_of_transport")
	private String modeOfTransport;
	@Column(name = "sales_tax")
	private String salesTax;
	@Column(name = "gross_amount")
	private double grossAmount;
	@Column(name = "amount_in_words")
	private String amountInWords;
	@Column(name = "delivery_to")
	private String deliverTo;
	@Column(name = "payment_terms")
	private String paymentTerms;
	@Column(name = "narration")
	private String narration;
	
	
	@Column(name = "screen_code",length = 10)
	private String screenCode ="STC";
	@Column(name = "screen_name",length = 30)
	private String screenName="Stock Tranfer Challan";
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@OneToMany(mappedBy = "stockTransferChallanVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<StockTransferChallanDetailsVO> details = new ArrayList<>();

//	@OneToMany(mappedBy = "stockTransferChallanVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<StockTransferChallanTaxDetailsVO> taxDetails = new ArrayList<>();
// 
}
