package com.efitops.basesetup.entity;


import java.math.BigDecimal;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scrap_note_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteDetailsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scrap_note_detailsgen")
    @SequenceGenerator(name = "scrap_note_detailsgen", sequenceName = "scrap_note_detailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "scrap_note_details_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMasterVO item; 



    @ManyToOne
    @JoinColumn(name = "primary_unit")
    private UnitMasterVO primaryUnit; 

    @Column(name = "stock",precision = 10, scale = 2)
    private BigDecimal stock ;

    @Column(name = "quantity",precision = 10, scale = 2)
    private BigDecimal quantity; 

    @Column(name = "weight",precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "rate",precision = 10, scale = 2)
    private BigDecimal rate; 

    @Column(name = "value",precision = 10, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "scrap_note_basic_id")
    @JsonBackReference
    private ScrapNoteVO scrapNoteVO;
}
