package com.efitops.basesetup.entity;

import java.time.LocalTime;
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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shiftmast")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shiftmastgen")
	@SequenceGenerator(name = "shiftmastgen", sequenceName = "shiftmastseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "shiftmastid")
	private Long id;

	@Column(name = "shiftname", length = 50)
	private String shiftName;

	@Column(name = "shifttype", length = 20)
	private String shiftType;

	@Column(name = "shiftcode", length = 20)
	private String shiftCode;

    @JsonFormat(pattern = "HH:mm")
	@Column(name = "fromhour", length = 20)
	private LocalTime fromHour;
	
    @JsonFormat(pattern = "HH:mm")
	@Column(name = "tohour", length = 20)
	private LocalTime toHour;

    @JsonFormat(pattern = "HH:mm")
	@Column(name = "timing", length = 20)
	private String timing;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;

	@OneToMany(mappedBy = "shiftVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ShiftDetailsVO> shiftDetailsVO;
	
	@OneToMany(mappedBy = "shiftVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ShiftBreakTimingDetailsVO> shiftBreakTimingDetailsVO;

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
