package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * PENDING: shape assumed from the field list you gave for Purchase Bill.
 * Wire this up once GrnRepo / GrnVO exist - see
 * PurchaseServiceImpl#getGrnDetailsForBill for the TODO stub.
 */
public class PurchaseBillGrnLookupResponseDTO {

    private String poType;              // LOCAL_PURCHASE_ORDER / PURCHASE_CONTRACT - whichever the GRN was raised against
    private String poNo;
    private LocalDate poDate;
    private LocalDate voucherPostingDate;
    private Long postingCategory;       // LOV id
    private String modvatCopyReceived;
    private String supplierDcInvNo;
    private LocalDate supplierDcInvDate;
    private List<PurchaseBillDetailsDTO> purchaseDetails; // seed rows for the "1-Purchase Detail" grid

    public String getPoType() { return poType; }
    public void setPoType(String poType) { this.poType = poType; }
    public String getPoNo() { return poNo; }
    public void setPoNo(String poNo) { this.poNo = poNo; }
    public LocalDate getPoDate() { return poDate; }
    public void setPoDate(LocalDate poDate) { this.poDate = poDate; }
    public LocalDate getVoucherPostingDate() { return voucherPostingDate; }
    public void setVoucherPostingDate(LocalDate voucherPostingDate) { this.voucherPostingDate = voucherPostingDate; }
    public Long getPostingCategory() { return postingCategory; }
    public void setPostingCategory(Long postingCategory) { this.postingCategory = postingCategory; }
    public String getModvatCopyReceived() { return modvatCopyReceived; }
    public void setModvatCopyReceived(String modvatCopyReceived) { this.modvatCopyReceived = modvatCopyReceived; }
    public String getSupplierDcInvNo() { return supplierDcInvNo; }
    public void setSupplierDcInvNo(String supplierDcInvNo) { this.supplierDcInvNo = supplierDcInvNo; }
    public LocalDate getSupplierDcInvDate() { return supplierDcInvDate; }
    public void setSupplierDcInvDate(LocalDate supplierDcInvDate) { this.supplierDcInvDate = supplierDcInvDate; }
    public List<PurchaseBillDetailsDTO> getPurchaseDetails() { return purchaseDetails; }
    public void setPurchaseDetails(List<PurchaseBillDetailsDTO> purchaseDetails) { this.purchaseDetails = purchaseDetails; }
}