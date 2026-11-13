package com.efitops.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userrolesaccess")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRolesVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userloginrolesgen")
	@SequenceGenerator(name = "userloginrolesgen",sequenceName = "userloginrolesseq",initialValue = 1000000001,allocationSize = 1)
	@Column(name="userloginrolesid")
	private long id;
	
//	@Column(name="role")
//	private String role;
//	@Column(name="roleid")
//	private Long roleId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roles")
	private RolesVO roles;
	
	@Column(name="start_date")
	private LocalDate startDate;
	@Column(name="end_date")
	private LocalDate endDate;
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name = "users_id")
    private UserVO userVO;
	
	
	
	
	
//	@Embedded
//	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
