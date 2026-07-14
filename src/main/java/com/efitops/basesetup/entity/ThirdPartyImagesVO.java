package com.efitops.basesetup.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thirdpartyimages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdPartyImagesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thirdpartyimagesgen")
	@SequenceGenerator(name = "thirdpartyimagesgen", sequenceName = "thirdpartyimagesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "thirdpartyimagesid")
	private Long id;

	@Column(name = "filepath")
	private String filePath;

	@Column(name = "filename")
	private String fileName;

}
