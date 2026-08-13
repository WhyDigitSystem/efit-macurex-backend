package com.efitops.basesetup.dto;

import java.time.LocalDate;

/**
 * Generic "pick a source document" row used by:
 *  - Purchase Delivery Schedule -> PO No. dropdown (poType = LOCAL_PURCHASE_ORDER / PURCHASE_CONTRACT)
 *  - Purchase Short Close -> PO/Del.Sch.No dropdown (poType = DLV / PO / SUB)
 */
public class PoLookupResponseDTO {

    private Long id;
    private String poType;
    private String poNo;
    private LocalDate poDate;
    private Long supplierId;
    private String supplierName;

    public PoLookupResponseDTO() {}

    public PoLookupResponseDTO(Long id, String poType, String poNo, LocalDate poDate, Long supplierId, String supplierName) {
        this.id = id;
        this.poType = poType;
        this.poNo = poNo;
        this.poDate = poDate;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPoType() { return poType; }
    public void setPoType(String poType) { this.poType = poType; }
    public String getPoNo() { return poNo; }
    public void setPoNo(String poNo) { this.poNo = poNo; }
    public LocalDate getPoDate() { return poDate; }
    public void setPoDate(LocalDate poDate) { this.poDate = poDate; }
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
}