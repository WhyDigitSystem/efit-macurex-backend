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
@Table(name = "quotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quotationgen")
	@SequenceGenerator(name = "quotationgen", sequenceName = "quotationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "quotation_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "user_category")
	private String userCategory;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate=LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "plant_id")
	private BranchVO plantId;
	
	@ManyToOne
	@JoinColumn(name = "party_id")
	private CustomerVO partyId;

	@Column(name = "with_enquiry")
	private String withEnquiry;
	
	@Column(name = "party_name")
	private String partyName;

	@Column(name = "old_enqury_no")
	private String oldEnquryNo;

	@Column(name = "enquiry_no")
	private String enquiryNo;

	@Column(name = "enquiry_date")
	private LocalDate enquiryDate;

	@Column(name = "enquiry_control")
	private String enquiryControl;

	@Column(name = "reason")
	private String reason;

	@Column(name = "prepared_by")
	private String preparedBy;

	@Column(name = "quotation_serial_no")
	private String quotationSerialNo;

	@Column(name = "customer_enquiry_no")
	private String customerEnquiryNo;

	@Column(name = "customer_enquiry_date")
	private String customerEnquiryDate;

	@Column(name = "enq_basic_id")
	private String enqBasicId;

	@Column(name = "valid_till")
	private LocalDate validTill;

	@Column(name = "kind_attention")
	private String kindAttention;

	@ManyToOne
	@JoinColumn(name = "tax_code")
	private TaxDefinitionVO taxCode;

	@Column(name = "tax_basic_id")
	private String taxBasicId;

	// Common Fields

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "screen_code")
	private String screenCode;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "freight", precision = 10, scale = 2)
	private BigDecimal freight;

	@Column(name = "freight_by")
	private String freightBy;

	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "terms")
	private String terms;

	@Column(name = "remarks")
	private String remarks;

	@OneToMany(mappedBy = "quotationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<QuotationItemDetailsVO> quotationItemDetailsVO;
	
	
	@OneToMany(mappedBy = "quotationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<QuotationItemTaxDetailsVO> quotationItemTaxDetailsVO;
	
	@OneToMany(mappedBy = "quotationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<QuotationIemFileUploadDetailsVO> quotationIemFileUploadDetailsVO;

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
}
