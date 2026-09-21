package com.efitops.basesetup.service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.AdvForStoresDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.AdvForStoresResponseDTO;
import com.efitops.basesetup.ResponseDTO.BillOfMaterialDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.BomCorrectionRequestNoteDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.BomCorrectionRequestNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.BulkIssueIndentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.BulkIssueIndentResponseDTO;
import com.efitops.basesetup.ResponseDTO.ControlPlanResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCapitalItemsDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCapitalItemsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCumGatePassDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCumGatePassResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanSubcontractingDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanSubcontractingResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.HsnResponseDTO;
import com.efitops.basesetup.ResponseDTO.InspectionRequisitionNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.JOShortCloseCustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.JOShortCloseItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderShortCloseDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialPlanningResponseDTO;
import com.efitops.basesetup.ResponseDTO.OperationMasterResponseforPSCRDTO;
import com.efitops.basesetup.ResponseDTO.ProcessSheetCompRoutingResponseDetails;
import com.efitops.basesetup.ResponseDTO.ProcessValidationEntryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProcessValidationEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleForNextThreeMonthDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleForNextThreeMonthResponseDTO;
import com.efitops.basesetup.ResponseDTO.ReconcileConsumptionStockDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ReconcileConsumptionStockResponseDTO;
import com.efitops.basesetup.ResponseDTO.ServiceAccMasterResponse1DTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractingGRNConsumptionResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractingGRNDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractingGRNResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractingGRNTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractItemDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.AdvForStoresDTO;
import com.efitops.basesetup.dto.AdvForStoresDetailsDTO;
import com.efitops.basesetup.dto.BomCorrectionRequestNoteDTO;
import com.efitops.basesetup.dto.BomCorrectionRequestNoteDetailsDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.BulkIssueIndentDTO;
import com.efitops.basesetup.dto.BulkIssueIndentDetailsDTO;
import com.efitops.basesetup.dto.DeliveryChallanCapitalItemsDTO;
import com.efitops.basesetup.dto.DeliveryChallanCapitalItemsDetailsDTO;
import com.efitops.basesetup.dto.DeliveryChallanCumGatePassDTO;
import com.efitops.basesetup.dto.DeliveryChallanCumGatePassDetailsDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDetailsDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.InspectionRequisitionNoteDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDetailsDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.JobOrderDetailsDTO;
import com.efitops.basesetup.dto.JobOrderShortCloseDTO;
import com.efitops.basesetup.dto.JobOrderShortCloseDetailsDTO;
import com.efitops.basesetup.dto.JobOrderTaxDetailsDTO;
import com.efitops.basesetup.dto.MaterialPlanningDTO;
import com.efitops.basesetup.dto.ProcessValidationEntryDTO;
import com.efitops.basesetup.dto.ProcessValidationEntryDetailsDTO;
import com.efitops.basesetup.dto.ProductionScheduleForNextThreeMonthDTO;
import com.efitops.basesetup.dto.ProductionScheduleForNextThreeMonthDetailsDTO;
import com.efitops.basesetup.dto.ReconcileConsumptionStockDTO;
import com.efitops.basesetup.dto.ReconcileConsumptionStockDetailsDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDetailsDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleItemDetailsDTO;
import com.efitops.basesetup.dto.SubContractingGRNConsumptionDTO;
import com.efitops.basesetup.dto.SubContractingGRNDTO;
import com.efitops.basesetup.dto.SubContractingGRNDetailsDTO;
import com.efitops.basesetup.dto.SubContractingGRNTaxDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentItemDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.dto.SupplierRateContractItemDetailsDTO;
import com.efitops.basesetup.dto.SupplierRateContractTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.AdvForStoresDetailsVO;
import com.efitops.basesetup.entity.AdvForStoresVO;
import com.efitops.basesetup.entity.BillOfMaterialVO;
import com.efitops.basesetup.entity.BomCorrectionRequestNoteDetailsVO;
import com.efitops.basesetup.entity.BomCorrectionRequestNoteVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.BulkIssueIndentDetailsVO;
import com.efitops.basesetup.entity.BulkIssueIndentVO;
import com.efitops.basesetup.entity.ControlPlanVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DeliveryChallanCapitalItemsDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanCapitalItemsVO;
import com.efitops.basesetup.entity.DeliveryChallanCumGatePassDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanCumGatePassVO;
import com.efitops.basesetup.entity.DeliveryChallanSubcontractingDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanSubcontractingVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.InspectionRequisitionNoteVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.JobOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.JobOrderAmendmentVO;
import com.efitops.basesetup.entity.JobOrderAttachmentVO;
import com.efitops.basesetup.entity.JobOrderDetailsVO;
import com.efitops.basesetup.entity.JobOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.JobOrderShortCloseVO;
import com.efitops.basesetup.entity.JobOrderTaxDetailsVO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MaterialPlanningVO;
import com.efitops.basesetup.entity.ProcessSheetCompRoutingDetailVO;
import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;
import com.efitops.basesetup.entity.ProcessValidationEntryDetailsVO;
import com.efitops.basesetup.entity.ProcessValidationEntryVO;
import com.efitops.basesetup.entity.ProductionScheduleForNextThreeMonthDetailsVO;
import com.efitops.basesetup.entity.ProductionScheduleForNextThreeMonthVO;
import com.efitops.basesetup.entity.ReconcileConsumptionStockDetailsVO;
import com.efitops.basesetup.entity.ReconcileConsumptionStockVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.entity.SubContractSupplyScheduleDetailsVO;
import com.efitops.basesetup.entity.SubContractSupplyScheduleItemDetailsVO;
import com.efitops.basesetup.entity.SubContractSupplyScheduleVO;
import com.efitops.basesetup.entity.SubContractingGRNConsumptionVO;
import com.efitops.basesetup.entity.SubContractingGRNDetailsVO;
import com.efitops.basesetup.entity.SubContractingGRNTaxDetailsVO;
import com.efitops.basesetup.entity.SubContractingGRNVO;
import com.efitops.basesetup.entity.SupplierRateContractAmendmentItemDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractAmendmentVO;
import com.efitops.basesetup.entity.SupplierRateContractItemDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractTaxDetailsVO;
import com.efitops.basesetup.entity.SupplierRateContractVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.AdvForStoresDetailsRepo;
import com.efitops.basesetup.repository.AdvForStoresRepo;
import com.efitops.basesetup.repository.BillOfMaterialRepo;
import com.efitops.basesetup.repository.BomCorrectionRequestNoteDetailsRepo;
import com.efitops.basesetup.repository.BomCorrectionRequestNoteRepo;
import com.efitops.basesetup.repository.BomRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.BulkIssueIndentDetailsRepo;
import com.efitops.basesetup.repository.BulkIssueIndentRepo;
import com.efitops.basesetup.repository.ControlPlanRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DeliveryChallanCapitalItemsDetailsRepo;
import com.efitops.basesetup.repository.DeliveryChallanCapitalItemsRepo;
import com.efitops.basesetup.repository.DeliveryChallanCumGatePassDetailsRepo;
import com.efitops.basesetup.repository.DeliveryChallanCumGatePassRepo;
import com.efitops.basesetup.repository.DeliveryChallanSubcontractingDetailsRepo;
import com.efitops.basesetup.repository.DeliveryChallanSubcontractingRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.GateInwardEntryRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.InspectionRequisitionNoteRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.JobOrderAmendmentDetailsRepo;
import com.efitops.basesetup.repository.JobOrderAmendmentRepo;
import com.efitops.basesetup.repository.JobOrderAttachmentRepo;
import com.efitops.basesetup.repository.JobOrderDetailsRepo;
import com.efitops.basesetup.repository.JobOrderRepo;
import com.efitops.basesetup.repository.JobOrderShortCloseDetailsRepo;
import com.efitops.basesetup.repository.JobOrderShortCloseRepo;
import com.efitops.basesetup.repository.JobOrderTaxDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MaterialPlanningRepo;
import com.efitops.basesetup.repository.ProcessSheetCompRoutingRepo;
import com.efitops.basesetup.repository.ProcessValidationEntryDetailsRepo;
import com.efitops.basesetup.repository.ProcessValidationEntryRepo;
import com.efitops.basesetup.repository.ProductionScheduleForNextThreeMonthDetailsRepo;
import com.efitops.basesetup.repository.ProductionScheduleForNextThreeMonthRepo;
import com.efitops.basesetup.repository.ReconcileConsumptionStockDetailsRepo;
import com.efitops.basesetup.repository.ReconcileConsumptionStockRepo;
import com.efitops.basesetup.repository.ServiceAccMasterRepo;
import com.efitops.basesetup.repository.SubContractSupplyScheduleDetailsRepo;
import com.efitops.basesetup.repository.SubContractSupplyScheduleItemDetailsRepo;
import com.efitops.basesetup.repository.SubContractSupplyScheduleRepo;
import com.efitops.basesetup.repository.SubContractingGRNConsumptionRepo;
import com.efitops.basesetup.repository.SubContractingGRNDetailsRepo;
import com.efitops.basesetup.repository.SubContractingGRNRepo;
import com.efitops.basesetup.repository.SubContractingGRNTaxDetailsRepo;
import com.efitops.basesetup.repository.SupplierRateContractAmendmentItemDetailsRepo;
import com.efitops.basesetup.repository.SupplierRateContractAmendmentRepo;
import com.efitops.basesetup.repository.SupplierRateContractItemDetailsRepo;
import com.efitops.basesetup.repository.SupplierRateContractRepo;
import com.efitops.basesetup.repository.SupplierRateContractTaxDetailsRepo;
import com.efitops.basesetup.repository.TransportRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class SubContractServiceImpl implements SubContractService {

	public static final Logger LOGGER = LoggerFactory.getLogger(RejectionInvoiceServiceImpl.class);

	@Autowired
	SupplierRateContractRepo supplierRateContractRepo;

	@Autowired
	SupplierRateContractItemDetailsRepo supplierRateContractItemDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SupplierRateContractTaxDetailsRepo supplierRateContractTaxDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private GSTStateMasterRepo gstStateMasterRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private HsnRepo hsnRepo;

	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	ServiceAccMasterRepo serviceAccMasterRepo;

	@Autowired
	JobOrderRepo jobOrderRepo;

	@Autowired
	JobOrderDetailsRepo jobOrderDetailsRepo;

	@Autowired
	JobOrderTaxDetailsRepo jobOrderTaxDetailsRepo;

	@Autowired
	BomRepo bomRepo;

	@Autowired
	JobOrderAttachmentRepo jobOrderAttachmentRepo;

	@Value("${server.base-url}")
	private String serverBaseUrl;

	@Value("${joborder.upload.path}")
	private String joborderUploadPath;

	@Autowired
	JobOrderAmendmentRepo jobOrderAmendmentRepo;

	@Autowired
	JobOrderAmendmentDetailsRepo jobOrderAmendmentDetailsRepo;

	@Autowired
	DeliveryChallanSubcontractingRepo deliveryChallanSubcontractingRepo;

	@Autowired
	DeliveryChallanSubcontractingDetailsRepo deliveryChallanSubcontractingDetailsRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	TransportRepo transportRepo;

	@Autowired
	SubContractSupplyScheduleRepo subContractSupplyScheduleRepo;

	@Autowired
	SubContractSupplyScheduleItemDetailsRepo subContractSupplyScheduleItemDetailsRepo;

	@Autowired
	SubContractSupplyScheduleDetailsRepo subContractSupplyScheduleDetailsRepo;

	@Autowired
	SupplierRateContractAmendmentRepo supplierRateContractAmendmentRepo;

	@Autowired
	SupplierRateContractAmendmentItemDetailsRepo supplierRateContractAmendmentItemDetailsRepo;

	@Autowired
	ProductionScheduleForNextThreeMonthRepo productionScheduleForNextThreeMonthRepo;

	@Autowired
	ProductionScheduleForNextThreeMonthDetailsRepo productionScheduleForNextThreeMonthDetailsRepo;

	@Autowired
	DeliveryChallanCumGatePassRepo deliveryChallanCumGatePassRepo;

	@Autowired
	DeliveryChallanCumGatePassDetailsRepo deliveryChallanCumGatePassDetailsRepo;

	@Autowired
	AdvForStoresRepo advForStoresRepo;

	@Autowired
	BillOfMaterialRepo billOfMaterialRepo;

	@Autowired
	AdvForStoresDetailsRepo advForStoresDetailsRepo;

	@Autowired
	JobOrderShortCloseRepo jobOrderShortCloseRepo;

	@Autowired
	JobOrderShortCloseDetailsRepo jobOrderShortCloseDetailsRepo;

	@Autowired
	DeliveryChallanCapitalItemsRepo deliveryChallanCapitalItemsRepo;

	@Autowired
	DeliveryChallanCapitalItemsDetailsRepo deliveryChallanCapitalItemsDetailsRepo;

	@Autowired
	SubContractingGRNTaxDetailsRepo subContractingGRNTaxDetailsRepo;

	@Autowired
	SubContractingGRNDetailsRepo subContractingGRNDetailsRepo;

	@Autowired
	SubContractingGRNRepo subContractingGRNRepo;

	@Autowired
	SubContractingGRNConsumptionRepo subContractingGRNConsumptionRepo;

	@Autowired
	GateInwardEntryRepo gateInwardEntryRepo;
	
	@Autowired
	SubContractSupplyScheduleRepo subcontractSupplyScheduleRepo;

	@Autowired
	MaterialPlanningRepo materialPlanningRepo;
	
	
	@Autowired
	BomCorrectionRequestNoteRepo bomCorrectionRequestNoteRepo;
	
	@Autowired
	BomCorrectionRequestNoteDetailsRepo bomCorrectionRequestNoteDetailsRepo;
	
	
	@Autowired
	InspectionRequisitionNoteRepo inspectionRequisitionNoteRepo;
	
	@Autowired
	ProcessValidationEntryRepo processValidationEntryRepo;
	
	@Autowired
	ProcessValidationEntryDetailsRepo processValidationEntryDetailsRepo;
	
	@Autowired
	ControlPlanRepo controlPlanRepo;
	
	@Autowired
	ProcessSheetCompRoutingRepo processSheetCompRoutingRepo;
	
	
	@Autowired
	BulkIssueIndentDetailsRepo bulkIssueIndentDetailsRepo;
	
	@Autowired
	BulkIssueIndentRepo bulkIssueIndentRepo;
	
	@Autowired
	ReconcileConsumptionStockRepo reconcileConsumptionStockRepo;
	
	@Autowired
	ReconcileConsumptionStockDetailsRepo reconcileConsumptionStockDetailsRepo;
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSupplierRateContract(SupplierRateContractDTO dto)
			throws ApplicationException {

		String screenCode = "SRC";

		Map<String, Object> response = new HashMap<>();

		String message;

		SupplierRateContractVO supplierRateContractVO;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			supplierRateContractVO = new SupplierRateContractVO();

			String docId = supplierRateContractRepo.getSupplierRateContractDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);

			supplierRateContractVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			supplierRateContractVO.setCreatedBy(dto.getCreatedBy());
			supplierRateContractVO.setUpdatedBy(dto.getCreatedBy());

			message = "Supplier Rate Contract Created Successfully";

		}

		// =========================================================
		// UPDATE
		// =========================================================

		else {

			supplierRateContractVO = supplierRateContractRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Supplier Rate Contract Not Found"));

			/*
			 * Delete existing child records.
			 *
			 * We are replacing old item/tax details with the details coming from the
			 * request.
			 */

			supplierRateContractItemDetailsRepo.deleteAll(
					supplierRateContractItemDetailsRepo.findBySupplierRateContractVO(supplierRateContractVO));

			supplierRateContractTaxDetailsRepo
					.deleteAll(supplierRateContractTaxDetailsRepo.findBySupplierRateContractVO(supplierRateContractVO));

			supplierRateContractVO.getSupplierRateContractItemDetailsVO().clear();

			supplierRateContractVO.getSupplierRateContractTaxDetailsVO().clear();

			supplierRateContractVO.setUpdatedBy(dto.getCreatedBy());

			message = "Supplier Rate Contract Updated Successfully";
		}

		// =========================================================
		// HEADER + CHILD MAPPING
		// =========================================================

		getSupplierRateContractVOFromDTO(dto, supplierRateContractVO);

		// =========================================================
		// SAVE HEADER + CHILDREN
		// =========================================================

		supplierRateContractVO = supplierRateContractRepo.saveAndFlush(supplierRateContractVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		SupplierRateContractResponseDTO responseDTO = convertToResponse(supplierRateContractVO);

		response.put("message", message);

		response.put("supplierRateContract", responseDTO);

		return response;
	}

	private void getSupplierRateContractVOFromDTO(SupplierRateContractDTO dto,
			SupplierRateContractVO supplierRateContractVO) throws ApplicationException {

		// =========================================================
		// HEADER MASTER MAPPING
		// =========================================================

		BranchVO branch = branchRepo.findById(dto.getBranch())
				.orElseThrow(() -> new ApplicationException("Branch Not Found"));

		DepartmentVO department = departmentRepo.findById(dto.getDepartment())
				.orElseThrow(() -> new ApplicationException("Department Not Found"));

		CustomerVO customer = customerRepo.findById(dto.getCustomer())
				.orElseThrow(() -> new ApplicationException("Customer Not Found"));

		GSTStateMasterVO gstState = gstStateMasterRepo.findById(dto.getGstState())
				.orElseThrow(() -> new ApplicationException("GST State Not Found"));

		ServiceAccMasterVO serviceName = serviceAccMasterRepo.findById(dto.getServiceName())
				.orElseThrow(() -> new ApplicationException("Service Name Not Found"));

		System.out.println("1. HSN ID = " + dto.getHsnSacCode());

		HsnVO hsnSacCode = hsnRepo.findByHsn_Id(dto.getHsnSacCode())
				.orElseThrow(() -> new ApplicationException("HSN/SAC Code Not Found"));

		System.out.println("2. HSN FOUND = " + hsnSacCode.getId());

		supplierRateContractVO.setHsnSacCode(hsnSacCode);

		System.out.println("3. HSN SET SUCCESS");

		EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
				.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

		EmployeeMasterVO authoriedBy = employeeMasterRepo.findById(dto.getAuthoriedBy())
				.orElseThrow(() -> new ApplicationException("Authoried By Employee Not Found"));

		// =========================================================
		// SET HEADER
		// =========================================================

		supplierRateContractVO.setBranch(branch);

		supplierRateContractVO.setDepartment(department);

		supplierRateContractVO.setBelongsTo(dto.getBelongsTo());

		supplierRateContractVO.setValidFrom(dto.getValidFrom());

		supplierRateContractVO.setValidTo(dto.getValidTo());

		supplierRateContractVO.setCustomer(customer);

		supplierRateContractVO.setContractFor(dto.getContractFor());

		supplierRateContractVO.setGstState(gstState);

		supplierRateContractVO.setIgstApplicable(dto.isIgstApplicable());

		supplierRateContractVO.setDeliveryDate(dto.getDeliveryDate());

		supplierRateContractVO.setTaxType(dto.getTaxType());

		supplierRateContractVO.setServiceName(serviceName);

//		supplierRateContractVO.setHsnSacCode(hsnSacCode);

		supplierRateContractVO.setScrap(dto.isScrap());

		supplierRateContractVO.setTaxPercentage(dto.getTaxPercentage());

		supplierRateContractVO.setDiscount(dto.getDiscount());

		supplierRateContractVO.setPaymentsTerms(dto.getPaymentsTerms());

		supplierRateContractVO.setDeliveryTerms(dto.getDeliveryTerms());

		supplierRateContractVO.setFreight(dto.getFreight());

		supplierRateContractVO.setFreightType(dto.getFreightType());

		supplierRateContractVO.setPackingType(dto.getPackingType());

		supplierRateContractVO.setInsurance(dto.getInsurance());

		supplierRateContractVO.setModeOfDespatch(dto.getModeOfDespatch());

		supplierRateContractVO.setInlandCharge(dto.getInlandCharge());

		supplierRateContractVO.setPreparedBy(preparedBy);

		supplierRateContractVO.setAuthoriedBy(authoriedBy);

		supplierRateContractVO.setNarration(dto.getNarration());

		supplierRateContractVO.setFreightType(dto.getFreightType());

		supplierRateContractVO.setPackingType(dto.getPackingType());

		// =========================================================
		// COMMON FIELDS
		// =========================================================

		supplierRateContractVO.setOrgId(dto.getOrgId());

		supplierRateContractVO.setFinancialYear(dto.getFinancialYear());

		supplierRateContractVO.setCreatedBy(dto.getCreatedBy());

		supplierRateContractVO.setCancelRemarks(dto.getCancelRemarks());

		supplierRateContractVO.setActive(dto.isActive());

		// =========================================================
		// ITEM DETAILS
		// =========================================================

		List<SupplierRateContractItemDetailsVO> itemDetailList = new ArrayList<>();

		if (dto.getSupplierRateContractItemDetailsDTO() != null
				&& !dto.getSupplierRateContractItemDetailsDTO().isEmpty()) {

			for (SupplierRateContractItemDetailsDTO childDTO : dto.getSupplierRateContractItemDetailsDTO()) {

				SupplierRateContractItemDetailsVO itemDetailVO = new SupplierRateContractItemDetailsVO();

				// -------------------------------------------------
				// Incoming Item
				// -------------------------------------------------

				ItemMasterVO incomingItemCode = itemMasterRepo.findById(childDTO.getIncomingItemCode())
						.orElseThrow(() -> new ApplicationException("Incoming Item Not Found"));

				// -------------------------------------------------
				// Purchase Unit
				// -------------------------------------------------

				UnitMasterVO purchaseUnit = unitMasterRepo.findById(childDTO.getPurchaseUnit())
						.orElseThrow(() -> new ApplicationException("Purchase Unit Not Found"));

				// -------------------------------------------------
				// Child Mapping
				// -------------------------------------------------

				itemDetailVO.setIncomingItemCode(incomingItemCode);

				itemDetailVO.setPurchaseUnit(purchaseUnit);

				itemDetailVO.setPlatingType(childDTO.getPlatingType());

				itemDetailVO.setThickness(childDTO.getThickness());

				itemDetailVO.setRate(childDTO.getRate());

				BigDecimal rate = childDTO.getRate() != null ? childDTO.getRate() : BigDecimal.ZERO;

				BigDecimal igstRate = childDTO.getIgstRate() != null ? childDTO.getIgstRate() : BigDecimal.ZERO;

				BigDecimal cgstRate = childDTO.getCgstRate() != null ? childDTO.getCgstRate() : BigDecimal.ZERO;

				BigDecimal sgstRate = childDTO.getSgstRate() != null ? childDTO.getSgstRate() : BigDecimal.ZERO;

				// =====================================================
				// IGST
				// =====================================================

				if (dto.isIgstApplicable()) {

					BigDecimal igstAmount = rate.multiply(igstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					itemDetailVO.setIgstRate(igstRate);
					itemDetailVO.setIgstAmount(igstAmount);

					itemDetailVO.setCgstRate(BigDecimal.ZERO);
					itemDetailVO.setCgstAmount(BigDecimal.ZERO);

					itemDetailVO.setSgstRate(BigDecimal.ZERO);
					itemDetailVO.setSgstAmount(BigDecimal.ZERO);
				}

				// =====================================================
				// CGST + SGST
				// =====================================================

				else {

					BigDecimal cgstAmount = rate.multiply(cgstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					BigDecimal sgstAmount = rate.multiply(sgstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					itemDetailVO.setCgstRate(cgstRate);
					itemDetailVO.setCgstAmount(cgstAmount);

					itemDetailVO.setSgstRate(sgstRate);
					itemDetailVO.setSgstAmount(sgstAmount);

					itemDetailVO.setIgstRate(BigDecimal.ZERO);
					itemDetailVO.setIgstAmount(BigDecimal.ZERO);
				}
				itemDetailVO.setValidFrom(childDTO.getValidFrom());

				itemDetailVO.setValidTo(childDTO.getValidTo());

				BigDecimal toolAmortizationAmount = itemDetailVO.getIgstAmount().add(itemDetailVO.getCgstAmount())
						.add(itemDetailVO.getSgstAmount());

				itemDetailVO.setToolAmortizationRate(toolAmortizationAmount);
				// -------------------------------------------------
				// Header Mapping
				// -------------------------------------------------

				itemDetailVO.setSupplierRateContractVO(supplierRateContractVO);

				itemDetailList.add(itemDetailVO);
			}
		}

		// =========================================================
		// ADD ITEM DETAILS TO HEADER
		// =========================================================

		supplierRateContractVO.getSupplierRateContractItemDetailsVO().clear();

		for (SupplierRateContractItemDetailsVO itemDetail : itemDetailList) {

			itemDetail.setSupplierRateContractVO(supplierRateContractVO);

			supplierRateContractVO.getSupplierRateContractItemDetailsVO().add(itemDetail);
		}

		// =========================================================
		// TAX DETAILS
		// =========================================================

		List<SupplierRateContractTaxDetailsVO> taxDetailList = new ArrayList<>();

		if (dto.getSupplierRateContractTaxDetailsDTO() != null
				&& !dto.getSupplierRateContractTaxDetailsDTO().isEmpty()) {

			for (SupplierRateContractTaxDetailsDTO taxDTO : dto.getSupplierRateContractTaxDetailsDTO()) {

				SupplierRateContractTaxDetailsVO taxVO = new SupplierRateContractTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());

				taxVO.setAmount(taxDTO.getAmount());

				// -------------------------------------------------
				// Header Mapping
				// -------------------------------------------------

				taxVO.setSupplierRateContractVO(supplierRateContractVO);

				taxDetailList.add(taxVO);
			}
		}

		// =========================================================
		// ADD TAX DETAILS TO HEADER
		// =========================================================

		supplierRateContractVO.getSupplierRateContractTaxDetailsVO().clear();

		for (SupplierRateContractTaxDetailsVO taxDetail : taxDetailList) {

			taxDetail.setSupplierRateContractVO(supplierRateContractVO);

			supplierRateContractVO.getSupplierRateContractTaxDetailsVO().add(taxDetail);
		}
	}

	private SupplierRateContractResponseDTO convertToResponse(SupplierRateContractVO vo) {

		SupplierRateContractResponseDTO dto = new SupplierRateContractResponseDTO();

		// ===================== HEADER MAPPING =====================

		dto.setId(vo.getId());
		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());
		dto.setBelongsTo(vo.getBelongsTo());
		dto.setValidFrom(vo.getValidFrom());
		dto.setValidTo(vo.getValidTo());
		dto.setContractFor(vo.getContractFor());
		dto.setIgstApplicable(vo.isIgstApplicable());
		dto.setDeliveryDate(vo.getDeliveryDate());
		dto.setTaxType(vo.getTaxType());
		dto.setScrap(vo.isScrap());
		dto.setTaxPercentage(vo.getTaxPercentage());
		dto.setDiscount(vo.getDiscount());
		dto.setPaymentsTerms(vo.getPaymentsTerms());
		dto.setDeliveryTerms(vo.getDeliveryTerms());
		dto.setFreight(vo.getFreight());
		dto.setInsurance(vo.getInsurance());
		dto.setModeOfDespatch(vo.getModeOfDespatch());
		dto.setInlandCharge(vo.getInlandCharge());
		dto.setNarration(vo.getNarration());

		// ===================== COMMON FIELDS =====================

		dto.setOrgId(vo.getOrgId());
		dto.setFinancialYear(vo.getFinancialYear());
		dto.setCreatedBy(vo.getCreatedBy());
		dto.setUpdatedBy(vo.getUpdatedBy());
		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setActive(vo.getActive());

		// ===================== BRANCH MAPPING =====================

		if (vo.getBranch() != null) {

			dto.setBranch(new BranchResponseDTO(vo.getBranch().getId(), vo.getBranch().getBranchCode(),
					vo.getBranch().getBranchName()));
		}

		// ===================== DEPARTMENT MAPPING =====================

		if (vo.getDepartment() != null) {

			dto.setDepartment(new DepartmentResponseDTO(vo.getDepartment().getId(),
					vo.getDepartment().getDepartmentCode(), vo.getDepartment().getDepartmentName()));
		}

		// ===================== CUSTOMER MAPPING =====================

		if (vo.getCustomer() != null) {

			CustomerDropdownResponseDTO customerDTO = new CustomerDropdownResponseDTO();

			customerDTO.setCustomerId(vo.getCustomer().getId());

			customerDTO.setCustomerCode(vo.getCustomer().getCustomerCode());

			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());

			customerDTO.setAddress(vo.getCustomer().getAddress());

			customerDTO.setGstState(
					vo.getCustomer().getGstState() != null ? vo.getCustomer().getGstState().getStateCode() : null);

			customerDTO.setGstNo(vo.getCustomer().getGstNo());

			customerDTO.setIgstApplicable(vo.getCustomer().isGstApplicable());

			customerDTO.setGstType(vo.getCustomer().getGstType());

			dto.setCustomer(customerDTO);
		}

		// ===================== GST STATE MAPPING =====================

		if (vo.getGstState() != null) {

			GSTStateMasterResponseDTO gstStateDTO = new GSTStateMasterResponseDTO();

			gstStateDTO.setId(vo.getGstState().getId());

			gstStateDTO.setGstState(vo.getGstState().getStateName());

			gstStateDTO.setGstStateCode(vo.getGstState().getStateCode());

			dto.setGstState(gstStateDTO);
		}

		// ===================== SERVICE NAME MAPPING =====================

		if (vo.getServiceName() != null) {

			ServiceAccMasterResponse1DTO serviceDTO = new ServiceAccMasterResponse1DTO();

			serviceDTO.setId(vo.getServiceName().getId());

			serviceDTO.setServiceName(vo.getServiceName().getServiceName());

			serviceDTO.setServiceDescription(vo.getServiceName().getServiceDescription());

			dto.setServiceName(serviceDTO);
		}

		// ===================== HSN / SAC MAPPING =====================

		if (vo.getHsnSacCode() != null) {

			HsnResponseDTO hsnDTO = new HsnResponseDTO();

			hsnDTO.setId(vo.getHsnSacCode().getId());

			hsnDTO.setHsn(vo.getHsnSacCode().getHsn());

			hsnDTO.setDescription(vo.getHsnSacCode().getDescription());

			dto.setHsnSacCode(hsnDTO);
		}

		// ===================== FREIGHT TYPE MAPPING =====================

//		if (vo.getFreightType() != null) {
//
//			ListOfValuesDetailsResponseDTO freightTypeDTO = new ListOfValuesDetailsResponseDTO();
//
//			freightTypeDTO.setId(vo.getFreightType().getId());
//
//			freightTypeDTO.setCode(vo.getFreightType().getValueCode());
//
//			freightTypeDTO.setDescription(vo.getFreightType().getValueDescription());
//
//			dto.setFreightType(freightTypeDTO);
//		}

		// ===================== PACKING TYPE MAPPING =====================

//		if (vo.getPackingType() != null) {
//
//			ListOfValuesDetailsResponseDTO packingTypeDTO = new ListOfValuesDetailsResponseDTO();
//
//			packingTypeDTO.setId(vo.getPackingType().getId());
//
//			packingTypeDTO.setCode(vo.getPackingType().getValueCode());
//
//			packingTypeDTO.setDescription(vo.getPackingType().
//());
//
//			dto.setPackingType(packingTypeDTO);
//		}

		// ===================== PREPARED BY MAPPING =====================

		if (vo.getPreparedBy() != null) {

			EmployeeDropdownResponseDTO preparedByDTO = new EmployeeDropdownResponseDTO();

			preparedByDTO.setEmployeeId(vo.getPreparedBy().getId());

			preparedByDTO.setEmployeeCode(vo.getPreparedBy().getEmployeeId());

			preparedByDTO.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			preparedByDTO.setEmail(vo.getPreparedBy().getEmail());

			dto.setPreparedBy(preparedByDTO);
		}

		// ===================== AUTHORISED BY MAPPING =====================

		if (vo.getAuthoriedBy() != null) {

			EmployeeDropdownResponseDTO authoriedByDTO = new EmployeeDropdownResponseDTO();

			authoriedByDTO.setEmployeeId(vo.getAuthoriedBy().getId());

			authoriedByDTO.setEmployeeCode(vo.getAuthoriedBy().getEmployeeId());

			authoriedByDTO.setEmployeeName(vo.getAuthoriedBy().getEmployeeName());

			authoriedByDTO.setEmail(vo.getAuthoriedBy().getEmail());

			dto.setAuthoriedBy(authoriedByDTO);
		}

		// ===================== ITEM DETAILS MAPPING =====================

		List<SupplierRateContractItemDetailsResponseDTO> itemResponse = new ArrayList<>();

		if (vo.getSupplierRateContractItemDetailsVO() != null) {

			for (SupplierRateContractItemDetailsVO detail : vo.getSupplierRateContractItemDetailsVO()) {

				SupplierRateContractItemDetailsResponseDTO detailDTO = new SupplierRateContractItemDetailsResponseDTO();

				detailDTO.setId(detail.getId());

				// ===================== ITEM MAPPING =====================

				if (detail.getIncomingItemCode() != null) {

					ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

					itemDTO.setId(detail.getIncomingItemCode().getId());

					itemDTO.setItemCode(detail.getIncomingItemCode().getItemCode());

					itemDTO.setItemDescription(detail.getIncomingItemCode().getItemDescription());

					// ===================== UNIT =====================

					if (detail.getIncomingItemCode().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(detail.getIncomingItemCode().getPrimaryUnit().getId());

						unitDTO.setUnitId(detail.getIncomingItemCode().getPrimaryUnit().getUnitId());

						unitDTO.setUnitDescription(detail.getIncomingItemCode().getPrimaryUnit().getDescription());

						itemDTO.setUnit(unitDTO);
					}

					// ===================== HSN =====================

					if (detail.getIncomingItemCode().getHsnCode() != null) {

						HsnResponseDTO hsnDTO = new HsnResponseDTO();

						hsnDTO.setId(detail.getIncomingItemCode().getHsnCode().getId());

						hsnDTO.setHsn(detail.getIncomingItemCode().getHsnCode().getHsn());

						hsnDTO.setDescription(detail.getIncomingItemCode().getHsnCode().getDescription());

						itemDTO.setHsn(hsnDTO);
					}

					detailDTO.setIncomingItemCode(itemDTO);
				}

				// ===================== ITEM FIELDS =====================

				detailDTO.setPlatingType(detail.getPlatingType());

				detailDTO.setThickness(detail.getThickness());

				detailDTO.setRate(detail.getRate());

				detailDTO.setSgstRate(detail.getSgstRate());

				detailDTO.setSgstAmount(detail.getSgstAmount());

				detailDTO.setCgstRate(detail.getCgstRate());

				detailDTO.setCgstAmount(detail.getCgstAmount());

				detailDTO.setIgstRate(detail.getIgstRate());

				detailDTO.setIgstAmount(detail.getIgstAmount());

				detailDTO.setValidFrom(detail.getValidFrom());

				detailDTO.setValidTo(detail.getValidTo());

				detailDTO.setToolAmortizationRate(detail.getToolAmortizationRate());

				itemResponse.add(detailDTO);
			}
		}

		dto.setSupplierRateContractItemDetailsDTO(itemResponse);

		// ===================== TAX DETAILS MAPPING =====================

		List<SupplierRateContractTaxDetailsResponseDTO> taxResponse = new ArrayList<>();

		if (vo.getSupplierRateContractTaxDetailsVO() != null) {

			for (SupplierRateContractTaxDetailsVO tax : vo.getSupplierRateContractTaxDetailsVO()) {

				SupplierRateContractTaxDetailsResponseDTO taxDTO = new SupplierRateContractTaxDetailsResponseDTO();

				taxDTO.setId(tax.getId());

				taxDTO.setParticulars(tax.getParticulars());

				taxDTO.setAmount(tax.getAmount());

				taxResponse.add(taxDTO);
			}
		}

		dto.setSupplierRateContractTaxDetailsDTO(taxResponse);

		return dto;
	}

	@Override
	public List<Map<String, Object>> getCustomerForSupplierRateContract(Long orgId, Long branch) {

		Set<Object[]> result = customerRepo.getCustomerForSupplierRateContract(orgId, branch);

		return getCustomerForSupplierRateContractDetails(result);
	}

	private List<Map<String, Object>> getCustomerForSupplierRateContractDetails(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("customerId", fs[0] != null ? ((Number) fs[0]).longValue() : null);

			part.put("customerCode", fs[1] != null ? fs[1].toString() : null);

			part.put("customerName", fs[2] != null ? fs[2].toString() : null);

			part.put("address", fs[3] != null ? fs[3].toString() : null);

			part.put("gstState", fs[4] != null ? fs[4].toString() : null);

			part.put("gstNo", fs[5] != null ? fs[5].toString() : null);

			// is_gst_applicable is Boolean
			part.put("igstApplicable", fs[6] != null ? (Boolean) fs[6] : false);

			part.put("gstType", fs[7] != null ? fs[7].toString() : null);

			part.put("gstStateId", fs[8] != null ? ((Number) fs[8]).longValue() : null);

			details.add(part);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getServiceForSupplierRateContract(Long orgId, Long branch) {

		Set<Object[]> result = serviceAccMasterRepo.getServiceForSupplierRateContract(orgId, branch);

		return getServiceForSupplierRateContractDetails(result);
	}

	private List<Map<String, Object>> getServiceForSupplierRateContractDetails(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("serviceId", fs[0] != null ? ((Number) fs[0]).longValue() : null);

			part.put("serviceName", fs[1] != null ? fs[1].toString() : null);

			part.put("serviceDescription", fs[2] != null ? fs[2].toString() : null);

			part.put("hsn", fs[3] != null ? fs[3].toString() : null);

			part.put("igstRate", fs[4] != null ? new BigDecimal(fs[4].toString()) : BigDecimal.ZERO);

			part.put("cgstRate", fs[5] != null ? new BigDecimal(fs[5].toString()) : BigDecimal.ZERO);

			part.put("sgstRate", fs[6] != null ? new BigDecimal(fs[6].toString()) : BigDecimal.ZERO);

			part.put("rate", fs[7] != null ? new BigDecimal(fs[7].toString()) : BigDecimal.ZERO);

			part.put("hsnId", fs[8] != null ? ((Number) fs[8]).longValue() : null);

			details.add(part);
		}

		return details;
	}

	@Override
	public SupplierRateContractResponseDTO getSupplierRateContractById(Long id) throws ApplicationException {

		SupplierRateContractVO supplierRateContractVO = supplierRateContractRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Supplier Rate Contract Not Found"));

		return convertToResponse(supplierRateContractVO);
	}

	@Override
	public List<SupplierRateContractResponseDTO> getSupplierRateContractByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException {

		List<SupplierRateContractVO> supplierRateContracts = supplierRateContractRepo.findByOrgIdAndBranch(orgId,
				branch);

		List<SupplierRateContractResponseDTO> responseList = new ArrayList<>();

		for (SupplierRateContractVO vo : supplierRateContracts) {

			responseList.add(convertToResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getSupplierRateContractDocId(Long orgId, String financialYear) {

		String screenCode1 = "SRC";

		String result = supplierRateContractRepo.getSupplierRateContractDocId(orgId, financialYear, screenCode1);

		return result;
	}

	@Override
	public List<Map<String, Object>> getSupplierRateContractItemDropdown(Long orgId, Long branch) {

		List<Object[]> result = itemMasterRepo.getSupplierRateContractItemDropdown(orgId, branch);

		return getSupplierRateContractItemDropdownDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractItemDropdownDetails(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("item", fs[0] != null ? fs[0].toString() : null);
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : null);
			part.put("itemId", fs[2] != null ? ((Number) fs[2]).longValue() : null);

			part.put("unitmasterId", fs[3] != null ? ((Number) fs[3]).longValue() : null);

			part.put("unitId", fs[4] != null ? fs[4].toString() : null);

			part.put("unitDescription", fs[5] != null ? fs[5].toString() : null);

			details.add(part);
		}

		return details;
	}

	// JobOrder

	@Override
	@Transactional
	public Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO, MultipartFile[] files)
			throws ApplicationException {

		String screenCode = "JO";

		JobOrderVO jobOrderVO;
		String message;

		if (ObjectUtils.isNotEmpty(jobOrderDTO.getId())) {

			jobOrderVO = jobOrderRepo.findById(jobOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Job Order Not Found"));

			jobOrderVO.setUpdatedBy(jobOrderDTO.getCreatedBy());

			message = "Job Order Updated Successfully";

		} else {

			jobOrderVO = new JobOrderVO();

			String docId = jobOrderRepo.getJobOrderDocId(jobOrderDTO.getOrgId(), jobOrderDTO.getFinancialYear(),
					screenCode);

			jobOrderVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(jobOrderDTO.getOrgId(), jobOrderDTO.getFinancialYear(),
							screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			jobOrderVO.setCreatedBy(jobOrderDTO.getCreatedBy());
			jobOrderVO.setUpdatedBy(jobOrderDTO.getCreatedBy());

			message = "Job Order Created Successfully";
		}

		// Header + Child Mapping
		createUpdateJobOrderVOByJobOrderDTO(jobOrderDTO, jobOrderVO);

		// Save Header
		jobOrderVO = jobOrderRepo.save(jobOrderVO);

		// Save Attachments
		try {
			saveJobOrderAttachments(files, jobOrderVO);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Response
		JobOrderResponseDTO responseDTO = buildJobOrderResponse(jobOrderVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("jobOrderVO", responseDTO);

		return response;
	}

	private void createUpdateJobOrderVOByJobOrderDTO(JobOrderDTO jobOrderDTO, JobOrderVO jobOrderVO)
			throws ApplicationException {

		// Branch
		if (jobOrderDTO.getBranch() != null && jobOrderDTO.getBranch() > 0) {
			BranchVO branch = branchRepo.findById(jobOrderDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			jobOrderVO.setBranch(branch);
		}

		// Department
		if (jobOrderDTO.getDepartment() != null && jobOrderDTO.getDepartment() > 0) {
			DepartmentVO department = departmentRepo.findById(jobOrderDTO.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));
			jobOrderVO.setDepartment(department);
		}

		jobOrderVO.setBelongsTo(jobOrderDTO.getBelongsTo());

		// Vendor
		if (jobOrderDTO.getVendor() != null && jobOrderDTO.getVendor() > 0) {
			CustomerVO vendor = customerRepo.findById(jobOrderDTO.getVendor())
					.orElseThrow(() -> new ApplicationException("Vendor Not Found"));
			jobOrderVO.setVendor(vendor);
		}

		// GST State
		if (jobOrderDTO.getGstState() != null && jobOrderDTO.getGstState() > 0) {
			GSTStateMasterVO gstState = gstStateMasterRepo.findById(jobOrderDTO.getGstState())
					.orElseThrow(() -> new ApplicationException("GST State Not Found"));
			jobOrderVO.setGstState(gstState);
		}

		jobOrderVO.setJobOrderFor(jobOrderDTO.getJobOrderFor());
		jobOrderVO.setIgstAppl(jobOrderDTO.isIgstAppl());
		jobOrderVO.setContractNo(jobOrderDTO.getContractNo());

		// Service Name
		if (jobOrderDTO.getServiceName() != null && jobOrderDTO.getServiceName() > 0) {
			ServiceAccMasterVO serviceName = serviceAccMasterRepo.findById(jobOrderDTO.getServiceName())
					.orElseThrow(() -> new ApplicationException("Service Not Found"));
			jobOrderVO.setServiceName(serviceName);
		}

		jobOrderVO.setIndentTime(jobOrderDTO.getIndentTime());

		// HSN/SAC
		if (jobOrderDTO.getHsnSacCode() != null && jobOrderDTO.getHsnSacCode() > 0) {
			HsnVO hsn = hsnRepo.findById(jobOrderDTO.getHsnSacCode())
					.orElseThrow(() -> new ApplicationException("HSN/SAC Not Found"));
			jobOrderVO.setHsnSacCode(hsn);
		}

		jobOrderVO.setTaxType(jobOrderDTO.getTaxType());
		jobOrderVO.setTaxPercentage(jobOrderDTO.getTaxPercentage());

		jobOrderVO.setPaymentTerms(jobOrderDTO.getPaymentTerms());
		jobOrderVO.setDeliveryDate(jobOrderDTO.getDeliveryDate());

		jobOrderVO.setNarration(jobOrderDTO.getNarration());
		jobOrderVO.setNote(jobOrderDTO.getNote());

		jobOrderVO.setOrgId(jobOrderDTO.getOrgId());
		jobOrderVO.setFinancialYear(jobOrderDTO.getFinancialYear());
		jobOrderVO.setActive(jobOrderDTO.isActive());
		jobOrderVO.setCancelRemarks(jobOrderDTO.getCancelRemarks());

		// Clear existing children on update
		if (ObjectUtils.isNotEmpty(jobOrderVO.getId())) {

			List<JobOrderDetailsVO> existingDetails = jobOrderDetailsRepo.findByJobOrder(jobOrderVO);
			jobOrderDetailsRepo.deleteAll(existingDetails);

			List<JobOrderTaxDetailsVO> existingTaxDetails = jobOrderTaxDetailsRepo.findByJobOrder(jobOrderVO);
			jobOrderTaxDetailsRepo.deleteAll(existingTaxDetails);
		}

		BigDecimal totalAmount = BigDecimal.ZERO;

		// Job Order Details (child items)
		List<JobOrderDetailsVO> detailsList = new ArrayList<>();

		if (jobOrderDTO.getJobOrderDetails() != null) {

			for (JobOrderDetailsDTO dto : jobOrderDTO.getJobOrderDetails()) {

				JobOrderDetailsVO detailVO = new JobOrderDetailsVO();

				if (dto.getIncomingItem() != null && dto.getIncomingItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(dto.getIncomingItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailVO.setIncomingItem(item);
				}

//	            if (dto.getBom() != null && dto.getBom() != 0) {
//	                BomVO bom = bomRepo.findById(dto.getBom())
//	                        .orElseThrow(() -> new ApplicationException("BOM Not Found"));
//	                detailVO.setBom(bom);
//	            }

				if (dto.getUnit() != null && dto.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(dto.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
					detailVO.setUnit(unit);
				}

				detailVO.setIncomingType(dto.getIncomingType());

				BigDecimal amount = BigDecimal.ZERO;

				if (dto.getOrderQty() != null && dto.getRate() != null) {
					amount = dto.getOrderQty().multiply(dto.getRate());
				}

				detailVO.setBom(dto.getBom());
				detailVO.setOrderQty(dto.getOrderQty());
				detailVO.setRate(dto.getRate());
				detailVO.setAmount(amount);

				// =========================
				// TAX CALCULATION
				// =========================

				if (jobOrderVO.isIgstAppl()) {

					// IGST
					BigDecimal igstAmount = amount
							.multiply(dto.getIgstRate() != null ? dto.getIgstRate() : BigDecimal.ZERO)
							.divide(BigDecimal.valueOf(100));

					detailVO.setIgstRate(dto.getIgstRate() != null ? dto.getIgstRate() : BigDecimal.ZERO);

					detailVO.setCgstRate(BigDecimal.ZERO);
					detailVO.setSgstRate(BigDecimal.ZERO);

					detailVO.setIgstAmount(igstAmount);
					detailVO.setCgstAmount(BigDecimal.ZERO);
					detailVO.setSgstAmount(BigDecimal.ZERO);

				} else {

					// CGST
					BigDecimal cgstAmount = amount
							.multiply(dto.getCgstRate() != null ? dto.getCgstRate() : BigDecimal.ZERO)
							.divide(BigDecimal.valueOf(100));

					// SGST
					BigDecimal sgstAmount = amount
							.multiply(dto.getSgstRate() != null ? dto.getSgstRate() : BigDecimal.ZERO)
							.divide(BigDecimal.valueOf(100));

					detailVO.setCgstRate(dto.getCgstRate() != null ? dto.getCgstRate() : BigDecimal.ZERO);

					detailVO.setSgstRate(dto.getSgstRate() != null ? dto.getSgstRate() : BigDecimal.ZERO);

					detailVO.setIgstRate(BigDecimal.ZERO);

					detailVO.setCgstAmount(cgstAmount);
					detailVO.setSgstAmount(sgstAmount);
					detailVO.setIgstAmount(BigDecimal.ZERO);
				}
				detailVO.setSentFor(dto.getSentFor());

				detailVO.setJobOrder(jobOrderVO);

				if (dto.getAmount() != null) {
					totalAmount = totalAmount.add(dto.getAmount());
				}

				detailsList.add(detailVO);
			}
		}

		jobOrderVO.setJobOrderDetails(detailsList);

		// Job Order Tax Details
		List<JobOrderTaxDetailsVO> taxDetailsList = new ArrayList<>();

		if (jobOrderDTO.getJobOrderTaxDetails() != null) {

			for (JobOrderTaxDetailsDTO dto : jobOrderDTO.getJobOrderTaxDetails()) {

				JobOrderTaxDetailsVO taxVO = new JobOrderTaxDetailsVO();

				taxVO.setParticulars(dto.getParticulars());
				taxVO.setAmount(dto.getAmount());
				taxVO.setJobOrder(jobOrderVO);

				taxDetailsList.add(taxVO);
			}
		}

		jobOrderVO.setJobOrderTaxDetails(taxDetailsList);

		jobOrderVO.setAmount(totalAmount);
	}

	private void saveJobOrderAttachments(MultipartFile[] files, JobOrderVO jobOrderVO)
			throws ApplicationException, java.io.IOException {

		// Delete old attachments (DB rows + physical files) on update, same as
		// jobOrderDetails/taxDetails
		List<JobOrderAttachmentVO> existingAttachments = jobOrderAttachmentRepo.findByJobOrderVO(jobOrderVO);

		if (existingAttachments != null && !existingAttachments.isEmpty()) {

			for (JobOrderAttachmentVO oldAttachment : existingAttachments) {

				try {
					Path oldPath = Paths.get(oldAttachment.getFilePath());
					Files.deleteIfExists(oldPath);
				} catch (IOException e) {
					// log and continue — don't block the update if a stray file is already missing
					System.err.println(
							"Could not delete old file: " + oldAttachment.getFilePath() + " - " + e.getMessage());
				}
			}

			jobOrderAttachmentRepo.deleteAll(existingAttachments);
		}

		// If no new files were sent, we're done — old ones are already cleared above
		if (files == null || files.length == 0) {
			jobOrderVO.setAttachments(new ArrayList<>());
			return;
		}

		try {

			File folder = new File(joborderUploadPath);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			List<JobOrderAttachmentVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalFileName = file.getOriginalFilename();
				String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

				Path path = Paths.get(joborderUploadPath, uniqueFileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				JobOrderAttachmentVO attachment = JobOrderAttachmentVO.builder().jobOrderVO(jobOrderVO)
						.name(originalFileName).fileName(uniqueFileName).filePath(path.toString())
						.fileSize(file.getSize()).contentType(file.getContentType()).uploadOn(LocalDateTime.now())
						.build();

				attachmentList.add(attachment);
			}

			List<JobOrderAttachmentVO> savedAttachments = jobOrderAttachmentRepo.saveAll(attachmentList);

			jobOrderVO.setAttachments(savedAttachments);

		} catch (IOException e) {
			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private JobOrderResponseDTO buildJobOrderResponse(JobOrderVO jobOrderVO) {

		JobOrderResponseDTO responseDTO = new JobOrderResponseDTO();

		responseDTO.setId(jobOrderVO.getId());
		responseDTO.setDocId(jobOrderVO.getDocId());
		responseDTO.setDocDate(jobOrderVO.getDocDate());
		responseDTO.setBelongsTo(jobOrderVO.getBelongsTo());
		responseDTO.setJobOrderFor(jobOrderVO.getJobOrderFor());
		responseDTO.setIgstAppl(jobOrderVO.isIgstAppl());
		responseDTO.setContractNo(jobOrderVO.getContractNo());
		responseDTO.setIndentTime(jobOrderVO.getIndentTime());
		responseDTO.setTaxType(jobOrderVO.getTaxType());
		responseDTO.setTaxPercentage(jobOrderVO.getTaxPercentage());
		responseDTO.setPaymentTerms(jobOrderVO.getPaymentTerms());
		responseDTO.setDeliveryDate(jobOrderVO.getDeliveryDate());
		responseDTO.setAmount(jobOrderVO.getAmount());
		responseDTO.setNarration(jobOrderVO.getNarration());
		responseDTO.setNote(jobOrderVO.getNote());
		responseDTO.setOrgId(jobOrderVO.getOrgId());
		responseDTO.setFinancialYear(jobOrderVO.getFinancialYear());
		responseDTO.setCreatedBy(jobOrderVO.getCreatedBy());
		responseDTO.setUpdatedBy(jobOrderVO.getUpdatedBy());
		responseDTO.setActive(jobOrderVO.getActive());
		responseDTO.setCancel(jobOrderVO.getCancel());
		responseDTO.setCancelRemarks(jobOrderVO.getCancelRemarks());
		responseDTO.setScreenCode(jobOrderVO.getScreenCode());
		responseDTO.setScreenName(jobOrderVO.getScreenName());
		responseDTO.setJobOrderFor(jobOrderVO.getJobOrderFor());

		if (jobOrderVO.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(jobOrderVO.getBranch().getId());
			branchDTO.setBranchCode(jobOrderVO.getBranch().getBranchCode());
			branchDTO.setBranchName(jobOrderVO.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (jobOrderVO.getVendor() != null) {

			CustomerDropdownResponseDTO vendorDTO = new CustomerDropdownResponseDTO();

			vendorDTO.setCustomerId(jobOrderVO.getVendor().getId());
			vendorDTO.setCustomerCode(jobOrderVO.getVendor().getCustomerCode());
			vendorDTO.setCustomerName(jobOrderVO.getVendor().getCustomerName());
			vendorDTO.setAddress(jobOrderVO.getVendor().getAddress());
//	        vendorDTO.setGstState(jobOrderVO.getVendor().getGstState().);
			vendorDTO.setGstNo(jobOrderVO.getVendor().getGstNo());
			vendorDTO.setIgstApplicable(jobOrderVO.getVendor().isGstApplicable());
			vendorDTO.setGstType(jobOrderVO.getVendor().getGstType());

			responseDTO.setVendor(vendorDTO);
		}

		if (jobOrderVO.getHsnSacCode() != null) {
			HsnResponseDTO hsnDTO = new HsnResponseDTO();
			hsnDTO.setId(jobOrderVO.getHsnSacCode().getId());
			hsnDTO.setHsn(jobOrderVO.getHsnSacCode().getHsn()); // adjust to actual HsnVO field names
			hsnDTO.setDescription(jobOrderVO.getHsnSacCode().getDescription());
			responseDTO.setHsnSacCode(hsnDTO);
		}

		if (jobOrderVO.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(jobOrderVO.getDepartment().getId());
			departmentDTO.setDepartmentCode(jobOrderVO.getDepartment().getDepartmentCode());
			departmentDTO.setDepartmentName(jobOrderVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(departmentDTO);
		}

		if (jobOrderVO.getServiceName() != null) {

			ServiceAccMasterResponse1DTO serviceDTO = new ServiceAccMasterResponse1DTO();

			serviceDTO.setId(jobOrderVO.getServiceName().getId());
			serviceDTO.setServiceDescription(jobOrderVO.getServiceName().getServiceDescription());
			serviceDTO.setServiceName(jobOrderVO.getServiceName().getServiceName());

			responseDTO.setServiceName(serviceDTO);
		}

		if (jobOrderVO.getGstState() != null) {

			GSTStateMasterResponseDTO gstStateDTO = new GSTStateMasterResponseDTO();

			gstStateDTO.setId(jobOrderVO.getGstState().getId());
			gstStateDTO.setGstState(jobOrderVO.getGstState().getStateName());
			gstStateDTO.setGstStateCode(jobOrderVO.getGstState().getStateCode());

			responseDTO.setGstState(gstStateDTO);
		}

		// Details
		List<JobOrderDetailsResponseDTO> detailsList = new ArrayList<>();

		if (jobOrderVO.getJobOrderDetails() != null) {

			for (JobOrderDetailsVO detailVO : jobOrderVO.getJobOrderDetails()) {

				JobOrderDetailsResponseDTO detailDTO = new JobOrderDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());
				detailDTO.setIncomingType(detailVO.getIncomingType());
				detailDTO.setOrderQty(detailVO.getOrderQty());
				detailDTO.setRate(detailVO.getRate());
				detailDTO.setAmount(detailVO.getAmount());
				detailDTO.setSgstRate(detailVO.getSgstRate());
				detailDTO.setSgstAmount(detailVO.getSgstAmount());
				detailDTO.setCgstRate(detailVO.getCgstRate());
				detailDTO.setCgstAmount(detailVO.getCgstAmount());
				detailDTO.setIgstRate(detailVO.getIgstRate());
				detailDTO.setIgstAmount(detailVO.getIgstAmount());
				detailDTO.setSentFor(detailVO.getSentFor());
				detailDTO.setBom(detailVO.getBom());

				if (detailVO.getIncomingItem() != null) {
					ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
					itemDTO.setId(detailVO.getIncomingItem().getId());
					itemDTO.setItemCode(detailVO.getIncomingItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getIncomingItem().getItemDescription());
					detailDTO.setIncomingItem(itemDTO);
				}
//	            if (detailVO.getBom() != null) {
//	                BomResponseDTO bomDTO = new BomResponseDTO();
//	                bomDTO.setId(detailVO.getBom().getId());
//	                bomDTO.setProductType(detailVO.getBom().getProductType());
//	                bomDTO.setProductCode(detailVO.getBom().getProductCode());
//	                bomDTO.setProductName(detailVO.getBom().getProductName());
//	                bomDTO.setUom(detailVO.getBom().getUom());          // adjust getter name if BomVO stores it differently
//	                bomDTO.setQty(detailVO.getBom().getQty());
//	                detailDTO.setBom(bomDTO);
//	            }

				if (detailVO.getUnit() != null) {
					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();
					unitDTO.setId(detailVO.getUnit().getId());
					unitDTO.setUnitId(detailVO.getUnit().getUnitId());
					unitDTO.setUnitDescription(detailVO.getUnit().getDescription());
					detailDTO.setUnit(unitDTO);
				}

				detailsList.add(detailDTO);
			}
		}

		responseDTO.setJobOrderDetails(detailsList);

		// Tax Details
		List<JobOrderTaxDetailsResponseDTO> taxDetailsList = new ArrayList<>();

		if (jobOrderVO.getJobOrderTaxDetails() != null) {

			for (JobOrderTaxDetailsVO taxVO : jobOrderVO.getJobOrderTaxDetails()) {

				JobOrderTaxDetailsResponseDTO taxDTO = new JobOrderTaxDetailsResponseDTO();
				taxDTO.setId(taxVO.getId());
				taxDTO.setParticulars(taxVO.getParticulars());
				taxDTO.setAmount(taxVO.getAmount());

				taxDetailsList.add(taxDTO);
			}
		}

		responseDTO.setJobOrderTaxDetails(taxDetailsList);

		// Attachments
		List<JobOrderAttachmentResponseDTO> attachmentList = new ArrayList<>();

		if (jobOrderVO.getAttachments() != null) {

			for (JobOrderAttachmentVO fileVO : jobOrderVO.getAttachments()) {

				JobOrderAttachmentResponseDTO fileDTO = new JobOrderAttachmentResponseDTO();
				fileDTO.setId(fileVO.getId());
				fileDTO.setName(fileVO.getName());
				fileDTO.setFileName(fileVO.getFileName());
				fileDTO.setFileSize(fileVO.getFileSize());
				fileDTO.setContentType(fileVO.getContentType());
				fileDTO.setUploadOn(fileVO.getUploadOn());

				String urlPath = joborderUploadPath.replace("C:/", "/").replace("\\", "/");
				fileDTO.setFilePath(serverBaseUrl + urlPath + fileVO.getFileName());

				attachmentList.add(fileDTO);
			}
		}

		responseDTO.setAttachments(attachmentList);

		return responseDTO;
	}

	@Override
	public List<Map<String, Object>> getSupplierRateContractDropdown(Long customer, Long orgId, Long branch) {

		Set<Object[]> result = supplierRateContractRepo.getSupplierRateContractDropdown(customer, orgId, branch);

		return getSupplierRateContractDropdownDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractDropdownDetails(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("jobOrderFor", fs[2] != null ? fs[2].toString() : null);

			part.put("docId", fs[0] != null ? fs[0].toString() : null);

			part.put("docDate", fs[1] != null ? fs[1] : null);

			part.put("taxPercentage", fs[8] != null ? new BigDecimal(fs[8].toString()) : BigDecimal.ZERO);

			part.put("igstRate", fs[9] != null ? new BigDecimal(fs[9].toString()) : BigDecimal.ZERO);

			part.put("cgstRate", fs[10] != null ? new BigDecimal(fs[10].toString()) : BigDecimal.ZERO);

			part.put("sgstRate", fs[11] != null ? new BigDecimal(fs[11].toString()) : BigDecimal.ZERO);

			// Service
			Map<String, Object> service = new HashMap<>();

			service.put("id", fs[3] != null ? Long.valueOf(fs[3].toString()) : null);

			service.put("name", fs[4] != null ? fs[4].toString() : null);

			part.put("serviceName", service);

			// HSN
			Map<String, Object> hsn = new HashMap<>();

			hsn.put("id", fs[5] != null ? Long.valueOf(fs[5].toString()) : null);

			hsn.put("code", fs[6] != null ? fs[6].toString() : null);

			hsn.put("description", fs[7] != null ? fs[7].toString() : null);

			part.put("hsnSacCode", hsn);

			details.add(part);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getSupplierRateContractItemDetailsForJobOrder(String docId, Long orgId,
			Long branch) {

		Set<Object[]> result = supplierRateContractRepo.getSupplierRateContractItemDetails(docId, orgId, branch);

		return getSupplierRateContractItemDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractItemDetails(Set<Object[]> result) {

		List<Map<String, Object>> details1 = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? Long.valueOf(fs[0].toString()) : null);
			part.put("incomingItemId", fs[1] != null ? Long.valueOf(fs[1].toString()) : null);
			part.put("itemCode", fs[2] != null ? fs[2].toString() : null);
			part.put("itemDescription", fs[3] != null ? fs[3].toString() : null);
			part.put("unitId", fs[4] != null ? Long.valueOf(fs[4].toString()) : null);
			part.put("unit", fs[5] != null ? fs[5].toString() : null);
			part.put("unitDescription", fs[6] != null ? fs[6].toString() : null);
			part.put("rate", fs[7] != null ? new BigDecimal(fs[7].toString()) : BigDecimal.ZERO);

			details1.add(part);
		}

		return details1;
	}

	@Override
	public JobOrderResponseDTO getJobOrderById(Long id) throws ApplicationException {

		JobOrderVO jobOrderVO = jobOrderRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Job Order Not Found"));

		return buildJobOrderResponse(jobOrderVO);
	}

	@Override
	public List<JobOrderResponseDTO> getJobOrderByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException {

		List<JobOrderVO> jobOrders = jobOrderRepo.findByOrgIdAndBranch(orgId, branch);

		List<JobOrderResponseDTO> responseList = new ArrayList<>();

		for (JobOrderVO vo : jobOrders) {

			responseList.add(buildJobOrderResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getJobOrderDocId(Long orgId, String financialYear) {

		String screenCode1 = "JO";

		String result = jobOrderRepo.getJobOrderDocId(orgId, financialYear, screenCode1);

		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateJobOrderAmendment(JobOrderAmendmentDTO jobOrderAmendmentDTO)
			throws ApplicationException {

		String screenCode = "JOA";

		Map<String, Object> response = new HashMap<>();

		String message;

		JobOrderAmendmentVO jobOrderAmendmentVO;

		if (ObjectUtils.isEmpty(jobOrderAmendmentDTO.getId())) {

			jobOrderAmendmentVO = new JobOrderAmendmentVO();

			String docId = jobOrderAmendmentRepo.getJobOrderAmendmentDocId(jobOrderAmendmentDTO.getOrgId(),
					jobOrderAmendmentDTO.getFinancialYear(), screenCode);

			jobOrderAmendmentVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(jobOrderAmendmentDTO.getOrgId(),
							jobOrderAmendmentDTO.getFinancialYear(), screenCode);

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			jobOrderAmendmentVO.setCreatedBy(jobOrderAmendmentDTO.getCreatedBy());

			jobOrderAmendmentVO.setUpdatedBy(jobOrderAmendmentDTO.getCreatedBy());

			message = "Job Order Amendment Created Successfully";

		} else {

			jobOrderAmendmentVO = jobOrderAmendmentRepo.findById(jobOrderAmendmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Job Order Amendment Not Found"));

			jobOrderAmendmentVO.setUpdatedBy(jobOrderAmendmentDTO.getCreatedBy());

			message = "Job Order Amendment Updated Successfully";
		}

		createUpdateJobOrderAmendmentVOByDTO(jobOrderAmendmentDTO, jobOrderAmendmentVO);

		// Cascade saves everything
		jobOrderAmendmentVO = jobOrderAmendmentRepo.save(jobOrderAmendmentVO);

		JobOrderAmendmentResponseDTO responseDTO = buildJobOrderAmendmentResponse(jobOrderAmendmentVO);

		response.put("message", message);
		response.put("jobOrderAmendment", responseDTO);

		return response;
	}

	private void createUpdateJobOrderAmendmentVOByDTO(JobOrderAmendmentDTO dto, JobOrderAmendmentVO jobOrderAmendmentVO)
			throws ApplicationException {

		jobOrderAmendmentVO.setJobOrderNo(dto.getJobOrderNo());

		jobOrderAmendmentVO.setJobOrderDate(dto.getJobOrderDate());

		jobOrderAmendmentVO.setRevisionNo(dto.getRevisionNo());

		jobOrderAmendmentVO.setOldDeliveryDate(dto.getOldDeliveryDate());

		jobOrderAmendmentVO.setNewDeliveryDate(dto.getNewDeliveryDate());

		jobOrderAmendmentVO.setRemarks(dto.getRemarks());

		jobOrderAmendmentVO.setOrgId(dto.getOrgId());

		jobOrderAmendmentVO.setFinancialYear(dto.getFinancialYear());

		jobOrderAmendmentVO.setActive(dto.isActive());

		jobOrderAmendmentVO.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			jobOrderAmendmentVO.setBranch(branch);
		}

		// ----------------------------------------------------
		// Party / Customer
		// ----------------------------------------------------

		if (dto.getCustomer() != null && dto.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			jobOrderAmendmentVO.setCustomer(customer);
		}

		// ----------------------------------------------------
		// Delete old child records while updating
		// ----------------------------------------------------

		if (dto.getId() != null) {

			List<JobOrderAmendmentDetailsVO> oldList = jobOrderAmendmentDetailsRepo
					.findByJobOrderAmendment(jobOrderAmendmentVO);

			jobOrderAmendmentDetailsRepo.deleteAll(oldList);
		}

		// ----------------------------------------------------
		// Child Details
		// ----------------------------------------------------

		List<JobOrderAmendmentDetailsVO> detailList = new ArrayList<>();

		if (dto.getJobOrderAmendmentDetails() != null && !dto.getJobOrderAmendmentDetails().isEmpty()) {

			for (JobOrderAmendmentDetailsDTO detailDTO : dto.getJobOrderAmendmentDetails()) {

				JobOrderAmendmentDetailsVO detailVO = new JobOrderAmendmentDetailsVO();

				// Item

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// Unit

				if (detailDTO.getUnit() != null && detailDTO.getUnit() != 0) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				detailVO.setOldQty(detailDTO.getOldQty());

				detailVO.setNewQty(detailDTO.getNewQty());

				// Parent Mapping

				detailVO.setJobOrderAmendment(jobOrderAmendmentVO);

				detailList.add(detailVO);
			}

			// Set child list to parent

			jobOrderAmendmentVO.setJobOrderAmendmentDetails(detailList);
		}
	}

	private JobOrderAmendmentResponseDTO buildJobOrderAmendmentResponse(JobOrderAmendmentVO vo) {

		JobOrderAmendmentResponseDTO response = new JobOrderAmendmentResponseDTO();

		// ================= Header =================

		response.setId(vo.getId());

		response.setDocId(vo.getDocId());

		response.setDocDate(vo.getDocDate());

		response.setJobOrderNo(vo.getJobOrderNo());

		response.setJobOrderDate(vo.getJobOrderDate());

		response.setRevisionNo(vo.getRevisionNo());

		response.setOldDeliveryDate(vo.getOldDeliveryDate());

		response.setNewDeliveryDate(vo.getNewDeliveryDate());

		response.setRemarks(vo.getRemarks());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setCreatedBy(vo.getCreatedBy());

		response.setUpdatedBy(vo.getUpdatedBy());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setActive(vo.getActive());

		response.setCancel(vo.getCancel());

		response.setScreenCode(vo.getScreenCode());

		response.setScreenName(vo.getScreenName());

		// ================= Branch =================

		if (vo.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(vo.getBranch().getId());

			branch.setBranchCode(vo.getBranch().getBranchCode());

			branch.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branch);
		}

		// ================= Customer =================

		if (vo.getCustomer() != null) {

			CustomerDropdownResponseDTO customer = new CustomerDropdownResponseDTO();

			customer.setCustomerId(vo.getCustomer().getId());

			customer.setCustomerCode(vo.getCustomer().getCustomerCode());

			customer.setCustomerName(vo.getCustomer().getCustomerName());

			response.setCustomer(customer);
		}

		// ================= Details =================

		List<JobOrderAmendmentDetailsResponseDTO> detailsResponse = new ArrayList<>();

		if (vo.getJobOrderAmendmentDetails() != null) {

			for (JobOrderAmendmentDetailsVO detailVO : vo.getJobOrderAmendmentDetails()) {

				JobOrderAmendmentDetailsResponseDTO detailResponse = new JobOrderAmendmentDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());

				detailResponse.setOldQty(detailVO.getOldQty());

				detailResponse.setNewQty(detailVO.getNewQty());

				// ================= Item =================

				if (detailVO.getItem() != null) {

					ItemResponseDTO item = new ItemResponseDTO();

					item.setId(detailVO.getItem().getId());

					item.setItemCode(detailVO.getItem().getItemCode());

					item.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponse.setItem(item);
				}

				// ================= Unit =================

				if (detailVO.getUnit() != null) {

					UnitResponseDTO unit = new UnitResponseDTO();

					unit.setId(detailVO.getUnit().getId());

					unit.setUnitId(detailVO.getUnit().getDescription());

					detailResponse.setUnit(unit);
				}

				detailsResponse.add(detailResponse);
			}
		}

		response.setJobOrderAmendmentDetails(detailsResponse);

		return response;
	}

	@Override
	public List<Map<String, Object>> getJobOrderNoAndDateForJobOrderAmd(Long branch, Long orgId, Long customer) {

		Set<Object[]> result = jobOrderRepo.getJobOrderNoAndDateForJobOrderAmd(branch, orgId, customer);

		return getJobOrderNoAndDate(result);
	}

	private List<Map<String, Object>> getJobOrderNoAndDate(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? fs[0] : null);
			part.put("jobOrderNo", fs[1] != null ? fs[1] : null);
			part.put("jobOrderDate", fs[2] != null ? fs[2] : null);

			details.add(part);
		}

		return details;
	}

	@Override
	public Integer getNextRevisionNoForJobOrderAmd(String jobOrderNo, Long branch, Long orgId) {

		Integer revisionNo = jobOrderAmendmentRepo.getNextRevisionNoForJobOrderAmd(jobOrderNo, branch, orgId);

		return revisionNo != null ? revisionNo : 1;
	}

	@Override
	public List<Map<String, Object>> getJobOrderItemDetailsForJobOrderAmd(String jobOrderNo, Long branch, Long orgId,
			Long customer) {

		Set<Object[]> result = jobOrderRepo.getJobOrderItemDetailsForJobOrderAmd(jobOrderNo, branch, orgId, customer);

		return getJobOrderItemDetailsForJobOrderAmd(result);
	}

	private List<Map<String, Object>> getJobOrderItemDetailsForJobOrderAmd(Set<Object[]> result) {

		List<Map<String, Object>> details1 = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? fs[0] : null);
			part.put("item", fs[1] != null ? fs[1] : null);
			part.put("itemCode", fs[2] != null ? fs[2] : null);
			part.put("itemDescription", fs[3] != null ? fs[3] : null);
			part.put("unit", fs[4] != null ? fs[4] : null);
			part.put("unitDescription", fs[5] != null ? fs[5] : null);
			part.put("rate", fs[6] != null ? fs[6] : null);

			part.put("bom", fs[7] != null ? fs[7] : null);

			part.put("deliveryDate", fs[8] != null ? fs[8] : null);
			part.put("orderQty", fs[9] != null ? fs[9] : null);


			details1.add(part);
		}

		return details1;
	}

	@Override
	public JobOrderAmendmentResponseDTO getJobOrderAmendmentById(Long id) throws ApplicationException {

		JobOrderAmendmentVO jobOrderAmendmentVO = jobOrderAmendmentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Job Order Amendment Not Found"));

		return buildJobOrderAmendmentResponse(jobOrderAmendmentVO);
	}

	@Override
	public List<JobOrderAmendmentResponseDTO> getJobOrderAmendmentByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException {

		List<JobOrderAmendmentVO> jobOrderAmendments = jobOrderAmendmentRepo.findByOrgIdAndBranch(orgId, branch);

		List<JobOrderAmendmentResponseDTO> responseList = new ArrayList<>();

		for (JobOrderAmendmentVO vo : jobOrderAmendments) {

			responseList.add(buildJobOrderAmendmentResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getJobOrderAmendmentDocId(Long orgId, String financialYear) {

		String screenCode1 = "JOA";

		String result = jobOrderAmendmentRepo.getJobOrderAmendmentDocId(orgId, financialYear, screenCode1);

		return result;
	}

	// DeliveryChallanSubcontracting

	@Override
	@Transactional
	public DeliveryChallanSubcontractingResponseDTO getDeliveryChallanSubcontractingById(Long id)
			throws ApplicationException {

		DeliveryChallanSubcontractingVO vo = deliveryChallanSubcontractingRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Delivery Challan For Sub Contracting Not Found"));

		return buildDeliveryChallanSubcontractingResponse(vo);
	}

	@Override
	@Transactional
	public List<DeliveryChallanSubcontractingResponseDTO> getAllDeliveryChallanSubcontractingByOrgIdAndBranch(
			Long orgId, Long branch) throws ApplicationException {

		List<DeliveryChallanSubcontractingVO> list = deliveryChallanSubcontractingRepo.findAllByOrgIdAndBranch(orgId,
				branch);

		List<DeliveryChallanSubcontractingResponseDTO> responseList = new ArrayList<>();

		for (DeliveryChallanSubcontractingVO vo : list) {

			responseList.add(buildDeliveryChallanSubcontractingResponse(vo));
		}

		return responseList;
	}

	@Override
	@Transactional
	public String getDeliveryChallanSubcontractingDocId(Long orgId, String financialYear) throws ApplicationException {

		String screenCode = "SCDC";

		String docId = deliveryChallanSubcontractingRepo.getDeliveryChallanSubcontractingDocId(orgId, financialYear,
				screenCode);

		if (docId == null || docId.isEmpty()) {
			throw new ApplicationException("Document ID Not Found");
		}

		return docId;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateDeliveryChallanSubcontracting(
			DeliveryChallanSubcontractingDTO deliveryChallanSubcontractingDTO) throws ApplicationException {

		String screenCode = "SCDC";

		Map<String, Object> response = new HashMap<>();

		String message;

		DeliveryChallanSubcontractingVO deliveryChallanSubcontractingVO;

		if (ObjectUtils.isEmpty(deliveryChallanSubcontractingDTO.getId())) {

			deliveryChallanSubcontractingVO = new DeliveryChallanSubcontractingVO();

			String docId = deliveryChallanSubcontractingRepo.getDeliveryChallanSubcontractingDocId(
					deliveryChallanSubcontractingDTO.getOrgId(), deliveryChallanSubcontractingDTO.getFinancialYear(),
					screenCode);

			deliveryChallanSubcontractingVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(deliveryChallanSubcontractingDTO.getOrgId(),
							deliveryChallanSubcontractingDTO.getFinancialYear(), screenCode);

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			deliveryChallanSubcontractingVO.setCreatedBy(deliveryChallanSubcontractingDTO.getCreatedBy());

			deliveryChallanSubcontractingVO.setUpdatedBy(deliveryChallanSubcontractingDTO.getCreatedBy());

			message = "Delivery Challan For Sub Contracting Created Successfully";

		} else {

			deliveryChallanSubcontractingVO = deliveryChallanSubcontractingRepo
					.findById(deliveryChallanSubcontractingDTO.getId())
					.orElseThrow(() -> new ApplicationException("Delivery Challan For Sub Contracting Not Found"));

			deliveryChallanSubcontractingVO.setUpdatedBy(deliveryChallanSubcontractingDTO.getCreatedBy());

			message = "Delivery Challan For Sub Contracting Updated Successfully";
		}

		createUpdateDeliveryChallanSubcontractingVOByDTO(deliveryChallanSubcontractingDTO,
				deliveryChallanSubcontractingVO);

		// Cascade saves everything
		deliveryChallanSubcontractingVO = deliveryChallanSubcontractingRepo.save(deliveryChallanSubcontractingVO);

		DeliveryChallanSubcontractingResponseDTO responseDTO = buildDeliveryChallanSubcontractingResponse(
				deliveryChallanSubcontractingVO);

		response.put("message", message);

		response.put("deliveryChallanSubcontracting", responseDTO);

		return response;
	}

	private void createUpdateDeliveryChallanSubcontractingVOByDTO(DeliveryChallanSubcontractingDTO dto,
			DeliveryChallanSubcontractingVO deliveryChallanSubcontractingVO) throws ApplicationException {

		// ============================================================
		// Header Fields
		// ============================================================

		deliveryChallanSubcontractingVO.setBelongsTo(dto.getBelongsTo());

		deliveryChallanSubcontractingVO.setJobOrderNo(dto.getJobOrderNo());

		deliveryChallanSubcontractingVO.setVehicleNo(dto.getVehicleNo());

		deliveryChallanSubcontractingVO.setQty(dto.getQty());

		deliveryChallanSubcontractingVO.setTimeOfIssue(dto.getTimeOfIssue());

		deliveryChallanSubcontractingVO.setTransportName(dto.getTransportName());

		deliveryChallanSubcontractingVO.setDcType(dto.getDcType());

		deliveryChallanSubcontractingVO.setApprovalByStores(dto.getApprovalByStores());

		deliveryChallanSubcontractingVO.setRemarks(dto.getRemarks());

		deliveryChallanSubcontractingVO.setOrgId(dto.getOrgId());

		deliveryChallanSubcontractingVO.setFinancialYear(dto.getFinancialYear());

		deliveryChallanSubcontractingVO.setActive(dto.isActive());

		deliveryChallanSubcontractingVO.setCancelRemarks(dto.getCancelRemarks());

		deliveryChallanSubcontractingVO.setSfgBom(dto.getSfgBom());
		// ============================================================
		// Branch
		// ============================================================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			deliveryChallanSubcontractingVO.setBranch(branch);
		}

		// ============================================================
		// Department
		// ============================================================

		if (dto.getDepartment() != null && dto.getDepartment() != 0) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			deliveryChallanSubcontractingVO.setDepartment(department);
		}

		// ============================================================
		// Vendor
		// ============================================================

		if (dto.getVendor() != null && dto.getVendor() != 0) {

			CustomerVO vendor = customerRepo.findById(dto.getVendor())
					.orElseThrow(() -> new ApplicationException("Vendor Not Found"));

			deliveryChallanSubcontractingVO.setVendor(vendor);
		}

		// ============================================================
		// Party Location
		// ============================================================

		if (dto.getPartyLocation() != null && dto.getPartyLocation() != 0) {

			LocationVO partyLocation = locationRepo.findById(dto.getPartyLocation())
					.orElseThrow(() -> new ApplicationException("Party Location Not Found"));

			deliveryChallanSubcontractingVO.setLocation(partyLocation);
		}

		// ============================================================
		// Incoming Item
		// ============================================================

		if (dto.getIncomingItem() != null && dto.getIncomingItem() != 0) {

			ItemMasterVO incomingItem = itemMasterRepo.findById(dto.getIncomingItem())
					.orElseThrow(() -> new ApplicationException("Incoming Item Not Found"));

			deliveryChallanSubcontractingVO.setIncomingItem(incomingItem);
		}

		// ============================================================
		// Transport
		// ============================================================

		// ============================================================
		// SFG BOM
		// ============================================================

//	    if (dto.getSfgBomId() != null
//	            && dto.getSfgBomId() != 0) {
//
//	        BomVO bom =
//	                bomRepo.findById(dto.getSfgBomId())
//	                        .orElseThrow(() ->
//	                                new ApplicationException(
//	                                        "BOM Not Found"));
//
//	        deliveryChallanSubcontractingVO.setSfgBomId(bom);
//	    }

		// ============================================================
		// Prepared By
		// ============================================================

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

			deliveryChallanSubcontractingVO.setPreparedBy(preparedBy);
		}

		// ============================================================
		// Approved By
		// ============================================================

		if (dto.getApprovedBy() != null && dto.getApprovedBy() != 0) {

			EmployeeMasterVO approvedBy = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Employee Not Found"));

			deliveryChallanSubcontractingVO.setApprovedBy(approvedBy);
		}

		// ============================================================
		// Delete Old Details While Updating
		// ============================================================

		if (dto.getId() != null) {

			List<DeliveryChallanSubcontractingDetailsVO> oldList = deliveryChallanSubcontractingDetailsRepo
					.findByDeliveryChallanSubcontracting(deliveryChallanSubcontractingVO);

			deliveryChallanSubcontractingDetailsRepo.deleteAll(oldList);
		}

		// ============================================================
		// Child Details
		// ============================================================

		List<DeliveryChallanSubcontractingDetailsVO> detailList = new ArrayList<>();

		if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

			for (DeliveryChallanSubcontractingDetailsDTO detailDTO : dto.getDetails()) {

				DeliveryChallanSubcontractingDetailsVO detailVO = new DeliveryChallanSubcontractingDetailsVO();

				// ====================================================
				// Outgoing Item
				// ====================================================

				if (detailDTO.getOutgoingItem() != null && detailDTO.getOutgoingItem() != 0) {

					ItemMasterVO outgoingItem = itemMasterRepo.findById(detailDTO.getOutgoingItem())
							.orElseThrow(() -> new ApplicationException("Outgoing Item Not Found"));

					detailVO.setOutgoingItem(outgoingItem);
				}

				// ====================================================
				// Unit
				// ====================================================

				if (detailDTO.getUnit() != null && detailDTO.getUnit() != 0) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				// ====================================================
				// From Location
				// ====================================================

				if (detailDTO.getFromLocation() != null && detailDTO.getFromLocation() != 0) {

					LocationVO fromLocation = locationRepo.findById(detailDTO.getFromLocation())
							.orElseThrow(() -> new ApplicationException("From Location Not Found"));

					detailVO.setFromLocation(fromLocation);
				}

				// ====================================================
				// Other Detail Fields
				// ====================================================

				detailVO.setStock(detailDTO.getStock());

				detailVO.setAvailableStock(detailDTO.getAvailableStock());

				detailVO.setIssueQty(detailDTO.getIssueQty());

				detailVO.setUnitRate(detailDTO.getUnitRate());

				BigDecimal amount = BigDecimal.ZERO;

				if (detailDTO.getIssueQty() != null && detailDTO.getUnitRate() != null) {

					amount = detailDTO.getIssueQty().multiply(detailDTO.getUnitRate());
				}

				detailVO.setAmount(amount);

				detailVO.setRemarks(detailDTO.getRemarks());

				detailVO.setContractNo(detailDTO.getContractNo());
				detailVO.setJobOrderFor(detailDTO.getJobOrderFor());
				// ====================================================
				// Parent Mapping
				// ====================================================

				detailVO.setDeliveryChallanSubcontracting(deliveryChallanSubcontractingVO);

				detailList.add(detailVO);
			}

			// Set child list to parent

			deliveryChallanSubcontractingVO.setDetails(detailList);
		}
	}

	private DeliveryChallanSubcontractingResponseDTO buildDeliveryChallanSubcontractingResponse(
			DeliveryChallanSubcontractingVO vo) {

		DeliveryChallanSubcontractingResponseDTO response = new DeliveryChallanSubcontractingResponseDTO();

		// ============================================================
		// Header
		// ============================================================

		response.setId(vo.getId());

		response.setDocId(vo.getDocId());

		response.setDocDate(vo.getDocDate());

		response.setBelongsTo(vo.getBelongsTo());

		response.setJobOrderNo(vo.getJobOrderNo());

		response.setVehicleNo(vo.getVehicleNo());

		response.setQty(vo.getQty());

		response.setTimeOfIssue(vo.getTimeOfIssue());

		response.setDcType(vo.getDcType());

		response.setApprovalByStores(vo.getApprovalByStores());

		response.setRemarks(vo.getRemarks());

		response.setOrgId(vo.getOrgId());

		response.setTransportName(vo.getTransportName());

		response.setFinancialYear(vo.getFinancialYear());

		response.setCreatedBy(vo.getCreatedBy());

		response.setUpdatedBy(vo.getUpdatedBy());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setActive(vo.getActive());

		response.setCancel(vo.getCancel());

		response.setScreenCode(vo.getScreenCode());

		response.setScreenName(vo.getScreenName());

		response.setSfgBom(vo.getSfgBom());

		// ============================================================
		// Branch
		// ============================================================

		if (vo.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(vo.getBranch().getId());

			branch.setBranchCode(vo.getBranch().getBranchCode());

			branch.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branch);
		}

		// ============================================================
		// Department
		// ============================================================

		if (vo.getDepartment() != null) {

			DepartmentResponseDTO department = new DepartmentResponseDTO();

			department.setId(vo.getDepartment().getId());

			department.setDepartmentCode(vo.getDepartment().getDepartmentCode());

			department.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(department);
		}

		// ============================================================
		// Vendor
		// ============================================================

		if (vo.getVendor() != null) {

			CustomerDropdownResponseDTO vendor = new CustomerDropdownResponseDTO();

			vendor.setCustomerId(vo.getVendor().getId());

			vendor.setCustomerCode(vo.getVendor().getCustomerCode());

			vendor.setCustomerName(vo.getVendor().getCustomerName());

			response.setVendor(vendor);
		}

		// ============================================================
		// Party Location
		// ============================================================

		if (vo.getLocation() != null) {

			LocationMasterResponseDTO location = new LocationMasterResponseDTO();

			location.setId(vo.getLocation().getId());

			location.setLocationName(vo.getLocation().getLocationName());

			response.setPartyLocation(location);
		}

		// ============================================================
		// Incoming Item
		// ============================================================

		if (vo.getIncomingItem() != null) {

			ItemResponseDTO item = new ItemResponseDTO();

			item.setId(vo.getIncomingItem().getId());

			item.setItemCode(vo.getIncomingItem().getItemCode());

			item.setItemDescription(vo.getIncomingItem().getItemDescription());

			// If ItemMasterVO has Unit
			if (vo.getIncomingItem().getPrimaryUnit() != null) {

				UnitResponseDTO unit = new UnitResponseDTO();

				unit.setId(vo.getIncomingItem().getPrimaryUnit().getId());

				unit.setUnitId(vo.getIncomingItem().getPrimaryUnit().getDescription());

				item.setUnit(unit);
			}

			response.setIncomingItem(item);
		}

		// ============================================================
		// Transport
		// ============================================================

		// ============================================================
		// SFG BOM
		// ============================================================

//	    if (vo.getSfgBomId() != null) {
//
//	        BomResponseDTO bom =
//	                new BomResponseDTO();
//
//	        bom.setId(
//	                vo.getSfgBomId().getId());
//
//	        bom.setProductType(
//	                vo.getSfgBomId().getProductType());
//
//	        bom.setProductCode(
//	                vo.getSfgBomId().getProductCode());
//
//	        bom.setProductName(
//	                vo.getSfgBomId().getProductName());
//
//	        bom.setUom(
//	                vo.getSfgBomId().getUom());
//
//	        bom.setQty(
//	                vo.getSfgBomId().getQty());
//
//	        response.setSfgBomId(bom);
//	    }

		// ============================================================
		// Prepared By
		// ============================================================

		if (vo.getPreparedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getPreparedBy().getId());

			employee.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(employee);
		}

		// ============================================================
		// Approved By
		// ============================================================

		if (vo.getApprovedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getApprovedBy().getId());

			employee.setEmployeeName(vo.getApprovedBy().getEmployeeName());

			response.setApprovedBy(employee);
		}

		// ============================================================
		// Details
		// ============================================================

		List<DeliveryChallanSubcontractingDetailsResponseDTO> detailsResponse = new ArrayList<>();

		if (vo.getDetails() != null) {

			for (DeliveryChallanSubcontractingDetailsVO detailVO : vo.getDetails()) {

				DeliveryChallanSubcontractingDetailsResponseDTO detailResponse = new DeliveryChallanSubcontractingDetailsResponseDTO();

				// ====================================================
				// Detail ID
				// ====================================================

				detailResponse.setId(detailVO.getId());

				// ====================================================
				// Stock
				// ====================================================

				detailResponse.setStock(detailVO.getStock());

				// ====================================================
				// Available Stock
				// ====================================================

				detailResponse.setAvailableStock(detailVO.getAvailableStock());

				// ====================================================
				// Issue Qty
				// ====================================================

				detailResponse.setIssueQty(detailVO.getIssueQty());

				// ====================================================
				// Unit Rate
				// ====================================================

				detailResponse.setUnitRate(detailVO.getUnitRate());

				// ====================================================
				// Amount
				// ====================================================

				detailResponse.setAmount(detailVO.getAmount());

				// ====================================================
				// Remarks
				// ====================================================

				detailResponse.setRemarks(detailVO.getRemarks());

				detailResponse.setContractNo(detailVO.getContractNo());
				detailResponse.setJobOrderFor(detailVO.getJobOrderFor());

				// ====================================================
				// Outgoing Item
				// ====================================================

				if (detailVO.getOutgoingItem() != null) {

					ItemResponseDTO item = new ItemResponseDTO();

					item.setId(detailVO.getOutgoingItem().getId());

					item.setItemCode(detailVO.getOutgoingItem().getItemCode());

					item.setItemDescription(detailVO.getOutgoingItem().getItemDescription());

					// Item Unit
					if (detailVO.getOutgoingItem().getPrimaryUnit() != null) {

						UnitResponseDTO itemUnit = new UnitResponseDTO();

						itemUnit.setId(detailVO.getOutgoingItem().getPrimaryUnit().getId());

						itemUnit.setUnitId(detailVO.getOutgoingItem().getPrimaryUnit().getDescription());

						item.setUnit(itemUnit);
					}

					detailResponse.setOutgoingItem(item);
				}

				// ====================================================
				// Unit
				// ====================================================

				if (detailVO.getUnit() != null) {

					UnitResponseDTO unit = new UnitResponseDTO();

					unit.setId(detailVO.getUnit().getId());

					unit.setUnitId(detailVO.getUnit().getDescription());

					detailResponse.setUnit(unit);
				}

				// ====================================================
				// From Location
				// ====================================================

				if (detailVO.getFromLocation() != null) {

					LocationMasterResponseDTO location = new LocationMasterResponseDTO();

					location.setId(detailVO.getFromLocation().getId());

					location.setLocationName(detailVO.getFromLocation().getLocationName());

					detailResponse.setFromLocation(location);
				}

				detailsResponse.add(detailResponse);
			}
		}

		response.setDetails(detailsResponse);

		return response;
	}

	@Override
	public List<Map<String, Object>> getLocationForDeliverChallanSubContract(Long orgId, Long branch) {

		Set<Object[]> result = locationRepo.getLocationForDeliverChallanSubContract(orgId, branch);

		return getSubContractLocationDropdown(result);
	}

	private List<Map<String, Object>> getSubContractLocationDropdown(Set<Object[]> result) {

		List<Map<String, Object>> details1 = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? fs[0] : null);

			part.put("locationId", fs[1] != null ? fs[1] : null);

			part.put("locationName", fs[2] != null ? fs[2] : null);

			details1.add(part);
		}

		return details1;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsforDeliveryChallanSubContract(String jobOrderNo, Long branch,
			Long orgId, Long vendor) {

		Set<Object[]> result = jobOrderRepo.getItemDetailsforDeliveryChallanSubContract(jobOrderNo, branch, orgId,
				vendor);

		return getItemDetailsforDeliveryChallanSubContract(result);
	}

	private List<Map<String, Object>> getItemDetailsforDeliveryChallanSubContract(Set<Object[]> result) {

		List<Map<String, Object>> details1 = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? fs[0] : null);
			part.put("jobOrderFor", fs[1] != null ? fs[1] : null);
			part.put("contractNo", fs[2] != null ? fs[2] : null);
			part.put("outgoingItem", fs[3] != null ? fs[3] : null);
			part.put("itemCode", fs[4] != null ? fs[4] : null);
			part.put("itemDescription", fs[5] != null ? fs[5] : null);
			part.put("unit", fs[6] != null ? fs[6] : null);
			part.put("unitDescription", fs[7] != null ? fs[7] : null);
			part.put("rate", fs[8] != null ? fs[8] : null);

			details1.add(part);
		}

		return details1;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateSubContractSupplySchedule(SubContractSupplyScheduleDTO dto)
			throws ApplicationException {

		String screenCode = "SCSS";

		Map<String, Object> response = new HashMap<>();

		String message;

		SubContractSupplyScheduleVO subContractSupplyScheduleVO;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			subContractSupplyScheduleVO = new SubContractSupplyScheduleVO();

			// Generate Document ID
			String docId = subContractSupplyScheduleRepo.getSubContractSupplyScheduleDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			subContractSupplyScheduleVO.setDocId(docId);

			// Update Document Last Number
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			subContractSupplyScheduleVO.setCreatedBy(dto.getCreatedBy());
			subContractSupplyScheduleVO.setUpdatedBy(dto.getCreatedBy());

			message = "Sub Contract Supplier Schedule Created Successfully";
		}

		// =========================================================
		// UPDATE
		// =========================================================

		else {

			subContractSupplyScheduleVO = subContractSupplyScheduleRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Sub Contract Supplier Schedule Not Found"));

			// Delete Old Item Details

			if (subContractSupplyScheduleVO.getItemDetails() != null) {

				// Delete Child of Child - Schedule Details
				for (SubContractSupplyScheduleItemDetailsVO itemDetails : subContractSupplyScheduleVO
						.getItemDetails()) {

					if (itemDetails.getScheduleDetails() != null) {
						subContractSupplyScheduleDetailsRepo.deleteAll(itemDetails.getScheduleDetails());

						itemDetails.getScheduleDetails().clear();
					}
				}

				// Delete Child - Item Details
				subContractSupplyScheduleItemDetailsRepo.deleteAll(subContractSupplyScheduleVO.getItemDetails());

				subContractSupplyScheduleVO.getItemDetails().clear();
			}

			subContractSupplyScheduleVO.setUpdatedBy(dto.getCreatedBy());

			message = "Sub Contract Supplier Schedule Updated Successfully";
		}

		// =========================================================
		// HEADER MAPPING
		// =========================================================

		getSubContractSupplyScheduleVOFromDTO(dto, subContractSupplyScheduleVO);

		// =========================================================
		// SAVE
		// =========================================================

		subContractSupplyScheduleVO = subContractSupplyScheduleRepo.saveAndFlush(subContractSupplyScheduleVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		response.put("message", message);

		response.put("subContractSupplyScheduleVO",
				convertToSubContractSupplyScheduleResponse(subContractSupplyScheduleVO));

		return response;
	}

	private void getSubContractSupplyScheduleVOFromDTO(SubContractSupplyScheduleDTO dto, SubContractSupplyScheduleVO vo)
			throws ApplicationException {

		// =========================================================
		// Branch
		// =========================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// =========================================================
		// Customer
		// =========================================================

		if (dto.getCustomer() != null) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			vo.setCustomer(customer);
		}

		// =========================================================
		// Prepared By
		// =========================================================

		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

			vo.setPreparedBy(preparedBy);
		}

		// =========================================================
		// Authorised By
		// =========================================================

		if (dto.getAuthorisedBy() != null) {

			EmployeeMasterVO authorisedBy = employeeMasterRepo.findById(dto.getAuthorisedBy())
					.orElseThrow(() -> new ApplicationException("Authorised By Employee Not Found"));

			vo.setAuthorisedBy(authorisedBy);
		}

		// =========================================================
		// Header Fields
		// =========================================================

		vo.setBelongsTo(dto.getBelongsTo());

		vo.setSchStartDate(dto.getSchStartDate());

		vo.setSchEndDate(dto.getSchEndDate());

		vo.setContractNo(dto.getContractNo());

		vo.setContractDate(dto.getContractDate());

		vo.setJobOrderNo(dto.getJobOrderNo());

		vo.setRemarks(dto.getRemarks());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());

		// =========================================================
		// Item Details
		// =========================================================

		List<SubContractSupplyScheduleItemDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getItemDetails() != null) {

			for (SubContractSupplyScheduleItemDetailsDTO itemDTO : dto.getItemDetails()) {

				SubContractSupplyScheduleItemDetailsVO itemDetails = new SubContractSupplyScheduleItemDetailsVO();

				// =================================================
				// Item
				// =================================================

				if (itemDTO.getItem() != null) {

					ItemMasterVO itemCode = itemMasterRepo.findById(itemDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					itemDetails.setItem(itemCode);
				}

				// =================================================
				// Unit
				// =================================================

				if (itemDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(itemDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					itemDetails.setUnit(unit);
				}

				// =================================================
				// Item Fields
				// =================================================

				itemDetails.setStock(itemDTO.getStock());

				itemDetails.setQty(itemDTO.getQty());

				itemDetails.setRate(itemDTO.getRate());

				// =================================================
				// Parent Mapping
				// =================================================

				itemDetails.setSubContractSupplyScheduleVO(vo);

				// =================================================
				// Schedule Details
				// =================================================

				List<SubContractSupplyScheduleDetailsVO> scheduleDetailsList = new ArrayList<>();

				if (itemDTO.getScheduleDetails() != null) {

					for (SubContractSupplyScheduleDetailsDTO scheduleDTO : itemDTO.getScheduleDetails()) {

						SubContractSupplyScheduleDetailsVO scheduleDetails = new SubContractSupplyScheduleDetailsVO();

						scheduleDetails.setPlanDate(scheduleDTO.getPlanDate());

						scheduleDetails.setScheduleQty(scheduleDTO.getScheduleQty());

						// Parent Item Mapping
						scheduleDetails.setItemDetails(itemDetails);

						scheduleDetailsList.add(scheduleDetails);
					}
				}

				itemDetails.setScheduleDetails(scheduleDetailsList);

				itemDetailsList.add(itemDetails);
			}
		}

		// =========================================================
		// Set Item Details to Header
		// =========================================================

		vo.setItemDetails(itemDetailsList);
	}

	private SubContractSupplyScheduleResponseDTO convertToSubContractSupplyScheduleResponse(
			SubContractSupplyScheduleVO vo) {

		SubContractSupplyScheduleResponseDTO response = new SubContractSupplyScheduleResponseDTO();

		// ============================================================
		// Header
		// ============================================================

		response.setId(vo.getId());

		response.setDocId(vo.getDocId());

		response.setSchStartDate(vo.getSchStartDate());

		response.setDocDate(vo.getDocDate());

		response.setSchEndDate(vo.getSchEndDate());

		response.setBelongsTo(vo.getBelongsTo());

		response.setContractNo(vo.getContractNo());

		response.setContractDate(vo.getContractDate());

		response.setJobOrderNo(vo.getJobOrderNo());

		response.setRemarks(vo.getRemarks());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		response.setCreatedBy(vo.getCreatedBy());

		response.setUpdatedBy(vo.getUpdatedBy());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setActive(vo.getActive());

		response.setCancel(vo.getCancel());

		response.setScreenCode(vo.getScreenCode());

		response.setScreenName(vo.getScreenName());

		// ============================================================
		// Branch
		// ============================================================

		if (vo.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(vo.getBranch().getId());

			branch.setBranchCode(vo.getBranch().getBranchCode());

			branch.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branch);
		}

		// ============================================================
		// Customer
		// ============================================================

		if (vo.getCustomer() != null) {

			CustomerDropdownResponseDTO customer = new CustomerDropdownResponseDTO();

			customer.setCustomerId(vo.getCustomer().getId());

			customer.setCustomerCode(vo.getCustomer().getCustomerCode());

			customer.setCustomerName(vo.getCustomer().getCustomerName());

			response.setCustomer(customer);
		}

		// ============================================================
		// Prepared By
		// ============================================================

		if (vo.getPreparedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getPreparedBy().getId());

			employee.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(employee);
		}

		// ============================================================
		// Authorised By
		// ============================================================

		if (vo.getAuthorisedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getAuthorisedBy().getId());

			employee.setEmployeeName(vo.getAuthorisedBy().getEmployeeName());

			response.setAuthorisedBy(employee);
		}

		// ============================================================
		// Item Details
		// ============================================================

		List<SubContractSupplyScheduleItemDetailsResponseDTO> itemDetailsResponse = new ArrayList<>();

		if (vo.getItemDetails() != null) {

			for (SubContractSupplyScheduleItemDetailsVO itemVO : vo.getItemDetails()) {

				SubContractSupplyScheduleItemDetailsResponseDTO itemResponse = new SubContractSupplyScheduleItemDetailsResponseDTO();

				// ====================================================
				// Item Detail ID
				// ====================================================

				itemResponse.setId(itemVO.getId());

				// ====================================================
				// Item Code
				// ====================================================

				if (itemVO.getItem() != null) {

					ItemMasterResponseDetailsDTO item = new ItemMasterResponseDetailsDTO();

					item.setId(itemVO.getItem().getId());

					item.setItemCode(itemVO.getItem().getItemCode());

					item.setItemDescription(itemVO.getItem().getItemDescription());

					// Item Primary Unit
					if (itemVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

						unit.setId(itemVO.getItem().getPrimaryUnit().getId());

						unit.setUnitId(itemVO.getItem().getPrimaryUnit().getUnitId());

						unit.setUnitId(itemVO.getItem().getPrimaryUnit().getDescription());

						item.setUnit(unit);
					}

					itemResponse.setItemCode(item);
				}

				// ====================================================
				// Unit
				// ====================================================

//	            if (itemVO.getUnit() != null) {
//
//	                UnitMasterResponseDTO unit =
//	                        new UnitMasterResponseDTO();
//
//	                unit.setId(
//	                        itemVO.getUnit().getId());
//
//	                unit.setUnitId(
//	                        itemVO.getUnit().getDescription());
//
//	                itemResponse.setUnit(unit);
//	            }

				// ====================================================
				// Stock
				// ====================================================

				itemResponse.setStock(itemVO.getStock());

				// ====================================================
				// Qty
				// ====================================================

				itemResponse.setQty(itemVO.getQty());

				// ====================================================
				// Rate
				// ====================================================

				itemResponse.setRate(itemVO.getRate());

				// ====================================================
				// Schedule Details
				// ====================================================

				List<SubContractSupplyScheduleDetailsResponseDTO> scheduleDetailsResponse = new ArrayList<>();

				if (itemVO.getScheduleDetails() != null) {

					for (SubContractSupplyScheduleDetailsVO scheduleVO : itemVO.getScheduleDetails()) {

						SubContractSupplyScheduleDetailsResponseDTO scheduleResponse = new SubContractSupplyScheduleDetailsResponseDTO();

						// ================================================
						// Schedule Detail ID
						// ================================================

						scheduleResponse.setId(scheduleVO.getId());

						// ================================================
						// Plan Date
						// ================================================

						scheduleResponse.setPlanDate(scheduleVO.getPlanDate());

						// ================================================
						// Schedule Qty
						// ================================================

						scheduleResponse.setScheduleQty(scheduleVO.getScheduleQty());

						scheduleDetailsResponse.add(scheduleResponse);
					}
				}

				itemResponse.setScheduleDetails(scheduleDetailsResponse);

				itemDetailsResponse.add(itemResponse);
			}
		}

		response.setItemDetails(itemDetailsResponse);

		return response;
	}

	@Override
	public List<Map<String, Object>> getJobOrderNoAndDateForSubContractSupplySch(Long branch, Long orgId,
			String contractNo) {

		Set<Object[]> result = jobOrderRepo.getJobOrderNoAndDateForSubContractSupplySch(branch, orgId, contractNo);

		return getJobOrderNoAndDateForSubContractSupplySch(result);
	}

	private List<Map<String, Object>> getJobOrderNoAndDateForSubContractSupplySch(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? fs[0] : null);
			part.put("jobOrderNo", fs[1] != null ? fs[1] : null);
			part.put("jobOrderDate", fs[2] != null ? fs[2] : null);

			details.add(part);
		}

		return details;
	}

	@Override
	public SubContractSupplyScheduleResponseDTO getSubContractSupplyScheduleById(Long id) throws ApplicationException {

		SubContractSupplyScheduleVO subContractSupplyScheduleVO = subContractSupplyScheduleRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Sub Contract Supply Schedule Not Found"));

		return convertToSubContractSupplyScheduleResponse(subContractSupplyScheduleVO);
	}

	@Override
	public List<SubContractSupplyScheduleResponseDTO> getSubContractSupplyScheduleByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException {

		List<SubContractSupplyScheduleVO> subContractSupplySchedules = subContractSupplyScheduleRepo
				.findByOrgIdAndBranch(orgId, branch);

		List<SubContractSupplyScheduleResponseDTO> responseList = new ArrayList<>();

		for (SubContractSupplyScheduleVO vo : subContractSupplySchedules) {

			responseList.add(convertToSubContractSupplyScheduleResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getSubContractSupplyScheduleDocId(Long orgId, String financialYear) {

		String screenCode1 = "SCSS";

		String result = subContractSupplyScheduleRepo.getSubContractSupplyScheduleDocId(orgId, financialYear,
				screenCode1);

		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateSupplierRateContractAmendment(SupplierRateContractAmendmentDTO dto)
			throws ApplicationException {

		String screenCode = "SRCA";

		Map<String, Object> response = new HashMap<>();

		String message;

		SupplierRateContractAmendmentVO supplierRateContractAmendmentVO;

		// ============================================================
		// Create
		// ============================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			supplierRateContractAmendmentVO = new SupplierRateContractAmendmentVO();

			// ========================================================
			// Generate Doc Id
			// ========================================================

			String docId = supplierRateContractAmendmentRepo.getSupplierRateContractAmendmentDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			supplierRateContractAmendmentVO.setDocId(docId);

			supplierRateContractAmendmentVO.setCreatedBy(dto.getCreatedBy());

			supplierRateContractAmendmentVO.setUpdatedBy(dto.getCreatedBy());

			message = "Supplier Rate Contract Amendment Created Successfully";

		} else {

			// ========================================================
			// Update
			// ========================================================

			supplierRateContractAmendmentVO = supplierRateContractAmendmentRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Supplier Rate Contract Amendment Not Found"));

			// ========================================================
			// Delete Old Item Details
			// ========================================================

			if (supplierRateContractAmendmentVO.getItemDetails() != null) {

				supplierRateContractAmendmentItemDetailsRepo
						.deleteAll(supplierRateContractAmendmentVO.getItemDetails());

				supplierRateContractAmendmentVO.getItemDetails().clear();
			}

			supplierRateContractAmendmentVO.setUpdatedBy(dto.getCreatedBy());

			message = "Supplier Rate Contract Amendment Updated Successfully";
		}

		// ============================================================
		// Set Header And Item Details
		// ============================================================

		getSupplierRateContractAmendmentVOFromDTO(dto, supplierRateContractAmendmentVO);

		// ============================================================
		// Save
		// ============================================================

		supplierRateContractAmendmentVO = supplierRateContractAmendmentRepo
				.saveAndFlush(supplierRateContractAmendmentVO);

		// ============================================================
		// Response
		// ============================================================

		response.put("message", message);

		response.put("supplierRateContractAmendmentVO",
				convertToSupplierRateContractAmendmentResponse(supplierRateContractAmendmentVO));

		return response;
	}

	private void getSupplierRateContractAmendmentVOFromDTO(SupplierRateContractAmendmentDTO dto,
			SupplierRateContractAmendmentVO vo) throws ApplicationException {

		// ============================================================
		// Branch
		// ============================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// ============================================================
		// Customer
		// ============================================================

		if (dto.getCustomer() != null) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			vo.setCustomer(customer);
		}

		// ============================================================
		// Prepared By
		// ============================================================

		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

			vo.setPreparedBy(preparedBy);
		}

		// ============================================================
		// Authorised By
		// ============================================================

		if (dto.getAuthorisedBy() != null) {

			EmployeeMasterVO authorisedBy = employeeMasterRepo.findById(dto.getAuthorisedBy())
					.orElseThrow(() -> new ApplicationException("Authorised By Employee Not Found"));

			vo.setAuthorisedBy(authorisedBy);
		}

		// ============================================================
		// Header Details
		// ============================================================

		vo.setBelongsTo(dto.getBelongsTo());

		vo.setContractDate(dto.getContractDate());

		vo.setContractNo(dto.getContractNo());

		vo.setValidFrom(dto.getValidFrom());

		vo.setNewValidFrom(dto.getNewValidFrom());

		vo.setValidTo(dto.getValidTo());

		vo.setNewValidTo(dto.getNewValidTo());

		vo.setRevisionNo(dto.getRevisionNo());

		vo.setFreightType(dto.getFreightType());

		vo.setPackingType(dto.getPackingType());

		vo.setInsuranceAmount(dto.getInsuranceAmount());

		vo.setModeOfDespatch(dto.getModeOfDespatch());

		vo.setTaxDescription(dto.getTaxDescription());

		vo.setRemarks(dto.getRemarks());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());

		// ============================================================
		// Item Details
		// ============================================================

		List<SupplierRateContractAmendmentItemDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getItemDetails() != null) {

			for (SupplierRateContractAmendmentItemDetailsDTO itemDTO : dto.getItemDetails()) {

				SupplierRateContractAmendmentItemDetailsVO itemDetails = new SupplierRateContractAmendmentItemDetailsVO();

				// ====================================================
				// Item
				// ====================================================

				if (itemDTO.getItem() != null) {

					ItemMasterVO item = itemMasterRepo.findById(itemDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					itemDetails.setItem(item);
				}

				// ====================================================
				// Unit
				// ====================================================

				if (itemDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(itemDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					itemDetails.setUnit(unit);
				}

				// ====================================================
				// Rate Details
				// ====================================================

				itemDetails.setOldRate(itemDTO.getOldRate());

				itemDetails.setNewRate(itemDTO.getNewRate());

				// ====================================================
				// Header Reference
				// ====================================================

				itemDetails.setSupplierRateContractAmendmentVO(vo);

				itemDetailsList.add(itemDetails);
			}
		}

		vo.setItemDetails(itemDetailsList);
	}

	private SupplierRateContractAmendmentResponseDTO convertToSupplierRateContractAmendmentResponse(
			SupplierRateContractAmendmentVO vo) {

		SupplierRateContractAmendmentResponseDTO response = new SupplierRateContractAmendmentResponseDTO();

		// ============================================================
		// Header Details
		// ============================================================

		response.setId(vo.getId());
		response.setDocId(vo.getDocId());
		response.setDocDate(vo.getDocDate());
		response.setBelongsTo(vo.getBelongsTo());
		response.setContractDate(vo.getContractDate());
		response.setContractNo(vo.getContractNo());
		response.setValidFrom(vo.getValidFrom());
		response.setNewValidFrom(vo.getNewValidFrom());
		response.setValidTo(vo.getValidTo());
		response.setNewValidTo(vo.getNewValidTo());
		response.setRevisionNo(vo.getRevisionNo());
		response.setFreightType(vo.getFreightType());
		response.setPackingType(vo.getPackingType());
		response.setInsuranceAmount(vo.getInsuranceAmount());
		response.setModeOfDespatch(vo.getModeOfDespatch());
		response.setTaxDescription(vo.getTaxDescription());
		response.setRemarks(vo.getRemarks());
		response.setOrgId(vo.getOrgId());
		response.setFinancialYear(vo.getFinancialYear());
		response.setCreatedBy(vo.getCreatedBy());
		response.setUpdatedBy(vo.getUpdatedBy());
		response.setCancelRemarks(vo.getCancelRemarks());
		response.setActive(vo.getActive());
		response.setCancel(vo.getCancel());
		response.setScreenCode(vo.getScreenCode());
		response.setScreenName(vo.getScreenName());

		// ============================================================
		// Branch
		// ============================================================

		if (vo.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(vo.getBranch().getId());

			branch.setBranchCode(vo.getBranch().getBranchCode());

			branch.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branch);
		}

		// ============================================================
		// Customer
		// ============================================================

		if (vo.getCustomer() != null) {

			CustomerDropdownResponseDTO customer = new CustomerDropdownResponseDTO();

			customer.setCustomerId(vo.getCustomer().getId());

			customer.setCustomerCode(vo.getCustomer().getCustomerCode());

			customer.setCustomerName(vo.getCustomer().getCustomerName());

			response.setCustomer(customer);
		}

		// ============================================================
		// Prepared By
		// ============================================================

		if (vo.getPreparedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getPreparedBy().getId());

			employee.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(employee);
		}

		// ============================================================
		// Authorised By
		// ============================================================

		if (vo.getAuthorisedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getAuthorisedBy().getId());

			employee.setEmployeeName(vo.getAuthorisedBy().getEmployeeName());

			response.setAuthorisedBy(employee);
		}

		// ============================================================
		// Item Details
		// ============================================================

		List<SupplierRateContractAmendmentItemDetailsResponseDTO> itemDetailsResponse = new ArrayList<>();

		if (vo.getItemDetails() != null) {

			for (SupplierRateContractAmendmentItemDetailsVO itemVO : vo.getItemDetails()) {

				SupplierRateContractAmendmentItemDetailsResponseDTO itemResponse = new SupplierRateContractAmendmentItemDetailsResponseDTO();

				itemResponse.setId(itemVO.getId());

				// ====================================================
				// Item
				// ====================================================

				if (itemVO.getItem() != null) {

					ItemMasterResponseDetailsDTO item = new ItemMasterResponseDetailsDTO();

					item.setId(itemVO.getItem().getId());

					item.setItemCode(itemVO.getItem().getItemCode());

					item.setItemDescription(itemVO.getItem().getItemDescription());

					// =================================================
					// Primary Unit
					// =================================================

					if (itemVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

						unit.setId(itemVO.getItem().getPrimaryUnit().getId());

						unit.setUnitId(itemVO.getItem().getPrimaryUnit().getDescription());

						item.setUnit(unit);
					}

					itemResponse.setItemCode(item);
				}

				// ====================================================
				// Unit
				// ====================================================

				if (itemVO.getUnit() != null) {

					UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

					unit.setId(itemVO.getUnit().getId());

					unit.setUnitId(itemVO.getUnit().getDescription());

					itemResponse.setUnit(unit);
				}

				// ====================================================
				// Rate Details
				// ====================================================

				itemResponse.setOldRate(itemVO.getOldRate());

				itemResponse.setNewRate(itemVO.getNewRate());

				itemDetailsResponse.add(itemResponse);
			}
		}

		response.setItemDetails(itemDetailsResponse);

		return response;
	}

	@Override
	public SupplierRateContractAmendmentResponseDTO getSupplierRateContractAmendmentById(Long id)
			throws ApplicationException {

		SupplierRateContractAmendmentVO supplierRateContractAmendmentVO = supplierRateContractAmendmentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Supplier Rate Contract Amendment Not Found"));

		return convertToSupplierRateContractAmendmentResponse(supplierRateContractAmendmentVO);
	}

	@Override
	public List<SupplierRateContractAmendmentResponseDTO> getSupplierRateContractAmendmentByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException {

		List<SupplierRateContractAmendmentVO> supplierRateContractAmendments = supplierRateContractAmendmentRepo
				.findByOrgIdAndBranch(orgId, branch);

		List<SupplierRateContractAmendmentResponseDTO> responseList = new ArrayList<>();

		for (SupplierRateContractAmendmentVO vo : supplierRateContractAmendments) {

			responseList.add(convertToSupplierRateContractAmendmentResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getSupplierRateContractAmendmentDocId(Long orgId, String financialYear) {

		String screenCode1 = "SRCA";

		String result = supplierRateContractAmendmentRepo.getSupplierRateContractAmendmentDocId(orgId, financialYear,
				screenCode1);

		return result;
	}

	@Override
	public List<Map<String, Object>> getRevisionNoDetailsForSupplierRateContractAmd(String contractNo, Long orgId,
			Long branch) {

		List<Object[]> result = supplierRateContractAmendmentRepo
				.getRevisionNoDetailsForSupplierRateContractAmd(contractNo, orgId, branch);

		List<Map<String, Object>> details = new ArrayList<>();

		if (result == null || result.isEmpty()) {

			Map<String, Object> part = new HashMap<>();

			part.put("newValidFrom", null);
			part.put("newValidTo", null);
			part.put("revisionNo", 1);

			details.add(part);

		} else {

			for (Object[] fs : result) {

				Map<String, Object> part = new HashMap<>();

				part.put("newValidFrom", fs[0] != null ? fs[0] : null);

				part.put("newValidTo", fs[1] != null ? fs[1] : null);

				part.put("revisionNo", fs[2] != null ? fs[2] : 1);

				details.add(part);
			}
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getSupplierRateContractItemDetailsForSRCAmd(String docId, Long orgId,
			Long branch) {

		List<Object[]> result = supplierRateContractAmendmentRepo.getSupplierRateContractItemDetailsForSRCAmd(docId,
				orgId, branch);

		return getSupplierRateContractAmendmentItemDetails(result);
	}

	private List<Map<String, Object>> getSupplierRateContractAmendmentItemDetails(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("id", fs[0] != null ? fs[0] : null);

			part.put("incomingItem", fs[1] != null ? fs[1] : null);

			part.put("itemCode", fs[2] != null ? fs[2] : null);

			part.put("itemDescription", fs[3] != null ? fs[3] : null);

			part.put("unit", fs[4] != null ? fs[4] : null);

			part.put("unitId", fs[5] != null ? fs[5] : null);

			part.put("description", fs[6] != null ? fs[6] : null);

			part.put("oldRate", fs[7] != null ? fs[7] : null);

			details.add(part);
		}

		return details;
	}

	//

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateProductionScheduleForNextThreeMonth(
			ProductionScheduleForNextThreeMonthDTO dto) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		ProductionScheduleForNextThreeMonthVO productionScheduleVO;

		String message;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			productionScheduleVO = new ProductionScheduleForNextThreeMonthVO();

			productionScheduleVO.setCreatedBy(dto.getCreatedBy());
			productionScheduleVO.setUpdatedBy(dto.getCreatedBy());

			message = "Production Schedule For Next Three Month Created Successfully";

		} else {

			// =====================================================
			// UPDATE
			// =====================================================

			productionScheduleVO = productionScheduleForNextThreeMonthRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Production Schedule For Next Three Month Not Found"));

			// =====================================================
			// DELETE OLD DETAILS
			// =====================================================

			List<ProductionScheduleForNextThreeMonthDetailsVO> oldDetails = productionScheduleForNextThreeMonthDetailsRepo
					.findByProductionScheduleForNextThreeMonthId(productionScheduleVO.getId());

			if (oldDetails != null && !oldDetails.isEmpty()) {

				productionScheduleForNextThreeMonthDetailsRepo.deleteAll(oldDetails);
			}

			productionScheduleVO.setUpdatedBy(dto.getCreatedBy());

			message = "Production Schedule For Next Three Month Updated Successfully";
		}

		// =========================================================
		// DTO TO VO
		// =========================================================

		getProductionScheduleForNextThreeMonthVOFromDTO(dto, productionScheduleVO);

		// =========================================================
		// SAVE
		// =========================================================

		productionScheduleVO = productionScheduleForNextThreeMonthRepo.saveAndFlush(productionScheduleVO);

		// =========================================================
		// CONVERT RESPONSE
		// =========================================================

		ProductionScheduleForNextThreeMonthResponseDTO productionScheduleResponseDTO = convertToResponse(
				productionScheduleVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		response.put("message", message);

		response.put("productionScheduleForNextThreeMonth", productionScheduleResponseDTO);

		return response;
	}

	// =============================================================
	// DTO TO VO
	// =============================================================

	private void getProductionScheduleForNextThreeMonthVOFromDTO(ProductionScheduleForNextThreeMonthDTO dto,
			ProductionScheduleForNextThreeMonthVO productionScheduleVO) throws ApplicationException {

		// =========================================================
		// BRANCH
		// =========================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			productionScheduleVO.setBranch(branch);
		}

		// =========================================================
		// HEADER
		// =========================================================

		productionScheduleVO.setMonthYear(dto.getMonthYear());

		productionScheduleVO.setOrgId(dto.getOrgId());

		productionScheduleVO.setFinancialYear(dto.getFinancialYear());

		productionScheduleVO.setCreatedBy(dto.getCreatedBy());

		productionScheduleVO.setActive(dto.isActive());

		productionScheduleVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================================================
		// DETAILS
		// =========================================================

		List<ProductionScheduleForNextThreeMonthDetailsVO> detailList = new ArrayList<>();

		if (dto.getProductionScheduleForNextThreeMonthDetails() != null
				&& !dto.getProductionScheduleForNextThreeMonthDetails().isEmpty()) {

			for (ProductionScheduleForNextThreeMonthDetailsDTO detailDTO : dto
					.getProductionScheduleForNextThreeMonthDetails()) {

				ProductionScheduleForNextThreeMonthDetailsVO detailVO = new ProductionScheduleForNextThreeMonthDetailsVO();

				// =================================================
				// ITEM
				// =================================================

				if (detailDTO.getItem() != null) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// =================================================
				// JANUARY
				// =================================================

				detailVO.setDate(detailDTO.getDate());

				detailVO.setJanuary(detailDTO.getJanuary());

				// =================================================
				// FEBRUARY
				// =================================================

				detailVO.setFebruary(detailDTO.getFebruary());

				// =================================================
				// MARCH
				// =================================================

				detailVO.setMarch(detailDTO.getMarch());

				// =================================================
				// APRIL
				// =================================================

				detailVO.setApril(detailDTO.getApril());

				// =================================================
				// MAY
				// =================================================

				detailVO.setMay(detailDTO.getMay());

				// =================================================
				// JUNE
				// =================================================

				detailVO.setJune(detailDTO.getJune());

				// =================================================
				// JULY
				// =================================================

				detailVO.setJuly(detailDTO.getJuly());

				// =================================================
				// AUGUST
				// =================================================

				detailVO.setAugust(detailDTO.getAugust());

				// =================================================
				// SEPTEMBER
				// =================================================

				detailVO.setSeptember(detailDTO.getSeptember());

				// =================================================
				// OCTOBER
				// =================================================

				detailVO.setOctober(detailDTO.getOctober());

				// =================================================
				// NOVEMBER
				// =================================================

				detailVO.setNovember(detailDTO.getNovember());

				// =================================================
				// DECEMBER
				// =================================================

				detailVO.setDecember(detailDTO.getDecember());

				// =================================================
				// PARENT
				// =================================================

				detailVO.setProductionScheduleForNextThreeMonth(productionScheduleVO);

				detailList.add(detailVO);
			}
		}

		// =========================================================
		// CLEAR EXISTING DETAILS
		// =========================================================

		if (productionScheduleVO.getProductionScheduleForNextThreeMonthDetails() == null) {

			productionScheduleVO.setProductionScheduleForNextThreeMonthDetails(new ArrayList<>());
		} else {

			productionScheduleVO.getProductionScheduleForNextThreeMonthDetails().clear();
		}

		// =========================================================
		// ADD DETAILS
		// =========================================================

		productionScheduleVO.getProductionScheduleForNextThreeMonthDetails().addAll(detailList);
	}

	// =============================================================
	// VO TO RESPONSE DTO
	// =============================================================

	private ProductionScheduleForNextThreeMonthResponseDTO convertToResponse(
			ProductionScheduleForNextThreeMonthVO productionScheduleVO) {

		ProductionScheduleForNextThreeMonthResponseDTO responseDTO = new ProductionScheduleForNextThreeMonthResponseDTO();

		// =========================================================
		// HEADER
		// =========================================================

		responseDTO.setId(productionScheduleVO.getId());

		responseDTO.setMonthYear(productionScheduleVO.getMonthYear());

		responseDTO.setOrgId(productionScheduleVO.getOrgId());

		responseDTO.setFinancialYear(productionScheduleVO.getFinancialYear());

		responseDTO.setCreatedBy(productionScheduleVO.getCreatedBy());

		responseDTO.setActive(productionScheduleVO.isActive());

		responseDTO.setCancelRemarks(productionScheduleVO.getCancelRemarks());

		// =========================================================
		// BRANCH
		// =========================================================

		if (productionScheduleVO.getBranch() != null) {

			responseDTO.setBranch(productionScheduleVO.getBranch().getId());
		}

		// =========================================================
		// DETAILS RESPONSE
		// =========================================================

		List<ProductionScheduleForNextThreeMonthDetailsResponseDTO> detailsResponse = new ArrayList<>();

		if (productionScheduleVO.getProductionScheduleForNextThreeMonthDetails() != null) {

			for (ProductionScheduleForNextThreeMonthDetailsVO detailVO : productionScheduleVO
					.getProductionScheduleForNextThreeMonthDetails()) {

				ProductionScheduleForNextThreeMonthDetailsResponseDTO detailResponse = new ProductionScheduleForNextThreeMonthDetailsResponseDTO();

				// =================================================
				// ITEM RESPONSE
				// =================================================

				if (detailVO.getItem() != null) {

					ItemMasterResponseDetailsDTO itemResponseDTO = new ItemMasterResponseDetailsDTO();

					itemResponseDTO.setId(detailVO.getItem().getId());

					itemResponseDTO.setItemCode(detailVO.getItem().getItemCode());

					itemResponseDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponse.setItem(itemResponseDTO);
				}

				// =================================================
				// MONTHS
				// =================================================

				detailResponse.setId(detailVO.getId());

				detailResponse.setDate(detailVO.getDate());

				detailResponse.setJanuary(detailVO.getJanuary());

				detailResponse.setFebruary(detailVO.getFebruary());

				detailResponse.setMarch(detailVO.getMarch());

				detailResponse.setApril(detailVO.getApril());

				detailResponse.setMay(detailVO.getMay());

				detailResponse.setJune(detailVO.getJune());

				detailResponse.setJuly(detailVO.getJuly());

				detailResponse.setAugust(detailVO.getAugust());

				detailResponse.setSeptember(detailVO.getSeptember());

				detailResponse.setOctober(detailVO.getOctober());

				detailResponse.setNovember(detailVO.getNovember());

				detailResponse.setDecember(detailVO.getDecember());

				detailsResponse.add(detailResponse);
			}
		}

		responseDTO.setProductionScheduleForNextThreeMonthDetails(detailsResponse);

		return responseDTO;
	}

	@Override
	public ProductionScheduleForNextThreeMonthResponseDTO getProductionScheduleForNextThreeMonthById(Long id)
			throws ApplicationException {

		ProductionScheduleForNextThreeMonthVO productionScheduleVO = productionScheduleForNextThreeMonthRepo
				.findById(id)
				.orElseThrow(() -> new ApplicationException("Production Schedule For Next Three Month Not Found"));

		return convertToResponse(productionScheduleVO);
	}

	@Override
	public List<ProductionScheduleForNextThreeMonthResponseDTO> getAllProductionScheduleForNextThreeMonthByOrgIdAndBranch(
			Long orgId, Long branch) throws ApplicationException {

		List<ProductionScheduleForNextThreeMonthVO> productionScheduleList = productionScheduleForNextThreeMonthRepo
				.findByOrgIdAndBranchId(orgId, branch);

		List<ProductionScheduleForNextThreeMonthResponseDTO> responseList = new ArrayList<>();

		for (ProductionScheduleForNextThreeMonthVO productionScheduleVO : productionScheduleList) {

			responseList.add(convertToResponse(productionScheduleVO));
		}

		return responseList;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateDeliveryChallanCumGatePass(DeliveryChallanCumGatePassDTO dto)
			throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		String screenCode = "DCCGP";
		DeliveryChallanCumGatePassVO deliveryChallanVO;

		String message;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			deliveryChallanVO = new DeliveryChallanCumGatePassVO();

			// Generate Document ID
			String docId = deliveryChallanCumGatePassRepo.getDeliveryChallanCumGatePassDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			deliveryChallanVO.setDocId(docId);

			// Update Document Last Number
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			deliveryChallanVO.setCreatedBy(dto.getCreatedBy());

			deliveryChallanVO.setUpdatedBy(dto.getCreatedBy());

			message = "Delivery Challan Cum Gate Pass Created Successfully";

		} else {

			// =====================================================
			// UPDATE
			// =====================================================

			deliveryChallanVO = deliveryChallanCumGatePassRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Delivery Challan Cum Gate Pass Not Found"));

			// =====================================================
			// DELETE OLD DETAILS
			// =====================================================

			List<DeliveryChallanCumGatePassDetailsVO> oldDetails = deliveryChallanCumGatePassDetailsRepo
					.findByDeliveryChallanCumGatePassVO(deliveryChallanVO);

			if (oldDetails != null && !oldDetails.isEmpty()) {
				deliveryChallanCumGatePassDetailsRepo.deleteAll(oldDetails);
			}

			deliveryChallanVO.setUpdatedBy(dto.getCreatedBy());

			message = "Delivery Challan Cum Gate Pass Updated Successfully";
		}

		// =========================================================
		// DTO TO VO
		// =========================================================

		getDeliveryChallanCumGatePassVOFromDTO(dto, deliveryChallanVO);

		// =========================================================
		// SAVE
		// =========================================================

		deliveryChallanVO = deliveryChallanCumGatePassRepo.saveAndFlush(deliveryChallanVO);

		// =========================================================
		// CONVERT RESPONSE
		// =========================================================

		DeliveryChallanCumGatePassResponseDTO deliveryChallanResponseDTO = convertToResponse(deliveryChallanVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		response.put("message", message);

		response.put("deliveryChallanCumGatePassVO", deliveryChallanResponseDTO);

		return response;
	}

	private void getDeliveryChallanCumGatePassVOFromDTO(DeliveryChallanCumGatePassDTO dto,
			DeliveryChallanCumGatePassVO deliveryChallanVO) throws ApplicationException {

		// =========================================================
		// BRANCH
		// =========================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			deliveryChallanVO.setBranch(branch);
		}

		// =========================================================
		// DEPARTMENT
		// =========================================================

		if (dto.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			deliveryChallanVO.setDepartment(department);
		}

		// =========================================================
		// CUSTOMER / PLANT
		// =========================================================

		if ("PARTY".equalsIgnoreCase(dto.getType())) {

			if (dto.getPartyPlantId() != null) {

				CustomerVO customer = customerRepo.findById(dto.getPartyPlantId())
						.orElseThrow(() -> new ApplicationException("Customer Not Found"));

				deliveryChallanVO.setCustomer(customer);
			}

		} else if ("BRANCH".equalsIgnoreCase(dto.getType())) {

			if (dto.getPartyPlantId() != null) {

				BranchVO branch = branchRepo.findById(dto.getPartyPlantId())
						.orElseThrow(() -> new ApplicationException("Branch Not Found"));

				deliveryChallanVO.setToBranch(branch);
			}
		}
		// =========================================================
		// FROM LOCATION
		// =========================================================

		if (dto.getFromLocation() != null) {

			LocationVO location = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			deliveryChallanVO.setFromLocation(location);
		}

		// =========================================================
		// WORK ORDER
		// =========================================================

//	        if (dto.getWorkOrderNo() != null) {
//
//	            JobOrderVO jobOrder =
//	                    jobOrderRepo.findById(dto.getWorkOrderNo())
//	                            .orElseThrow(() ->
//	                                    new ApplicationException(
//	                                            "Work Order Not Found"));
//
//	            deliveryChallanVO.setWorkOrderNo(jobOrder);
//	        }

		deliveryChallanVO.setWorkOrderNo(dto.getWorkOrderNo());
		// =========================================================
		// PREPARED BY
		// =========================================================

		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));

			deliveryChallanVO.setPreparedBy(employee);
		}

		// =========================================================
		// HEADER
		// =========================================================

		deliveryChallanVO.setBelongsTo(dto.getBelongsTo());

		deliveryChallanVO.setType(dto.getType());

		deliveryChallanVO.setIGSTAppl(dto.isIGSTAppl());

		deliveryChallanVO.setGstnNo(dto.getGstnNo());

		deliveryChallanVO.setModeOfTransport(dto.getModeOfTransport());

		deliveryChallanVO.setVehicleNo(dto.getVehicleNo());

		deliveryChallanVO.setTotalQty(dto.getTotalQty());

		deliveryChallanVO.setRemarks(dto.getRemarks());

		// =========================================================
		// COMMON FIELDS
		// =========================================================

		deliveryChallanVO.setOrgId(dto.getOrgId());

		deliveryChallanVO.setFinancialYear(dto.getFinancialYear());

		deliveryChallanVO.setCreatedBy(dto.getCreatedBy());

		deliveryChallanVO.setActive(dto.isActive());

		deliveryChallanVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================================================
		// DETAILS
		// =========================================================

		List<DeliveryChallanCumGatePassDetailsVO> detailList = new ArrayList<>();

		if (dto.getDeliveryChallanCumGatePassDetailsDTO() != null
				&& !dto.getDeliveryChallanCumGatePassDetailsDTO().isEmpty()) {

			for (DeliveryChallanCumGatePassDetailsDTO detailDTO : dto.getDeliveryChallanCumGatePassDetailsDTO()) {

				DeliveryChallanCumGatePassDetailsVO detailVO = new DeliveryChallanCumGatePassDetailsVO();

				// =================================================
				// ITEM
				// =================================================

				if (detailDTO.getItem() != null) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// =================================================
				// HSN / SAC
				// =================================================

				if (detailDTO.getHsnSacCode() != null) {

					HsnVO hsn = hsnRepo.findById(detailDTO.getHsnSacCode())
							.orElseThrow(() -> new ApplicationException("HSN/SAC Code Not Found"));

					detailVO.setHsnSacCode(hsn);
				}

				// =================================================
				// UNIT
				// =================================================

				if (detailDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				// =================================================
				// QUANTITY / RATE
				// =================================================

				detailVO.setStock(detailDTO.getStock());

				detailVO.setAvailableQty(detailDTO.getAvailableQty());

				detailVO.setQty(detailDTO.getQty());

				detailVO.setDueDate(detailDTO.getDueDate());

				detailVO.setPreviousQty(detailDTO.getPreviousQty());

				detailVO.setLcRate(detailDTO.getLcRate());

				detailVO.setRate(detailDTO.getRate());

				if (detailDTO.getRate() != null && detailDTO.getQty() != null) {
					detailVO.setAmount(detailDTO.getRate().multiply(detailDTO.getQty()));
				}

				// =================================================
				// PARENT
				// =================================================

				detailVO.setDeliveryChallanCumGatePassVO(deliveryChallanVO);

				detailList.add(detailVO);
			}
		}

		// =========================================================
		// CLEAR EXISTING DETAILS
		// =========================================================

		if (deliveryChallanVO.getDeliveryChallanCumGatePassDetailsVO() == null) {

			deliveryChallanVO.setDeliveryChallanCumGatePassDetailsVO(new ArrayList<>());

		} else {

			deliveryChallanVO.getDeliveryChallanCumGatePassDetailsVO().clear();
		}

		// =========================================================
		// ADD DETAILS
		// =========================================================

		deliveryChallanVO.getDeliveryChallanCumGatePassDetailsVO().addAll(detailList);
	}

	private DeliveryChallanCumGatePassResponseDTO convertToResponse(DeliveryChallanCumGatePassVO deliveryChallanVO) {

		DeliveryChallanCumGatePassResponseDTO responseDTO = new DeliveryChallanCumGatePassResponseDTO();

		responseDTO.setId(deliveryChallanVO.getId());
		responseDTO.setDocId(deliveryChallanVO.getDocId());
		responseDTO.setDocDate(deliveryChallanVO.getDocDate());
		responseDTO.setBelongsTo(deliveryChallanVO.getBelongsTo());
		responseDTO.setType(deliveryChallanVO.getType());
		responseDTO.setIGSTAppl(deliveryChallanVO.isIGSTAppl());
		responseDTO.setGstnNo(deliveryChallanVO.getGstnNo());
		responseDTO.setModeOfTransport(deliveryChallanVO.getModeOfTransport());
		responseDTO.setVehicleNo(deliveryChallanVO.getVehicleNo());
		responseDTO.setTotalQty(deliveryChallanVO.getTotalQty());
		responseDTO.setRemarks(deliveryChallanVO.getRemarks());

		responseDTO.setWorkOrderNo(deliveryChallanVO.getWorkOrderNo());

		// Common Fields
		responseDTO.setCreatedBy(deliveryChallanVO.getCreatedBy());

		responseDTO.setActive(deliveryChallanVO.getActive());

		responseDTO.setCancel(deliveryChallanVO.getCancel());

		responseDTO.setUpdatedBy(deliveryChallanVO.getUpdatedBy());

		responseDTO.setCancelRemarks(deliveryChallanVO.getCancelRemarks());

		responseDTO.setScreenName(deliveryChallanVO.getScreenName());

		responseDTO.setScreenCode(deliveryChallanVO.getScreenCode());

		responseDTO.setOrgId(deliveryChallanVO.getOrgId());

		responseDTO.setFinancialYear(deliveryChallanVO.getFinancialYear());

		// Department
		if (deliveryChallanVO.getDepartment() != null) {

			DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();

			departmentResponseDTO.setId(deliveryChallanVO.getDepartment().getId());

			departmentResponseDTO.setDepartmentName(deliveryChallanVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(departmentResponseDTO);
		}

		// Customer / Branch Based On Type
		if (deliveryChallanVO.getType() != null) {

			if ("PARTY".equalsIgnoreCase(deliveryChallanVO.getType())) {

				if (deliveryChallanVO.getCustomer() != null) {

					CustomerDropdownResponseDTO customerResponseDTO = new CustomerDropdownResponseDTO();

					customerResponseDTO.setCustomerId(deliveryChallanVO.getCustomer().getId());

					customerResponseDTO.setCustomerCode(deliveryChallanVO.getCustomer().getCustomerCode());

					customerResponseDTO.setCustomerName(deliveryChallanVO.getCustomer().getCustomerName());

					customerResponseDTO.setAddress(deliveryChallanVO.getCustomer().getAddress());

					if (deliveryChallanVO.getCustomer().getGstState() != null) {

						customerResponseDTO.setGstState(deliveryChallanVO.getCustomer().getGstState().getStateName());
					}

					customerResponseDTO.setGstNo(deliveryChallanVO.getCustomer().getGstNo());

					customerResponseDTO.setIgstApplicable(deliveryChallanVO.getCustomer().isGstApplicable());

					customerResponseDTO.setGstType(deliveryChallanVO.getCustomer().getGstType());

					responseDTO.setCustomer(customerResponseDTO);
				}

			} else if ("BRANCH".equalsIgnoreCase(deliveryChallanVO.getType())) {

				if (deliveryChallanVO.getToBranch() != null) {

					BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

					branchResponseDTO.setId(deliveryChallanVO.getToBranch().getId());

					branchResponseDTO.setBranchName(deliveryChallanVO.getToBranch().getBranchName());

					responseDTO.setToBranch(branchResponseDTO);
				}
			}
		}

		// From Location
		if (deliveryChallanVO.getFromLocation() != null) {

			LocationMasterResponseDTO locationResponseDTO = new LocationMasterResponseDTO();

			locationResponseDTO.setId(deliveryChallanVO.getFromLocation().getId());

			locationResponseDTO.setLocationName(deliveryChallanVO.getFromLocation().getLocationName());

			responseDTO.setFromLocation(locationResponseDTO);
		}

		// Work Order
//	        if (deliveryChallanVO.getWorkOrderNo() != null) {
//
//	            WorkOrderResponseDTO workOrderResponseDTO =
//	                    new WorkOrderResponseDTO();
//
//	            workOrderResponseDTO.setId(
//	                    deliveryChallanVO.getWorkOrderNo().getId());
//
//	            workOrderResponseDTO.setWorkOrderNO(
//	                    deliveryChallanVO.getWorkOrderNo()
//	                            .getDocId());
//
//	            workOrderResponseDTO.setWorkOrderDate(
//	                    deliveryChallanVO.getWorkOrderNo()
//	                            .getDocDate());
//	            responseDTO.setWorkOrderNo(
//	                    workOrderResponseDTO);
//	        }

		// Prepared By
		if (deliveryChallanVO.getPreparedBy() != null) {

			EmployeeMasterResponseDetailsDTO preparedByDTO = new EmployeeMasterResponseDetailsDTO();

			preparedByDTO.setId(deliveryChallanVO.getPreparedBy().getId());

			preparedByDTO.setEmployeeName(deliveryChallanVO.getPreparedBy().getEmployeeName());

			preparedByDTO.setEmployeeCode(deliveryChallanVO.getPreparedBy().getEmployeeId());

			responseDTO.setPreparedBy(preparedByDTO);
		}

		// Branch
		if (deliveryChallanVO.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(deliveryChallanVO.getBranch().getId());

			branchResponseDTO.setBranchName(deliveryChallanVO.getBranch().getBranchName());

			responseDTO.setBranch(branchResponseDTO);
		}

		// Details
		if (deliveryChallanVO.getDeliveryChallanCumGatePassDetailsVO() != null) {

			List<DeliveryChallanCumGatePassDetailsResponseDTO> detailsResponseList = new ArrayList<>();

			for (DeliveryChallanCumGatePassDetailsVO detailVO : deliveryChallanVO
					.getDeliveryChallanCumGatePassDetailsVO()) {

				DeliveryChallanCumGatePassDetailsResponseDTO detailResponseDTO = new DeliveryChallanCumGatePassDetailsResponseDTO();

				detailResponseDTO.setId(detailVO.getId());

				detailResponseDTO.setStock(detailVO.getStock());

				detailResponseDTO.setAvailableQty(detailVO.getAvailableQty());

				detailResponseDTO.setQty(detailVO.getQty());

				detailResponseDTO.setDueDate(detailVO.getDueDate());

				detailResponseDTO.setPreviousQty(detailVO.getPreviousQty());

				detailResponseDTO.setLcRate(detailVO.getLcRate());

				detailResponseDTO.setRate(detailVO.getRate());

				detailResponseDTO.setAmount(detailVO.getAmount());

				// Item
				if (detailVO.getItem() != null) {

					ItemMasterResponseDetailsDTO itemResponseDTO = new ItemMasterResponseDetailsDTO();

					itemResponseDTO.setId(detailVO.getItem().getId());

					itemResponseDTO.setItemCode(detailVO.getItem().getItemCode());

					itemResponseDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponseDTO.setItem(itemResponseDTO);
				}

				if (detailVO.getHsnSacCode() != null) {

					HsnResponseDTO hsnResponseDTO = new HsnResponseDTO();

					hsnResponseDTO.setId(detailVO.getHsnSacCode().getId());

					hsnResponseDTO.setHsn(detailVO.getHsnSacCode().getHsn());

					hsnResponseDTO.setDescription(detailVO.getHsnSacCode().getDescription());

					detailResponseDTO.setHsnSacCode(hsnResponseDTO);
				}

				// Unit
				if (detailVO.getUnit() != null) {

					UnitMasterResponseDTO unitResponseDTO = new UnitMasterResponseDTO();

					unitResponseDTO.setId(detailVO.getUnit().getId());

					unitResponseDTO.setUnitId(detailVO.getUnit().getUnitId());

					unitResponseDTO.setUnitDescription(detailVO.getUnit().getDescription());

					detailResponseDTO.setUnit(unitResponseDTO);
				}

				detailsResponseList.add(detailResponseDTO);
			}

			responseDTO.setDeliveryChallanCumGatePassDetails(detailsResponseList);
		}

		return responseDTO;
	}

	@Override
	public List<Map<String, Object>> getDeliveryChallanCumGatePassDetails(String jobOrderNo, Long branch, Long orgId,
			Long customer) {

		Set<Object[]> result = deliveryChallanCumGatePassRepo.getDeliveryChallanCumGatePassDetails(jobOrderNo, branch,
				orgId, customer);

		return getDeliveryChallanCumGatePassDetailsResponse(result);
	}

	private List<Map<String, Object>> getDeliveryChallanCumGatePassDetailsResponse(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] dc : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("item", dc[0] != null ? Long.valueOf(dc[0].toString()) : null);

			part.put("itemCode", dc[1] != null ? dc[1].toString() : null);

			part.put("itemDescription", dc[2] != null ? dc[2].toString() : null);

			part.put("hsnSacCode", dc[3] != null ? Long.valueOf(dc[3].toString()) : null);

			part.put("unit", dc[4] != null ? Long.valueOf(dc[4].toString()) : null);

			part.put("unitDescription", dc[5] != null ? dc[5].toString() : null);

			part.put("qty", dc[6] != null ? new BigDecimal(dc[6].toString()) : BigDecimal.ZERO);

			part.put("rate", dc[7] != null ? new BigDecimal(dc[7].toString()) : BigDecimal.ZERO);

			details.add(part);
		}

		return details;
	}

	@Override
	public DeliveryChallanCumGatePassResponseDTO getDeliveryChallanCumGatePassById(Long id)
			throws ApplicationException {

		DeliveryChallanCumGatePassVO deliveryChallanVO = deliveryChallanCumGatePassRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Delivery Challan Cum Gate Pass Not Found"));

		return convertToResponse(deliveryChallanVO);
	}

	@Override
	public List<DeliveryChallanCumGatePassResponseDTO> getDeliveryChallanCumGatePassByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException {

		List<DeliveryChallanCumGatePassVO> deliveryChallanList = deliveryChallanCumGatePassRepo
				.findByOrgIdAndBranch(orgId, branch);

		List<DeliveryChallanCumGatePassResponseDTO> responseList = new ArrayList<>();

		for (DeliveryChallanCumGatePassVO vo : deliveryChallanList) {

			responseList.add(convertToResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getDeliveryChallanCumGatePassDocId(Long orgId, String financialYear) {

		String screenCode = "DCCGP";

		String result = deliveryChallanCumGatePassRepo.getDeliveryChallanCumGatePassDocId(orgId, financialYear,
				screenCode);

		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateAdvForStores(AdvForStoresDTO advForStoresDTO) throws ApplicationException {

		String screenCode = "ADV";

		Map<String, Object> response = new HashMap<>();

		String message;

		AdvForStoresVO advForStoresVO;

		// =========================================================
		// CREATE
		// =========================================================
		if (ObjectUtils.isEmpty(advForStoresDTO.getId())) {

			advForStoresVO = new AdvForStoresVO();

			String docId = advForStoresRepo.getAdvForStoresDocId(advForStoresDTO.getOrgId(),
					advForStoresDTO.getFinancialYear(), screenCode);

			System.out.println("ADV orgId : " + advForStoresDTO.getOrgId());
			System.out.println("ADV financialYear: " + advForStoresDTO.getFinancialYear());
			System.out.println("ADV screenCode : " + screenCode);
			System.out.println("Generated ADV DocId: " + docId);
			if (docId == null || docId.isBlank()) {
				throw new ApplicationException("ADV For Stores DocId Generation Failed");

			}

			advForStoresVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(advForStoresDTO.getOrgId(), advForStoresDTO.getFinancialYear(),
							screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			advForStoresVO.setCreatedBy(advForStoresDTO.getCreatedBy());

			advForStoresVO.setUpdatedBy(advForStoresDTO.getCreatedBy());

			message = "ADV For Stores Created Successfully";

		} else {

			// =====================================================
			// UPDATE
			// =====================================================

			advForStoresVO = advForStoresRepo.findById(advForStoresDTO.getId())
					.orElseThrow(() -> new ApplicationException("ADV For Stores Not Found"));

			advForStoresVO.setUpdatedBy(advForStoresDTO.getCreatedBy());

			message = "ADV For Stores Updated Successfully";
		}

		// =========================================================
		// HEADER + CHILD MAPPING
		// =========================================================

		createUpdateAdvForStoresVOByDTO(advForStoresDTO, advForStoresVO);

		// =========================================================
		// SAVE
		// =========================================================

		advForStoresVO = advForStoresRepo.save(advForStoresVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		AdvForStoresResponseDTO responseDTO = buildAdvForStoresResponse(advForStoresVO);

		response.put("message", message);
		response.put("advForStores", responseDTO);

		return response;
	}

	private void createUpdateAdvForStoresVOByDTO(AdvForStoresDTO dto, AdvForStoresVO advForStoresVO)
			throws ApplicationException {

		// =========================================================
		// BASIC FIELDS
		// =========================================================

		advForStoresVO.setBelongsTo(dto.getBelongsTo());
		advForStoresVO.setTime(dto.getTime());

		advForStoresVO.setRemarks(dto.getRemarks());

		advForStoresVO.setOrgId(dto.getOrgId());
		advForStoresVO.setFinancialYear(dto.getFinancialYear());

		advForStoresVO.setActive(dto.isActive());

		advForStoresVO.setCancelRemarks(dto.getCancelRemarks());

		advForStoresVO.setScreenName("ADV FOR STORES");
		advForStoresVO.setScreenCode("ADV");

		// =========================================================
		// BRANCH
		// =========================================================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			advForStoresVO.setBranch(branch);
		}

		// =========================================================
		// CUSTOMER
		// =========================================================

		if (dto.getCustomer() != null && dto.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			advForStoresVO.setCustomer(customer);
		}

		// =========================================================
		// INCOMING PART NO / ITEM
		// =========================================================

		if (dto.getIncomingPartNo() != null && dto.getIncomingPartNo() != 0) {

			ItemMasterVO item = itemMasterRepo.findById(dto.getIncomingPartNo())
					.orElseThrow(() -> new ApplicationException("Incoming Part Not Found"));

			advForStoresVO.setIncomingPartNo(item);
		}

		// =========================================================
		// BOM
		// =========================================================

		if (dto.getBom() != null && dto.getBom() != 0) {

			BillOfMaterialVO bom = billOfMaterialRepo.findById(dto.getBom())
					.orElseThrow(() -> new ApplicationException("BOM Not Found"));

			advForStoresVO.setBom(bom);
		}

		// =========================================================
		// PREPARED BY
		// =========================================================

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

			advForStoresVO.setPreparedBy(employee);
		}

		// =========================================================
		// DELETE OLD CHILDREN DURING UPDATE
		// =========================================================

		if (dto.getId() != null) {

			List<AdvForStoresDetailsVO> oldList = advForStoresDetailsRepo.findByAdvForStoresVO(advForStoresVO);

			if (oldList != null && !oldList.isEmpty()) {
				advForStoresDetailsRepo.deleteAll(oldList);
			}
		}

		// =========================================================
		// CHILD DETAILS
		// =========================================================

		List<AdvForStoresDetailsVO> detailList = new ArrayList<>();

		if (dto.getAdvForStoresDetails() != null && !dto.getAdvForStoresDetails().isEmpty()) {

			for (AdvForStoresDetailsDTO detailDTO : dto.getAdvForStoresDetails()) {

				AdvForStoresDetailsVO detailVO = new AdvForStoresDetailsVO();

				// -------------------------------------------------
				// ITEM
				// -------------------------------------------------

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// -------------------------------------------------
				// UNIT
				// -------------------------------------------------

				if (detailDTO.getUnit() != null && detailDTO.getUnit() != 0) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				// -------------------------------------------------
				// QUANTITY
				// -------------------------------------------------

				detailVO.setBomQty(detailDTO.getBomQty());

				detailVO.setIssueQty(detailDTO.getIssueQty());

				// -------------------------------------------------
				// PARENT MAPPING
				// -------------------------------------------------

				detailVO.setAdvForStoresVO(advForStoresVO);

				detailList.add(detailVO);
			}

			// -----------------------------------------------------
			// SET CHILD LIST
			// -----------------------------------------------------

			advForStoresVO.setAdvForStoresDetailsVO(detailList);
		}
	}

	private AdvForStoresResponseDTO buildAdvForStoresResponse(AdvForStoresVO vo) {

		AdvForStoresResponseDTO response = new AdvForStoresResponseDTO();

		// =========================================================
		// HEADER
		// =========================================================

		response.setId(vo.getId());

		response.setDocId(vo.getDocId());

		response.setDocDate(vo.getDocDate());

		response.setBelongsTo(vo.getBelongsTo());

		response.setTime(vo.getTime());

		response.setRemarks(vo.getRemarks());

		response.setCreatedBy(vo.getCreatedBy());

		response.setUpdatedBy(vo.getUpdatedBy());

		response.setCancelRemarks(vo.getCancelRemarks());

		response.setActive(vo.isActive());

		response.setScreenName(vo.getScreenName());

		response.setScreenCode(vo.getScreenCode());

		response.setOrgId(vo.getOrgId());

		response.setFinancialYear(vo.getFinancialYear());

		// =========================================================
		// BRANCH
		// =========================================================

		if (vo.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(vo.getBranch().getId());

			branch.setBranchCode(vo.getBranch().getBranchCode());

			branch.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branch);
		}

		// =========================================================
		// CUSTOMER
		// =========================================================

		if (vo.getCustomer() != null) {

			CustomerDropdownResponseDTO customer = new CustomerDropdownResponseDTO();

			customer.setCustomerId(vo.getCustomer().getId());

			customer.setCustomerCode(vo.getCustomer().getCustomerCode());

			customer.setCustomerName(vo.getCustomer().getCustomerName());

			customer.setAddress(vo.getCustomer().getAddress());
			customer.setGstNo(vo.getCustomer().getGstNo());
//	            customer.setGstState(vo.getCustomer().getGstState());

			response.setCustomer(customer);
		}

		// =========================================================
		// INCOMING PART NO
		// =========================================================

		if (vo.getIncomingPartNo() != null) {

			ItemResponseDTO item = new ItemResponseDTO();

			item.setId(vo.getIncomingPartNo().getId());

			item.setItemCode(vo.getIncomingPartNo().getItemCode());

			item.setItemDescription(vo.getIncomingPartNo().getItemDescription());

			response.setIncomingPartNo(item);
		}

		// =========================================================
		// BOM
		// =========================================================

		if (vo.getBom() != null) {

			BillOfMaterialDropdownResponseDTO bom = new BillOfMaterialDropdownResponseDTO();

			bom.setId(vo.getBom().getId());
			bom.setDocId(vo.getBom().getDocId());
			bom.setDocDate(vo.getBom().getDocDate());

			response.setBom(bom);
		}

		// =========================================================
		// PREPARED BY
		// =========================================================

		if (vo.getPreparedBy() != null) {

			EmployeeResponseDTO employee = new EmployeeResponseDTO();

			employee.setId(vo.getPreparedBy().getId());
			employee.setEmployeeName(vo.getPreparedBy().getEmployeeName());

			response.setPreparedBy(employee);
		}

		// =========================================================
		// CHILD DETAILS
		// =========================================================

		List<AdvForStoresDetailsResponseDTO> detailsResponse = new ArrayList<>();

		if (vo.getAdvForStoresDetailsVO() != null) {

			for (AdvForStoresDetailsVO detailVO : vo.getAdvForStoresDetailsVO()) {

				AdvForStoresDetailsResponseDTO detailResponse = new AdvForStoresDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());

				detailResponse.setBomQty(detailVO.getBomQty());

				detailResponse.setIssueQty(detailVO.getIssueQty());

				// =================================================
				// ITEM
				// =================================================

				if (detailVO.getItem() != null) {

					ItemResponseDTO item = new ItemResponseDTO();

					item.setId(detailVO.getItem().getId());

					item.setItemCode(detailVO.getItem().getItemCode());

					item.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponse.setItem(item);
				}

				// =================================================
				// UNIT
				// =================================================

				if (detailVO.getUnit() != null) {

					UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

					unit.setId(detailVO.getUnit().getId());

					unit.setUnitId(detailVO.getUnit().getUnitId());

					unit.setUnitDescription(detailVO.getUnit().getDescription());

					detailResponse.setUnit(unit);
				}

				detailsResponse.add(detailResponse);
			}
		}

		response.setAdvForStoresDetails(detailsResponse);

		return response;
	}

	@Override
	public List<Map<String, Object>> getLatestBomDropdown(Long itemId, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> result = billOfMaterialRepo.getLatestBomDropdown(itemId, orgId, branch);

		if (result == null || result.isEmpty()) {
			throw new ApplicationException("BOM Not Found");
		}

		List<Map<String, Object>> bomDetails = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> bomMap = new HashMap<>();

			bomMap.put("id", obj[0]);
			bomMap.put("docId", obj[1]);
			bomMap.put("docDate", obj[2]);

			bomDetails.add(bomMap);
		}

		return bomDetails;
	}

	@Override
	public List<Map<String, Object>> getBomDetailsByDocId(String docId, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> result = billOfMaterialRepo.getBomDetailsByDocId(docId, orgId, branch);

		if (result == null || result.isEmpty()) {
			throw new ApplicationException("BOM Details Not Found");
		}

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> map = new HashMap<>();

			map.put("bomDetailsId", fs[0]);

			map.put("itemId", fs[1]);
			map.put("itemCode", fs[2]);
			map.put("itemDescription", fs[3]);

			map.put("itemType", fs[4]);
			map.put("manbou", fs[5]);
			map.put("qty", fs[6]);

			map.put("unitId", fs[7]);
			map.put("unitCode", fs[8]);
			map.put("unitDescription", fs[9]);

			map.put("bomId", fs[10]);

			details.add(map);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getFGAndSFGItems(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> result = itemMasterRepo.getFGAndSFGItems(orgId, branch);

		if (result == null || result.isEmpty()) {
			throw new ApplicationException("FG / SFG Items Not Found");
		}

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemCode", fs[0]);
			map.put("itemDescription", fs[1]);
			map.put("itemId", fs[2]);
			map.put("unitmasterId", fs[3]);
			map.put("unitId", fs[4]);
			map.put("unitDescription", fs[5]);

			details.add(map);
		}

		return details;
	}

	@Override
	public AdvForStoresResponseDTO getAdvForStoresById(Long id) throws ApplicationException {

		AdvForStoresVO advForStoresVO = advForStoresRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("ADV For Stores Not Found"));

		return buildAdvForStoresResponse(advForStoresVO);
	}

	@Override
	public List<AdvForStoresResponseDTO> getAdvForStoresByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException {

		List<AdvForStoresVO> advForStoresList = advForStoresRepo.findByOrgIdAndBranch(orgId, branch);

		List<AdvForStoresResponseDTO> responseList = new ArrayList<>();

		for (AdvForStoresVO vo : advForStoresList) {

			responseList.add(buildAdvForStoresResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getAdvForStoresDocId(Long orgId, String financialYear) {

		String screenCode = "ADV";

		String result = advForStoresRepo.getAdvForStoresDocId(orgId, financialYear, screenCode);

		return result;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateJobOrderShortClose(JobOrderShortCloseDTO dto) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		String screenCode = "JOSC";

		JobOrderShortCloseVO jobOrderShortCloseVO;

		String message;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			jobOrderShortCloseVO = new JobOrderShortCloseVO();

			// =====================================================
			// GENERATE DOCUMENT ID
			// =====================================================

			String docId = jobOrderShortCloseRepo.getJobOrderShortCloseDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);

			if (docId == null || docId.isBlank()) {

				throw new ApplicationException("Job Order Short Close DocId Generation Failed");
			}

			jobOrderShortCloseVO.setDocId(docId);

			// =====================================================
			// UPDATE DOCUMENT LAST NUMBER
			// =====================================================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			// =====================================================
			// CREATED BY
			// =====================================================

			jobOrderShortCloseVO.setCreatedBy(dto.getCreatedBy());

			jobOrderShortCloseVO.setUpdatedBy(dto.getCreatedBy());

			message = "Job Order Short Close Created Successfully";
		}

		// =========================================================
		// UPDATE
		// =========================================================

		else {

			jobOrderShortCloseVO = jobOrderShortCloseRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Job Order Short Close Not Found"));

			// =====================================================
			// DELETE OLD DETAILS
			// =====================================================

			if (dto.getId() != null) {

				List<JobOrderShortCloseDetailsVO> oldList = jobOrderShortCloseDetailsRepo
						.findByJobOrderShortCloseVO(jobOrderShortCloseVO);

				jobOrderShortCloseDetailsRepo.deleteAll(oldList);
			}

			// =====================================================
			// UPDATED BY
			// =====================================================

			jobOrderShortCloseVO.setUpdatedBy(dto.getCreatedBy());

			message = "Job Order Short Close Updated Successfully";
		}

		// =========================================================
		// DTO TO VO
		// =========================================================

		getJobOrderShortCloseVOFromDTO(dto, jobOrderShortCloseVO);

		// =========================================================
		// SAVE
		// =========================================================

		jobOrderShortCloseVO = jobOrderShortCloseRepo.save(jobOrderShortCloseVO);

		// =========================================================
		// CONVERT RESPONSE
		// =========================================================

		JobOrderShortCloseResponseDTO jobOrderShortCloseResponseDTO = convertToResponse(jobOrderShortCloseVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		response.put("message", message);

		response.put("jobOrderShortCloseVO", jobOrderShortCloseResponseDTO);

		return response;
	}

	private void getJobOrderShortCloseVOFromDTO(JobOrderShortCloseDTO dto, JobOrderShortCloseVO jobOrderShortCloseVO)
			throws ApplicationException {

		// =========================================================
		// CUSTOMER
		// =========================================================

		if (dto.getCustomer() != null) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			jobOrderShortCloseVO.setCustomer(customer);
		}

		// =========================================================
		// BRANCH
		// =========================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			jobOrderShortCloseVO.setBranch(branch);
		}

		jobOrderShortCloseVO.setJobOrderNo(dto.getJobOrderNo());

		jobOrderShortCloseVO.setReferenceForSc(dto.getReferenceForSc());

		// =========================================================
		// COMMON FIELDS
		// =========================================================

		jobOrderShortCloseVO.setOrgId(dto.getOrgId());

		jobOrderShortCloseVO.setFinancialYear(dto.getFinancialYear());

		jobOrderShortCloseVO.setActive(dto.isActive());

		jobOrderShortCloseVO.setCancelRemarks(dto.getCancelRemarks());
		// =========================================================
		// DETAILS
		// =========================================================

		List<JobOrderShortCloseDetailsVO> detailList = new ArrayList<>();

		if (dto.getJobOrderShortCloseDetails() != null && !dto.getJobOrderShortCloseDetails().isEmpty()) {

			for (JobOrderShortCloseDetailsDTO detailDTO : dto.getJobOrderShortCloseDetails()) {

				JobOrderShortCloseDetailsVO detailVO = new JobOrderShortCloseDetailsVO();

				// =================================================
				// ITEM
				// =================================================

				if (detailDTO.getItem() != null) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// =================================================
				// QUANTITIES
				// =================================================

				detailVO.setOrderQty(detailDTO.getOrderQty());

				detailVO.setSuppliedQty(detailDTO.getSuppliedQty());

				BigDecimal pendingQty = BigDecimal.ZERO;

				if (detailDTO.getOrderQty() != null) {

					BigDecimal suppliedQty = detailDTO.getSuppliedQty() != null ? detailDTO.getSuppliedQty()
							: BigDecimal.ZERO;

					pendingQty = detailDTO.getOrderQty().subtract(suppliedQty);
				}

				detailVO.setPendingQty(pendingQty);

				detailVO.setRequiredQty(detailDTO.getRequiredQty());

				BigDecimal shortCloseQty = BigDecimal.ZERO;

				if (pendingQty != null) {

					BigDecimal requiredQty = detailDTO.getRequiredQty() != null ? detailDTO.getRequiredQty()
							: BigDecimal.ZERO;

					shortCloseQty = pendingQty.subtract(requiredQty);
				}

				detailVO.setShortCloseQty(shortCloseQty);

				// =================================================
				// PARENT
				// =================================================

				detailVO.setJobOrderShortCloseVO(jobOrderShortCloseVO);

				detailList.add(detailVO);
			}
		}

		jobOrderShortCloseVO.setJobOrderShortCloseDetailsVO(detailList);
	}

	private JobOrderShortCloseResponseDTO convertToResponse(JobOrderShortCloseVO jobOrderShortCloseVO) {

		JobOrderShortCloseResponseDTO responseDTO = new JobOrderShortCloseResponseDTO();

		// =========================================================
		// HEADER
		// =========================================================

		responseDTO.setId(jobOrderShortCloseVO.getId());

		responseDTO.setDocId(jobOrderShortCloseVO.getDocId());

		responseDTO.setDocDate(jobOrderShortCloseVO.getDocDate());

		responseDTO.setJobOrderNo(jobOrderShortCloseVO.getJobOrderNo());

		responseDTO.setReferenceForSc(jobOrderShortCloseVO.getReferenceForSc());

		// =========================================================
		// COMMON FIELDS
		// =========================================================

		responseDTO.setCreatedBy(jobOrderShortCloseVO.getCreatedBy());

		responseDTO.setActive(jobOrderShortCloseVO.isActive());

		responseDTO.setCancel(jobOrderShortCloseVO.isCancel());

		responseDTO.setUpdatedBy(jobOrderShortCloseVO.getUpdatedBy());

		responseDTO.setCancelRemarks(jobOrderShortCloseVO.getCancelRemarks());

		responseDTO.setScreenName(jobOrderShortCloseVO.getScreenName());

		responseDTO.setScreenCode(jobOrderShortCloseVO.getScreenCode());

		responseDTO.setOrgId(jobOrderShortCloseVO.getOrgId());

		responseDTO.setFinancialYear(jobOrderShortCloseVO.getFinancialYear());

		// =========================================================
		// CUSTOMER
		// =========================================================

		if (jobOrderShortCloseVO.getCustomer() != null) {

			JOShortCloseCustomerResponseDTO customerResponseDTO = new JOShortCloseCustomerResponseDTO();

			customerResponseDTO.setCustomerId(jobOrderShortCloseVO.getCustomer().getId());

			customerResponseDTO.setCustomerCode(jobOrderShortCloseVO.getCustomer().getCustomerCode());

			customerResponseDTO.setCustomerName(jobOrderShortCloseVO.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerResponseDTO);
		}

		// =========================================================
		// BRANCH
		// =========================================================

		if (jobOrderShortCloseVO.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(jobOrderShortCloseVO.getBranch().getId());

			branchResponseDTO.setBranchName(jobOrderShortCloseVO.getBranch().getBranchName());

			branchResponseDTO.setBranchCode(jobOrderShortCloseVO.getBranch().getBranchCode());

			responseDTO.setBranch(branchResponseDTO);
		}

		// =========================================================
		// DETAILS
		// =========================================================

		if (jobOrderShortCloseVO.getJobOrderShortCloseDetailsVO() != null) {

			List<JobOrderShortCloseDetailsResponseDTO> detailsResponseList = new ArrayList<>();

			for (JobOrderShortCloseDetailsVO detailVO : jobOrderShortCloseVO.getJobOrderShortCloseDetailsVO()) {

				JobOrderShortCloseDetailsResponseDTO detailResponseDTO = new JobOrderShortCloseDetailsResponseDTO();

				detailResponseDTO.setId(detailVO.getId());

				detailResponseDTO.setOrderQty(detailVO.getOrderQty());

				detailResponseDTO.setSuppliedQty(detailVO.getSuppliedQty());

				detailResponseDTO.setPendingQty(detailVO.getPendingQty());

				detailResponseDTO.setRequiredQty(detailVO.getRequiredQty());

				detailResponseDTO.setShortCloseQty(detailVO.getShortCloseQty());

				// =================================================
				// ITEM
				// =================================================

				if (detailVO.getItem() != null) {

					JOShortCloseItemResponseDTO itemResponseDTO = new JOShortCloseItemResponseDTO();

					itemResponseDTO.setId(detailVO.getItem().getId());

					itemResponseDTO.setItemCode(detailVO.getItem().getItemCode());

					itemResponseDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponseDTO.setItem(itemResponseDTO);
				}

				detailsResponseList.add(detailResponseDTO);
			}

			responseDTO.setJobOrderShortCloseDetails(detailsResponseList);
		}

		return responseDTO;
	}

	@Override
	public List<Map<String, Object>> getTotalSuppliedQtyforJobOrderClose(Long orgId, Long branch, String jobOrderNo,
			Long item) {

		BigDecimal result = deliveryChallanSubcontractingDetailsRepo.getTotalSuppliedQtyforJobOrderClose(orgId, branch,
				jobOrderNo, item);

		List<Map<String, Object>> details = new ArrayList<>();

		Map<String, Object> part = new HashMap<>();

		part.put("issueQty", result != null ? result : BigDecimal.ZERO);

		details.add(part);

		return details;
	}

	@Override
	public List<JobOrderShortCloseResponseDTO> getJobOrderShortCloseByOrgIdAndBranch(Long orgId, Long branch)
			throws ApplicationException {

		List<JobOrderShortCloseVO> jobOrderShortCloseList = jobOrderShortCloseRepo.findByOrgIdAndBranch(orgId, branch);

		List<JobOrderShortCloseResponseDTO> responseList = new ArrayList<>();

		for (JobOrderShortCloseVO vo : jobOrderShortCloseList) {

			responseList.add(convertToResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getJobOrderShortCloseDocId(Long orgId, String financialYear) {

		String screenCode = "JOSC";

		String result = jobOrderShortCloseRepo.getJobOrderShortCloseDocId(orgId, financialYear, screenCode);

		return result;
	}

	@Override
	public JobOrderShortCloseResponseDTO getJobOrderShortCloseById(Long id) throws ApplicationException {

		JobOrderShortCloseVO jobOrderShortCloseVO = jobOrderShortCloseRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Job Order Short Close Not Found"));

		return convertToResponse(jobOrderShortCloseVO);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateDeliveryChallanCapitalItems(DeliveryChallanCapitalItemsDTO dto)
			throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		String screenCode = "DCCI";

		DeliveryChallanCapitalItemsVO deliveryChallanVO;

		String message;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			deliveryChallanVO = new DeliveryChallanCapitalItemsVO();

			// =====================================================
			// GENERATE DOCUMENT ID
			// =====================================================

			String docId = deliveryChallanCapitalItemsRepo.getDeliveryChallanCapitalItemsDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			if (docId == null || docId.isBlank()) {

				throw new ApplicationException("Delivery Challan Capital Items DocId Generation Failed");
			}

			deliveryChallanVO.setDocId(docId);

			// =====================================================
			// UPDATE DOCUMENT LAST NUMBER
			// =====================================================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			// =====================================================
			// CREATED BY
			// =====================================================

			deliveryChallanVO.setCreatedBy(dto.getCreatedBy());

			deliveryChallanVO.setUpdatedBy(dto.getCreatedBy());

			message = "Delivery Challan Capital Items Created Successfully";

		} else {

			// =========================================================
			// UPDATE
			// =========================================================

			deliveryChallanVO = deliveryChallanCapitalItemsRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Delivery Challan Capital Items Not Found"));

			// =====================================================
			// DELETE OLD DETAILS
			// =====================================================

			List<DeliveryChallanCapitalItemsDetailsVO> oldDetails = deliveryChallanCapitalItemsDetailsRepo
					.findByDeliveryChallanCapitalItemsVO(deliveryChallanVO);

			if (oldDetails != null && !oldDetails.isEmpty()) {

				deliveryChallanCapitalItemsDetailsRepo.deleteAll(oldDetails);
			}

			deliveryChallanVO.setUpdatedBy(dto.getCreatedBy());

			message = "Delivery Challan Capital Items Updated Successfully";
		}

		// =========================================================
		// DTO TO VO
		// =========================================================

		getDeliveryChallanCapitalItemsVOFromDTO(dto, deliveryChallanVO);

		// =========================================================
		// SAVE
		// =========================================================

		deliveryChallanVO = deliveryChallanCapitalItemsRepo.saveAndFlush(deliveryChallanVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		DeliveryChallanCapitalItemsResponseDTO responseDTO = convertToResponse(deliveryChallanVO);

		response.put("message", message);

		response.put("deliveryChallanCapitalItemsVO", responseDTO);

		return response;
	}

	private void getDeliveryChallanCapitalItemsVOFromDTO(DeliveryChallanCapitalItemsDTO dto,
			DeliveryChallanCapitalItemsVO vo) throws ApplicationException {

		// BRANCH

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// DEPARTMENT

		if (dto.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(department);
		}

		// VENDOR

		if (dto.getVendor() != null) {

			CustomerVO vendor = customerRepo.findById(dto.getVendor())
					.orElseThrow(() -> new ApplicationException("Vendor Not Found"));

			vo.setVendor(vendor);
		}

		// CUSTOMER LOCATION

		if (dto.getCustomerLocation() != null) {

			LocationVO customerLocation = locationRepo.findById(dto.getCustomerLocation())
					.orElseThrow(() -> new ApplicationException("Customer Location Not Found"));

			vo.setCustomerLocation(customerLocation);
		}

		// HEADER

		vo.setBelongsTo(dto.getBelongsTo());

		vo.setIndentNo(dto.getIndentNo());

		vo.setTransportName(dto.getTransportName());

		vo.setVehicleNo(dto.getVehicleNo());

		vo.setDcType(dto.getDcType());

		vo.setApprovalByStores(dto.getApprovalByStores());

		vo.setRemarks(dto.getRemarks());

		vo.setActive(dto.isActive());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.setOrgId(dto.getOrgId());

		vo.setFinancialYear(dto.getFinancialYear());

		// PREPARED BY

		if (dto.getPreparedBy() != null) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));

			vo.setPreparedBy(preparedBy);
		}

		// APPROVED BY

		if (dto.getApprovedBy() != null) {

			EmployeeMasterVO approvedBy = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Employee Not Found"));

			vo.setApprovedBy(approvedBy);
		}

		// DETAILS

		List<DeliveryChallanCapitalItemsDetailsVO> detailList = new ArrayList<>();

		if (dto.getDetails() != null) {

			for (DeliveryChallanCapitalItemsDetailsDTO detailsDTO : dto.getDetails()) {

				DeliveryChallanCapitalItemsDetailsVO detailsVO = new DeliveryChallanCapitalItemsDetailsVO();

				// OUTGOING ITEM

				if (detailsDTO.getOutgoingItem() != null) {

					ItemMasterVO outgoingItem = itemMasterRepo.findById(detailsDTO.getOutgoingItem())
							.orElseThrow(() -> new ApplicationException("Outgoing Item Not Found"));

					detailsVO.setOutgoingItem(outgoingItem);
				}

				// UNIT

				if (detailsDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(detailsDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailsVO.setUnit(unit);
				}

				// FROM LOCATION

				if (detailsDTO.getFromLocation() != null) {

					LocationVO fromLocation = locationRepo.findById(detailsDTO.getFromLocation())
							.orElseThrow(() -> new ApplicationException("From Location Not Found"));

					detailsVO.setFromLocation(fromLocation);
				}

				detailsVO.setStock(detailsDTO.getStock());

				detailsVO.setAvailableStock(detailsDTO.getAvailableStock());

				detailsVO.setIssueQty(detailsDTO.getIssueQty());

				detailsVO.setUnitRate(detailsDTO.getUnitRate());

				BigDecimal amount = BigDecimal.ZERO;

				if (detailsDTO.getIssueQty() != null && detailsDTO.getUnitRate() != null) {

					amount = detailsDTO.getIssueQty().multiply(detailsDTO.getUnitRate()).setScale(2,
							RoundingMode.HALF_UP);
				}

				detailsVO.setAmount(amount);

				detailsVO.setRemarks(detailsDTO.getRemarks());

				// PARENT

				detailsVO.setDeliveryChallanCapitalItemsVO(vo);

				detailList.add(detailsVO);
			}
		}

		vo.getDetails().addAll(detailList);
	}

	private DeliveryChallanCapitalItemsResponseDTO convertToResponse(DeliveryChallanCapitalItemsVO vo) {

		DeliveryChallanCapitalItemsResponseDTO response = new DeliveryChallanCapitalItemsResponseDTO();

		response.setId(vo.getId());
		response.setDocId(vo.getDocId());
		response.setDocDate(vo.getDocDate());
		response.setBelongsTo(vo.getBelongsTo());
		response.setIndentNo(vo.getIndentNo());
		response.setTransportName(vo.getTransportName());
		response.setVehicleNo(vo.getVehicleNo());
		response.setDcType(vo.getDcType());
		response.setApprovalByStores(vo.getApprovalByStores());
		response.setRemarks(vo.getRemarks());
		response.setCreatedBy(vo.getCreatedBy());
		response.setActive(vo.isActive());
		response.setCancel(vo.isCancel());
		response.setUpdatedBy(vo.getUpdatedBy());
		response.setCancelRemarks(vo.getCancelRemarks());
		response.setScreenName(vo.getScreenName());
		response.setScreenCode(vo.getScreenCode());
		response.setOrgId(vo.getOrgId());
		response.setFinancialYear(vo.getFinancialYear());

		// Branch
		if (vo.getBranch() != null) {
			BranchResponseDTO branchResponse = new BranchResponseDTO();

			branchResponse.setId(vo.getBranch().getId());
			branchResponse.setBranchCode(vo.getBranch().getBranchCode());
			branchResponse.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branchResponse);
		}

		// Department
		if (vo.getDepartment() != null) {
			DepartmentResponseDTO departmentResponse = new DepartmentResponseDTO();

			departmentResponse.setId(vo.getDepartment().getId());
			departmentResponse.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			departmentResponse.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(departmentResponse);
		}

		// Vendor
		if (vo.getVendor() != null) {
			CustomerResponseDetailsDTO vendorResponse = new CustomerResponseDetailsDTO();

			vendorResponse.setId(vo.getVendor().getId());
			vendorResponse.setCustomerName(vo.getVendor().getCustomerName());
			vendorResponse.setCustomerCode(vo.getVendor().getCustomerCode());

			response.setVendor(vendorResponse);
		}

		// Customer Location
		if (vo.getCustomerLocation() != null) {
			LocationMasterResponseDTO locationResponse = new LocationMasterResponseDTO();

			locationResponse.setId(vo.getCustomerLocation().getId());
			locationResponse.setLocationName(vo.getCustomerLocation().getLocationName());

			response.setCustomerLocation(locationResponse);
		}

		// Prepared By
		if (vo.getPreparedBy() != null) {
			EmployeeDropdownResponseDTO preparedByResponse = new EmployeeDropdownResponseDTO();

			preparedByResponse.setEmployeeId(vo.getPreparedBy().getId());
			preparedByResponse.setEmployeeCode(vo.getPreparedBy().getEmployeeId());
			preparedByResponse.setEmployeeName(vo.getPreparedBy().getEmployeeName());
			preparedByResponse.setEmail(vo.getPreparedBy().getEmail());

			response.setPreparedBy(preparedByResponse);
		}

		// Approved By
		if (vo.getApprovedBy() != null) {
			EmployeeDropdownResponseDTO approvedByResponse = new EmployeeDropdownResponseDTO();

			approvedByResponse.setEmployeeId(vo.getApprovedBy().getId());
			approvedByResponse.setEmployeeCode(vo.getApprovedBy().getEmployeeId());
			approvedByResponse.setEmployeeName(vo.getApprovedBy().getEmployeeName());
			approvedByResponse.setEmail(vo.getApprovedBy().getEmail());

			response.setApprovedBy(approvedByResponse);
		}

		// Details
		List<DeliveryChallanCapitalItemsDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (vo.getDetails() != null) {

			for (DeliveryChallanCapitalItemsDetailsVO detailsVO : vo.getDetails()) {

				DeliveryChallanCapitalItemsDetailsResponseDTO detailsResponse = new DeliveryChallanCapitalItemsDetailsResponseDTO();

				detailsResponse.setId(detailsVO.getId());
				detailsResponse.setStock(detailsVO.getStock());
				detailsResponse.setAvailableStock(detailsVO.getAvailableStock());
				detailsResponse.setIssueQty(detailsVO.getIssueQty());
				detailsResponse.setUnitRate(detailsVO.getUnitRate());
				detailsResponse.setAmount(detailsVO.getAmount());
				detailsResponse.setRemarks(detailsVO.getRemarks());

				// Outgoing Item
				if (detailsVO.getOutgoingItem() != null) {

					ItemResponse1DTO itemResponse = new ItemResponse1DTO();

					itemResponse.setId(detailsVO.getOutgoingItem().getId());

					itemResponse.setItemCode(detailsVO.getOutgoingItem().getItemCode());

					itemResponse.setItemDescription(detailsVO.getOutgoingItem().getItemDescription());

					// Item Unit
					if (detailsVO.getOutgoingItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO itemUnitResponse = new UnitMasterResponseDTO();

						itemUnitResponse.setId(detailsVO.getOutgoingItem().getPrimaryUnit().getId());

						itemUnitResponse.setUnitId(detailsVO.getOutgoingItem().getPrimaryUnit().getUnitId());

						itemUnitResponse
								.setUnitDescription(detailsVO.getOutgoingItem().getPrimaryUnit().getDescription());

						itemResponse.setUnit(itemUnitResponse);
					}

					detailsResponse.setOutgoingItem(itemResponse);
				}

				// Unit
				if (detailsVO.getUnit() != null) {

					UnitMasterResponseDTO unitResponse = new UnitMasterResponseDTO();

					unitResponse.setId(detailsVO.getUnit().getId());
					unitResponse.setUnitId(detailsVO.getUnit().getUnitId());
					unitResponse.setUnitDescription(detailsVO.getUnit().getDescription());

					detailsResponse.setUnit(unitResponse);
				}

				// From Location
				if (detailsVO.getFromLocation() != null) {

					LocationMasterResponseDTO locationResponse = new LocationMasterResponseDTO();

					locationResponse.setId(detailsVO.getFromLocation().getId());

					locationResponse.setLocationName(detailsVO.getFromLocation().getLocationName());

					detailsResponse.setFromLocation(locationResponse);
				}

				detailResponseList.add(detailsResponse);
			}
		}

		response.setDetails(detailResponseList);

		return response;
	}

	@Override
	public DeliveryChallanCapitalItemsResponseDTO getDeliveryChallanCapitalItemsById(Long id)
			throws ApplicationException {

		DeliveryChallanCapitalItemsVO deliveryChallanVO = deliveryChallanCapitalItemsRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Delivery Challan Capital Items Not Found"));

		return convertToResponse(deliveryChallanVO);
	}

	@Override
	public List<DeliveryChallanCapitalItemsResponseDTO> getDeliveryChallanCapitalItemsByOrgIdAndBranch(Long orgId,
			Long branch) throws ApplicationException {

		List<DeliveryChallanCapitalItemsVO> deliveryChallanList = deliveryChallanCapitalItemsRepo
				.findByOrgIdAndBranch(orgId, branch);

		List<DeliveryChallanCapitalItemsResponseDTO> responseList = new ArrayList<>();

		for (DeliveryChallanCapitalItemsVO vo : deliveryChallanList) {

			responseList.add(convertToResponse(vo));
		}

		return responseList;
	}

	@Override
	public String getDeliveryChallanCapitalItemsDocId(Long orgId, String financialYear) {

		String screenCode = "DCCI";

		String result = deliveryChallanCapitalItemsRepo.getDeliveryChallanCapitalItemsDocId(orgId, financialYear,
				screenCode);

		return result;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateSubContractingGRN(SubContractingGRNDTO dto) throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		String screenCode = "SCGRN";

		SubContractingGRNVO subContractingGRNVO;

		String message;

		// =========================================================
		// CREATE
		// =========================================================

		if (ObjectUtils.isEmpty(dto.getId())) {

			subContractingGRNVO = new SubContractingGRNVO();

			// =====================================================
			// GENERATE DOCUMENT ID
			// =====================================================

//			String docId = subContractingGRNRepo.getSubContractingGRNDocId(dto.getOrgId(), dto.getFinancialYear(),
//					screenCode);
//
//			if (docId == null || docId.isBlank()) {
//
//				throw new ApplicationException("Sub Contracting GRN DocId Generation Failed");
//			}
//
//			subContractingGRNVO.setDocId(docId);

			// =====================================================
			// UPDATE DOCUMENT LAST NUMBER
			// =====================================================

//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
//
//			if (documentTypeMappingDetailsVO != null) {
//
//				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
//
//				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//			}

			// =====================================================
			// CREATED BY
			// =====================================================

			subContractingGRNVO.setCreatedBy(dto.getCreatedBy());

			subContractingGRNVO.setUpdatedBy(dto.getCreatedBy());

			message = "Sub Contracting GRN Created Successfully";

		} else {

			// =========================================================
			// UPDATE
			// =========================================================

			subContractingGRNVO = subContractingGRNRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Sub Contracting GRN Not Found"));

			// =========================================================
			// GET OLD DETAILS
			// =========================================================

			List<SubContractingGRNDetailsVO> oldDetails = subContractingGRNDetailsRepo
					.findBySubContractingGRNVO(subContractingGRNVO);

			// =========================================================
			// DELETE OLD CONSUMPTION
			// =========================================================

			if (oldDetails != null && !oldDetails.isEmpty()) {

				for (SubContractingGRNDetailsVO oldDetail : oldDetails) {

					subContractingGRNConsumptionRepo.deleteByDetailsId(oldDetail.getId());
				}

				subContractingGRNConsumptionRepo.flush();
			}

			// =========================================================
			// DELETE OLD DETAILS
			// =========================================================

			if (oldDetails != null && !oldDetails.isEmpty()) {

				subContractingGRNDetailsRepo.deleteAll(oldDetails);

				subContractingGRNDetailsRepo.flush();
			}

			// =========================================================
			// DELETE OLD TAX DETAILS
			// =========================================================

			List<SubContractingGRNTaxDetailsVO> oldTaxDetails = subContractingGRNTaxDetailsRepo
					.findBySubContractingGRNVO(subContractingGRNVO);

			if (oldTaxDetails != null && !oldTaxDetails.isEmpty()) {

				subContractingGRNTaxDetailsRepo.deleteAll(oldTaxDetails);

				subContractingGRNTaxDetailsRepo.flush();
			}

			// =========================================================
			// CLEAR OLD JAVA COLLECTIONS
			// =========================================================

			subContractingGRNVO.getDetails().clear();
			subContractingGRNVO.getTaxDetails().clear();

			// =========================================================
			// UPDATE USER
			// =========================================================

			subContractingGRNVO.setUpdatedBy(dto.getCreatedBy());

			message = "Sub Contracting GRN Updated Successfully";
		}
		// =========================================================
		// DTO TO VO
		// =========================================================

		getSubContractingGRNVOFromDTO(dto, subContractingGRNVO);

		// =========================================================
		// SAVE
		// =========================================================

		subContractingGRNVO = subContractingGRNRepo.save(subContractingGRNVO);

		// =========================================================
		// RESPONSE
		// =========================================================

		SubContractingGRNResponseDTO responseDTO = convertToResponse(subContractingGRNVO);

		response.put("message", message);

		response.put("subContractingGRNVO", responseDTO);

		return response;
	}

	private void getSubContractingGRNVOFromDTO(SubContractingGRNDTO dto, SubContractingGRNVO vo)
			throws ApplicationException {

		// =========================================================
		// BRANCH
		// =========================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// =========================================================
		// DEPARTMENT
		// =========================================================

		if (dto.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(department);
		}

		// =========================================================
		// VENDOR
		// =========================================================

		if (dto.getVendor() != null) {

			CustomerVO vendor = customerRepo.findById(dto.getVendor())
					.orElseThrow(() -> new ApplicationException("Vendor Not Found"));

			vo.setVendor(vendor);
		}

		// =========================================================
		// VENDOR LOCATION
		// =========================================================

		if (dto.getVendorLocation() != null) {

			LocationVO vendorLocation = locationRepo.findById(dto.getVendorLocation())
					.orElseThrow(() -> new ApplicationException("Vendor Location Not Found"));

			vo.setVendorLocation(vendorLocation);
		}

		// =========================================================
		// HEADER
		// =========================================================

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setGstState(dto.getGstState());
		vo.setGatePassNo(dto.getGatePassNo());
		vo.setIsIGSTAppl(dto.getIsIGSTAppl());
		vo.setScheduleNo(dto.getScheduleNo());
		vo.setGstnNo(dto.getGstnNo());
		vo.setRework(dto.getRework());
		vo.setGstType(dto.getGstType());
		vo.setRevsChrg(dto.isRevsChrg());

		vo.setSchStartDate(dto.getSchStartDate());
		vo.setSchEndDate(dto.getSchEndDate());
		if (dto.getServiceName() != null) {

		    ServiceAccMasterVO serviceAccMasterVO =
		            serviceAccMasterRepo.findById(dto.getServiceName())
		                    .orElseThrow(() ->
		                            new ApplicationException("Service Account Master Not Found"));

		    vo.setServiceName(serviceAccMasterVO);
		}

		vo.setSchEndDate(dto.getSchEndDate());

		if (dto.getSacCode() != null) {

		    HsnVO hsnVO =
		            hsnRepo.findById(dto.getSacCode())
		                    .orElseThrow(() ->
		                            new ApplicationException("HSN Not Found"));

		    vo.setSacCode(hsnVO);
		}
		vo.setContractNo(dto.getContractNo());
		vo.setTaxType(dto.getTaxType());
		vo.setSupplierDcNo(dto.getSupplierDcNo());
		vo.setTaxPercentage(dto.getTaxPercentage());
		vo.setSupplierDcDate(dto.getSupplierDcDate());
		vo.setGrnClearTime(dto.getGrnClearTime());
		vo.setBasicAmount(dto.getBasicAmount());
		vo.setRemarks(dto.getRemarks());
		vo.setTotalTax(dto.getTotalTax());
		vo.setTotalAmount(dto.getTotalAmount());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		// =========================================================
		// DETAILS
		// =========================================================

		List<SubContractingGRNDetailsVO> detailList = new ArrayList<>();

		if (dto.getDetails() != null) {

			for (SubContractingGRNDetailsDTO detailsDTO : dto.getDetails()) {

				SubContractingGRNDetailsVO detailsVO = new SubContractingGRNDetailsVO();

				// =================================================
				// INCOMING ITEM
				// =================================================

				if (detailsDTO.getIncomingItem() != null) {

					ItemMasterVO incomingItem = itemMasterRepo.findById(detailsDTO.getIncomingItem())
							.orElseThrow(() -> new ApplicationException("Incoming Item Not Found"));

					detailsVO.setIncomingItem(incomingItem);
				}

				// =================================================
				// PRIMARY UNIT
				// =================================================

				if (detailsDTO.getPrimaryUnit() != null) {

					UnitMasterVO primaryUnit = unitMasterRepo.findById(detailsDTO.getPrimaryUnit())
							.orElseThrow(() -> new ApplicationException("Primary Unit Not Found"));

					detailsVO.setPrimaryUnit(primaryUnit);
				}

				// =================================================
				// LOCATION
				// =================================================

				if (detailsDTO.getLocation() != null) {

					LocationVO location = locationRepo.findById(detailsDTO.getLocation())
							.orElseThrow(() -> new ApplicationException("Location Not Found"));

					detailsVO.setLocation(location);
				}

				// =================================================
				// DETAILS VALUES
				// =================================================

				detailsVO.setStock(detailsDTO.getStock());
				detailsVO.setTolerance(detailsDTO.getTolerance());
				detailsVO.setJobOrderNo(detailsDTO.getJobOrderNo());
				detailsVO.setJobOrderQty(detailsDTO.getJobOrderQty());
				detailsVO.setJobOrderRate(detailsDTO.getJobOrderRate());
				detailsVO.setGatePassQty(detailsDTO.getGatePassQty());
				detailsVO.setInspectionable(detailsDTO.getInspectionable());

				BigDecimal pendingQty = BigDecimal.ZERO;

				if (detailsDTO.getJobOrderQty() != null) {

				    BigDecimal gatePassQty = detailsDTO.getGatePassQty() != null
				            ? detailsDTO.getGatePassQty()
				            : BigDecimal.ZERO;

				    pendingQty = detailsDTO.getJobOrderQty()
				            .subtract(gatePassQty);
				}

				detailsVO.setPendingQty(pendingQty);				
				detailsVO.setReceivedQty( detailsDTO.getReceivedQty());

				BigDecimal excessQty = BigDecimal.ZERO;

				if (detailsDTO.getReceivedQty() != null
				        && detailsDTO.getGatePassQty() != null
				        && detailsDTO.getReceivedQty()
				                .compareTo(detailsDTO.getGatePassQty()) > 0) {

				    excessQty = detailsDTO.getReceivedQty()
				            .subtract(detailsDTO.getGatePassQty());
				}

				detailsVO.setExcessQty(excessQty);
				detailsVO.setQtyInPrimaryUnit( detailsDTO.getQtyInPrimaryUnit());
				detailsVO.setAcceptedQty(detailsDTO.getAcceptedQty());
				detailsVO.setAccQtyInPrimaryUnit(detailsDTO.getAccQtyInPrimaryUnit());
				detailsVO.setRejectedQty(detailsDTO.getRejectedQty());
				detailsVO.setRejQtyInPrimaryUnit(detailsDTO.getRejQtyInPrimaryUnit());
				BigDecimal amount = BigDecimal.ZERO;

				if (detailsDTO.getJobOrderRate() != null
				        && detailsDTO.getAcceptedQty() != null) {

				    amount = detailsDTO.getJobOrderRate()
				            .multiply(detailsDTO.getAcceptedQty())
				            .setScale(2, RoundingMode.HALF_UP);
				}

				detailsVO.setAmount(amount);		
				
				//gst calculation
				
				BigDecimal sgstRate = detailsDTO.getSgstRate();
				BigDecimal cgstRate = detailsDTO.getCgstRate();
				BigDecimal igstRate = detailsDTO.getIgstRate();
				
				BigDecimal sgstAmount = BigDecimal.ZERO;
				BigDecimal cgstAmount = BigDecimal.ZERO;
				BigDecimal igstAmount = BigDecimal.ZERO;
				
		
				if (Boolean.TRUE.equals(vo.getIsIGSTAppl())) {

				     igstAmount = amount
				            .multiply(igstRate)
				            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

				    detailsVO.setIgstRate(igstRate);
				    detailsVO.setCgstRate(BigDecimal.ZERO);
				    detailsVO.setSgstRate(BigDecimal.ZERO);

				    detailsVO.setIgstAmount(igstAmount);
				    detailsVO.setCgstAmount(BigDecimal.ZERO);
				    detailsVO.setSgstAmount(BigDecimal.ZERO);

				} else {

				     cgstAmount = amount
				            .multiply(cgstRate)
				            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

				     sgstAmount = amount
				            .multiply(sgstRate)
				            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

				    detailsVO.setCgstRate(cgstRate);
				    detailsVO.setSgstRate(sgstRate);
				    detailsVO.setIgstRate(BigDecimal.ZERO);

				    detailsVO.setCgstAmount(cgstAmount);
				    detailsVO.setSgstAmount(sgstAmount);
				    detailsVO.setIgstAmount(BigDecimal.ZERO);
				}

				// =================================================
				// CONSUMPTION
				// =================================================

				List<SubContractingGRNConsumptionVO> consumptionList = new ArrayList<>();

				if (detailsDTO.getConsumption() != null) {

					for (SubContractingGRNConsumptionDTO consumptionDTO : detailsDTO.getConsumption()) {

						SubContractingGRNConsumptionVO consumptionVO = new SubContractingGRNConsumptionVO();

						// OUTGOING ITEM
						if (consumptionDTO.getOutgoingItem() != null) {

							ItemMasterVO outgoingItem = itemMasterRepo.findById(consumptionDTO.getOutgoingItem())
									.orElseThrow(() -> new ApplicationException("Outgoing Item Not Found"));

							consumptionVO.setOutgoingItem(outgoingItem);
						}

						// UNIT
						if (consumptionDTO.getUnit() != null) {

							UnitMasterVO unit = unitMasterRepo.findById(consumptionDTO.getUnit())
									.orElseThrow(() -> new ApplicationException("Unit Not Found"));

							consumptionVO.setUnit(unit);
						}

						consumptionVO.setItemType(consumptionDTO.getItemType());

						consumptionVO.setBomQty(consumptionDTO.getBomQty());

						consumptionVO.setAvailableStock(consumptionDTO.getAvailableStock());

						BigDecimal bomQty = consumptionDTO.getBomQty() != null
						        ? consumptionDTO.getBomQty()
						        : BigDecimal.ZERO;

						BigDecimal jobOrderQty = detailsDTO.getJobOrderQty() != null
						        ? detailsDTO.getJobOrderQty()
						        : BigDecimal.ZERO;

						BigDecimal consumeQty = bomQty.multiply(jobOrderQty);

						consumptionVO.setConsumedQty(consumeQty);

						
						consumptionVO.setScrapItem(consumptionDTO.getScrapItem());

						consumptionVO.setBomScrap(consumptionDTO.getBomScrap());

						consumptionVO.setScrapQty(consumptionDTO.getScrapQty());

						BigDecimal rate = consumptionDTO.getRate() != null
						        ? consumptionDTO.getRate()
						        : BigDecimal.ZERO;

						BigDecimal amounts = consumeQty.multiply(rate);

						consumptionVO.setRate(rate);
						consumptionVO.setAmount(amounts);

						// PARENT
						consumptionVO.setSubContractingGRNDetailsVO(detailsVO);

						consumptionList.add(consumptionVO);
					}
				}

				detailsVO.getConsumption().addAll(consumptionList);

				// =================================================
				// PARENT
				// =================================================

				detailsVO.setSubContractingGRNVO(vo);

				detailList.add(detailsVO);
			}
		}

		vo.getDetails().addAll(detailList);

		// =========================================================
		// TAX DETAILS
		// =========================================================

		List<SubContractingGRNTaxDetailsVO> taxDetailList = new ArrayList<>();

		if (dto.getTaxDetails() != null) {

			for (SubContractingGRNTaxDetailsDTO taxDTO : dto.getTaxDetails()) {

				SubContractingGRNTaxDetailsVO taxVO = new SubContractingGRNTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());

				taxVO.setTaxAmount(taxDTO.getTaxAmount());

				taxVO.setSubContractingGRNVO(vo);

				taxDetailList.add(taxVO);
			}
		}

		vo.getTaxDetails().addAll(taxDetailList);
	}

	private SubContractingGRNResponseDTO convertToResponse(SubContractingGRNVO vo) {

		SubContractingGRNResponseDTO response = new SubContractingGRNResponseDTO();

		// =========================
		// Header Details
		// =========================

		response.setId(vo.getId());
		response.setDocId(vo.getDocId());
		response.setDocDate(vo.getDocDate());
		response.setBelongsTo(vo.getBelongsTo());
		response.setGstState(vo.getGstState());
		response.setGatePassNo(vo.getGatePassNo());
		response.setIsIGSTAppl(vo.getIsIGSTAppl());
		response.setScheduleNo(vo.getScheduleNo());
		response.setGstnNo(vo.getGstnNo());
		response.setRework(vo.getRework());
		response.setGstType(vo.getGstType());
		response.setRevsChrg(vo.isRevsChrg());
		response.setSchStartDate(vo.getSchStartDate());
		// Service Account Master
		if (vo.getServiceName() != null) {

		    ServiceAccMasterResponse1DTO service =
		            new ServiceAccMasterResponse1DTO();

		    service.setId(vo.getServiceName().getId());
		    service.setServiceName(vo.getServiceName().getServiceName());
		    service.setServiceDescription(
		            vo.getServiceName().getServiceDescription());

		    response.setServiceName(service);
		}

		// Schedule End Date
		response.setSchEndDate(vo.getSchEndDate());

		// HSN / SAC Code
		if (vo.getSacCode() != null) {

		    HsnResponseDTO hsn =
		            new HsnResponseDTO();

		    hsn.setId(vo.getSacCode().getId());
		    hsn.setHsn(vo.getSacCode().getHsn());
		    hsn.setDescription(vo.getSacCode().getDescription());

		    response.setSacCode(hsn);
		}
		response.setContractNo(vo.getContractNo());
		response.setTaxType(vo.getTaxType());
		response.setSupplierDcNo(vo.getSupplierDcNo());
		response.setTaxPercentage(vo.getTaxPercentage());
		response.setSupplierDcDate(vo.getSupplierDcDate());
		response.setGrnClearTime(vo.getGrnClearTime());
		response.setBasicAmount(vo.getBasicAmount());
		response.setRemarks(vo.getRemarks());
		response.setTotalTax(vo.getTotalTax());
		response.setTotalAmount(vo.getTotalAmount());

		response.setCreatedBy(vo.getCreatedBy());
		response.setActive(vo.isActive());
		response.setCancel(vo.isCancel());
		response.setUpdatedBy(vo.getUpdatedBy());
		response.setCancelRemarks(vo.getCancelRemarks());

		response.setScreenName(vo.getScreenName());
		response.setScreenCode(vo.getScreenCode());
		response.setOrgId(vo.getOrgId());
		response.setFinancialYear(vo.getFinancialYear());

		// =========================
		// Branch
		// =========================

		if (vo.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(vo.getBranch().getId());
			branch.setBranchCode(vo.getBranch().getBranchCode());
			branch.setBranchName(vo.getBranch().getBranchName());

			response.setBranch(branch);
		}

		// =========================
		// Department
		// =========================

		if (vo.getDepartment() != null) {

			DepartmentResponseDTO department = new DepartmentResponseDTO();

			department.setId(vo.getDepartment().getId());
			department.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			department.setDepartmentName(vo.getDepartment().getDepartmentName());

			response.setDepartment(department);
		}

		// =========================
		// Vendor
		// =========================

		if (vo.getVendor() != null) {

			CustomerDropdownResponseDTO vendor = new CustomerDropdownResponseDTO();

			vendor.setCustomerId(vo.getVendor().getId());
			vendor.setCustomerCode(vo.getVendor().getCustomerCode());
			vendor.setCustomerName(vo.getVendor().getCustomerName());
			vendor.setAddress(vo.getVendor().getAddress());
			vendor.setGstState(vo.getVendor().getGstState().getStateName());
			vendor.setGstNo(vo.getVendor().getGstNo());
			vendor.setIgstApplicable(vo.getVendor().isGstApplicable());
			vendor.setGstType(vo.getVendor().getGstType());

			response.setVendor(vendor);
		}

		// =========================
		// Vendor Location
		// =========================

		if (vo.getVendorLocation() != null) {

			LocationMasterResponseDTO location = new LocationMasterResponseDTO();

			location.setId(vo.getVendorLocation().getId());
			location.setLocationName(vo.getVendorLocation().getLocationName());

			response.setVendorLocation(location);
		}

		// =========================
		// Details
		// =========================

		List<SubContractingGRNDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (vo.getDetails() != null) {

			for (SubContractingGRNDetailsVO detailsVO : vo.getDetails()) {

				SubContractingGRNDetailsResponseDTO detailsResponse = new SubContractingGRNDetailsResponseDTO();

				detailsResponse.setId(detailsVO.getId());

				// Incoming Item
				if (detailsVO.getIncomingItem() != null) {

					ItemResponse1DTO item = new ItemResponse1DTO();

					item.setId(detailsVO.getIncomingItem().getId());

					item.setItemCode(detailsVO.getIncomingItem().getItemCode());

					item.setItemDescription(detailsVO.getIncomingItem().getItemDescription());

					if (detailsVO.getIncomingItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

						unit.setId(detailsVO.getIncomingItem().getPrimaryUnit().getId());

						unit.setUnitId(detailsVO.getIncomingItem().getPrimaryUnit().getUnitId());

						unit.setUnitDescription(detailsVO.getIncomingItem().getPrimaryUnit().getDescription());

						item.setUnit(unit);
					}

					detailsResponse.setIncomingItem(item);
				}

				detailsResponse.setStock(detailsVO.getStock());
				detailsResponse.setTolerance(detailsVO.getTolerance());

				// Primary Unit
				if (detailsVO.getPrimaryUnit() != null) {

					UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

					unit.setId(detailsVO.getPrimaryUnit().getId());

					unit.setUnitId(detailsVO.getPrimaryUnit().getUnitId());

					unit.setUnitDescription(detailsVO.getPrimaryUnit().getDescription());

					detailsResponse.setPrimaryUnit(unit);
				}

				detailsResponse.setJobOrderNo(detailsVO.getJobOrderNo());

				detailsResponse.setJobOrderQty(detailsVO.getJobOrderQty());

				detailsResponse.setJoRate(detailsVO.getJobOrderRate());

				detailsResponse.setGatePassQty(detailsVO.getGatePassQty());

				detailsResponse.setInspectionable(detailsVO.getInspectionable());

				detailsResponse.setPendingQty(detailsVO.getPendingQty());

				detailsResponse.setReceivedQty(detailsVO.getReceivedQty());

				detailsResponse.setExcessQty(detailsVO.getExcessQty());

				detailsResponse.setQtyInPrimaryUnit(detailsVO.getQtyInPrimaryUnit());

				// Location
				if (detailsVO.getLocation() != null) {

					LocationMasterResponseDTO location = new LocationMasterResponseDTO();

					location.setId(detailsVO.getLocation().getId());

					location.setLocationName(detailsVO.getLocation().getLocationName());

					detailsResponse.setLocation(location);
				}

				detailsResponse.setAcceptedQty(detailsVO.getAcceptedQty());

				detailsResponse.setAccQtyInPrimaryUnit(detailsVO.getAccQtyInPrimaryUnit());

				detailsResponse.setRejectedQty(detailsVO.getRejectedQty());

				detailsResponse.setRejQtyInPrimaryUnit(detailsVO.getRejQtyInPrimaryUnit());

				detailsResponse.setAmount(detailsVO.getAmount());

				detailsResponse.setSgstRate(detailsVO.getSgstRate());

				detailsResponse.setCgstRate(detailsVO.getCgstRate());

				detailsResponse.setIgstRate(detailsVO.getIgstRate());
				
				detailsResponse.setSgstRate(detailsVO.getSgstRate());

				detailsResponse.setCgstRate(detailsVO.getCgstRate());

				detailsResponse.setIgstRate(detailsVO.getIgstRate());

				// =========================
				// Consumption
				// =========================

				List<SubContractingGRNConsumptionResponseDTO> consumptionResponseList = new ArrayList<>();

				if (detailsVO.getConsumption() != null) {

					for (SubContractingGRNConsumptionVO consumptionVO : detailsVO.getConsumption()) {

						SubContractingGRNConsumptionResponseDTO consumptionResponse = new SubContractingGRNConsumptionResponseDTO();

						consumptionResponse.setId(consumptionVO.getId());

						// Outgoing Item
						if (consumptionVO.getOutgoingItem() != null) {

							ItemResponse1DTO item = new ItemResponse1DTO();

							item.setId(consumptionVO.getOutgoingItem().getId());

							item.setItemCode(consumptionVO.getOutgoingItem().getItemCode());

							item.setItemDescription(consumptionVO.getOutgoingItem().getItemDescription());

							if (consumptionVO.getOutgoingItem().getPrimaryUnit() != null) {

								UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

								unit.setId(consumptionVO.getOutgoingItem().getPrimaryUnit().getId());

								unit.setUnitId(consumptionVO.getOutgoingItem().getPrimaryUnit().getUnitId());

								unit.setUnitDescription(
										consumptionVO.getOutgoingItem().getPrimaryUnit().getDescription());

								item.setUnit(unit);
							}

							consumptionResponse.setOutgoingItem(item);
						}

						// Unit
						if (consumptionVO.getUnit() != null) {

							UnitMasterResponseDTO unit = new UnitMasterResponseDTO();

							unit.setId(consumptionVO.getUnit().getId());

							unit.setUnitId(consumptionVO.getUnit().getUnitId());

							unit.setUnitDescription(consumptionVO.getUnit().getDescription());

							consumptionResponse.setUnit(unit);
						}

						consumptionResponse.setItemType(consumptionVO.getItemType());

						consumptionResponse.setBomQty(consumptionVO.getBomQty());

						consumptionResponse.setAvailableStock(consumptionVO.getAvailableStock());

						consumptionResponse.setConsumedQty(consumptionVO.getConsumedQty());

						consumptionResponse.setScrapItem(consumptionVO.getScrapItem());

						consumptionResponse.setBomScrap(consumptionVO.getBomScrap());

						consumptionResponse.setScrapQty(consumptionVO.getScrapQty());

						consumptionResponse.setRate(consumptionVO.getRate());

						consumptionResponse.setAmount(consumptionVO.getAmount());

						consumptionResponseList.add(consumptionResponse);
					}
				}

				detailsResponse.setConsumption(consumptionResponseList);

				detailResponseList.add(detailsResponse);
			}
		}

		response.setDetails(detailResponseList);

		// =========================
		// Tax Details
		// =========================

		List<SubContractingGRNTaxDetailsResponseDTO> taxResponseList = new ArrayList<>();

		if (vo.getTaxDetails() != null) {

			for (SubContractingGRNTaxDetailsVO taxVO : vo.getTaxDetails()) {

				SubContractingGRNTaxDetailsResponseDTO taxResponse = new SubContractingGRNTaxDetailsResponseDTO();

				taxResponse.setId(taxVO.getId());

				taxResponse.setParticulars(taxVO.getParticulars());

				taxResponse.setTaxAmount(taxVO.getTaxAmount());

				taxResponseList.add(taxResponse);
			}
		}

		response.setTaxDetails(taxResponseList);

		return response;
	}

	@Override
	public List<Map<String, Object>> getGateInwardEntryDropdown(Long orgId, Long branch, Long customer) {

		Set<Object[]> result = gateInwardEntryRepo.getGateInwardEntryDropdown(orgId, branch, customer);

		return getGateInwardEntryDropdownDetails(result);
	}

	private List<Map<String, Object>> getGateInwardEntryDropdownDetails(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("GatePassNo", fs[0] != null ? fs[0].toString() : null);

			part.put("GatePassDate", fs[1] != null ? fs[1] : null);

			part.put("supplierDCNumber", fs[2] != null ? fs[2].toString() : null);

			part.put("supplierDcDate", fs[3] != null ? fs[3] : null);

			details.add(part);
		}

		return details;
	}
	
	
	@Override
	public List<Map<String, Object>> getSubcontractSupplyScheduleforSubContractingGRN(
	        Long orgId, Long branch, Long customer) {

	    Set<Object[]> result =
	            subcontractSupplyScheduleRepo.getSubcontractSupplyScheduleforSubContractingGRN(
	                    orgId, branch, customer);

	    return getSubcontractSupplyScheduleDropdownDetails(result);
	}

	private List<Map<String, Object>> getSubcontractSupplyScheduleDropdownDetails(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("scheduleNo",
	                fs[0] != null ? fs[0].toString() : null);

	        part.put("scheduleDate",
	                fs[1] != null ? fs[1] : null);

	        part.put("schStartDate",
	                fs[2] != null ? fs[2] : null);

	        part.put("schEndDate",
	                fs[3] != null ? fs[3] : null);

	        part.put("contractNo",
	                fs[4] != null ? fs[4].toString() : null);

	        part.put("hsnId",
	                fs[5] != null ? fs[5] : null);

	        part.put("hsnCode",
	                fs[6] != null ? fs[6].toString() : null);

	        part.put("hsnDescription",
	                fs[7] != null ? fs[7].toString() : null);

	        part.put("serviceId",
	                fs[8] != null ? fs[8] : null);

	        part.put("serviceName",
	                fs[9] != null ? fs[9].toString() : null);

	        // GST Rate Master
	        part.put("gstRateMasterId",
	                fs[10] != null ? fs[10] : null);

	        part.put("gstRate",
	                fs[11] != null ? fs[11] : null);

	        part.put("igstRate",
	                fs[12] != null ? fs[12] : null);

	        part.put("cgstRate",
	                fs[13] != null ? fs[13] : null);

	        part.put("sgstRate",
	                fs[14] != null ? fs[14] : null);

	        details.add(part);
	    }

	    return details;
	}
	
	
	@Override
	public List<Map<String, Object>> getItemDetailsForSubContractingGRN(
	        String scheduleNo,
	        Long orgId,
	        Long branch,
	        Long customer) {

	    Set<Object[]> result =
	            subcontractSupplyScheduleRepo.getItemDetailsForSubContractingGRN(
	                    scheduleNo, orgId, branch, customer);

	    return getSubcontractSupplyScheduleItemDropdownDetails(result);
	}

	private List<Map<String, Object>> getSubcontractSupplyScheduleItemDropdownDetails(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("itemId",
	                fs[0] != null ? fs[0] : null);

	        part.put("itemCode",
	                fs[1] != null ? fs[1].toString() : null);

	        part.put("itemDescription",
	                fs[2] != null ? fs[2].toString() : null);

	        part.put("unitId",
	                fs[3] != null ? fs[3] : null);

	        part.put("unitCode",
	                fs[4] != null ? fs[4].toString() : null);

	        part.put("unitDescription",
	                fs[5] != null ? fs[5].toString() : null);

	        part.put("jobOrderNo",
	                fs[6] != null ? fs[6].toString() : null);

	        part.put("jobOrderQty",
	                fs[7] != null ? fs[7] : null);

	        part.put("jobOrderRate",
	                fs[8] != null ? fs[8] : null);

	        details.add(part);
	    }

	    return details;
	}
	
	
	@Override
	public List<Map<String, Object>> getBomItemDetailsforSubContractingGRN(
	        Long orgId,
	        Long branch,
	        Long itemId) {

	    Set<Object[]> result =
	            billOfMaterialRepo.getBomItemDetailsforSubContractingGRN(
	                    orgId,
	                    branch,
	                    itemId);

	    return getBomItemDetailsResponse(result);
	}

	private List<Map<String, Object>> getBomItemDetailsResponse(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("bomId",
	                fs[0] != null ? fs[0] : null);

	        part.put("itemId",
	                fs[1] != null ? fs[1] : null);

	        part.put("itemCode",
	                fs[2] != null ? fs[2].toString() : null);

	        part.put("itemDescription",
	                fs[3] != null ? fs[3].toString() : null);

	        part.put("unitId",
	                fs[4] != null ? fs[4] : null);

	        part.put("unitCode",
	                fs[5] != null ? fs[5].toString() : null);

	        part.put("unitDescription",
	                fs[6] != null ? fs[6].toString() : null);

	        part.put("bomQty",
	                fs[7] != null ? fs[7] : null);

	        part.put("scrapItem",
	                fs[8] != null ? fs[8].toString() : null);

	        part.put("scrapQty",
	                fs[9] != null ? fs[9] : null);

	        part.put("BomDocId",
	                fs[10] != null ? fs[10] : null);
	        
	        details.add(part);
	    }

	    return details;
	}

	
	@Override
	public SubContractingGRNResponseDTO getSubContractingGRNById(
	        Long id) throws ApplicationException {

	    SubContractingGRNVO vo =
	            subContractingGRNRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Sub Contracting GRN Not Found"));

	    return convertToResponse(vo);
	}
	
	@Override
	public List<SubContractingGRNResponseDTO>
	        getSubContractingGRNByOrgIdAndBranch(
	                Long orgId,
	                Long branch) throws ApplicationException {

	    List<SubContractingGRNVO> grnList =
	            subContractingGRNRepo.findByOrgIdAndBranch(
	                    orgId,
	                    branch);

	    List<SubContractingGRNResponseDTO> responseList =
	            new ArrayList<>();

	    for (SubContractingGRNVO vo : grnList) {

	        responseList.add(
	                convertToResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getSubContractingGRNDocId(
	        Long orgId,
	        String financialYear) {

	    String screenCode = "SCGRN";

	    return subContractingGRNRepo
	            .getSubContractingGRNDocId(
	                    orgId,
	                    financialYear,
	                    screenCode);
	}


	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateMaterialPlanning(
	        MaterialPlanningDTO dto) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String screenCode = "MRP";

	    MaterialPlanningVO materialPlanningVO;

	    String message;

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        // CREATE
	        materialPlanningVO = new MaterialPlanningVO();

	        String docId = materialPlanningRepo.getMaterialPlanningDocId(
	                dto.getOrgId(),
	                dto.getFinancialYear(),
	                screenCode);

	        if (docId == null || docId.isBlank()) {
	            throw new ApplicationException(
	                    "Material Planning DocId Generation Failed");
	        }

	        materialPlanningVO.setDocId(docId);

	        // Update document last number
	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
	                documentTypeMappingDetailsRepo
	                        .findByOrgIdAndFinYearAndScreenCode(
	                                dto.getOrgId(),
	                                dto.getFinancialYear(),
	                                screenCode);

	        if (documentTypeMappingDetailsVO != null) {

	            documentTypeMappingDetailsVO.setLastNo(
	                    documentTypeMappingDetailsVO.getLastNo() + 1);

	            documentTypeMappingDetailsRepo.save(
	                    documentTypeMappingDetailsVO);
	        }

	        materialPlanningVO.setCreatedBy(dto.getCreatedBy());
	        materialPlanningVO.setUpdatedBy(dto.getCreatedBy());

	        message = "Material Planning Created Successfully";

	    } else {

	        // UPDATE
	        materialPlanningVO = materialPlanningRepo.findById(dto.getId())
	                .orElseThrow(() ->
	                        new ApplicationException(
	                                "Material Planning Not Found"));

	        materialPlanningVO.setUpdatedBy(dto.getCreatedBy());

	        message = "Material Planning Updated Successfully";
	    }

	    // Map DTO → VO
	    materialPlanningVO.setFromDate(dto.getFromDate());
	    materialPlanningVO.setDocDate(dto.getDocDate());
	    materialPlanningVO.setMrpType(dto.getMrpType());
	    materialPlanningVO.setActive(dto.isActive());
	    if (dto.getBranch() != null) {

	        BranchVO branchVO = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() ->
	                        new ApplicationException("Branch Not Found"));

	        materialPlanningVO.setBranch(branchVO);
	    }
	    materialPlanningVO.setCancelRemarks(dto.getCancelRemarks());
	    materialPlanningVO.setOrgId(dto.getOrgId());
	    materialPlanningVO.setFinancialYear(dto.getFinancialYear());

	    materialPlanningVO =
	            materialPlanningRepo.saveAndFlush(materialPlanningVO);

	    // VO → Response DTO
	    MaterialPlanningResponseDTO responseDTO =
	            convertToResponse(materialPlanningVO);

	    response.put("message", message);
	    response.put("materialPlanningVO", responseDTO);

	    return response;
	}
	
	private MaterialPlanningResponseDTO convertToResponse(
	        MaterialPlanningVO vo) {

	    MaterialPlanningResponseDTO response =
	            new MaterialPlanningResponseDTO();

	    response.setId(vo.getId());
	    response.setFromDate(vo.getFromDate());
	    response.setDocId(vo.getDocId());
	    response.setDocDate(vo.getDocDate());
	    response.setMrpType(vo.getMrpType());
	    response.setCreatedBy(vo.getCreatedBy());
	    response.setActive(vo.isActive());
	    response.setUpdatedBy(vo.getUpdatedBy());
	    response.setCancel(vo.isCancel());
	    response.setCancelRemarks(vo.getCancelRemarks());
	    response.setOrgId(vo.getOrgId());
	    response.setFinancialYear(vo.getFinancialYear());

	    if (vo.getBranch() != null) {

	        BranchResponseDTO branchResponseDTO =
	                new BranchResponseDTO();

	        branchResponseDTO.setId(vo.getBranch().getId());
	        branchResponseDTO.setBranchCode(
	                vo.getBranch().getBranchCode());
	        branchResponseDTO.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branchResponseDTO);
	    }
	    return response;
	}
	
	@Override
	public MaterialPlanningResponseDTO getMaterialPlanningById(Long id)
	        throws ApplicationException {

	    MaterialPlanningVO materialPlanningVO =
	            materialPlanningRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Material Planning Not Found"));

	    return convertToResponse(materialPlanningVO);
	}
	
	@Override
	public List<MaterialPlanningResponseDTO> getMaterialPlanningByOrgIdAndBranch(
	        Long orgId, Long branch) throws ApplicationException {

	    List<MaterialPlanningVO> materialPlanningList =
	            materialPlanningRepo.findByOrgIdAndBranch(orgId, branch);

	    List<MaterialPlanningResponseDTO> responseList =
	            new ArrayList<>();

	    for (MaterialPlanningVO vo : materialPlanningList) {

	        responseList.add(convertToResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getMaterialPlanningDocId(
	        Long orgId, String financialYear) {

	    String screenCode = "MRP";

	    return materialPlanningRepo.getMaterialPlanningDocId(
	            orgId,
	            financialYear,
	            screenCode);
	}
	
	
	//BomCorrectionRequestNote
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public Map<String, Object> createUpdateBomCorrectionRequestNote(
	        BomCorrectionRequestNoteDTO dto) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String screenCode = "BCRN";

	    BomCorrectionRequestNoteVO bomCorrectionRequestNoteVO;
	    String message;

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        // CREATE
	        bomCorrectionRequestNoteVO =
	                new BomCorrectionRequestNoteVO();

	        String docId =
	                bomCorrectionRequestNoteRepo
	                        .getBomCorrectionRequestNoteDocId(
	                                dto.getOrgId(),
	                                dto.getFinancialYear(),
	                                screenCode);

	        if (docId == null || docId.isBlank()) {
	            throw new ApplicationException(
	                    "BOM Correction Request Note DocId Generation Failed");
	        }

	        bomCorrectionRequestNoteVO.setDocId(docId);

	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
	                documentTypeMappingDetailsRepo
	                        .findByOrgIdAndFinYearAndScreenCode(
	                                dto.getOrgId(),
	                                dto.getFinancialYear(),
	                                screenCode);

	        if (documentTypeMappingDetailsVO != null) {

	            documentTypeMappingDetailsVO.setLastNo(
	                    documentTypeMappingDetailsVO.getLastNo() + 1);

	            documentTypeMappingDetailsRepo.save(
	                    documentTypeMappingDetailsVO);
	        }

	        bomCorrectionRequestNoteVO.setCreatedBy(
	                dto.getCreatedBy());

	        bomCorrectionRequestNoteVO.setUpdatedBy(
	                dto.getCreatedBy());

	        message =
	                "BOM Correction Request Note Created Successfully";

	    } else {

	        // UPDATE
	        bomCorrectionRequestNoteVO =
	                bomCorrectionRequestNoteRepo.findById(dto.getId())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "BOM Correction Request Note Not Found"));

	        List<BomCorrectionRequestNoteDetailsVO> oldDetails =
	                bomCorrectionRequestNoteDetailsRepo
	                        .findByBomCorrectionRequestNoteVO(
	                                bomCorrectionRequestNoteVO);

	        if (oldDetails != null && !oldDetails.isEmpty()) {
	            bomCorrectionRequestNoteDetailsRepo.deleteAll(oldDetails);
	            bomCorrectionRequestNoteDetailsRepo.flush();
	        }

	        bomCorrectionRequestNoteVO.setUpdatedBy(
	                dto.getCreatedBy());

	        message =
	                "BOM Correction Request Note Updated Successfully";
	    }

	    // DTO → VO
	    getBomCorrectionRequestNoteVOFromDTO(
	            dto,
	            bomCorrectionRequestNoteVO);

	    bomCorrectionRequestNoteVO =
	            bomCorrectionRequestNoteRepo.saveAndFlush(
	                    bomCorrectionRequestNoteVO);

	    BomCorrectionRequestNoteResponseDTO responseDTO =
	            convertToResponse(bomCorrectionRequestNoteVO);

	    response.put("message", message);

	    response.put(
	            "bomCorrectionRequestNoteVO",
	            responseDTO);

	    return response;
	}
	
	private void getBomCorrectionRequestNoteVOFromDTO(
	        BomCorrectionRequestNoteDTO dto,
	        BomCorrectionRequestNoteVO vo)
	        throws ApplicationException {

	    if (dto.getBranch() != null) {

	        BranchVO branchVO =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        vo.setBranch(branchVO);
	    }

	    if (dto.getCorrectionRequestedBy() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getCorrectionRequestedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Correction Requested By Employee Not Found"));

	        vo.setCorrectionRequestedBy(employee);
	    }

	    if (dto.getCorrectionRequestApprovedBy() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getCorrectionRequestApprovedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Correction Request Approved By Employee Not Found"));

	        vo.setCorrectionRequestApprovedBy(employee);
	    }

	    if (dto.getFgPartNo() != null) {

	        ItemMasterVO item =
	                itemMasterRepo.findById(dto.getFgPartNo())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "FG Part No Not Found"));

	        vo.setFgPartNo(item);
	    }

	   

	    if (dto.getManagerProduction() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getManagerProduction())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Manager Production Not Found"));

	        vo.setManagerProduction(employee);
	    }

	    if (dto.getManagerQuality() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getManagerQuality())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Manager Quality Not Found"));

	        vo.setManagerQuality(employee);
	    }

	    if (dto.getManagerTdc() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getManagerTdc())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Manager TDC Not Found"));

	        vo.setManagerTdc(employee);
	    }

	    if (dto.getManagerPurchase() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getManagerPurchase())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Manager Purchase Not Found"));

	        vo.setManagerPurchase(employee);
	    }

	    if (dto.getAuthorisedSignator() != null) {

	        EmployeeMasterVO employee =
	                employeeMasterRepo.findById(
	                                dto.getAuthorisedSignator())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Authorised Signator Not Found"));

	        vo.setAuthorisedSignator(employee);
	    }

	    // Header fields
	    vo.setProductName(dto.getProductName());
	    vo.setCustomerPartNo(dto.getCustomerPartNo());
	    vo.setReasonForChange(dto.getReasonForChange());
	    vo.setDecision(dto.getDecision());
	    vo.setActive(dto.isActive());
	    vo.setCancel(dto.isCancel());
	    vo.setCancelRemarks(dto.getCancelRemarks());
	    vo.setOrgId(dto.getOrgId());
	    vo.setFinancialYear(dto.getFinancialYear());
	    vo.setCustomerName(dto.getCustomerName());
	    vo.setSupplier(dto.getSupplier());

	    // Details
	    if (dto.getDetails() != null) {

	        List<BomCorrectionRequestNoteDetailsVO> detailList =
	                new ArrayList<>();

	        for (BomCorrectionRequestNoteDetailsDTO detailsDTO :
	                dto.getDetails()) {

	            BomCorrectionRequestNoteDetailsVO detailsVO =
	                    new BomCorrectionRequestNoteDetailsVO();

	            if (detailsDTO.getPartNo() != null) {

	                ItemMasterVO item =
	                        itemMasterRepo.findById(
	                                        detailsDTO.getPartNo())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Part No Not Found"));

	                detailsVO.setPartNo(item);
	            }

	            if (detailsDTO.getUnit() != null) {

	                UnitMasterVO unit =
	                        unitMasterRepo.findById(
	                                        detailsDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                detailsVO.setUnit(unit);
	            }

	            detailsVO.setBomQty(detailsDTO.getBomQty());
	            detailsVO.setAddedRemoved(
	                    detailsDTO.getAddedRemoved());

	            detailsVO.setBomCorrectionRequestNoteVO(vo);

	            detailList.add(detailsVO);
	        }

	        vo.getDetails().addAll(detailList);
	    }
	}
	
	private BomCorrectionRequestNoteResponseDTO convertToResponse(
	        BomCorrectionRequestNoteVO vo) {

	    BomCorrectionRequestNoteResponseDTO response =
	            new BomCorrectionRequestNoteResponseDTO();

	    response.setId(vo.getId());
	    response.setDocId(vo.getDocId());
	    response.setDocDate(vo.getDocDate());

	    // Branch
	    if (vo.getBranch() != null) {

	        BranchResponseDTO branchResponseDTO =
	                new BranchResponseDTO();

	        branchResponseDTO.setId(vo.getBranch().getId());
	        branchResponseDTO.setBranchCode(
	                vo.getBranch().getBranchCode());
	        branchResponseDTO.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branchResponseDTO);
	    }

	    // Correction Requested By
	    if (vo.getCorrectionRequestedBy() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getCorrectionRequestedBy().getId());
	        employee.setEmployeeCode(
	                vo.getCorrectionRequestedBy().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getCorrectionRequestedBy().getEmployeeName());
	        employee.setEmail(
	                vo.getCorrectionRequestedBy().getEmail());

	        response.setCorrectionRequestedBy(employee);
	    }

	    // Correction Request Approved By
	    if (vo.getCorrectionRequestApprovedBy() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getCorrectionRequestApprovedBy().getId());
	        employee.setEmployeeCode(
	                vo.getCorrectionRequestApprovedBy().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getCorrectionRequestApprovedBy().getEmployeeName());
	        employee.setEmail(
	                vo.getCorrectionRequestApprovedBy().getEmail());

	        response.setCorrectionRequestApprovedBy(employee);
	    }

	    // FG Part No
	    if (vo.getFgPartNo() != null) {

	        ItemResponse1DTO item =
	                new ItemResponse1DTO();

	        item.setId(vo.getFgPartNo().getId());
	        item.setItemCode(
	                vo.getFgPartNo().getItemCode());
	        item.setItemDescription(
	                vo.getFgPartNo().getItemDescription());

	        if (vo.getFgPartNo().getPrimaryUnit() != null) {

	            UnitMasterResponseDTO unit =
	                    new UnitMasterResponseDTO();

	            unit.setId(
	                    vo.getFgPartNo().getPrimaryUnit().getId());

	            unit.setUnitId(
	                    vo.getFgPartNo().getPrimaryUnit().getUnitId());

	            unit.setUnitDescription(
	                    vo.getFgPartNo().getPrimaryUnit().getDescription());

	            item.setUnit(unit);
	        }

	        response.setFgPartNo(item);
	    }

	    response.setProductName(vo.getProductName());
	    response.setCustomerPartNo(vo.getCustomerPartNo());
	    response.setCustomerName(vo.getCustomerName());
	    response.setReasonForChange(vo.getReasonForChange());
	    response.setDecision(vo.getDecision());
	    response.setSupplier(vo.getSupplier());

	   

	    // Manager Production
	    if (vo.getManagerProduction() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getManagerProduction().getId());
	        employee.setEmployeeCode(
	                vo.getManagerProduction().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getManagerProduction().getEmployeeName());
	        employee.setEmail(
	                vo.getManagerProduction().getEmail());

	        response.setManagerProduction(employee);
	    }

	    // Manager Quality
	    if (vo.getManagerQuality() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getManagerQuality().getId());
	        employee.setEmployeeCode(
	                vo.getManagerQuality().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getManagerQuality().getEmployeeName());
	        employee.setEmail(
	                vo.getManagerQuality().getEmail());

	        response.setManagerQuality(employee);
	    }

	    // Manager TDC
	    if (vo.getManagerTdc() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getManagerTdc().getId());
	        employee.setEmployeeCode(
	                vo.getManagerTdc().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getManagerTdc().getEmployeeName());
	        employee.setEmail(
	                vo.getManagerTdc().getEmail());

	        response.setManagerTdc(employee);
	    }

	    // Manager Purchase
	    if (vo.getManagerPurchase() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getManagerPurchase().getId());
	        employee.setEmployeeCode(
	                vo.getManagerPurchase().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getManagerPurchase().getEmployeeName());
	        employee.setEmail(
	                vo.getManagerPurchase().getEmail());

	        response.setManagerPurchase(employee);
	    }

	    // Authorised Signator
	    if (vo.getAuthorisedSignator() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getAuthorisedSignator().getId());
	        employee.setEmployeeCode(
	                vo.getAuthorisedSignator().getEmployeeId());
	        employee.setEmployeeName(
	                vo.getAuthorisedSignator().getEmployeeName());
	        employee.setEmail(
	                vo.getAuthorisedSignator().getEmail());

	        response.setAuthorisedSignator(employee);
	    }

	    response.setCreatedBy(vo.getCreatedBy());
	    response.setActive(vo.isActive());
	    response.setCancel(vo.isCancel());
	    response.setUpdatedBy(vo.getUpdatedBy());
	    response.setCancelRemarks(vo.getCancelRemarks());
	    response.setScreenName(vo.getScreenName());
	    response.setScreenCode(vo.getScreenCode());
	    response.setOrgId(vo.getOrgId());
	    response.setFinancialYear(vo.getFinancialYear());

	    // Details
	    List<BomCorrectionRequestNoteDetailsResponseDTO> detailResponseList =
	            new ArrayList<>();

	    if (vo.getDetails() != null) {

	        for (BomCorrectionRequestNoteDetailsVO detailsVO :
	                vo.getDetails()) {

	            BomCorrectionRequestNoteDetailsResponseDTO detailsResponse =
	                    new BomCorrectionRequestNoteDetailsResponseDTO();

	            detailsResponse.setId(detailsVO.getId());
	            detailsResponse.setBomQty(detailsVO.getBomQty());
	            detailsResponse.setAddedRemoved(
	                    detailsVO.getAddedRemoved());

	            // Part No
	            if (detailsVO.getPartNo() != null) {

	                ItemResponse1DTO item =
	                        new ItemResponse1DTO();

	                item.setId(detailsVO.getPartNo().getId());
	                item.setItemCode(
	                        detailsVO.getPartNo().getItemCode());
	                item.setItemDescription(
	                        detailsVO.getPartNo().getItemDescription());

	                if (detailsVO.getPartNo().getPrimaryUnit() != null) {

	                    UnitMasterResponseDTO unit =
	                            new UnitMasterResponseDTO();

	                    unit.setId(
	                            detailsVO.getPartNo()
	                                    .getPrimaryUnit().getId());

	                    unit.setUnitId(
	                            detailsVO.getPartNo()
	                                    .getPrimaryUnit().getUnitId());

	                    unit.setUnitDescription(
	                            detailsVO.getPartNo()
	                                    .getPrimaryUnit().getDescription());

	                    item.setUnit(unit);
	                }

	                detailsResponse.setPartNo(item);
	            }

	            // Unit
	            if (detailsVO.getUnit() != null) {

	                UnitMasterResponseDTO unit =
	                        new UnitMasterResponseDTO();

	                unit.setId(detailsVO.getUnit().getId());
	                unit.setUnitId(
	                        detailsVO.getUnit().getUnitId());
	                unit.setUnitDescription(
	                        detailsVO.getUnit().getDescription());

	                detailsResponse.setUnit(unit);
	            }

	            detailResponseList.add(detailsResponse);
	        }
	    }

	    response.setDetails(detailResponseList);

	    return response;
	}
	
	@Override
	public BomCorrectionRequestNoteResponseDTO getBomCorrectionRequestNoteById(
	        Long id) throws ApplicationException {

	    BomCorrectionRequestNoteVO vo =
	            bomCorrectionRequestNoteRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "BOM Correction Request Note Not Found"));

	    return convertToResponse(vo);
	}
	
	@Override
	public List<BomCorrectionRequestNoteResponseDTO>
	        getBomCorrectionRequestNoteByOrgIdAndBranch(
	                Long orgId, Long branch) throws ApplicationException {

	    List<BomCorrectionRequestNoteVO> bomCorrectionRequestNoteList =
	            bomCorrectionRequestNoteRepo
	                    .findByOrgIdAndBranch(orgId, branch);

	    List<BomCorrectionRequestNoteResponseDTO> responseList =
	            new ArrayList<>();

	    for (BomCorrectionRequestNoteVO vo :
	            bomCorrectionRequestNoteList) {

	        responseList.add(convertToResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getBomCorrectionRequestNoteDocId(
	        Long orgId, String financialYear) {

	    String screenCode = "BCRN";

	    return bomCorrectionRequestNoteRepo
	            .getBomCorrectionRequestNoteDocId(
	                    orgId,
	                    financialYear,
	                    screenCode);
	}
	
	@Override
	public List<Map<String, Object>> getFGItemsforBOMCorrectionRequestNote(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> result = itemMasterRepo.getFGItemsforBOMCorrectionRequestNote(orgId, branch);

		if (result == null || result.isEmpty()) {
			throw new ApplicationException("FG Items Not Found");
		}

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemCode", fs[0]);
			map.put("itemDescription", fs[1]);
			map.put("itemId", fs[2]);
			map.put("unitmasterId", fs[3]);
			map.put("unitId", fs[4]);
			map.put("unitDescription", fs[5]);
			map.put("customerPartNo", fs[6]);

			details.add(map);
		}

		return details;
	}

	
	@Override
	public List<Map<String, Object>> getAllItemsNotFGforBOMCorrectionRequestNote(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> result = itemMasterRepo.getAllItemsNotFGforBOMCorrectionRequestNote(orgId, branch);

		if (result == null || result.isEmpty()) {
			throw new ApplicationException("FG Items Not Found");
		}

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemCode", fs[0]);
			map.put("itemDescription", fs[1]);
			map.put("itemId", fs[2]);
			map.put("unitmasterId", fs[3]);
			map.put("unitId", fs[4]);
			map.put("unitDescription", fs[5]);

			details.add(map);
		}

		return details;
	}

	
	@Override
	public List<Map<String, Object>> getEmployeesByDepartmentforBOMCorrectionRequestNote(
	        Long orgId,
	        Long branch,
	        String department) {

	    Set<Object[]> result =
	            employeeMasterRepo.getEmployeesByDepartmentforBOMCorrectionRequestNote(
	                    orgId,
	                    branch,
	                    department);

	    return getEmployeesByDepartmentDetails(result);
	}
	
	private List<Map<String, Object>> getEmployeesByDepartmentDetails(
	        Set<Object[]> result) {

	    List<Map<String, Object>> details = new ArrayList<>();

	    for (Object[] fs : result) {

	        Map<String, Object> part = new HashMap<>();

	        part.put("employeeId",
	                fs[0] != null ? fs[0] : null);

	        part.put("employeeCode",
	                fs[1] != null ? fs[1].toString() : null);

	        part.put("employeeName",
	                fs[2] != null ? fs[2].toString() : null);

	        details.add(part);
	    }

	    return details;
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Map<String, Object> createUpdateInspectionRequisitionNote(
	        InspectionRequisitionNoteDTO dto) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String screenCode = "IRN";

	    InspectionRequisitionNoteVO vo;
	    String message;

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        vo = new InspectionRequisitionNoteVO();

	        vo.setCreatedBy(dto.getCreatedBy());
	        vo.setUpdatedBy(dto.getCreatedBy());

	        message = "Inspection Requisition Note Created Successfully";

	    } else {

	        vo = inspectionRequisitionNoteRepo
	                .findById(dto.getId())
	                .orElseThrow(() ->
	                        new ApplicationException(
	                                "Inspection Requisition Note Not Found"));

	        vo.setUpdatedBy(dto.getCreatedBy());

	        message = "Inspection Requisition Note Updated Successfully";
	    }

	    getInspectionRequisitionNoteVOFromDTO(dto, vo);

	    vo = inspectionRequisitionNoteRepo.saveAndFlush(vo);

	    InspectionRequisitionNoteResponseDTO responseDTO =
	            convertToResponse(vo);

	    response.put("message", message);
	    response.put("inspectionRequisitionNoteVO", responseDTO);

	    return response;
	}
	
	private void getInspectionRequisitionNoteVOFromDTO(
	        InspectionRequisitionNoteDTO dto,
	        InspectionRequisitionNoteVO vo) throws ApplicationException {

	    if (dto.getRequestedBy() != null) {
	        vo.setRequestedBy(dto.getRequestedBy());
	    }

	    vo.setReasonForInspectionRequest(
	            dto.getReasonForInspectionRequest());

	    vo.setProductCategory(
	            dto.getProductCategory());

	    vo.setRequestComments(
	            dto.getRequestComments());

	    vo.setSamplesSubmittedTo(
	            dto.getSamplesSubmittedTo());

	    vo.setPartName(
	            dto.getPartName());

	    vo.setPartNumber(
	            dto.getPartNumber());

	    vo.setSampleQuantity(
	            dto.getSampleQuantity());

	    vo.setProduct(
	            dto.getProduct());

	    vo.setCustomer(
	            dto.getCustomer());

	    vo.setSupplier(
	            dto.getSupplier());


	    // Purchase Manager
	    if (dto.getPurchaseManager() != null) {

	        EmployeeMasterVO purchaseManager =
	                employeeMasterRepo
	                        .findById(dto.getPurchaseManager())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Purchase Manager Not Found"));

	        vo.setPurchaseManager(purchaseManager);
	    } else {
	        vo.setPurchaseManager(null);
	    }

	    vo.setPurchaseManagerDate(
	            dto.getPurchaseManagerDate());


	    // TDC Manager
	    if (dto.getTdcManager() != null) {

	        EmployeeMasterVO tdcManager =
	                employeeMasterRepo
	                        .findById(dto.getTdcManager())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "TDC Manager Not Found"));

	        vo.setTdcManager(tdcManager);
	    } else {
	        vo.setTdcManager(null);
	    }

	    vo.setTdcManagerDate(
	            dto.getTdcManagerDate());


	    // Quality Manager
	    if (dto.getQualityManager() != null) {

	        EmployeeMasterVO qualityManager =
	                employeeMasterRepo
	                        .findById(dto.getQualityManager())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Quality Manager Not Found"));

	        vo.setQualityManager(qualityManager);
	    } else {
	        vo.setQualityManager(null);
	    }

	    vo.setQualityManagerDate(
	            dto.getQualityManagerDate());


	    // Production Manager
	    if (dto.getProductionManager() != null) {

	        EmployeeMasterVO productionManager =
	                employeeMasterRepo
	                        .findById(dto.getProductionManager())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Production Manager Not Found"));

	        vo.setProductionManager(productionManager);
	    } else {
	        vo.setProductionManager(null);
	    }

	    vo.setProductionManagerDate(
	            dto.getProductionManagerDate());


	    // Approval Requested By
	    if (dto.getApprovalRequestedBy() != null) {

	        EmployeeMasterVO approvalRequestedBy =
	                employeeMasterRepo
	                        .findById(dto.getApprovalRequestedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Approval Requested By Employee Not Found"));

	        vo.setApprovalRequestedBy(approvalRequestedBy);
	    } else {
	        vo.setApprovalRequestedBy(null);
	    }


	    // Approved By
	    if (dto.getApprovedBy() != null) {

	        EmployeeMasterVO approvedBy =
	                employeeMasterRepo
	                        .findById(dto.getApprovedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Approved By Employee Not Found"));

	        vo.setApprovedBy(approvedBy);
	    } else {
	        vo.setApprovedBy(null);
	    }


	    // Branch
	    if (dto.getBranch() != null) {

	        BranchVO branch =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        vo.setBranch(branch);
	    }

	    vo.setOrgId(dto.getOrgId());

	    vo.setFinancialYear(dto.getFinancialYear());

	    vo.setActive(dto.isActive());

	    vo.setCancelRemarks(dto.getCancelRemarks());

	}
	
	private InspectionRequisitionNoteResponseDTO convertToResponse(
	        InspectionRequisitionNoteVO vo) {

	    InspectionRequisitionNoteResponseDTO response =
	            new InspectionRequisitionNoteResponseDTO();

	    response.setId(vo.getId());

	    response.setRequestedBy(
	            vo.getRequestedBy());

	    response.setDate(
	            vo.getDate());

	    response.setReasonForInspectionRequest(
	            vo.getReasonForInspectionRequest());

	    response.setProductCategory(
	            vo.getProductCategory());

	    response.setRequestComments(
	            vo.getRequestComments());

	    response.setSamplesSubmittedTo(
	            vo.getSamplesSubmittedTo());

	    response.setPartName(
	            vo.getPartName());

	    response.setPartNumber(
	            vo.getPartNumber());

	    response.setSampleQuantity(
	            vo.getSampleQuantity());

	    response.setProduct(
	            vo.getProduct());

	    response.setCustomer(
	            vo.getCustomer());

	    response.setSupplier(
	            vo.getSupplier());


	    // Purchase Manager
	    if (vo.getPurchaseManager() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getPurchaseManager().getId());

	        employee.setEmployeeCode(
	                vo.getPurchaseManager().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getPurchaseManager().getEmployeeName());

	        employee.setEmail(
	                vo.getPurchaseManager().getEmail());

	        response.setPurchaseManager(employee);
	    }


	    response.setPurchaseManagerDate(
	            vo.getPurchaseManagerDate());


	    // TDC Manager
	    if (vo.getTdcManager() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getTdcManager().getId());

	        employee.setEmployeeCode(
	                vo.getTdcManager().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getTdcManager().getEmployeeName());

	        employee.setEmail(
	                vo.getTdcManager().getEmail());

	        response.setTdcManager(employee);
	    }


	    response.setTdcManagerDate(
	            vo.getTdcManagerDate());


	    // Quality Manager
	    if (vo.getQualityManager() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getQualityManager().getId());

	        employee.setEmployeeCode(
	                vo.getQualityManager().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getQualityManager().getEmployeeName());

	        employee.setEmail(
	                vo.getQualityManager().getEmail());

	        response.setQualityManager(employee);
	    }


	    response.setQualityManagerDate(
	            vo.getQualityManagerDate());


	    // Production Manager
	    if (vo.getProductionManager() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getProductionManager().getId());

	        employee.setEmployeeCode(
	                vo.getProductionManager().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getProductionManager().getEmployeeName());

	        employee.setEmail(
	                vo.getProductionManager().getEmail());

	        response.setProductionManager(employee);
	    }


	    response.setProductionManagerDate(
	            vo.getProductionManagerDate());


	    // Approval Requested By
	    if (vo.getApprovalRequestedBy() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getApprovalRequestedBy().getId());

	        employee.setEmployeeCode(
	                vo.getApprovalRequestedBy().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getApprovalRequestedBy().getEmployeeName());

	        employee.setEmail(
	                vo.getApprovalRequestedBy().getEmail());

	        response.setApprovalRequestedBy(employee);
	    }


	    // Approved By
	    if (vo.getApprovedBy() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getApprovedBy().getId());

	        employee.setEmployeeCode(
	                vo.getApprovedBy().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getApprovedBy().getEmployeeName());

	        employee.setEmail(
	                vo.getApprovedBy().getEmail());

	        response.setApprovedBy(employee);
	    }


	    response.setCreatedBy(
	            vo.getCreatedBy());

	    response.setActive(
	            vo.isActive());

	    response.setCancel(
	            vo.isCancel());

	    response.setUpdatedBy(
	            vo.getUpdatedBy());

	    response.setCancelRemarks(
	            vo.getCancelRemarks());

	    response.setScreenName(
	            vo.getScreenName());

	    response.setScreenCode(
	            vo.getScreenCode());

	    response.setOrgId(
	            vo.getOrgId());

	    response.setFinancialYear(
	            vo.getFinancialYear());


	    // Branch
	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(
	                vo.getBranch().getId());

	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());

	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }

	    return response;
	}
	
	@Override
	public InspectionRequisitionNoteResponseDTO getInspectionRequisitionNoteById(
	        Long id) throws ApplicationException {

	    InspectionRequisitionNoteVO vo =
	            inspectionRequisitionNoteRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Inspection Requisition Note Not Found"));

	    return convertToResponse(vo);
	}
	
	@Override
	public List<InspectionRequisitionNoteResponseDTO>
	getInspectionRequisitionNoteByOrgIdAndBranch(
	        Long orgId, Long branch) throws ApplicationException {

	    List<InspectionRequisitionNoteVO> list =
	            inspectionRequisitionNoteRepo
	                    .findByOrgIdAndBranch(orgId, branch);

	    List<InspectionRequisitionNoteResponseDTO> responseList =
	            new ArrayList<>();

	    for (InspectionRequisitionNoteVO vo : list) {

	        responseList.add(
	                convertToResponse(vo));
	    }

	    return responseList;
	}
	
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Map<String, Object> createUpdateProcessValidationEntry(
	        ProcessValidationEntryDTO dto) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String screenCode = "PVE";

	    ProcessValidationEntryVO vo;
	    String message;

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        vo = new ProcessValidationEntryVO();

	        String docId = processValidationEntryRepo
	                .getProcessValidationEntryDocId(
	                        dto.getOrgId(),
	                        dto.getFinancialYear(),
	                        screenCode);

	        if (docId == null || docId.isBlank()) {
	            throw new ApplicationException(
	                    "BOM Correction Request Note DocId Generation Failed");
	        }

	        vo.setDocId(docId);
	        
	        

	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
	                documentTypeMappingDetailsRepo
	                        .findByOrgIdAndFinYearAndScreenCode(
	                                dto.getOrgId(),
	                                dto.getFinancialYear(),
	                                screenCode);

	        if (documentTypeMappingDetailsVO != null) {

	            documentTypeMappingDetailsVO.setLastNo(
	                    documentTypeMappingDetailsVO.getLastNo() + 1);

	            documentTypeMappingDetailsRepo
	                    .save(documentTypeMappingDetailsVO);
	        }

	        vo.setCreatedBy(dto.getCreatedBy());
	        vo.setUpdatedBy(dto.getCreatedBy());

	        message = "Process Validation Entry Created Successfully";

	    } else {

	        vo = processValidationEntryRepo
	                .findById(dto.getId())
	                .orElseThrow(() ->
	                        new ApplicationException(
	                                "Process Validation Entry Not Found"));

	        /*
	         * Delete old child records
	         */
	        List<ProcessValidationEntryDetailsVO> oldDetails =
	                processValidationEntryDetailsRepo
	                        .findByProcessValidationEntryVO(vo);

	        if (oldDetails != null && !oldDetails.isEmpty()) {

	            processValidationEntryDetailsRepo.deleteAll(oldDetails);
	        }

	        vo.getDetails().clear();

	        vo.setUpdatedBy(dto.getCreatedBy());

	        message = "Process Validation Entry Updated Successfully";
	    }

	    getProcessValidationEntryVOFromDTO(dto, vo);

	    vo = processValidationEntryRepo.saveAndFlush(vo);

	    ProcessValidationEntryResponseDTO responseDTO =
	            convertToResponse(vo);

	    response.put("message", message);

	    response.put(
	            "processValidationEntryVO",
	            responseDTO);

	    return response;
	}
	
	private void getProcessValidationEntryVOFromDTO(
	        ProcessValidationEntryDTO dto,
	        ProcessValidationEntryVO vo)
	        throws ApplicationException {

		if (dto.getItem() != null) {
		    ItemMasterVO itemVO = itemMasterRepo.findById(dto.getItem())
		            .orElseThrow(() -> new ApplicationException("Item Not Found"));

		    vo.setItem(itemVO);
		} else {
		    vo.setItem(null);
		}
		
		if (dto.getCustomer() != null) {
		    CustomerVO customerVO = customerRepo.findById(dto.getCustomer())
		            .orElseThrow(() -> new ApplicationException("Customer Not Found"));

		    vo.setCustomer(customerVO);
		} else {
		    vo.setCustomer(null);
		}
		
	    vo.setValidationReason(
	            dto.getValidationReason());

	    vo.setDetailsOfChanges(
	            dto.getDetailsOfChanges());

	    vo.setCharacteristicsToBeMeasured(
	            dto.getCharacteristicsToBeMeasured());

	    vo.setSpecification(
	            dto.getSpecification());

	    vo.setDateImplemented(
	            dto.getDateImplemented());

	    vo.setRecommendedForProduction(
	            dto.getRecommendedForProduction());

	    vo.setDateOfNextValidation(
	            dto.getDateOfNextValidation());

	    vo.setResultsRemarks(
	            dto.getResultsRemarks());

	    vo.setOrgId(dto.getOrgId());

	    vo.setFinancialYear(
	            dto.getFinancialYear());

	    vo.setActive(dto.isActive());

	    vo.setCancelRemarks(
	            dto.getCancelRemarks());


	    /*
	     * Branch
	     */
	    if (dto.getBranch() != null) {

	        BranchVO branch =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        vo.setBranch(branch);
	    }


	    /*
	     * Process Sheet
	     */
	    if (dto.getProcessSheetNo() != null) {

	        ProcessSheetCompRoutingVO processSheet =
	                processSheetCompRoutingRepo
	                        .findById(dto.getProcessSheetNo())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Process Sheet Not Found"));

	        vo.setProcessSheetNo(processSheet);

	    } else {

	        vo.setProcessSheetNo(null);
	    }


	    /*
	     * Control Plan
	     */
	    if (dto.getControlPlan() != null) {

	        ControlPlanVO controlPlan =
	                controlPlanRepo
	                        .findById(dto.getControlPlan())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Control Plan Not Found"));

	        vo.setControlPlan(controlPlan);

	    } else {

	        vo.setControlPlan(null);
	    }


	    /*
	     * Details
	     */
	    if (dto.getDetails() != null) {

	        for (ProcessValidationEntryDetailsDTO detailsDTO
	                : dto.getDetails()) {

	            ProcessValidationEntryDetailsVO detailsVO =
	                    new ProcessValidationEntryDetailsVO();

	            detailsVO.setParameter1(
	                    detailsDTO.getParameter1());

	            detailsVO.setParameter2(
	                    detailsDTO.getParameter2());

	            detailsVO.setParameter3(
	                    detailsDTO.getParameter3());

	            detailsVO.setParameter4(
	                    detailsDTO.getParameter4());

	            detailsVO.setParameter5(
	                    detailsDTO.getParameter5());

	            detailsVO.setParameter6(
	                    detailsDTO.getParameter6());

	            detailsVO.setParameter7(
	                    detailsDTO.getParameter7());

	            detailsVO.setProcessValidationEntryVO(vo);

	            vo.getDetails().add(detailsVO);
	        }
	    }
	}
	
	private ProcessValidationEntryResponseDTO convertToResponse(
	        ProcessValidationEntryVO vo) {

	    ProcessValidationEntryResponseDTO response =
	            new ProcessValidationEntryResponseDTO();

	    response.setId(vo.getId());

	    response.setDocId(vo.getDocId());

	    response.setDocDate(vo.getDocDate());

	 // Item
	 // Item
	    if (vo.getItem() != null) {

	        ItemResponse1DTO itemResponse = new ItemResponse1DTO();

	        itemResponse.setId(vo.getItem().getId());

	        itemResponse.setItemCode(
	                vo.getItem().getItemCode());

	        itemResponse.setItemDescription(
	                vo.getItem().getItemDescription());

	        if (vo.getItem().getPurchaseUnit() != null) {

	            UnitMasterResponseDTO unitResponse =
	                    new UnitMasterResponseDTO();

	            unitResponse.setId(
	                    vo.getItem().getPurchaseUnit().getId());

	            unitResponse.setUnitId(
	                    vo.getItem().getPurchaseUnit().getUnitId());

	            unitResponse.setUnitDescription(
	                    vo.getItem().getPurchaseUnit().getDescription());

	            itemResponse.setUnit(unitResponse);
	        }

	        response.setItem(itemResponse);
	    }
	    
	 // Customer
	    if (vo.getCustomer() != null) {

	        CustomerResponse1DTO customerResponse =
	                new CustomerResponse1DTO();

	        customerResponse.setId(
	                vo.getCustomer().getId());

	        customerResponse.setCustomerName(
	                vo.getCustomer().getCustomerName());

	        response.setCustomer(customerResponse);
	    }

	    response.setValidationReason(
	            vo.getValidationReason());

	    response.setDetailsOfChanges(
	            vo.getDetailsOfChanges());

	    response.setCharacteristicsToBeMeasured(
	            vo.getCharacteristicsToBeMeasured());

	    response.setSpecification(
	            vo.getSpecification());

	    response.setDateImplemented(
	            vo.getDateImplemented());

	    response.setRecommendedForProduction(
	            vo.getRecommendedForProduction());

	    response.setDateOfNextValidation(
	            vo.getDateOfNextValidation());

	    response.setResultsRemarks(
	            vo.getResultsRemarks());

	    response.setCreatedBy(vo.getCreatedBy());

	    response.setActive(vo.isActive());

	    response.setCancel(vo.isCancel());

	    response.setUpdatedBy(vo.getUpdatedBy());

	    response.setCancelRemarks(
	            vo.getCancelRemarks());

	    response.setScreenName(
	            vo.getScreenName());

	    response.setScreenCode(
	            vo.getScreenCode());

	    response.setOrgId(vo.getOrgId());

	    response.setFinancialYear(
	            vo.getFinancialYear());


	    /*
	     * Branch
	     */
	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(
	                vo.getBranch().getId());

	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());

	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }


	    /*
	     * Process Sheet
	     */
	    if (vo.getProcessSheetNo() != null) {

	        ProcessSheetCompRoutingVO processSheetVO = vo.getProcessSheetNo();

	        ProcessSheetCompRoutingResponseDetails processSheet =
	                new ProcessSheetCompRoutingResponseDetails();

	        processSheet.setId(processSheetVO.getId());
	        processSheet.setDocId(processSheetVO.getDocId());
	        processSheet.setDocDate(processSheetVO.getDocDate());

	        List<OperationMasterResponseforPSCRDTO> operations =
	                new ArrayList<>();

	        if (processSheetVO.getProcessSheetCompRoutingDetailVO() != null) {

	            for (ProcessSheetCompRoutingDetailVO detailVO :
	                    processSheetVO.getProcessSheetCompRoutingDetailVO()) {

	                if (detailVO.getOperation() != null) {

	                    OperationMasterResponseforPSCRDTO operation =
	                            new OperationMasterResponseforPSCRDTO();

	                    operation.setId(detailVO.getOperation().getId());
	                    operation.setOperationId(
	                            detailVO.getOperation().getOperationId());
	                    operation.setDescription(
	                            detailVO.getOperation().getDescription());

	                    operations.add(operation);
	                }
	            }
	        }

	        processSheet.setOperations(operations);

	        response.setProcessSheetNo(processSheet);
	    }

	    /*
	     * Control Plan
	     */
	    if (vo.getControlPlan() != null) {

	        ControlPlanResponseDetailsDTO controlPlan =
	                new ControlPlanResponseDetailsDTO();

	        controlPlan.setId(vo.getControlPlan().getId());
	        controlPlan.setPlanNo(vo.getControlPlan().getPlanNo());

	        response.setControlPlan(controlPlan);
	    }


	    /*
	     * Details
	     */
	    List<ProcessValidationEntryDetailsResponseDTO>
	            detailsList = new ArrayList<>();

	    if (vo.getDetails() != null) {

	        for (ProcessValidationEntryDetailsVO detailsVO
	                : vo.getDetails()) {

	            ProcessValidationEntryDetailsResponseDTO
	                    detailsResponse =
	                    new ProcessValidationEntryDetailsResponseDTO();

	            detailsResponse.setId(
	                    detailsVO.getId());

	            detailsResponse.setParameter1(
	                    detailsVO.getParameter1());

	            detailsResponse.setParameter2(
	                    detailsVO.getParameter2());

	            detailsResponse.setParameter3(
	                    detailsVO.getParameter3());

	            detailsResponse.setParameter4(
	                    detailsVO.getParameter4());

	            detailsResponse.setParameter5(
	                    detailsVO.getParameter5());

	            detailsResponse.setParameter6(
	                    detailsVO.getParameter6());

	            detailsResponse.setParameter7(
	                    detailsVO.getParameter7());

	            detailsList.add(detailsResponse);
	        }
	    }

	    response.setDetails(detailsList);

	    return response;
	}
	
	
	@Override
	public ProcessValidationEntryResponseDTO getProcessValidationEntryById(Long id)
	        throws ApplicationException {

	    ProcessValidationEntryVO vo =
	            processValidationEntryRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Process Validation Entry Not Found"));

	    return convertToResponse(vo);
	}
	
	@Override
	public List<ProcessValidationEntryResponseDTO> getProcessValidationEntryByOrgIdAndBranch(
	        Long orgId, Long branch) throws ApplicationException {

	    List<ProcessValidationEntryVO> list =
	            processValidationEntryRepo
	                    .findByOrgIdAndBranch(orgId, branch);

	    List<ProcessValidationEntryResponseDTO> responseList =
	            new ArrayList<>();

	    for (ProcessValidationEntryVO vo : list) {

	        responseList.add(convertToResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getProcessValidationEntryDocId(
	        Long orgId, String financialYear) {

	    String screenCode = "PVE";

	    return processValidationEntryRepo
	            .getProcessValidationEntryDocId(
	                    orgId, financialYear, screenCode);
	}
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Map<String, Object> createUpdateBulkIssueIndent(
	        BulkIssueIndentDTO bulkIssueIndentDTO) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    BulkIssueIndentVO bulkIssueIndentVO = null;
	    String message = null;

	    if (ObjectUtils.isEmpty(bulkIssueIndentDTO.getId())) {

	        bulkIssueIndentVO = new BulkIssueIndentVO();

	        String screenCode = "BII";

//	        String docId = bulkIssueIndentRepo.getBulkIssueIndentDocId(
//	                bulkIssueIndentDTO.getOrgId(),
//	                bulkIssueIndentDTO.getFinancialYear(),
//	                screenCode);
//
//	        bulkIssueIndentVO.setDocId(docId);
//
//	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
//	                documentTypeMappingDetailsRepo
//	                        .findByOrgIdAndFinYearAndScreenCode(
//	                        		bulkIssueIndentDTO.getOrgId(),
//	                        		bulkIssueIndentDTO.getFinancialYear(),
//	                                screenCode);
//
//	        if (documentTypeMappingDetailsVO != null) {
//
//	            documentTypeMappingDetailsVO.setLastNo(
//	                    documentTypeMappingDetailsVO.getLastNo() + 1);
//
//	            documentTypeMappingDetailsRepo
//	                    .save(documentTypeMappingDetailsVO);
//	        }

	        bulkIssueIndentVO.setCreatedBy(
	                bulkIssueIndentDTO.getCreatedBy());

	        bulkIssueIndentVO.setUpdatedBy(
	                bulkIssueIndentDTO.getCreatedBy());

	        bulkIssueIndentVO.setActive(true);
	        bulkIssueIndentVO.setCancel(false);

	        message = "Bulk Issue Indent created successfully";

	    } else {

	        bulkIssueIndentVO = bulkIssueIndentRepo
	                .findById(bulkIssueIndentDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException(
	                                "Bulk Issue Indent Not Found"));

	        List<BulkIssueIndentDetailsVO> oldDetails =
	                bulkIssueIndentDetailsRepo
	                        .findByBulkIssueIndentVO(
	                                bulkIssueIndentVO);

	        if (oldDetails != null && !oldDetails.isEmpty()) {

	            bulkIssueIndentDetailsRepo.deleteAll(oldDetails);

	            bulkIssueIndentDetailsRepo.flush();
	        }

	        bulkIssueIndentVO.getDetails().clear();

	        bulkIssueIndentVO.setUpdatedBy(
	                bulkIssueIndentDTO.getCreatedBy());

	        message = "Bulk Issue Indent updated successfully";
	    }

	    bulkIssueIndentVO =
	            getBulkIssueIndentVOFromDTO(
	                    bulkIssueIndentDTO,
	                    bulkIssueIndentVO);

	    BulkIssueIndentVO savedVO =
	            bulkIssueIndentRepo.saveAndFlush(
	                    bulkIssueIndentVO);

	    response.put("message", message);

	    response.put(
	            "bulkIssueIndentVO",
	            convertToResponse(savedVO));

	    return response;
	}
	
	private BulkIssueIndentVO getBulkIssueIndentVOFromDTO(
	        BulkIssueIndentDTO dto,
	        BulkIssueIndentVO vo) throws ApplicationException {

	    vo.setBelongsTo(dto.getBelongsTo());
	    vo.setTimeOfIndent(dto.getTimeOfIndent());
	    vo.setApprovedByPM(dto.getApprovedByPM());
	    vo.setRemarks(dto.getRemarks());
	    vo.setOrgId(dto.getOrgId());
	    vo.setFinancialYear(dto.getFinancialYear());

	    if (dto.getBranch() != null) {

	        BranchVO branch = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() ->
	                        new ApplicationException("Branch Not Found"));

	        vo.setBranch(branch);
	    }

	    if (dto.getDepartment() != null) {

	        DepartmentVO department =
	                departmentRepo.findById(dto.getDepartment())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Department Not Found"));

	        vo.setDepartment(department);
	    }

	    if (dto.getFgSfgItem() != null) {

	        ItemMasterVO item =
	                itemMasterRepo.findById(dto.getFgSfgItem())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "FG/SFG Item Not Found"));

	        vo.setFgSfgItem(item);
	    }

	    if (dto.getBom() != null) {

	        BillOfMaterialVO bom =
	                billOfMaterialRepo.findById(dto.getBom())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "BOM Not Found"));

	        vo.setBom(bom);
	    }

	    if (dto.getFromLocation() != null) {

	        LocationVO location =
	                locationRepo.findById(dto.getFromLocation())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "From Location Not Found"));

	        vo.setFromLocation(location);
	    }

	    if (dto.getPreparedBy() != null) {

	        EmployeeMasterVO preparedBy =
	                employeeMasterRepo.findById(dto.getPreparedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Prepared By Employee Not Found"));

	        vo.setPreparedBy(preparedBy);
	    }

	    if (dto.getAuthorisedBy() != null) {

	        EmployeeMasterVO authorisedBy =
	                employeeMasterRepo.findById(dto.getAuthorisedBy())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Authorised By Employee Not Found"));

	        vo.setAuthorisedBy(authorisedBy);
	    }

	    if (dto.getDetails() != null) {

	        for (BulkIssueIndentDetailsDTO detailsDTO :
	                dto.getDetails()) {

	            BulkIssueIndentDetailsVO detailsVO =
	                    new BulkIssueIndentDetailsVO();

	            if (detailsDTO.getItem() != null) {

	                ItemMasterVO item =
	                        itemMasterRepo.findById(detailsDTO.getItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Item Not Found"));

	                detailsVO.setItem(item);
	            }

	            detailsVO.setReqQty(
	                    detailsDTO.getReqQty());

	            if (detailsDTO.getUnit() != null) {

	                UnitMasterVO unit =
	                        unitMasterRepo.findById(detailsDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                detailsVO.setUnit(unit);
	            }

	            detailsVO.setRequiredDate(
	                    detailsDTO.getRequiredDate());

	            detailsVO.setPurpose(
	                    detailsDTO.getPurpose());

	            detailsVO.setBulkIssueIndentVO(vo);

	            vo.getDetails().add(detailsVO);
	        }
	    }

	    return vo;
	}
	
	private BulkIssueIndentResponseDTO convertToResponse(
	        BulkIssueIndentVO vo) {

	    BulkIssueIndentResponseDTO response =
	            new BulkIssueIndentResponseDTO();

	    response.setId(vo.getId());
	    response.setDocId(vo.getDocId());
	    response.setDocDate(vo.getDocDate());

	    response.setBelongsTo(vo.getBelongsTo());

	    response.setTimeOfIndent(
	            vo.getTimeOfIndent());

	    response.setApprovedByPM(
	            vo.getApprovedByPM());

	    response.setRemarks(vo.getRemarks());

	    response.setCreatedBy(vo.getCreatedBy());

	    response.setActive(vo.isActive());

	    response.setCancel(vo.isCancel());

	    response.setUpdatedBy(vo.getUpdatedBy());

	    response.setCancelRemarks(
	            vo.getCancelRemarks());

	    response.setScreenName(
	            vo.getScreenName());

	    response.setScreenCode(
	            vo.getScreenCode());

	    response.setOrgId(vo.getOrgId());

	    response.setFinancialYear(
	            vo.getFinancialYear());

	    // Branch
	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(vo.getBranch().getId());
	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());
	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }

	    // Department
	    if (vo.getDepartment() != null) {

	        DepartmentResponseDTO department =
	                new DepartmentResponseDTO();

	        department.setId(
	                vo.getDepartment().getId());

	        department.setDepartmentCode(
	                vo.getDepartment().getDepartmentCode());

	        department.setDepartmentName(
	                vo.getDepartment().getDepartmentName());

	        response.setDepartment(department);
	    }

	    // FG / SFG Item
	    if (vo.getFgSfgItem() != null) {

	        ItemResponse1DTO item =
	                new ItemResponse1DTO();

	        item.setId(
	                vo.getFgSfgItem().getId());

	        item.setItemCode(
	                vo.getFgSfgItem().getItemCode());

	        item.setItemDescription(
	                vo.getFgSfgItem().getItemDescription());

	        if (vo.getFgSfgItem().getPurchaseUnit() != null) {

	            UnitMasterResponseDTO unit =
	                    new UnitMasterResponseDTO();

	            unit.setId(
	                    vo.getFgSfgItem()
	                            .getPurchaseUnit()
	                            .getId());

	            unit.setUnitId(
	                    vo.getFgSfgItem()
	                            .getPurchaseUnit()
	                            .getUnitId());

	            unit.setUnitDescription(
	                    vo.getFgSfgItem()
	                            .getPurchaseUnit()
	                            .getDescription());

	            item.setUnit(unit);
	        }

	        response.setFgSfgItem(item);
	    }

	    // BOM
	    if (vo.getBom() != null) {

	        response.setBomId(
	                vo.getBom().getId());
	    }

	    // From Location
	    if (vo.getFromLocation() != null) {

	        LocationMasterResponseDTO location =
	                new LocationMasterResponseDTO();

	        location.setId(
	                vo.getFromLocation().getId());

	        location.setLocationName(
	                vo.getFromLocation().getLocationName());

	        response.setFromLocation(location);
	    }

	    // Prepared By
	    if (vo.getPreparedBy() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getPreparedBy().getId());

	        employee.setEmployeeCode(
	                vo.getPreparedBy().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getPreparedBy().getEmployeeName());

	        employee.setEmail(
	                vo.getPreparedBy().getEmail());

	        response.setPreparedBy(employee);
	    }

	    // Authorised By
	    if (vo.getAuthorisedBy() != null) {

	        EmployeeDropdownResponseDTO employee =
	                new EmployeeDropdownResponseDTO();

	        employee.setEmployeeId(
	                vo.getAuthorisedBy().getId());

	        employee.setEmployeeCode(
	                vo.getAuthorisedBy().getEmployeeId());

	        employee.setEmployeeName(
	                vo.getAuthorisedBy().getEmployeeName());

	        employee.setEmail(
	                vo.getAuthorisedBy().getEmail());

	        response.setAuthorisedBy(employee);
	    }

	    // Child Details
	    List<BulkIssueIndentDetailsResponseDTO> detailsList =
	            new ArrayList<>();

	    if (vo.getDetails() != null &&
	            !vo.getDetails().isEmpty()) {

	        for (BulkIssueIndentDetailsVO detailsVO :
	                vo.getDetails()) {

	            BulkIssueIndentDetailsResponseDTO detailsResponse =
	                    new BulkIssueIndentDetailsResponseDTO();

	            detailsResponse.setId(
	                    detailsVO.getId());

	            detailsResponse.setReqQty(
	                    detailsVO.getReqQty());

	            detailsResponse.setRequiredDate(
	                    detailsVO.getRequiredDate());

	            detailsResponse.setPurpose(
	                    detailsVO.getPurpose());

	            // Item
	            if (detailsVO.getItem() != null) {

	                ItemResponse1DTO item =
	                        new ItemResponse1DTO();

	                item.setId(
	                        detailsVO.getItem().getId());

	                item.setItemCode(
	                        detailsVO.getItem().getItemCode());

	                item.setItemDescription(
	                        detailsVO.getItem()
	                                .getItemDescription());

	                detailsResponse.setItem(item);
	            }

	            // Unit
	            if (detailsVO.getUnit() != null) {

	                UnitMasterResponseDTO unit =
	                        new UnitMasterResponseDTO();

	                unit.setId(
	                        detailsVO.getUnit().getId());

	                unit.setUnitId(
	                        detailsVO.getUnit().getUnitId());

	                unit.setUnitDescription(
	                        detailsVO.getUnit().getDescription());

	                detailsResponse.setUnit(unit);
	            }

	            detailsList.add(detailsResponse);
	        }
	    }

	    response.setDetails(detailsList);

	    return response;
	}
	
	
	@Override
	public BulkIssueIndentResponseDTO getBulkIssueIndentById(Long id)
	        throws ApplicationException {

	    BulkIssueIndentVO vo = bulkIssueIndentRepo.findById(id)
	            .orElseThrow(() ->
	                    new ApplicationException("Bulk Issue Indent Not Found"));

	    return convertToResponse(vo);
	}

	@Override
	public List<BulkIssueIndentResponseDTO> getBulkIssueIndentByOrgIdAndBranch(
	        Long orgId, Long branch) throws ApplicationException {

	    List<BulkIssueIndentVO> list =
	            bulkIssueIndentRepo.findByOrgIdAndBranch(orgId, branch);

	    List<BulkIssueIndentResponseDTO> responseList =
	            new ArrayList<>();

	    for (BulkIssueIndentVO vo : list) {
	        responseList.add(convertToResponse(vo));
	    }

	    return responseList;
	}
	
	
	@Override
	public String getBulkIssueIndentDocId(Long orgId, String financialYear) {

	    String screenCode = "BII";

	    return bulkIssueIndentRepo.getBulkIssueIndentDocId(
	            orgId, financialYear, screenCode);
	}
	
	//ReconcileConsumptionStock
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Map<String, Object> createUpdateReconcileConsumptionStock(
	        ReconcileConsumptionStockDTO reconcileConsumptionStockDTO)
	        throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    ReconcileConsumptionStockVO reconcileConsumptionStockVO = null;

	    String message = null;

	    if (ObjectUtils.isEmpty(reconcileConsumptionStockDTO.getId())) {

	        reconcileConsumptionStockVO =
	                new ReconcileConsumptionStockVO();

	        String screenCode = "RCS";

//	        String docId =
//	                reconcileConsumptionStockRepo
//	                        .getReconcileConsumptionStockDocId(
//	                                reconcileConsumptionStockDTO.getOrgId(),
//	                                reconcileConsumptionStockDTO.getFinancialYear(),
//	                                screenCode);
//
//	        reconcileConsumptionStockVO.setDocId(docId);
//
//	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO =
//	                documentTypeMappingDetailsRepo
//	                        .findByOrgIdAndFinYearAndScreenCode(
//	                        		reconcileConsumptionStockDTO.getOrgId(),
//	                                reconcileConsumptionStockDTO.getFinancialYear(),
//	                                screenCode);
//
//	        if (documentTypeMappingDetailsVO != null) {
//
//	            documentTypeMappingDetailsVO.setLastNo(
//	                    documentTypeMappingDetailsVO.getLastNo() + 1);
//
//	            documentTypeMappingDetailsRepo.save(
//	                    documentTypeMappingDetailsVO);
//	        }


	        reconcileConsumptionStockVO.setCreatedBy(
	                reconcileConsumptionStockDTO.getCreatedBy());

	        reconcileConsumptionStockVO.setUpdatedBy(
	                reconcileConsumptionStockDTO.getCreatedBy());

	        reconcileConsumptionStockVO.setActive(true);

	        reconcileConsumptionStockVO.setCancel(false);

	        message =
	                "Reconcile Consumption Stock created successfully";

	    } else {

	        reconcileConsumptionStockVO =
	                reconcileConsumptionStockRepo
	                        .findById(
	                                reconcileConsumptionStockDTO.getId())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Reconcile Consumption Stock Not Found"));

	        List<ReconcileConsumptionStockDetailsVO> oldDetails =
	                reconcileConsumptionStockDetailsRepo
	                        .findByReconcileConsumptionStockVO(
	                                reconcileConsumptionStockVO);

	        if (oldDetails != null && !oldDetails.isEmpty()) {

	            reconcileConsumptionStockDetailsRepo
	                    .deleteAll(oldDetails);

	            reconcileConsumptionStockDetailsRepo.flush();
	        }

	        reconcileConsumptionStockVO.getDetails().clear();

	        reconcileConsumptionStockVO.setUpdatedBy(
	                reconcileConsumptionStockDTO.getCreatedBy());

	        message =
	                "Reconcile Consumption Stock updated successfully";
	    }

	    reconcileConsumptionStockVO =
	            getReconcileConsumptionStockVOFromDTO(
	                    reconcileConsumptionStockDTO,
	                    reconcileConsumptionStockVO);

	    ReconcileConsumptionStockVO savedVO =
	            reconcileConsumptionStockRepo.saveAndFlush(
	                    reconcileConsumptionStockVO);

	    response.put("message", message);

	    response.put(
	            "reconcileConsumptionStockVO",
	            convertToResponse(savedVO));

	    return response;
	}
	
	private ReconcileConsumptionStockVO getReconcileConsumptionStockVOFromDTO(
	        ReconcileConsumptionStockDTO dto,
	        ReconcileConsumptionStockVO vo)
	        throws ApplicationException {

	    vo.setReconcileDate(dto.getReconcileDate());

	    vo.setOrgId(dto.getOrgId());

	    vo.setFinancialYear(dto.getFinancialYear());

	    if (dto.getBranch() != null) {

	        BranchVO branch =
	                branchRepo.findById(dto.getBranch())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Branch Not Found"));

	        vo.setBranch(branch);
	    }

	    if (dto.getShopFloor() != null) {

	        LocationVO shopFloor =
	                locationRepo.findById(dto.getShopFloor())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "Shop Floor Not Found"));

	        vo.setShopFloor(shopFloor);
	    }

	    if (dto.getFgItem() != null) {

	        ItemMasterVO fgItem =
	                itemMasterRepo.findById(dto.getFgItem())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "FG Item Not Found"));

	        vo.setFgItem(fgItem);
	    }

	    if (dto.getRmLocation() != null) {

	        LocationVO rmLocation =
	                locationRepo.findById(dto.getRmLocation())
	                        .orElseThrow(() ->
	                                new ApplicationException(
	                                        "RM Location Not Found"));

	        vo.setRmLocation(rmLocation);
	    }

	    if (dto.getDetails() != null) {

	        for (ReconcileConsumptionStockDetailsDTO detailsDTO :
	                dto.getDetails()) {

	            ReconcileConsumptionStockDetailsVO detailsVO =
	                    new ReconcileConsumptionStockDetailsVO();

	            if (detailsDTO.getItem() != null) {

	                ItemMasterVO item =
	                        itemMasterRepo.findById(detailsDTO.getItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Item Not Found"));

	                detailsVO.setItem(item);
	            }

	            if (detailsDTO.getUnit() != null) {

	                UnitMasterVO unit =
	                        unitMasterRepo.findById(detailsDTO.getUnit())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Unit Not Found"));

	                detailsVO.setUnit(unit);
	            }

	            detailsVO.setAvailableQty(
	                    detailsDTO.getAvailableQty());

	            detailsVO.setConsumptionQty(
	                    detailsDTO.getConsumptionQty());

	            detailsVO.setPostedQty(
	                    detailsDTO.getPostedQty());

	            BigDecimal differenceQty = detailsDTO.getDifferenceQty() != null
	                    ? detailsDTO.getDifferenceQty()
	                    : BigDecimal.ZERO;

	            BigDecimal rate = detailsDTO.getRate() != null
	                    ? detailsDTO.getRate()
	                    : BigDecimal.ZERO;

	            BigDecimal value = differenceQty
	                    .multiply(rate)
	                    .setScale(2, RoundingMode.HALF_UP);

	            detailsVO.setDifferenceQty(differenceQty);
	            detailsVO.setRate(rate);
	            detailsVO.setValue(value);

	            detailsVO.setReconcileConsumptionStockVO(vo);

	            vo.getDetails().add(detailsVO);
	        }
	    }

	    return vo;
	}
	
	private ReconcileConsumptionStockResponseDTO convertToResponse(
	        ReconcileConsumptionStockVO vo) {

	    ReconcileConsumptionStockResponseDTO response =
	            new ReconcileConsumptionStockResponseDTO();

	    response.setId(vo.getId());
	    response.setDocId(vo.getDocId());
	    response.setDocDate(vo.getDocDate());
	    response.setReconcileDate(vo.getReconcileDate());

	    response.setCreatedBy(vo.getCreatedBy());
	    response.setActive(vo.isActive());
	    response.setCancel(vo.isCancel());
	    response.setUpdatedBy(vo.getUpdatedBy());
	    response.setCancelRemarks(vo.getCancelRemarks());
	    response.setScreenName(vo.getScreenName());
	    response.setScreenCode(vo.getScreenCode());
	    response.setOrgId(vo.getOrgId());
	    response.setFinancialYear(vo.getFinancialYear());

	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch =
	                new BranchResponseDTO();

	        branch.setId(vo.getBranch().getId());
	        branch.setBranchCode(
	                vo.getBranch().getBranchCode());
	        branch.setBranchName(
	                vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }

	    if (vo.getShopFloor() != null) {

	        LocationMasterResponseDTO location =
	                new LocationMasterResponseDTO();

	        location.setId(vo.getShopFloor().getId());
	        location.setLocationName(
	                vo.getShopFloor().getLocationName());

	        response.setShopFloor(location);
	    }

	    if (vo.getFgItem() != null) {

	        ItemResponse1DTO item =
	                new ItemResponse1DTO();

	        item.setId(vo.getFgItem().getId());
	        item.setItemCode(
	                vo.getFgItem().getItemCode());
	        item.setItemDescription(
	                vo.getFgItem().getItemDescription());

	        if (vo.getFgItem().getPurchaseUnit() != null) {

	            UnitMasterResponseDTO unit =
	                    new UnitMasterResponseDTO();

	            unit.setId(
	                    vo.getFgItem()
	                            .getPurchaseUnit()
	                            .getId());

	            unit.setUnitId(
	                    vo.getFgItem()
	                            .getPurchaseUnit()
	                            .getUnitId());

	            unit.setUnitDescription(
	                    vo.getFgItem()
	                            .getPurchaseUnit()
	                            .getDescription());

	            item.setUnit(unit);
	        }

	        response.setFgItem(item);
	    }

	    if (vo.getRmLocation() != null) {

	        LocationMasterResponseDTO location =
	                new LocationMasterResponseDTO();

	        location.setId(vo.getRmLocation().getId());

	        location.setLocationName(
	                vo.getRmLocation().getLocationName());

	        response.setRmLocation(location);
	    }

	    List<ReconcileConsumptionStockDetailsResponseDTO> detailsList =
	            new ArrayList<>();

	    if (vo.getDetails() != null &&
	            !vo.getDetails().isEmpty()) {

	        for (ReconcileConsumptionStockDetailsVO detailsVO :
	                vo.getDetails()) {

	            ReconcileConsumptionStockDetailsResponseDTO detailsResponse =
	                    new ReconcileConsumptionStockDetailsResponseDTO();

	            detailsResponse.setId(detailsVO.getId());

	            detailsResponse.setAvailableQty(
	                    detailsVO.getAvailableQty());

	            detailsResponse.setConsumptionQty(
	                    detailsVO.getConsumptionQty());

	            detailsResponse.setPostedQty(
	                    detailsVO.getPostedQty());

	            detailsResponse.setDifferenceQty(
	                    detailsVO.getDifferenceQty());

	            detailsResponse.setRate(
	                    detailsVO.getRate());

	            detailsResponse.setValue(
	                    detailsVO.getValue());

	            if (detailsVO.getItem() != null) {

	                ItemResponse1DTO item =
	                        new ItemResponse1DTO();

	                item.setId(detailsVO.getItem().getId());
	                item.setItemCode(
	                        detailsVO.getItem().getItemCode());
	                item.setItemDescription(
	                        detailsVO.getItem().getItemDescription());

	                detailsResponse.setItem(item);
	            }

	            if (detailsVO.getUnit() != null) {

	                UnitMasterResponseDTO unit =
	                        new UnitMasterResponseDTO();

	                unit.setId(detailsVO.getUnit().getId());
	                unit.setUnitId(
	                        detailsVO.getUnit().getUnitId());
	                unit.setUnitDescription(
	                        detailsVO.getUnit().getDescription());

	                detailsResponse.setUnit(unit);
	            }

	            detailsList.add(detailsResponse);
	        }
	    }

	    response.setDetails(detailsList);

	    return response;
	}
	
	
	@Override
	public ReconcileConsumptionStockResponseDTO getReconcileConsumptionStockById(
	        Long id) throws ApplicationException {

	    ReconcileConsumptionStockVO vo =
	            reconcileConsumptionStockRepo.findById(id)
	                    .orElseThrow(() ->
	                            new ApplicationException(
	                                    "Reconcile Consumption Stock Not Found"));

	    return convertToResponse(vo);
	}
	
	@Override
	public List<ReconcileConsumptionStockResponseDTO>
	        getReconcileConsumptionStockByOrgIdAndBranch(
	                Long orgId, Long branch)
	                throws ApplicationException {

	    List<ReconcileConsumptionStockVO> list =
	            reconcileConsumptionStockRepo
	                    .findByOrgIdAndBranch(orgId, branch);

	    List<ReconcileConsumptionStockResponseDTO> responseList =
	            new ArrayList<>();

	    for (ReconcileConsumptionStockVO vo : list) {

	        responseList.add(
	                convertToResponse(vo));
	    }

	    return responseList;
	}
	
	@Override
	public String getReconcileConsumptionStockDocId(
	        Long orgId, String financialYear) {

	    String screenCode = "RCS";

	    return reconcileConsumptionStockRepo
	            .getReconcileConsumptionStockDocId(
	                    orgId,
	                    financialYear,
	                    screenCode);
	}
	
	
	
}
