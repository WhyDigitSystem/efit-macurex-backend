package com.efitops.basesetup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

// Payment Details 2 grid: Cheque/RTGS No, Cheque Date, Pending Amount, Paid Amount, Total Paid Amount
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transport_bill_payment_details_2")
public class TransportBillPaymentDetails2VO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transportbillgen")
    @SequenceGenerator(
            name = "transportbillgen",
            sequenceName = "transportbillseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "transport_bill_payment_details2_id")
    private Long id;

    @Column(name = "cheque_rtgs_no")
    private String chequeRtgsNo;

    @Column(name = "cheque_date")
    private LocalDate chequeDate;

    @Column(name = "pending_amount")
    private BigDecimal pendingAmount;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "total_paid_amount")
    private BigDecimal totalPaidAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_bill_id", referencedColumnName = "transportbill_id")
    private TransportBillVO transportBillVO;


}