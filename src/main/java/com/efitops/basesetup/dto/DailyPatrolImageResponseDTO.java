package com.efitops.basesetup.dto;


	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor

	public class DailyPatrolImageResponseDTO {
			
	private String fileName;
	private String profileImage; // Base64 image
	
	}


