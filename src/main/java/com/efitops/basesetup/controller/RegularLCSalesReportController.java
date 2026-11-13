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
	public ResponseEntity<ResponseDTO> getInternalIndentReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String bel, @RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getInternalIndentReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getInternalIndentReport(fromDate, toDate, bel, branch,
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

	@GetMapping("/getSupplierRateContractReport")
	public ResponseEntity<ResponseDTO> getSupplierRateContractReport(@RequestParam Long branch,
			@RequestParam Long orgId, @RequestParam String frdt, @RequestParam String todt) {

		String methodName = "getSupplierRateContractReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getSupplierRateContractReport(branch, orgId, frdt, todt);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobOrderStatusReport")
	public ResponseEntity<ResponseDTO> getJobOrderStatusReport(@RequestParam Long branch, @RequestParam String fromDate,
			@RequestParam String toDate) {

		String methodName = "getJobOrderStatusReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getJobOrderStatusReport(branch, fromDate, toDate);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobOrderdetailsReport")
	public ResponseEntity<ResponseDTO> getJobOrderdetailsReport(@RequestParam Long branch, @RequestParam Long orgId,
			@RequestParam String fromdt, @RequestParam String todate) {

		String methodName = "getJobOrderdetailsReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getJobOrderdetailsReport(fromdt, todate, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStoresAdviceReport")
	public ResponseEntity<ResponseDTO> getStoresAdviceReport(@RequestParam Long branch, @RequestParam Long orgId,
			@RequestParam String fromDate, @RequestParam String toDate) {

		String methodName = "getStoresAdviceReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getStoresAdviceReport(fromDate, toDate, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractingGrnPendingListForInspection")
	public ResponseEntity<ResponseDTO> getSubContractingGrnPendingListForInspection(@RequestParam Long branch,
			@RequestParam Long orgId, @RequestParam Long locId, @RequestParam String fromDate,
			@RequestParam String toDate) {

		String methodName = "getSubContractingGrnPendingListForInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getSubContractingGrnPendingListForInspection(fromDate,
					toDate, locId, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractingGrnItemWiseReport")
	public ResponseEntity<ResponseDTO> getSubContractingGrnItemWiseReport(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam Long plant, @RequestParam Long locId, @RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getSubContractingGrnItemWiseReport()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			responseObjectsMap = regularLCSalesReportService.getSubContractingGrnItemWiseReport(fromDate, toDate, plant,
					locId, branch, orgId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBomTreeReportLive")

	public ResponseEntity<ResponseDTO> getBomTreeReportLive(
	        @RequestParam String itemCode,
	        @RequestParam String branch,
	        @RequestParam Long orgId) {

	    String methodName = "getBomTreeReportLive()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getBomTreeReportLive(itemCode, branch, orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBomTreeAfterCorrectionReport")

	public ResponseEntity<ResponseDTO> getBomTreeAfterCorrectionReport(
	        @RequestParam String itemCode,
	        @RequestParam String branch,
	        @RequestParam Long orgId) {

	    String methodName = "getBomTreeAfterCorrectionReport()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getBomTreeAfterCorrectionReport(itemCode, branch, orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBomListFgReport")
	public ResponseEntity<ResponseDTO> getBomListFgReport(
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    String methodName = "getBomListFgReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getBomListFgReport(branch, orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEngineeringChangeRecordReport")
	public ResponseEntity<ResponseDTO> getEngineeringChangeRecordReport(
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    String methodName = "getEngineeringChangeRecordReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getEngineeringChangeRecordReport(branch, orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGateInwarddetails")
	public ResponseEntity<ResponseDTO> getGateInwarddetails(
	        @RequestParam Long branch,
	        @RequestParam Long orgId,
	        @RequestParam String startDate,
	        @RequestParam String endDate) {

	    String methodName = "getGateInwarddetails()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getGateInwarddetails(branch, orgId, startDate, endDate);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGateInwardModvatReport")
	public ResponseEntity<ResponseDTO> getGateInwardModvatReport(
	        @RequestParam Long branch,
	        @RequestParam Long orgId,
	        @RequestParam String startDate,
	        @RequestParam String endDate) {

	    String methodName = "getGateInwardModvatReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getGateInwardModvatReport(branch, orgId, startDate, endDate);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getDeliveryScheduleWeekly")
	public ResponseEntity<ResponseDTO> getDeliveryScheduleWeekly(
	        @RequestParam String monthYear,
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    String methodName = "getDeliveryScheduleWeekly()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap = regularLCSalesReportService
	                .getDeliveryScheduleWeekly(monthYear, branch, orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
}
