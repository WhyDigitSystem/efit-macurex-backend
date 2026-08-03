package com.efitops.basesetup.dto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerComplaintDTO {
	private Long id;
	private Long qtyNo;
	private MultipartFile[] images;
	private String remarks;
	private Long branch;
	private Long  customerName;
	private String buyerName;
	private Long department;
	private Long item;
	private String detailsOfComplaint;
	private String preparedBy;
	private String userCategory;
	private String financialYear;
	private String prefix;
	private String complaintNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate complaintDate;
	private String complaintType;
	private String customerRefNo;
	private Long customerId;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	
	
	
	
	
	
	
	

}
