package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.AccountDTO;
import com.efitops.basesetup.dto.BranchDTO;
import com.efitops.basesetup.dto.ChargeTypeRequestDTO;
import com.efitops.basesetup.dto.ChequeBookDTO;
import com.efitops.basesetup.dto.CostCenterDTO;
import com.efitops.basesetup.dto.EmployeeDTO;
import com.efitops.basesetup.dto.GroupLedgerDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.PartyMasterDTO;
import com.efitops.basesetup.dto.SacCodeDTO;
import com.efitops.basesetup.dto.SetTaxRateDTO;
import com.efitops.basesetup.dto.StudentDTO;
import com.efitops.basesetup.dto.SubLedgerAccountDTO;
import com.efitops.basesetup.dto.TaxMasterDTO;
import com.efitops.basesetup.dto.TcsMasterDTO;
import com.efitops.basesetup.dto.TdsMasterDTO;
import com.efitops.basesetup.entity.AccountVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.ChargeTypeRequestVO;
import com.efitops.basesetup.entity.ChequeBookVO;
import com.efitops.basesetup.entity.CostCenterVO;
import com.efitops.basesetup.entity.EmployeeVO;
import com.efitops.basesetup.entity.GroupLedgerVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.SacCodeVO;
import com.efitops.basesetup.entity.SetTaxRateVO;
import com.efitops.basesetup.entity.StudentVO;
import com.efitops.basesetup.entity.SubLedgerAccountVO;
import com.efitops.basesetup.entity.TaxMasterVO;
import com.efitops.basesetup.entity.TcsMasterVO;
import com.efitops.basesetup.entity.TdsMasterVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface MasterService {

	// Branch

	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception;

	void deleteBranch(Long branchid);

	// employee

	List<EmployeeVO> getAllEmployee();

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgId);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

	void deleteEmployee(Long employeeid);

//	SetTaxRateVO
	List<SetTaxRateVO> getAllSetTaxRateByOrgId(Long orgId);

	List<SetTaxRateVO> getAllSetTaxRateById(Long id);

	SetTaxRateVO updateCreateSetTaxRate(@Valid SetTaxRateDTO setTaxRateDTO) throws Exception;

	List<SetTaxRateVO> getSetTaxRateByActive();

//	TaxMasterVO
	TaxMasterVO updateCreateTaxMaster(TaxMasterDTO taxMasterDTO) throws ApplicationException;

	List<TaxMasterVO> getAllTaxMasterByOrgId(Long orgId);

	List<TaxMasterVO> getAllTaxMasterById(Long id);

	List<TaxMasterVO> getTaxMasterByActive();

//	TcsMasterVO 
	List<TcsMasterVO> getAllTcsMasterByOrgId(Long orgId);

	List<TcsMasterVO> getAllTcsMasterById(Long id);

	TcsMasterVO updateCreateTcsMaster(@Valid TcsMasterDTO tcsMasterDTO) throws ApplicationException;

	List<TcsMasterVO> getTcsMasterByActive();

//	TdsMasterVO
	List<TdsMasterVO> getAllTdsMasterByOrgId(Long orgId);

	List<TdsMasterVO> getAllTdsMasterById(Long id);

	TdsMasterVO updateCreateTdsMaster(@Valid TdsMasterDTO tdsMasterDTO) throws ApplicationException;

	List<TdsMasterVO> getTdsMasterByActive();

//	AccountVO
	List<AccountVO> getAllAccountByOrgId(Long orgId);

	AccountVO updateCreateAccount(@Valid AccountDTO accountDTO) throws ApplicationException;

	List<AccountVO> getAllAccountById(Long id);

	List<AccountVO> getAccountByActive();

//	GroupLedgerVO
	List<GroupLedgerVO> getAllGroupLedgerById(Long id);

	List<GroupLedgerVO> getAllGroupLedgerByOrgId(Long orgId);

	List<Map<String, Object>> getGroupName(Long orgId);

	GroupLedgerVO updateCreateGroupLedger(@Valid GroupLedgerDTO groupLedgerDTO) throws ApplicationException;

	List<GroupLedgerVO> getGroupLedgerByActive();

//	SacCode
	List<SacCodeVO> getAllSacCodeById(Long id);

	List<SacCodeVO> getAllSacCodeByOrgId(Long orgId);

	List<SacCodeVO> getAllActiveSacCodeByOrgId(Long orgId);

	SacCodeVO updateCreateSacCode(@Valid SacCodeDTO sacCodeDTO) throws ApplicationException;

//	List<SacCodeVO> getSacCodeByActive();

//	SubLedgerAccount
	List<SubLedgerAccountVO> getAllSubLedgerAccountByOrgId(Long orgId);

	SubLedgerAccountVO updateCreateSubLedgerAccount(@Valid SubLedgerAccountDTO subLedgerAccountDTO)
			throws ApplicationException;

	List<SubLedgerAccountVO> getAllSubLedgerAccountById(Long id);

	List<SubLedgerAccountVO> getSubLedgerAccountByActive();

//	CostCenterVO
	List<CostCenterVO> getAllCostCenterByOrgId(Long orgId);

	CostCenterVO updateCreateCostCenter(@Valid CostCenterDTO costCenterDTO) throws ApplicationException;

	List<CostCenterVO> getAllCostCenterById(Long id);

	List<CostCenterVO> getCostCenterByActive();

//	ChequeBook
	List<ChequeBookVO> getAllChequeBookByOrgId(Long orgId);

	ChequeBookVO updateCreateChequeBook(@Valid ChequeBookDTO chequeBookDTO) throws ApplicationException;

	List<ChequeBookVO> getAllChequeBookById(Long id);

	List<ChequeBookVO> getChequeBookByActive();

//	ChargeTypeRequest
	List<ChargeTypeRequestVO> getAllChargeTypeRequestByOrgId(Long orgId);

	List<Map<String, Object>> getChargeType(Long orgId);

	ChargeTypeRequestVO updateCreateChargeTypeRequest(@Valid ChargeTypeRequestDTO chargeTypeRequestDTO)
			throws ApplicationException;

	List<ChargeTypeRequestVO> getAllChargeTypeRequestById(Long id);

	List<ChargeTypeRequestVO> getChargeTypeRequestByActive();

	List<Map<String, Object>> getSalesAccountFromGroup(Long orgId);

	List<Map<String, Object>> getPaymentAccountFromGroup(Long orgId);
//	ListOfValues

	List<ListOfValuesVO> getListOfValuesById(Long id);

	List<ListOfValuesVO> getListOfValuesByOrgId(Long orgid);

	ListOfValuesVO updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO) throws ApplicationException;

	// PartyMaster

	List<PartyMasterVO> getPartyMasterByOrgId(Long orgid);

	List<PartyMasterVO> getPartyMasterById(Long id);

	PartyMasterVO updateCreatePartyMaster(@Valid PartyMasterDTO partyMasterDTO) throws ApplicationException;

	String getPartyMasterDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getAllListValues(Long orgId, String listDescription);

	List<Map<String, Object>> getCurrencyForPartyMaster(Long orgId, String country);

	Map<String, Object> createUpdateEmployee(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFileEmployee(HttpServletRequest request) throws IOException, IOException;

	List<ImageResponseDTO> getEmployeeMasterImages(Long id) throws Exception;

}
