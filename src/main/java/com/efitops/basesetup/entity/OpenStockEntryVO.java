package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "open_stock_entry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenStockEntryVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "open_stock_entrygen")
	@SequenceGenerator(name = "open_stock_entrygen", sequenceName = "open_stock_entryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "open_stock_entry_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate=LocalDate.now();

	@Column(name = "as_on_date")
    private LocalDate asOnDate;
	
	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;
	
	
	@ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item;
	
	@Column(name = "qty")
	private BigDecimal qty;

	@Column(name = "rate")
	private BigDecimal rate;
	
	@Column(name = "amount")
    private BigDecimal amount;
	
    @Column(name = "remarks")
	private String remarks;
    
    
    @Column(name = "active")
	private boolean active;

    
    @Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "OPENSTOCKENTRY";
	@Column(name = "screen_code")
	private String screenCode = "OSE";
	
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

	
	
	
	
	
	
	
	


}
