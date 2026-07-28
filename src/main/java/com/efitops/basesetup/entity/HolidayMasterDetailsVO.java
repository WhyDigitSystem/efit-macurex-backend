package com.efitops.basesetup.entity;

import java.time.LocalDate;

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
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "holidaydetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayMasterDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holidaydetailsgen")
	@SequenceGenerator(name = "holidaydetailsgen", sequenceName = "holidaydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "holidaydetails_id")
	private Long id;
	
	@Column(name = "holiday_date")
	private LocalDate holidayDate;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "holiday_type")
	private String holidayType;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "compensatory")
	private String compensatory;
	
	@Column(name = "compensatory_date")
	private LocalDate compensatoryDate;
	
	@Column(name = "screen_name")
	private String screenName="HOLIDAYMASTERDETAILS";
	
	@Column(name = "screen_code")
	private String screenCode="HMD";
	
	 @ManyToOne
	    @JoinColumn(name="holiday_id")
	    @JsonBackReference
	    private HolidayMasterVO holidayMasterVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

}
