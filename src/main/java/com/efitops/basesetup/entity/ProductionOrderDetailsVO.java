package com.efitops.basesetup.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "production_order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_order_detailsgen")
    @SequenceGenerator(name = "production_order_detailsgen", sequenceName = "production_order_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "production_order_details_id")
    private Long id;

    @Column(name = "schedule_order_no")
    private String scheduleOrderNo; 

    @Column(name = "schedule_date")
    private LocalDate scheduleDate; 

    @Column(name = "schedule_order_qty", precision = 10, scale = 2)
    private BigDecimal scheduleOrderQty;

    @Column(name = "balance_qty", precision = 10, scale = 2)
    private BigDecimal balanceQty ;

    @Column(name = "new_req_qty", precision = 10, scale = 2)
    private BigDecimal newReqQty ;

    @Column(name = "short_closed_qty", precision = 10, scale = 2)
    private BigDecimal shortClosedQty ;

    @Column(name = "reason")
    private String reason; 

    @ManyToOne
    @JoinColumn(name = "production_sch_order_short_close_basic_id")
    @JsonBackReference
    private ProductionSchOrderShortCloseVO productionSchOrderShortCloseVO;
}
