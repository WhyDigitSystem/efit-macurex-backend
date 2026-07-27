package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partymast")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyMasterVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partymastgen")
    @SequenceGenerator(name = "partymastgen", sequenceName = "partymastseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "party_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partycat")
    private ListOfValuesVO partyCat;
    
    @ManyToOne
    @JoinColumn(name = "partycat1")
    private ListOfValuesVO partyCat1;

    @ManyToOne
    @JoinColumn(name = "partycat2")
    private ListOfValuesVO partyCat2;
    
    @ManyToOne
    @JoinColumn(name = "suptype")
    private ListOfValuesVO supType;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "docdate")
    private LocalDate docDate;
 
    @Column(name = "salutation", length = 8)
    private String salutation;
 
    @Column(name = "partytype", length = 50)
    private String partyType;
 
    @Column(name = "accountname", length = 100)
    private String accountName;
 
    @Column(name = "accountcode", length = 20)
    private String accountCode;
    
    @Column(name = "partyname", length = 150)
    private String partyName;
 
    @Column(name = "legalname", length = 100)
    private String legalName;
 
    @Column(name = "tradename", length = 100)
    private String tradeName;
 
    @Column(name = "isgroupcompany", length = 20)
    private String isGroupCompany;
 
//    @Column(name = "zoneid", length = 10)
//    private String zoneId;
 
    @Column(name = "custcode", length = 20)
    private String custCode;
 
    @Column(name = "accgrpname", length = 50)
    private String accGrpName;
 
    @Column(name = "newaccountname", length = 50)
    private String newAccountName;
 
    @Column(name = "aacname", length = 100)
    private String aacName;
 
    @Column(name = "ncurr", length = 15)
    private String nCurr;
 
    @Column(name = "parentid", precision = 15, scale = 0)
    private Long parentId;
 
    @Column(name = "acat", length = 50)
    private String aCat;
 
    @Column(name = "rp", length = 1)
    private String rp;
 
    @Column(name = "dbcr", length = 10)
    private String dbcr;
 
//    @Column(name = "grpcompany", length = 50)
//    private String grpCompany;
 
    @Column(name = "type", length = 20)
    private String type;
 
    @Column(name = "accf", length = 1)
    private String acCf;
 
    @Column(name = "loccf", length = 1)
    private String loccf;
 
    @Column(name = "edit_list", length = 25)
    private String editList;
 
    @Column(name = "ppmrating", precision = 10, scale = 2)
    private BigDecimal ppmrating;
 
    @Column(name = "supptype", length = 25)
    private String supptype;
 
    @Column(name = "register", length = 30)
    private String register;
 
    @Column(name = "excise", length = 10)
    private String excise;
 
    @Column(name = "standard", length = 10)
    private String standard;
 
    @Column(name = "custcredlmt", precision = 10, scale = 2)
    private BigDecimal custcredlmt;
 
    @Column(name = "creditdays", precision = 10, scale = 0)
    private Long creditdays;
 
//    @Column(name = "belongs", length = 10)
//    private String belongs;
// 
//    @Column(name = "buycode", length = 30)
//    private String buyCode;
 
    @Column(name = "isoexpdate")
    private LocalDate isoExpdate;
    
    @ManyToOne
    @JoinColumn(name = "logistics")
    private TransportMasterVO logistics;
 
    @Column(name = "logiscost", length = 10)
    private String logisCost;
 
    @Column(name = "gsttype", length = 50)
    private String gstType;
 
    @Column(name = "gstnno", length = 50)
    private String gstnNo;
 
