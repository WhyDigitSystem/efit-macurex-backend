	package com.efitops.basesetup.entity;
	
	import java.math.BigDecimal;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.List;
	
	import javax.persistence.CascadeType;
	import javax.persistence.Column;
	import javax.persistence.Embedded;
	import javax.persistence.Entity;
	import javax.persistence.FetchType;
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
	@Table(name = "flash_nc_report_basic")
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class FlashNCReportVO {
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flash_nc_report_basicgen")
		@SequenceGenerator(name = "flash_nc_report_basicgen", sequenceName = "flash_nc_report_basicseq", initialValue = 1000000001, allocationSize = 1)
		@Column(name = "flash_nc_report_basic_id")
		private Long id;
	
		@Column(name = "doc_id")
		private String docId;
	
		@Column(name = "doc_date")
		private LocalDate docDate = LocalDate.now();
	
		@ManyToOne
		@JoinColumn(name = "branch")
		private BranchVO branch;
	
		@ManyToOne
		@JoinColumn(name = "belongs_to")
		private ListOfValuesDetailsVO belongsTo;
	
		@ManyToOne
		@JoinColumn(name = "reference")
		private ListOfValuesDetailsVO reference;
	
		@ManyToOne
		@JoinColumn(name = "from_dept")
		private ListOfValuesDetailsVO fromDept;
	
		@ManyToOne
		@JoinColumn(name = "to_dept")
		private ListOfValuesDetailsVO toDept;
	
		@Column(name = "description")
		private String Description;
	
		@Column(name = "drawing_no")
		private String drawingNo;
	
		@Column(name = "mrin_sc_grnno")
		private String mrinSCGRNNO;
	
		@Column(name = "mrin_date")
		private LocalDate mrinDate;
	
		@Column(name = "occ_percentage")
		private BigDecimal occPercentage;
	
		@Column(name = "invoice_no")
		private String invoiceNo;
	
		@Column(name = "po_no")
		private String poNo;
	
		@ManyToOne
		@JoinColumn(name = "supplier")
		private CustomerVO supplier;
	
		@Column(name = "operation_no")
		private String operationNo;
	
		@ManyToOne
		@JoinColumn(name = "item")
		private ItemMasterVO item;
	
		@Column(name = "lot_qty")
		private BigDecimal lotQty;
	
		@Column(name = "sample_qty")
		private BigDecimal sampleQty;
	
		@Column(name = "nc_qty")
		private BigDecimal ncQty;
	
		@ManyToOne
		@JoinColumn(name = "disposal")
		private ListOfValuesDetailsVO disposal;
	
		@Column(name = "defect_seen")
		private String defectSeen;
	
		@Column(name = "problem_status")
		private String problemStatus;
	
		@Column(name = "action_on_defective_lot")
		private String actionOnDefectiveLot;
	
		@ManyToOne
		@JoinColumn(name = "inspected_by")
		private EmployeeMasterVO inspectedBy;
	
		@ManyToOne
		@JoinColumn(name = "status")
		private ListOfValuesDetailsVO status;
	
		@Column(name = "narration")
		private String narration;
	
		@Column(name = "flash_nc_image_name")
		private String flashNCImageName;
	
		@Column(name = "org_id")
		private Long orgId;
	
		@Column(name = "financial_year")
		private String financialYear;
	
		@Column(name = "active")
		private boolean active;
	
		@Column(name = "cancel")
		private boolean cancel = false;
	
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
	
		@Column(name = "created_by")
		private String createdBy;
	
		@Column(name = "modified_by")
		private String updatedBy;
	
		@Column(name = "screen_code")
		private String screenCode = "FNR";
	
		@Column(name = "screen_name")
		private String screenName = "FLASH NC REPORT";
	
		@OneToMany(mappedBy = "flashNCReportVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonManagedReference
		private List<FlashNCReportAttachmentVO> flashNCReportAttachmentVO = new ArrayList<>();
	
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
