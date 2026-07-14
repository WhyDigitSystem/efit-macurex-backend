package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "machinemaster")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "machinemastergen")
	@SequenceGenerator(name = "machinemastergen", sequenceName = "machinemasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machinemasterid")
	private Long id;
	
	@Column(name ="docid")
    private String docId;

    @Column(name = "docdate")
    private LocalDate docDate=LocalDate.now();

    @Column(name = "department")
    private String department;

    @Column(name = "type")
    private String type;

    @Column(name = "machineno")
    private String machineNo;

    @Column(name = "machinename")
    private String machineName;

    @Column(name = "calibrationrequired")
    private boolean calibrationRequired;

    @Column(name = "location")
    private String location;

    @Column(name = "processno")
    private String processNo;

    @Column(name = "machinecategory")
    private String machineCategory;

    @Column(name = "section")
    private Long section;

    @Column(name = "model")
    private String model;

    @Column(name = "serialno")
    private String serialNo;

    @Column(name = "status")
    private String status;

    @Column(name = "manufacturedby")
    private String manufacturedBy;

    @Column(name = "madein")
    private String madeIn;

    @Column(name = "purchasedfrom")
    private String purchasedFrom;

    @Column(name = "modeofpurchase")
    private String modeOfPurchase;

    @Column(name = "machineincharge")
    private String machineIncharge;

    @Column(name = "machineusedfor")
    private String machineUsedFor;

    @Column(name = "pmchecklistno")
    private String pmCheckListNo;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "active")
    private boolean active=true;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "modifiedby")
    private String updatedBy;

    @Column(name = "cancel")
    private boolean cancel=false;
    
    @Column(name = "cancelremarks")
    private String cancelRemarks;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
	
    @Column(name = "finyear", length = 5)
    private String finYear;
    
    @Column(name = "screencode")
    private String screenCode="MM";
    
    @Column(name = "screenname")
    private String screenname="MACHINEMASTER";
    
    @Column(name="instrumentname")
	private String instrumentName;
	@Lob
	@Column(name = "attachements", columnDefinition = "LONGBLOB")
	private byte[] attachements;
	@Column(name="filepath")
	private String filePath;
    
    
    @OneToMany(mappedBy ="machineMasterVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<MachineTechnicalInfoVO> machineTechnicalInfoVO;
    
    @OneToMany(mappedBy ="machineMasterVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<MachineCapacityVO> machineCapacityVO;
    
    @OneToMany(mappedBy ="machineMasterVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<MachineMasterAttachmentVO> documents;
	
    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	
}
