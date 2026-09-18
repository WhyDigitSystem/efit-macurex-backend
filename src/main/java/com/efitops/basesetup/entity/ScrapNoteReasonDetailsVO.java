package com.efitops.basesetup.entity;


import java.math.BigDecimal;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scrap_note_reason_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteReasonDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrap_note_reason_detailsgen")
    @SequenceGenerator(name = "scrap_note_reason_detailsgen", sequenceName = "scrap_note_reason_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "scrap_note_reason_details_id")
    private Long id;


    @Column(name = "reason_code")
    private String reasonCode; 

    @Column(name = "reason_description")
    private String reasonDescription; 

    @Column(name = "rej_qty")
    private BigDecimal rejQty; 

    @ManyToOne
    @JoinColumn(name = "scrap_note_basic_id")
    @JsonBackReference
    private ScrapNoteVO scrapNoteVO;
}
