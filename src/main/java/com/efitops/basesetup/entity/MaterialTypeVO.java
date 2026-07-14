package com.efitops.basesetup.entity;

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
@Table(name = "materialtype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialTypeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialtypegen")
	@SequenceGenerator(name = "materialtypegen", sequenceName = "materialtypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "materialtypeid", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;
	@Column(name = "materialtype")
	private String materialType;
	@Column(name = "itemgroup")
	private String itemGroup;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifyby", length = 25)
	private String updatedBy;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel;

	@OneToMany(mappedBy = "materialTypeVO", cascade = CascadeType.ALL)
	List<MaterialTypeDetailsVO> materialTypeDetailsVO;
	@JsonManagedReference

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
