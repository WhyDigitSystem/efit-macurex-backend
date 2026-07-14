package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDesignationDetailsDTO {
	
	private String screenCode ;
	private String screenName ;
	private String entityName;
	private String createMessage ;
	private String updateMessage ;
	private List<String> updateFields ;
	private List<String> createFields ;

}
