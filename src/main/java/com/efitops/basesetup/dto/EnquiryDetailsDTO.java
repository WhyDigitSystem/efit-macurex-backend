package com.efitops.basesetup.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryDetailsDTO {

    private Long id;

    private Long itemcode;

    private Integer annualquantity;

    private LocalDate dlrydate;

    private String needrdapproval;

    private LocalDate quoteduedate;

    private String remarks;

}