//    @Column(name = "gststate", length = 50)
//    private String gstState;
// 
//    @Column(name = "gststatecode", length = 2)
//    private String gstStateCode;
// 
//    @Column(name = "gststateid", length = 2)
//    private String gstStateId;
 
    @Column(name = "isigstappl", length = 10)
    private String isIgstAppl;
    
    //d2
    
    @Column(name = "add1", length = 125)
    private String add1;
 
    @Column(name = "add2", length = 125)
    private String add2;
 
    @Column(name = "add3", length = 125)
    private String add3;
 
    @Column(name = "add4", length = 125)
    private String add4;

    @ManyToOne
    @JoinColumn(name = "city")
    private CityVO city;
    
    @ManyToOne
    @JoinColumn(name = "state")
    private StateVO state;

    @ManyToOne
    @JoinColumn(name = "country")
    private CountryVO country;
 
    @Column(name = "pincode", length = 20)
    private String pinCode;
 
    @Column(name = "email", length = 100)
    private String email;
 
    @Column(name = "http", length = 50)
    private String http;
 
    @Column(name = "cinno", length = 50)
    private String cinNo;
 
    @Column(name = "overdueinterest", precision = 6, scale = 2)
    private BigDecimal overDueInterest;
 
    @Column(name = "introducedby", length = 40)
    private String introducedBy;
 
    @Column(name = "cstno", length = 100)
    private String cstNo;
 
    @Column(name = "exciseregno", length = 30)
    private String exciseRegNo;
 
    @Column(name = "ecctype", length = 30)
    private String eccType;
 
    @Column(name = "panno", length = 20)
    private String panNo;
 
    @Column(name = "esino", length = 30)
    private String esino;
 
    @Column(name = "tinno", length = 30)
    private String tinNo;
 
    @Column(name = "sstno", length = 50)
    private String sstNo;
 
    @Column(name = "phone", length = 40)
    private String phone;
 
    @Column(name = "contactperson", length = 200)
    private String contactPerson;
 
    @Column(name = "mobile", length = 50)
    private String mobile;
 
    @Column(name = "fax", length = 25)
    private String fax;
 
    @Column(name = "rcslno", length = 100)
    private String rcslNo;
 
    @Column(name = "effectivedate")
    private LocalDate effectiveDate;
 
    @Column(name = "division", length = 100)
    private String division;
 
    @Column(name = "range", length = 100)
    private String range;
 
    @Column(name = "remarks")
    private String remarks;
 
    @Column(name = "scraploc", length = 25)
    private String scrapLoc;
    
    //dc5
    @Column(name = "doa")
    private LocalDate doa;
 
    @Column(name = "siso", length = 10)
    private String siso;
 
    @Column(name = "excontrol", length = 50)
    private String exControl;
 
    @Column(name = "redate")
    private LocalDate reDate;
 
    @Column(name = "creditperiod", precision = 3, scale = 0)
    private Long creditPeriod;
 
    @Column(name = "suppcount", precision = 10, scale = 0)
    private Long suppCount;
 
    @Column(name = "approved", precision = 1, scale = 0)
    private Long approved;
 
    @Column(name = "appdate")
    private LocalDate appDate;
 
    @Column(name = "sos")
    private String sos;
 
    @Column(name = "baseapp")
    private String baseApp;
    
    //d7
    
    @Column(name = "scountry", length = 50)
    private String sCountry;
 
    @Column(name = "supplier", length = 50)
    private String supplier;
 
    @Column(name = "sadd1", length = 100)
    private String sAdd1;
 
    @Column(name = "sadd2", length = 100)
    private String sAdd2;
 
    @Column(name = "sadd3", length = 100)
    private String sAdd3;
 
    @Column(name = "scity", length = 50)
    private String sCity;
 
    @Column(name = "spincode", length = 10)
    private String sPincode;
 
    @Column(name = "sstate", length = 50)
    private String sState;
    
    //defaultfield
    
    @Column(name = "purchobasicid")
    private Long purchoBasicId;

    @Column(name = "sourceid")
    private Long sourceId;

    @Column(name = "mapname")
    private String mapName;

    @Column(name = "username")
    private String userName;
    
    @Column(name = "wkid")
    private String wkId;

    @Column(name = "app_level")
    private Integer appLevel;

    @Column(name = "app_desc")
    private Integer appDesc;

    @Column(name = "app_slevel")
    private Integer appSLevel;

    @Column(name = "wfroles")
    private String wfRoles;
    
    //default fields wds

    @Column(name = "org_id")
    private Long orgId;
	
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "active")
    private boolean active ;

    @Column(name = "cancel")
    private boolean cancel = false;
    
    @Column(name = "financial_year")
    private String finYear ;
    
    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "screen_code", length = 5)
    private String screenCode = "PTY";

    @Column(name = "screen_name", length = 30)
    private String screenName = "PARTY MASTER";
   

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

    @JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    public boolean isActive() {
        return active;
    }

    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }
}