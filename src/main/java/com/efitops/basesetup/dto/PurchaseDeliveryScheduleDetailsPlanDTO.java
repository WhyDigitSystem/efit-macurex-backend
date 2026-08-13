package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseDeliveryScheduleDetailsPlanDTO {

	private Long id;
	private LocalDate planDate;
	private Integer weekNo;
	private BigDecimal scheduleQty;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public LocalDate getPlanDate() { return planDate; }
	public void setPlanDate(LocalDate planDate) { this.planDate = planDate; }
	public Integer getWeekNo() { return weekNo; }
	public void setWeekNo(Integer weekNo) { this.weekNo = weekNo; }
	public BigDecimal getScheduleQty() { return scheduleQty; }
	public void setScheduleQty(BigDecimal scheduleQty) { this.scheduleQty = scheduleQty; }
}
