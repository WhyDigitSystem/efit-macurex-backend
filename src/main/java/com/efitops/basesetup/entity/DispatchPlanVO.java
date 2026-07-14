package com.efitops.basesetup.entity;

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
@Table(name = "dispatchplan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispatchPlanVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dispatchplangen")
	@SequenceGenerator(name = "dispatchplangen", sequenceName = "dispatchplanseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dispatchplanid")
	private Long id;
	@Column(name="docid")
	private String docId;
	@Column(name="docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name="routecardentry")
	private String routeCardEntry;
	@Column(name="customername")
	private String customerName;
	@Column(name="customercode")
	private String customerCode;
	@Column(name="workorderno")
	private String workOrderNo;
	@Column(name="scheduledispatchdate")
	private LocalDate scheduleDispatchDate;
	@Column(name="dispatchtype")
	private String dispatchType;
	@Column(name="narration")
	private String narration;
	
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
	private String screenCode="DP";
	@Column(name="screenname")
	private String screenName="DISPATCHPLAN";
	
    @OneToMany(mappedBy ="dispatchPlanVO",cascade =CascadeType.ALL)
    @JsonManagedReference
    private List<DispatchPlanDetailsVO> dispatchPlanDetailsVO;
	
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
