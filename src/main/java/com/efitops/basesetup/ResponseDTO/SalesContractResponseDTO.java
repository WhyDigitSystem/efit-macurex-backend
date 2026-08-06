package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractResponseDTO {

    private Long id;

    private String customerContractNo;

    private LocalDate contractDate;

    private BranchResponseDTO branch;

    private String belongsTo;

    private String contractType;

    private String withQuotation;

    private String invoiceType;

    private SalesCustomerResponseDTO customer;

    private String quotationNo;

    private LocalDate quotationDate;

    private String customerPoNo;

    private LocalDate customerPoDate;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    private String postRate;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private boolean active;
    
    
    //summary
    
  	private BigDecimal totalAmount;

   	private String amountInWords;

   	private String paymentTerms;

   	private String priceTerms;

   	private String terms;
   	
   	private String notes;
    
    
    private List<SalesContractDetailsResponseDTO> details;
    private List<SalesContractResponseTaxDetailsDTO> salesContractTaxDetailsDTO;
    private List<SalesContractAttachResponseDTO> attachments;
    
}
