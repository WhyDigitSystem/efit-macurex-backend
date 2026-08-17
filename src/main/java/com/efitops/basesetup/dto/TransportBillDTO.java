package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportBillDTO {

    private Long id;

    // Plant ID -> linked to Branch
    private Long branch;

    // Doc. No -> linked to Document Type Master
    private Long documentType;
    private String docNo;
    private LocalDate docDate;

    // Transport Name -> linked to Transport Master
    private Long transportName;

    private String billNo;
    private LocalDate billDate;
    private BigDecimal totalAmount;

    private LocalDate billReceivedDate;
    private LocalDate accReceivedDate;
    private Long receivedBy;
    private Long accReceivedBy;

    private Long orgId;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private String  financialYear;

    private List<TransportBillPaymentDetailsDTO> paymentDetails1;
//    private List<TransportBillPaymentDetails2DTO> paymentDetails2;



}