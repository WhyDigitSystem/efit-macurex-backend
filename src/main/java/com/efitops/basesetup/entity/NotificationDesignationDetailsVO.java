package com.efitops.basesetup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificationdesignationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDesignationDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationdesignationdetailsgen")
	@SequenceGenerator(name = "notificationdesignationdetailsgen", sequenceName = "notificationdesignationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "notificationdesignationdetailsid")
	private Long id;
	
	@Column(name = "screencode", length = 30)
	private String screenCode ;
	@Column(name = "screenname", length = 30)
	private String screenName ;
	@Column(name = "entityname")
	private String entityName;
	@Column(name = "createmessage")
	private String createMessage ;
	@Column(name = "updatemessage")
	private String updateMessage ;
	@Column(name = "updatefields")
	private String updateFields ;
	@Column(name = "createfields")
	private String createFields ;
	
	@ManyToOne
	@JoinColumn(name = "notificationdesignationid", columnDefinition = "BIGINT DEFAULT 0")
	@JsonBackReference
	private NotificationDesignationVO notificationDesignationVO;
	

}
