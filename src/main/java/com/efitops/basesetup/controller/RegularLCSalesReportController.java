package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;
import com.efitops.basesetup.service.RejectionInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.RegularLCSalesReportService;

@RestController
@RequestMapping("/api/regularLCSalesReport")
public class RegularLCSalesReportController extends BaseController {

	private final RejectionInvoiceService rejectionInvoiceService;

	@Autowired
	RegularLCSalesReportService regularLCSalesReportService;

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImportController.class);

	RegularLCSalesReportController(RejectionInvoiceService rejectionInvoiceService) {
		this.rejectionInvoiceService = rejectionInvoiceService;
	}

	@GetMapping("/getRegularLCSalesReport")
	public ResponseEntity<ResponseDTO> getRegularLCSalesReport(@RequestParam Long belongsTo, @RequestParam Long branch,
			@RequestParam Long orgId, @RequestParam String fromDate, @RequestParam String toDate) {

		String methodName = "getRegularLCSalesReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getRegularLCSalesReport(belongsTo, branch, orgId, fromDate,
					toDate);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	rejectioninvoice report
	@GetMapping("/getRejectionInvoiceReport")
	public ResponseEntity<ResponseDTO> getRejectionInvoiceReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getRejectionInvoiceReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getRejectionInvoiceReport(fromDate, toDate, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRegularLCAppliancesReport")
	public ResponseEntity<ResponseDTO> getRegularLCAppliancesReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getRegularLCAppliancesReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getRegularLCAppliancesReport(fromDate, toDate, branch,
					orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerDetailsSales")
	public ResponseEntity<ResponseDTO> getCustomerDetailsSales(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getCustomerDetailsSales()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getCustomerDetailsSales(fromDate, toDate, orgId, branch);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
//	Schedule vs sales

	@GetMapping("/getScheduleVsSalesReport")
	public ResponseEntity<ResponseDTO> getScheduleVsSalesReport(@RequestParam String mon, @RequestParam Long under,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getScheduleVsSalesReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getScheduleVsSalesReport(mon, under, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	invoice/docket details
	@GetMapping("/getInvoiceDocketDetailsReport")
	public ResponseEntity<ResponseDTO> getInvoiceDocketDetailsReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getInvoiceDocketDetailsReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getInvoiceDocketDetailsReport(fromDate, toDate, branch,
					orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	sales delivery schedule year wise

	@GetMapping("/getDeliveryScheduleYearWiseReport")
	public ResponseEntity<ResponseDTO> getDeliveryScheduleYearWiseReport(@RequestParam String year,
			@RequestParam String belongs, @RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getDeliveryScheduleYearWiseReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getDeliveryScheduleYearWiseReport(year, belongs, branch,
					orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	sales contract approved report
	@GetMapping("/getSalesContractApprovalReport")
	public ResponseEntity<ResponseDTO> getSalesContractApprovalReport(@RequestParam String frdt,
			@RequestParam String endt, @RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getSalesContractApprovalReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getSalesContractApprovalReport(frdt, endt, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
//	physical stock reconcilation

	@GetMapping("/getPhysicalStockReconciliationReport")
	public ResponseEntity<ResponseDTO> getPhysicalStockReconciliationReport(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam String fromDate, @RequestParam String toDate) {

		String methodName = "getPhysicalStockReconciliationReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getPhysicalStockReconciliationReport(orgId, branch,
					fromDate, toDate);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssueReport")
	public ResponseEntity<ResponseDTO> getIssueReport(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String bel, @RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getIssueReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getIssueReport(fromDate, toDate, bel, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getInternalIndentReport")
	public ResponseEntity<ResponseDTO> getInternalIndentReport(
	        @RequestParam String fromDate,
	        @RequestParam String toDate,
	        @RequestParam String bel,
	        @RequestParam Long branch,
	        @RequestParam Long orgId
	        ) {

	    String methodName = "getInternalIndentReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {
	        responseObjectsMap = regularLCSalesReportService
	                .getInternalIndentReport(fromDate, toDate, bel,branch,orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg,
	                e);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSupplierRateContractReport")
	public ResponseEntity<ResponseDTO> getSupplierRateContractReport(
	        @RequestParam Long branch,
	        @RequestParam Long orgId,
	        @RequestParam String frdt,
	        @RequestParam String todt) {

	    String methodName = "getSupplierRateContractReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {
	        responseObjectsMap = regularLCSalesReportService
	                .getSupplierRateContractReport(branch,orgId, frdt, todt);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg,
	                e);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
}
