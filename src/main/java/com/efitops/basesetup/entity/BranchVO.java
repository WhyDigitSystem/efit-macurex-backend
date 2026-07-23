package com.efitops.basesetup.entity;

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
@Table(name = "branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branchgen")
	@SequenceGenerator(name = "branchgen", sequenceName = "branchseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "branch_id")
    private Long id;

    @Column(name = "branch_code", length = 20)
    private String branchCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_incharge")
    private String branchIncharge;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String email;

    @Column(name = "address", length = 1000)
    private String address;

    @Column(name = "ecc_no")
    private String eccNo;
    
    @Column(name = "division_name")
    private String division;

    @Column(name = "pincode")
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateVO state;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityVO city;

    @Column(name = "gstin_no")
    private String gstinNo;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "cin_no")
    private String cinNo;

    @Column(name = "duns_no")
    private String dunsNo;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel=false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_code", length = 5)
    private String screenCode = "BRN";

    @Column(name = "screen_name", length = 30)
    private String screenName = "BRANCH";

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
    
    
	@OneToMany(mappedBy = "branchVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<BankDetailsVO> bankDetailsVO;
    

    @JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }
}
