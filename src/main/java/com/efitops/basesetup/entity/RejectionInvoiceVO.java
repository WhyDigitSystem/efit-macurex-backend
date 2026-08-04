//package com.efitops.basesetup.entity;
//
//
//	
//	import java.sql.Time;
//	import java.time.LocalDate;
//
//	import javax.persistence.Column;
//	import javax.persistence.Entity;
//	import javax.persistence.GeneratedValue;
//	import javax.persistence.GenerationType;
//	import javax.persistence.Id;
//	import javax.persistence.JoinColumn;
//	import javax.persistence.ManyToOne;
//	import javax.persistence.SequenceGenerator;
//	import javax.persistence.Table;
//
//	import lombok.AllArgsConstructor;
//	import lombok.Data;
//	import lombok.NoArgsConstructor;
//
//	@Entity
//	@Table(name = "rejection_invoice_basic")
//	@Data
//	@AllArgsConstructor
//	@NoArgsConstructor
//	public class RejectionInvoiceVO {
//		@Id
//		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rejectioninvoicebasicgen")
//		@SequenceGenerator(name = "rejectioninvoicebasicgen", sequenceName = "rejectioninvoicebasicgen", initialValue = 1000000001, allocationSize = 1)
//		@Column(name = "rejection_invoice_basic_id")
//		private Long id;
//		
//		@ManyToOne
//		@JoinColumn(name = "branch")
//		private BranchVO branch;
//		
//		@ManyToOne
//		@JoinColumn(name = "location_id")
//		private LocationVO locationId;
//		
//		@Column(name = "rejection_invoice_no")
//		private String rejectionInvoiceNo;
//		
//		@ManyToOne
//		@JoinColumn(name = "belongs_to")
//		private ListOfValuesVO belongsTo;
//		
//		@Column(name = "vehicle")
//		private String vehicle;
//		
//		@Column(name = "doc_type")
//		private String docType;
//		
//		@ManyToOne
//		@JoinColumn(name = "customer_id")
//		private CustomerVO customer;
//		
//		@Column(name = "time_of_issue")
//		private Time timeOfIssue;
//		
//		@Column(name = "invoice_date")
//		private LocalDate invoiceDate;
//		
//		@Column(name = "di_no")
//		private String diNo;
//		
//		@Column(name = "invoice_type")
//		private String invoiceType;
//		
//		@ManyToOne
//		@JoinColumn(name = "main_currency")
//		private CurrencyVO currency;
//		
//		@Column(name = "sch_no")
//		private String schNo;
//		
//		
//	
//		
//		
//		
//		
//		
//
//	}
//
//
//}
