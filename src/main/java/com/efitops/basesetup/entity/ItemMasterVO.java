package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "itemmaster")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemmastergen")
	@SequenceGenerator(name = "itemmastergen", sequenceName = "itemmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "itemmaster_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	
	@ManyToOne
	@JoinColumn(name = "listofvalues_id")
	private ListOfValuesVO itemGroup;

	@Column(name = "capital")
	private String capital;

	@Column(name = "item_type")
	private String itemType;

//	@Column(name = "item_group")
//	private String itemGroup;

	@Column(name = "grade")
	private String grade;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "excise_tariff_no")
	private String exciseTariffNo;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "thickness", precision = 10, scale = 2)
	private BigDecimal thickness;

	@Column(name = "stock")
	private String stock;

	@Column(name = "width", precision = 10, scale = 2)
	private BigDecimal width;

	@Column(name = "proto_type")
	private String protoType;

	@Column(name = "lenth", precision = 10, scale = 2)
	private BigDecimal lenth;

	@Column(name = "psw")
	private String psw;

	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;

	@Column(name = "need_qc_approval")
	private String needQcApproval;

	@Column(name = "primary_unit")
	private String primaryUnit;

	@Column(name = "inspection")
	private String inspection;

	@Column(name = "abc_grade")
	private String abcGrade;

	@Column(name = "drawing_no")
	private String drawingNo;

	@Column(name = "excisble_item")
	private String excisbleItem;

	@Column(name = "lot_size", precision = 10, scale = 2)
	private BigDecimal lotSize;

	@Column(name = "shelf_life_part")
	private String shelfLifePart;

	@Column(name = "import_local")
	private String importLocal;

	@Column(name = "safety_stock")
	private String safetyStock;

	@Column(name = "grn_required")
	private String grnRequired;

	@Column(name = "row_materials")
	private String rowmaterials;

	@Column(name = "hsn_sac_code")
	private String hsnSacCode;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks", length = 150)
	private String cancelRemarks;

	@Column(name = "screen_code", length = 5)
	private String screenCode = "ITM";
	@Column(name = "screen_name", length = 30)
	private String screenName = " ITEM MASTER";

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemUnitsVO> itemUnitsVO;

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemInventoryVO> itemInventoryVO;

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemPurchaseVO> itemPurchaseVO;

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemSalesVO> itemSalesVO;

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemOthersVO> itemOthersVO;

	@OneToMany(mappedBy = "itemMasterVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemDrawingVO> itemDrawingVO;

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
