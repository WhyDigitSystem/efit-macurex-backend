package com.efitops.basesetup.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_transfer_challan_tax_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanTaxDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stock_transfer_challan_tax_detailgen")
    @SequenceGenerator(name = "stock_transfer_challan_tax_detailgen",sequenceName = "stock_transfer_challan_tax_detailseq",initialValue = 1000000001,allocationSize = 1)
    @Column(name = "stock_transfer_challan_tax_detail_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "particulars")
    private ListOfValuesDetailsVO particulars;
    
    @Column(name = "accept_qty_amount", precision = 18, scale = 2)
    private BigDecimal acceptQtyAmount;
    
    @Column(name = "revised_amount")
    private BigDecimal revisedAmoount;
    
    @ManyToOne
    @JoinColumn(name = "stock_transfer_challan_basic_id")
    @JsonBackReference
    private StockTransferChallanVO stockTransferChallanVO;
   
   
    
    

}
