package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.BranchResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportBillResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

//    private DocumentTypeResponseDTO documentType;
    private String docNo;
    private LocalDate docDate;

    private TransportResponseDTO transportName;

    private String billNo;
    private LocalDate billDate;
    private BigDecimal totalAmount;

    private LocalDate billReceivedDate;
    private LocalDate accReceivedDate;
    private EmployeeDropdownResponseDTO receivedBy;
    private EmployeeDropdownResponseDTO accReceivedBy;

    private Long orgId;
    private Boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;

    private List<TransportBillPaymentDetailsResponseDTO> paymentDetails1;
//    private List<TransportBillPaymentDetails2ResponseDTO> paymentDetails2;


}