package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.AdjustmentJournalDTO;
import com.efitops.basesetup.dto.ArApAdjustmentOffSetDTO;
import com.efitops.basesetup.dto.BankingDepositDTO;
import com.efitops.basesetup.dto.BankingWithdrawalDTO;
import com.efitops.basesetup.dto.BrsOpeningDTO;
import com.efitops.basesetup.dto.ChartCostCenterDTO;
import com.efitops.basesetup.dto.ContraVoucherDTO;
import com.efitops.basesetup.dto.DailyMonthlyExRatesDTO;
import com.efitops.basesetup.dto.DebitNoteDTO;
import com.efitops.basesetup.dto.FundTransferDTO;
import com.efitops.basesetup.dto.GeneralJournalDTO;
import com.efitops.basesetup.dto.GlOpeningBalanceDTO;
import com.efitops.basesetup.dto.GstSalesVoucherDTO;
import com.efitops.basesetup.dto.PaymentReversalDTO;
import com.efitops.basesetup.dto.PaymentVoucherDTO;
import com.efitops.basesetup.dto.ReceiptReversalDTO;
import com.efitops.basesetup.dto.ReconcileBankDTO;
import com.efitops.basesetup.dto.ReconcileCashDTO;
import com.efitops.basesetup.dto.ReconcileCorpBankDTO;
import com.efitops.basesetup.dto.TmsJobCardDTO;
import com.efitops.basesetup.entity.AdjustmentJournalVO;
import com.efitops.basesetup.entity.ArApAdjustmentOffSetVO;
import com.efitops.basesetup.entity.BankingDepositVO;
import com.efitops.basesetup.entity.BankingWithdrawalVO;
import com.efitops.basesetup.entity.BrsExcelUploadVO;
import com.efitops.basesetup.entity.BrsOpeningVO;
import com.efitops.basesetup.entity.ChartCostCenterVO;
import com.efitops.basesetup.entity.ContraVoucherVO;
import com.efitops.basesetup.entity.DailyMonthlyExRatesVO;
import com.efitops.basesetup.entity.DebitNoteVO;
import com.efitops.basesetup.entity.FundTransferVO;
import com.efitops.basesetup.entity.GeneralJournalVO;
import com.efitops.basesetup.entity.GlOpeningBalanceVO;
import com.efitops.basesetup.entity.GstSalesVoucherVO;
import com.efitops.basesetup.entity.PaymentReversalVO;
import com.efitops.basesetup.entity.PaymentVoucherVO;
import com.efitops.basesetup.entity.ReceiptReversalVO;
import com.efitops.basesetup.entity.ReconcileBankVO;
import com.efitops.basesetup.entity.ReconcileCashVO;
import com.efitops.basesetup.entity.ReconcileCorpBankVO;
import com.efitops.basesetup.entity.TmsJobCardVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface TransactionService {

//	DailyMonthlyExRatesVO
	List<DailyMonthlyExRatesVO> getAllDailyMonthlyExRatesByOrgId(Long orgId);

	DailyMonthlyExRatesVO updateCreateDailyMonthlyExRates(@Valid DailyMonthlyExRatesDTO irnCreditDTO)
			throws ApplicationException;

	List<DailyMonthlyExRatesVO> getAllDailyMonthlyExRatesById(Long id);

	List<DailyMonthlyExRatesVO> getDailyMonthlyExRatesByActive();

//	BrsOpening
	List<BrsOpeningVO> getAllBrsOpeningByOrgId(Long orgId);

	Map<String, Object> updateCreateBrsOpening(@Valid BrsOpeningDTO brsOpeningDTO) throws ApplicationException;

	List<BrsOpeningVO> getAllBrsOpeningById(Long id);

	List<BrsOpeningVO> getBrsOpeningByActive();

	List<Map<String, Object>> getBranchForBrsOpening(Long orgId);

	void ExcelUploadForBrs(MultipartFile[] files, Long orgId, String createdBy, String branch, String branchCode)
			throws ApplicationException, EncryptedDocumentException, IOException;

	List<BrsExcelUploadVO> getAllBrsExcelByOrgId(Long orgId);

	int getTotalRows();

	int getSuccessfulUploads();

//	ChartCostCenter
	List<ChartCostCenterVO> getAllChartCostCenterByOrgId(Long orgId);

	List<Map<String, Object>> updateCreateChartCostCenterList(@Valid List<ChartCostCenterDTO> chartCostCenterDTOList)
			throws ApplicationException;


	
	List<ChartCostCenterVO> getChartCostCenterById(Long id);

	List<ChartCostCenterVO> getChartCostCenterByActive();

//	FundTransfer
	List<FundTransferVO> getAllFundTransferByOrgId(Long orgId);

	Map<String, Object> updateCreateFundTransfer(@Valid FundTransferDTO fundTransferDTO) throws ApplicationException;

	List<FundTransferVO> getAllFundTransferById(Long id);

	List<FundTransferVO> getFundTransferByActive();

// GeneralJournal
	List<GeneralJournalVO> getAllGeneralJournalByOrgId(Long orgId);

	Map<String, Object> updateCreateGeneralJournal(@Valid GeneralJournalDTO generalJournalDTO)
			throws ApplicationException;

	List<GeneralJournalVO> getGeneralJournalById(Long id);

	List<GeneralJournalVO> getGeneralJournalByActive();
	
	List<Map<String, Object>> getAccountNameFromGroup( Long orgId);

// DebitNote
	List<DebitNoteVO> getAllDebitNoteByOrgId(Long orgId);

	DebitNoteVO updateCreateDebitNote(@Valid DebitNoteDTO debitNoteDTO) throws ApplicationException;

	List<DebitNoteVO> getAllDebitNoteById(Long id);

	List<DebitNoteVO> getDebitNoteByActive();

// GstSalesVoucher
	List<GstSalesVoucherVO> getAllGstSalesVoucherByOrgId(Long orgId);

	GstSalesVoucherVO updateCreateGstSalesVoucher(@Valid GstSalesVoucherDTO gstSalesVoucherDTO)
			throws ApplicationException;

	List<GstSalesVoucherVO> getAllGstSalesVoucherById(Long id);

	List<GstSalesVoucherVO> getGstSalesVoucherByActive();

// PaymentVoucher
	List<PaymentVoucherVO> getAllPaymentVoucherByOrgId(Long orgId);

	Map<String, Object> updateCreatePaymentVoucher(@Valid PaymentVoucherDTO paymentVoucherDTO)
			throws ApplicationException;

	List<PaymentVoucherVO> getPaymentVoucherById(Long id);

	List<PaymentVoucherVO> getPaymentVoucherByActive();

	PaymentVoucherVO getpaymentVoucherByDocId(Long orgId, String docId);

	String getpaymentVoucherDocId(Long orgId, String finYear, String branch, String branchCode);

//	ReceiptReversal
	List<ReceiptReversalVO> getAllReceiptReversalByOrgId(Long orgId);

	ReceiptReversalVO updateCreateReceiptReversal(@Valid ReceiptReversalDTO receiptReversalDTO)
			throws ApplicationException;

	List<ReceiptReversalVO> getAllReceiptReversalById(Long id);

	List<ReceiptReversalVO> getReceiptReversalByActive();

//	PaymentReversal
	List<PaymentReversalVO> getAllPaymentReversalByOrgId(Long orgId);

	PaymentReversalVO updateCreatePaymentReversal(@Valid PaymentReversalDTO paymentReversalDTO)
			throws ApplicationException;

	List<PaymentReversalVO> getAllPaymentReversalById(Long id);

	List<PaymentReversalVO> getPaymentReversalByActive();

//	ArApAdjustmentOffSet
	List<ArApAdjustmentOffSetVO> getAllArApAdjustmentOffSetByOrgId(Long orgId);

	ArApAdjustmentOffSetVO updateCreateArApAdjustmentOffSet(@Valid ArApAdjustmentOffSetDTO arApAdjustmentOffSetDTO)
			throws ApplicationException;

	List<ArApAdjustmentOffSetVO> getAllArApAdjustmentOffSetById(Long id);

	List<ArApAdjustmentOffSetVO> getArApAdjustmentOffSetByActive();

	// GlOpeningBalance
	List<GlOpeningBalanceVO> getAllGlOpeningBalanceByOrgId(Long orgId);

	Map<String, Object> updateCreateGlOpeningBalance(@Valid GlOpeningBalanceDTO glOpeningBalanceDTO)
			throws ApplicationException;

	List<GlOpeningBalanceVO> getAllGlOpeningBalanceById(Long id);

	List<GlOpeningBalanceVO> getGlOpeningBalanceByActive();

	String getGlOpeningBalanceDocId(Long orgId, String finYear, String branch, String branchCode);

	GlOpeningBalanceVO getGlOpeningBalanceByDocId(Long orgId, String docId);

	// ReconciliationSummary

//	List<ReconciliationSummaryVO> getAllReconciliationSummaryById(Long id);
//
//	List<ReconciliationSummaryVO> getAllReconciliationSummaryByOrgId(Long orgId);
//
//	ReconciliationSummaryVO updateCreateReconciliationSummary(@Valid ReconciliationSummaryDTO reconciliationSummaryDTO);

	// ReconcileBank
	List<ReconcileBankVO> getAllReconcileBankByOrgId(Long orgId);

	Map<String, Object> updateCreateReconcileBank(@Valid ReconcileBankDTO reconcileBankDTO) throws ApplicationException;

	List<ReconcileBankVO> getAllReconcileBankById(Long id);

	String getReconcileBankDocId(Long orgId, String finYear, String branch, String branchCode);

	String getGeneralJournalDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getBankNameForGroupLedger(Long orgId);

	// ReconcileCorpBank
	List<ReconcileCorpBankVO> getAllReconcileCorpBankByOrgId(Long orgId);

	Map<String, Object> updateCreateReconcileCorpBank(@Valid ReconcileCorpBankDTO reconcileCorpBankDTO)
			throws ApplicationException;

	List<ReconcileCorpBankVO> getAllReconcileCorpBankById(Long id);

	String getReconcileCorpBankDocId(Long orgId, String finYear, String branch, String branchCode);


	// ReconcileCash

	List<ReconcileCashVO> getAllReconcileCashByOrgId(Long orgId);

	Map<String, Object> updateCreateReconcileCash(@Valid ReconcileCashDTO reconcileCashDTO) throws ApplicationException;

	List<ReconcileCashVO> getAllReconcileCashById(Long id);

	FundTransferVO getFundTranferByDocId(Long orgId, String docId);

	String getFundTranferDocId(Long orgId, String finYear, String branch, String branchCode);

	String getReconcileCashDocId(Long orgId, String finYear, String branch, String branchCode);

	String getChartCostCenterDocId(Long orgId, String finYear, String branch, String branchCode);


	/// TMS-TT-JobCard

	List<TmsJobCardVO> getAllTmsJobCardByOrgId(Long orgId);

	Map<String, Object> updateCreateTmsJobCard(@Valid TmsJobCardDTO tmsJobCardDTO) throws ApplicationException;

	List<TmsJobCardVO> getAllTmsJobCardById(Long id);

	List<Map<String, Object>> getSalesPersonFromPartyMaster(Long orgId, String partyName);

	List<Map<String, Object>> getAllCustomersFromPartyMaster(Long orgId);

	String getTmsJobCardDocId(Long orgId, String finYear, String branch, String branchCode);

	// AdjustmentJournal

	List<AdjustmentJournalVO> getAllAdjustmentJournalByOrgId(Long orgId);

	List<AdjustmentJournalVO> getAdjustmentJournalById(Long id);

	Map<String, Object> updateCreateAdjustmentJournal(@Valid AdjustmentJournalDTO adjustmentJournalDTO)
			throws ApplicationException;

	String getAdjustmentJournalDocId(Long orgId, String finYear, String branch, String branchCode);

	//BankingDeposit
	List<BankingDepositVO> getAllBankingDepositByOrgId(Long orgId);

	List<BankingDepositVO> getBankingDepositById(Long id);

	Map<String, Object> updateCreateBankingDeposit(@Valid BankingDepositDTO bankingDepositDTO)
			throws ApplicationException;

	String getBankingDepositDocId(Long orgId, String finYear, String branch, String branchCode);
 
	List<Map<String, Object>> getBankNameFromGroupforBankingDeposit(Long orgId);

	//BankingWithdrawal
	List<BankingWithdrawalVO> getAllBankingWithdrawalByOrgId(Long orgId);

	List<BankingWithdrawalVO> getBankingWithdrawalById(Long id);

	Map<String, Object> updateCreateBankingWithdrawal(@Valid BankingWithdrawalDTO bankingWithdrawalDTO) throws ApplicationException;

	String getBankingWithdrawalDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getCurrencyAndExrates(Long orgId);

	//contraVoucher
	
	List<ContraVoucherVO> getAllContraVoucherByOrgId(Long orgId);

	List<ContraVoucherVO> getContraVoucherById(Long id);

	String getContraVoucherDocId(Long orgId, String finYear, String branch, String branchCode);

	Map<String, Object> updateCreateContraVoucher(@Valid ContraVoucherDTO contraVoucherDTO) throws ApplicationException;


	
}