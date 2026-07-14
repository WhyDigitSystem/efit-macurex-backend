package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.IrnCreditNoteDTO;
import com.efitops.basesetup.entity.IrnCreditNoteVO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.TaxInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface IrnCreditNoteService {
	
//	IrnCredit
	List<IrnCreditNoteVO> getAllIrnCreditByOrgId(Long orgId);
	
	List<IrnCreditNoteVO> getAllIrnCreditById(Long id);

	Map<String, Object> updateCreateIrnCreditNote(IrnCreditNoteDTO irnCreditDTO) throws ApplicationException;
	
	String getIrnCreditNoteDocId(Long orgId, String finYear, String branch, String branchCode);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);
	
	List<TaxInvoiceVO>getOriginBillNofromTaxInvoiceByParty(Long orgId,String party,String branchCode);
}
