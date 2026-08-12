package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_return_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnTaxDetailsVO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_return_tax_detail_seq")
    @SequenceGenerator(name = "sales_return_tax_detail_seq",sequenceName = "sales_return_tax_detail_seq",allocationSize = 1)
    @Column(name = "sales_return_tax_detail_id")
    private Long id;

    // Header Mapping
    @ManyToOne
    @JoinColumn(name = "sales_return_basic_id")
    private SalesReturnVO salesReturn;

    // Particulars (List Of Values)
    @ManyToOne
    @JoinColumn(name = "particulars")
    private ListOfValuesDetailsVO particulars;

    // Calculated Amount
    @Column(name = "amount")
    private BigDecimal amount;

}
