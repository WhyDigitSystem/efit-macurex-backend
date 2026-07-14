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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemgen")
	@SequenceGenerator(name = "itemgen", sequenceName = "itemseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemid")
	private Long id;

	@Column(name = "itemname")
	private String itemName;

	@Column(name = "itemtype")
	private String itemType;

	@Column(name = "materialtype")
	private String materialType;

	@Column(name = "materialgroup")
	private String materialGroup;

	@Column(name = "materialsubgroup")
	private String materialSubGroup;

	@Column(name = "itemdesc")
	private String itemDesc;

	@Column(name = "primaryunit")
	private String primaryUnit;

	@Column(name = "hsncode")
	private String hsnCode;

	@Column(name = "needqcapproval")
	private String needqcapproval;

	@Column(name = "inspection")
	private String inspection;

	@Column(name = "instrumentseqcode")
	private String instrumentSeqCode;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;

	@Column(name = "screencode", length = 5)
	private String screenCode = "IT";
	@Column(name = "screenname", length = 30)
	private String screenName = " ITEM";

	@OneToMany(mappedBy = "itemVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemInventoryVO> itemInventoryVO;

	@OneToMany(mappedBy = "itemVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemPriceSlabVO> itemPriceSlabVO;

	@OneToMany(mappedBy = "itemVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemTaxSlabVO> itemTaxSlabVO;

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
