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
@Table(name = "gate_outward_Entry_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GateOutwardEntryVO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gate_outward_Entry_basicgen")
	@SequenceGenerator(name = "gate_outward_Entry_basicgen", sequenceName = "sc_bill_detailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gate_outward_Entry_basic_id")
	private Long id;
	
	    @ManyToOne
	    @JoinColumn(name = "plant_id")
	    private BranchVO plantId;
	      
	    @Column(name = "doc_id")
		private String docId;
	    
	    @Column(name = "serial_no")
	    private String serialNo;

		@Column(name = "doc_date")
		private LocalDate docDate = LocalDate.now();

	    @Column(name = "break_down")
	    private String breakDown;

	    @Column(name = "break_down_no")
	    private String breakDownNo;

	    @Column(name = "outward_time")
	    private LocalTime outwardTime;

	    @ManyToOne
	    @JoinColumn(name = "material_type")
	    private ToolCategoryVO materialType;

	    @ManyToOne
	    @JoinColumn(name = "material_taken_out_by")
	    private EmployeeMasterVO materialTakenOutBy;

	    @ManyToOne
	    @JoinColumn(name = "material_sent_to")
	    private CustomerVO materialSentTo;

	    @Column(name = "challan_no")
	    private String challanNo;

	    @Column(name = "vehicle_no")
	    private String vehicleNo;
	     
	    @Column(name = "remarks")
	    private String remarks;
	     
	    @Column(name = "active")
		private boolean active;
	    
	    @Column(name = "org_id")
		private Long orgId;
	    
	    @Column(name = "financial_year")
	    private String financialYear;

		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String updatedBy;
		@Column(name = "cancel")
		private boolean cancel = false;
		@Column(name = "cancel_remarks")
		private String cancelRemarks;
		@Column(name = "screen_name")
		private String screenName = "GATEOUTWARDENTRY";
		@Column(name = "screen_code")
		private String screenCode = "GOE";
		
		
		@JsonGetter("activeStatus")
		public String getActiveStatus() {
			return active ? "Active" : "In-Active";
		}

		@JsonGetter("cancelStatus")
		public String getCancelStatus() {
			return cancel ? "T" : "F";
		}

		@Embedded
		private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
		
		

		@OneToMany(mappedBy = "gateOutwardEntryVO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private List<GateOutwardEntryDetailVO> gateOutwardEntryDetailVO = new ArrayList<>();
		
		
		
		
		
		


}
