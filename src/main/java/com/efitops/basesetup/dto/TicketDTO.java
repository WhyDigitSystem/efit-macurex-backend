package com.efitops.basesetup.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

	private Long id;

	private String subject;

	private String description;

	private String createdBy;

	// private byte[] screenShot;

	private String userName;

	private Long orgId;

	private String status;

	@Email(message = "Invalid Email")
	private String email;

	private String branch;

	private String branchCode;

	private String companyName;

	private String ticketStatus;

}
