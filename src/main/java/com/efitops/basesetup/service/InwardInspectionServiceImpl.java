package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.InwardInspectionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InwardInspectionFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InwardInspectionMeasurementsResponseDTO;
import com.efitops.basesetup.ResponseDTO.InwardInspectionResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseInwardDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.InwardInspectionDTO;
import com.efitops.basesetup.dto.InwardInspectionDetailsDTO;
import com.efitops.basesetup.dto.InwardInspectionMeasurementsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.InwardInspectionDetailsVO;
import com.efitops.basesetup.entity.InwardInspectionFileUploadDetailsVO;
import com.efitops.basesetup.entity.InwardInspectionMeasurementsVO;
import com.efitops.basesetup.entity.InwardInspectionVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.InwardInspectionDetailsRepo;
import com.efitops.basesetup.repository.InwardInspectionFileUploadDetailsRepo;
import com.efitops.basesetup.repository.InwardInspectionMeasurementsRepo;
import com.efitops.basesetup.repository.InwardInspectionRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class InwardInspectionServiceImpl implements InwardInspectionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardInspectionServiceImpl.class);

	@Autowired
	private InwardInspectionRepo inwardInspectionRepo;

	@Autowired
	private InwardInspectionDetailsRepo inwardInspectionDetailsRepo;

	@Autowired
	private InwardInspectionMeasurementsRepo inwardInspectionMeasurementsRepo;

	@Autowired
	private InwardInspectionFileUploadDetailsRepo inwardInspectionFileUploadDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Value("${inward.inspection.upload.path}")
	private String uploadPath;

	@Override
	public InwardInspectionResponseDTO getInwardInspectionById(Long id) throws ApplicationException {
		InwardInspectionVO inwardInspectionVO = inwardInspectionRepo.getInwardInspectionById(id);
		if (inwardInspectionVO == null) {
			throw new ApplicationException("Inward Invoice Not Found");
		}

		return buildInwardInspectionResponse(inwardInspectionVO);
	}

	@Override
	public List<InwardInspectionResponseDTO> getInwardInspectionByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<InwardInspectionVO> inwardInspectionList = inwardInspectionRepo.getInwardInspectionByOrgId(orgId, branch);
		if (inwardInspectionList == null || inwardInspectionList.isEmpty()) {
			throw new ApplicationException("Inward Inspection Not Found");
		}
		List<InwardInspectionResponseDTO> responseList = new ArrayList<>();
		for (InwardInspectionVO inwardInspectionVO : inwardInspectionList) {
			responseList.add(buildInwardInspectionResponse(inwardInspectionVO));
		}
		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateInwardInspection(InwardInspectionDTO inwardInspectionDTO,
			MultipartFile[] files) throws ApplicationException {
		InwardInspectionVO inwardInspectionVO;
		String message;

		if (ObjectUtils.isNotEmpty(inwardInspectionDTO.getId())) {
			inwardInspectionVO = inwardInspectionRepo.findById(inwardInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Inward Inspection Not Found"));

			inwardInspectionVO.setUpdatedBy(inwardInspectionDTO.getCreatedBy());
			message = "Inward Inspection Updated Successfully";
		} else {
			inwardInspectionVO = new InwardInspectionVO();
			String screenCode = "II";
			String docId = inwardInspectionRepo.getInwardInspectionDocId(inwardInspectionDTO.getOrgId(),
					inwardInspectionDTO.getFinancialYear(), screenCode);
			inwardInspectionVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(inwardInspectionDTO.getOrgId(),
							inwardInspectionDTO.getFinancialYear(), screenCode);
			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			inwardInspectionVO.setCreatedBy(inwardInspectionDTO.getCreatedBy());
			inwardInspectionVO.setUpdatedBy(inwardInspectionDTO.getCreatedBy());
			message = "Inward Inspection Created Successfully";
		}
		setInwardInspectionValues(inwardInspectionDTO, inwardInspectionVO);

		inwardInspectionVO = inwardInspectionRepo.save(inwardInspectionVO);
		saveAttachments(files, inwardInspectionVO);

		InwardInspectionResponseDTO inwardInspectionResponse = buildInwardInspectionResponse(inwardInspectionVO);
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("inwardInspectionVO", inwardInspectionResponse);
		return response;
	}

	private void setInwardInspectionValues(InwardInspectionDTO dto, InwardInspectionVO vo) throws ApplicationException {

		vo.setInwardType(dto.getInwardType());
		vo.setMrinGrnNo(dto.getMrinGrnNo());
		vo.setMrinGrnDate(dto.getMrinGrnDate());
		vo.setIsoExpiaryDate(dto.getIsoExpiaryDate());
		vo.setPoPcJoNo(dto.getPoPcJoNo());
		vo.setPpapSample(dto.getPpapSample());
		vo.setScheduleNo(dto.getScheduleNo());
		vo.setSupInvNo(dto.getSupInvNo());
		vo.setSupInvDt(dto.getSupInvDt());
		vo.setConsiderations(dto.getConsiderations());
		vo.setDisposalAction(dto.getDisposalAction());
		vo.setResult(dto.getResult());
		vo.setNotes(dto.getNotes());

		vo.setActive(dto.isActive());
		vo.setCancel(dto.isCancel());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (dto.getSupplierCode() != null && dto.getSupplierCode() != 0) {
			CustomerVO supplier = customerRepo.findById(dto.getSupplierCode())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));
			vo.setSupplierCode(supplier);
		}

		if (dto.getCheckedBy() != null && dto.getCheckedBy() != 0) {
			EmployeeMasterVO checkedBy = employeeMasterRepo.findById(dto.getCheckedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));
			vo.setCheckedBy(checkedBy);
		}

		if (dto.getApprovedBy() != null && dto.getApprovedBy() != 0) {
			EmployeeMasterVO approvedBy = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));
			vo.setApprovedBy(approvedBy);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<InwardInspectionDetailsVO> oldDetails = inwardInspectionDetailsRepo.findByInwardInspectionVO(vo);
			inwardInspectionDetailsRepo.deleteAll(oldDetails);
			List<InwardInspectionFileUploadDetailsVO> oldFileDetails = inwardInspectionFileUploadDetailsRepo
					.findByInwardInspectionVO(vo);
			inwardInspectionFileUploadDetailsRepo.deleteAll(oldFileDetails);
		}

		List<InwardInspectionDetailsVO> detailsList = new ArrayList<>();
		if (dto.getInwardInspectionDetailsDTO() != null) {
			for (InwardInspectionDetailsDTO detailDTO : dto.getInwardInspectionDetailsDTO()) {
				InwardInspectionDetailsVO detailVO = new InwardInspectionDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailVO.setItem(item);
				}

				if (detailDTO.getPurchaseUnit() != null && detailDTO.getPurchaseUnit() > 0) {
					UnitMasterVO purchaseUnit = unitMasterRepo.findById(detailDTO.getPurchaseUnit())
							.orElseThrow(() -> new ApplicationException("Purchase Unit Not Found"));
					detailVO.setPurchaseUnit(purchaseUnit);
				}

				if (detailDTO.getPrimaryUnit() != null && detailDTO.getPrimaryUnit() > 0) {
					UnitMasterVO primaryUnit = unitMasterRepo.findById(detailDTO.getPrimaryUnit())
							.orElseThrow(() -> new ApplicationException("Primary Unit Not Found"));
					detailVO.setPrimaryUnit(primaryUnit);
				}

				if (detailDTO.getReceivedUnit() != null && detailDTO.getReceivedUnit() > 0) {
					UnitMasterVO receivedUnit = unitMasterRepo.findById(detailDTO.getReceivedUnit())
							.orElseThrow(() -> new ApplicationException("Received Unit Not Found"));
					detailVO.setReceivedUnit(receivedUnit);
				}

				if (detailDTO.getAcceptUnit() != null && detailDTO.getAcceptUnit() > 0) {
					UnitMasterVO acceptUnit = unitMasterRepo.findById(detailDTO.getAcceptUnit())
							.orElseThrow(() -> new ApplicationException("Accept Unit Not Found"));
					detailVO.setAcceptUnit(acceptUnit);
				}

				if (detailDTO.getRejectUnit() != null && detailDTO.getRejectUnit() > 0) {
					UnitMasterVO rejectUnit = unitMasterRepo.findById(detailDTO.getRejectUnit())
							.orElseThrow(() -> new ApplicationException("Reject Unit Not Found"));
					detailVO.setRejectUnit(rejectUnit);
				}

				if (detailDTO.getReworkUnit() != null && detailDTO.getReworkUnit() > 0) {
					UnitMasterVO reworkUnit = unitMasterRepo.findById(detailDTO.getReworkUnit())
							.orElseThrow(() -> new ApplicationException("Rework Unit Not Found"));
					detailVO.setReworkUnit(reworkUnit);
				}

				if (detailDTO.getReceivedLocation() != null && detailDTO.getReceivedLocation() > 0) {
					LocationVO receivedLocation = locationRepo.findById(detailDTO.getReceivedLocation())
							.orElseThrow(() -> new ApplicationException("ReceivedLocation Not Found"));
					detailVO.setReceivedLocation(receivedLocation);
				}

				if (detailDTO.getReworkLocation() != null && detailDTO.getReworkLocation() > 0) {
					LocationVO reworkLocation = locationRepo.findById(detailDTO.getReworkLocation())
							.orElseThrow(() -> new ApplicationException("ReworkLocation Not Found"));
					detailVO.setReworkLocation(reworkLocation);
				}

				if (detailDTO.getRejectedLocation() != null && detailDTO.getRejectedLocation() > 0) {
					LocationVO reworkLocation = locationRepo.findById(detailDTO.getRejectedLocation())
							.orElseThrow(() -> new ApplicationException("RejectLocation Not Found"));
					detailVO.setRejectedLocation(reworkLocation);
				}

				detailVO.setInspection(detailDTO.getInspection());
				detailVO.setStk(detailDTO.getStk());
				detailVO.setDrawingNo(detailDTO.getDrawingNo());
				detailVO.setOrderQty(detailDTO.getOrderQty());
				detailVO.setReceivedQty(detailDTO.getReceivedQty());
				detailVO.setAcceptQty(detailDTO.getAcceptQty());
				detailVO.setInspectionReportReceived(detailDTO.getInspectionReportReceived());
				detailVO.setTcReceived(detailDTO.getTcReceived());
				detailVO.setBatchNo(detailDTO.getBatchNo());
				detailVO.setQtyAccOnDevtn(detailDTO.getQtyAccOnDevtn());
				detailVO.setAccQtyAfterSegn(detailDTO.getAccQtyAfterSegn());
				detailVO.setReworkQty(detailDTO.getReworkQty());
				detailVO.setTotAccQty(detailDTO.getTotAccQty());
				detailVO.setConversionFactor(detailDTO.getConversionFactor());
				detailVO.setTotalAccQtyInPrimaryUnit(detailDTO.getTotalAccQtyInPrimaryUnit());
				detailVO.setRejectQty(detailDTO.getRejectQty());
				detailVO.setReason(detailDTO.getReason());
				detailVO.setRate(detailDTO.getRate());
				detailVO.setTotalReceivedQty(detailDTO.getTotalReceivedQty());
				detailVO.setSampleSize(detailDTO.getSampleSize());

				detailVO.setAmount(detailDTO.getTotalAccQtyInPrimaryUnit().multiply(detailDTO.getRate()));
//
//				if (ObjectUtils.isNotEmpty(detailVO.getId())) {
//
//					List<InwardInspectionMeasurementsVO> oldFileDetails = inwardInspectionMeasurementsRepo
//							.findByInwardInspectionDetailsVO(detailVO);
//					inwardInspectionMeasurementsRepo.deleteAll(oldFileDetails);
//				}

				List<InwardInspectionMeasurementsVO> measurementsList = new ArrayList<>();
				if (detailDTO.getInwardInspectionMeasurementsDTO() != null) {
					for (InwardInspectionMeasurementsDTO measurementDTO : detailDTO
							.getInwardInspectionMeasurementsDTO()) {
						InwardInspectionMeasurementsVO measurementVO = new InwardInspectionMeasurementsVO();
						measurementVO.setParameters(measurementDTO.getParameters());
						measurementVO.setType(measurementDTO.getType());
						measurementVO.setSpec(measurementDTO.getSpec());
						measurementVO.setAccCriteria(measurementDTO.getAccCriteria());
						measurementVO.setUom(measurementDTO.getUom());
						measurementVO.setTest1(measurementDTO.getTest1());
						measurementVO.setTest2(measurementDTO.getTest2());
						measurementVO.setTest3(measurementDTO.getTest3());
						measurementVO.setTest4(measurementDTO.getTest4());
						measurementVO.setTest5(measurementDTO.getTest5());
						measurementVO.setStatus(measurementDTO.getStatus());
						measurementVO.setRemarks(measurementDTO.getRemarks());
						measurementVO.setInwardInspectionDetailsVO(detailVO);
						measurementsList.add(measurementVO);
					}
				}
				detailVO.setInwardInspectionMeasurementsVO(measurementsList);

				detailVO.setInwardInspectionVO(vo);
				detailsList.add(detailVO);
			}
		}
		vo.setInwardInspectionDetailsVO(detailsList);
	}

	private InwardInspectionResponseDTO buildInwardInspectionResponse(InwardInspectionVO vo) {
		InwardInspectionResponseDTO responseDTO = new InwardInspectionResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setInwardType(vo.getInwardType());
		responseDTO.setMrinGrnNo(vo.getMrinGrnNo());
		responseDTO.setMrinGrnDate(vo.getMrinGrnDate());
		responseDTO.setTimeOfInspection(vo.getTimeOfInspection());
		responseDTO.setGrnTime(vo.getGrnTime());
		responseDTO.setIsoExpiaryDate(vo.getIsoExpiaryDate());
		responseDTO.setPoPcJoNo(vo.getPoPcJoNo());
		responseDTO.setPpapSample(vo.getPpapSample());
		responseDTO.setScheduleNo(vo.getScheduleNo());
		responseDTO.setSupInvNo(vo.getSupInvNo());
		responseDTO.setSupInvDt(vo.getSupInvDt());
		responseDTO.setConsiderations(vo.getConsiderations());
		responseDTO.setDisposalAction(vo.getDisposalAction());
		responseDTO.setResult(vo.getResult());
		responseDTO.setNotes(vo.getNotes());

		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (vo.getSupplierCode() != null) {
			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
			supplierDTO.setId(vo.getSupplierCode().getId());
			supplierDTO.setSupplierName(vo.getSupplierCode().getCustomerName());
			supplierDTO.setSupplierCode(vo.getSupplierCode().getCustomerCode());
			supplierDTO.setAddress(vo.getSupplierCode().getAddress());
			supplierDTO.setGstNo(vo.getSupplierCode().getGstNo());
			supplierDTO.setGstApproval(vo.getSupplierCode().isGstApplicable() ? "Yes" : "No");
			if (vo.getSupplierCode().getGstState() != null) {
				supplierDTO.setGstSate(vo.getSupplierCode().getGstState().getStateName());
			}
			responseDTO.setSupplierCode(supplierDTO);
		}

		if (vo.getCheckedBy() != null) {
			EmployeeMasterResponseDetailsDTO checkedByDTO = new EmployeeMasterResponseDetailsDTO();
			checkedByDTO.setId(vo.getCheckedBy().getId());
			checkedByDTO.setEmployeeName(vo.getCheckedBy().getEmployeeName());
			responseDTO.setCheckedBy(checkedByDTO);
		}

		if (vo.getApprovedBy() != null) {
			EmployeeMasterResponseDetailsDTO approvedByDTO = new EmployeeMasterResponseDetailsDTO();
			approvedByDTO.setId(vo.getApprovedBy().getId());
			approvedByDTO.setEmployeeName(vo.getApprovedBy().getEmployeeName());
			responseDTO.setApprovedBy(approvedByDTO);
		}

		List<InwardInspectionDetailsResponseDTO> detailsResponseList = new ArrayList<>();
		if (vo.getInwardInspectionDetailsVO() != null && !vo.getInwardInspectionDetailsVO().isEmpty()) {
			for (InwardInspectionDetailsVO detailVO : vo.getInwardInspectionDetailsVO()) {
				InwardInspectionDetailsResponseDTO detailResponse = new InwardInspectionDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());
				detailResponse.setInspection(detailVO.getInspection());
				detailResponse.setStk(detailVO.getStk());
				detailResponse.setDrawingNo(detailVO.getDrawingNo());
				detailResponse.setOrderQty(detailVO.getOrderQty());
				detailResponse.setReceivedQty(detailVO.getReceivedQty());
				detailResponse.setAcceptQty(detailVO.getAcceptQty());
				detailResponse.setInspectionReportReceived(detailVO.getInspectionReportReceived());
				detailResponse.setTcReceived(detailVO.getTcReceived());
				detailResponse.setBatchNo(detailVO.getBatchNo());
				detailResponse.setQtyAccOnDevtn(detailVO.getQtyAccOnDevtn());
				detailResponse.setAccQtyAfterSegn(detailVO.getAccQtyAfterSegn());
				detailResponse.setReworkQty(detailVO.getReworkQty());
				detailResponse.setTotAccQty(detailVO.getTotAccQty());
				detailResponse.setConversionFactor(detailVO.getConversionFactor());
				detailResponse.setTotalAccQtyInPrimaryUnit(detailVO.getTotalAccQtyInPrimaryUnit());
				detailResponse.setRejectQty(detailVO.getRejectQty());
				detailResponse.setReason(detailVO.getReason());
				detailResponse.setRate(detailVO.getRate());
				detailResponse.setAmount(detailVO.getAmount());
				detailResponse.setTotalReceivedQty(detailVO.getTotalReceivedQty());
				detailResponse.setSampleSize(detailVO.getSampleSize());

				if (detailVO.getItem() != null) {
					ItemMasterDetailsResponseInwardDTO itemDTO = new ItemMasterDetailsResponseInwardDTO();
					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					if (detailVO.getItem().getPrimaryUnit() != null) {
						UnitMasterResponseDTO unit = new UnitMasterResponseDTO();
						unit.setId(detailVO.getItem().getPrimaryUnit().getId());
						unit.setUnitId(detailVO.getItem().getPrimaryUnit().getUnitId());
						itemDTO.setItemDescription(detailVO.getItem().getItemDescription());
						itemDTO.setPrimaryUnit(unit);

					}

					if (detailVO.getItem().getPurchaseUnit() != null) {
						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();
						unitDTO.setId(detailVO.getItem().getPurchaseUnit().getId());
						unitDTO.setUnitId(detailVO.getItem().getPurchaseUnit().getUnitId());
						unitDTO.setUnitDescription(detailVO.getItem().getPurchaseUnit().getDescription());
						itemDTO.setPurchaseUnit(unitDTO);
					}
					detailResponse.setItem(itemDTO);
				}

				if (detailVO.getReceivedUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getReceivedUnit().getId());
					unitDTO.setUnitId(detailVO.getReceivedUnit().getUnitId());
					detailResponse.setReceivedUnit(unitDTO);
				}

				if (detailVO.getAcceptUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getAcceptUnit().getId());
					unitDTO.setUnitId(detailVO.getAcceptUnit().getUnitId());
					detailResponse.setAcceptUnit(unitDTO);
				}

				if (detailVO.getRejectUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getRejectUnit().getId());
					unitDTO.setUnitId(detailVO.getRejectUnit().getUnitId());
					detailResponse.setRejectUnit(unitDTO);
				}

				if (detailVO.getReworkUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getReworkUnit().getId());
					unitDTO.setUnitId(detailVO.getReworkUnit().getUnitId());
					detailResponse.setReworkUnit(unitDTO);
				}

				if (detailVO.getReceivedLocation() != null) {
					LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();
					locationDTO.setId(detailVO.getReceivedLocation().getId());
					locationDTO.setLocationName(detailVO.getReceivedLocation().getLocationName());
					detailResponse.setReceivedLocation(locationDTO);
				}

				if (detailVO.getReworkLocation() != null) {
					LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();
					locationDTO.setId(detailVO.getReworkLocation().getId());
					locationDTO.setLocationName(detailVO.getReworkLocation().getLocationName());
					detailResponse.setReworkLocation(locationDTO);
				}

				if (detailVO.getRejectedLocation() != null) {
					LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();
					locationDTO.setId(detailVO.getRejectedLocation().getId());
					locationDTO.setLocationName(detailVO.getRejectedLocation().getLocationName());
					detailResponse.setRejectedLocation(locationDTO);
				}

				List<InwardInspectionMeasurementsResponseDTO> measurementsResponseList = new ArrayList<>();
				if (detailVO.getInwardInspectionMeasurementsVO() != null
						&& !detailVO.getInwardInspectionMeasurementsVO().isEmpty()) {
					for (InwardInspectionMeasurementsVO measurementVO : detailVO.getInwardInspectionMeasurementsVO()) {
						InwardInspectionMeasurementsResponseDTO measurementResponse = new InwardInspectionMeasurementsResponseDTO();
						measurementResponse.setId(measurementVO.getId());
						measurementResponse.setParameters(measurementVO.getParameters());
						measurementResponse.setType(measurementVO.getType());
						measurementResponse.setSpec(measurementVO.getSpec());
						measurementResponse.setAccCriteria(measurementVO.getAccCriteria());
						measurementResponse.setUom(measurementVO.getUom());
						measurementResponse.setTest1(measurementVO.getTest1());
						measurementResponse.setTest2(measurementVO.getTest2());
						measurementResponse.setTest3(measurementVO.getTest3());
						measurementResponse.setTest4(measurementVO.getTest4());
						measurementResponse.setTest5(measurementVO.getTest5());
						measurementResponse.setStatus(measurementVO.getStatus());
						measurementResponse.setRemarks(measurementVO.getRemarks());
						measurementsResponseList.add(measurementResponse);
					}
				}
				detailResponse.setInwardInspectionMeasurementsResponseDTO(measurementsResponseList);

				detailsResponseList.add(detailResponse);
			}
		}
		responseDTO.setInwardInspectionDetailsResponseDTO(detailsResponseList);

		List<InwardInspectionFileUploadDetailsResponseDTO> fileResponseList = new ArrayList<>();
		if (vo.getInwardInspectionFileUploadDetailsVO() != null
				&& !vo.getInwardInspectionFileUploadDetailsVO().isEmpty()) {
			for (InwardInspectionFileUploadDetailsVO fileVO : vo.getInwardInspectionFileUploadDetailsVO()) {
				InwardInspectionFileUploadDetailsResponseDTO fileResponse = new InwardInspectionFileUploadDetailsResponseDTO();
				fileResponse.setId(fileVO.getId());
				fileResponse.setName(fileVO.getName());
				fileResponse.setFileName(fileVO.getFileName());
				fileResponse.setFilePath(fileVO.getFilePath());
				fileResponse.setFileSize(fileVO.getFileSize());
				fileResponse.setContentType(fileVO.getContentType());
				fileResponse.setUploadOn(fileVO.getUploadOn());
				fileResponseList.add(fileResponse);
			}
		}
		responseDTO.setInwardInspectionFileUploadDetailsResponseDTO(fileResponseList);

		return responseDTO;
	}

	private void saveAttachments(MultipartFile[] files, InwardInspectionVO inwardInspectionVO)
			throws ApplicationException {
		if (files == null || files.length == 0) {
			return;
		}

		try {
			Path inwardFolder = Paths.get(uploadPath, "inwardinspection", inwardInspectionVO.getId().toString());
			createDirectory(inwardFolder);

			if (ObjectUtils.isNotEmpty(inwardInspectionVO.getId())) {
				List<InwardInspectionFileUploadDetailsVO> existingAttachments = inwardInspectionFileUploadDetailsRepo
						.findByInwardInspectionVO(inwardInspectionVO);
				if (existingAttachments != null && !existingAttachments.isEmpty()) {
					inwardInspectionFileUploadDetailsRepo.deleteAll(existingAttachments);
				}
			}

			List<InwardInspectionFileUploadDetailsVO> attachmentList = new ArrayList<>();
			for (MultipartFile file : files) {
				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}
				originalName = originalName.replaceAll("\\s+", "_");

				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + inwardInspectionVO.getId() + extension;
				Path filePath = inwardFolder.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/inwardinspection/viewFile/").toUriString();
				String relativePath = uploadPath.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				String publicUrl = baseUrl + relativePath;

				InwardInspectionFileUploadDetailsVO attachment = new InwardInspectionFileUploadDetailsVO();
				attachment.setInwardInspectionVO(inwardInspectionVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());
				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {
				List<InwardInspectionFileUploadDetailsVO> saved = inwardInspectionFileUploadDetailsRepo
						.saveAll(attachmentList);
				inwardInspectionVO.setInwardInspectionFileUploadDetailsVO(saved);
			}

		} catch (IOException e) {
			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private void createDirectory(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewInwardInspectionFile(HttpServletRequest request) throws IOException {
		return serveFile(request, "/api/inwardinspection/viewFile/", uploadPath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException {
		String uri = request.getRequestURI();
		String relativePath = uri.replace(apiPrefix, "");
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	@Override
	public String getInwardInspectionDocId(Long orgId, String financialYear) {
		String screenCode = "II";
		return inwardInspectionRepo.getInwardInspectionDocId(orgId, financialYear, screenCode);
	}

//	@Override
//	public List<Map<String, Object>> getSupplierDetailsForInwardInspection(Long orgId, Long branch) {
//		Set<Object[]> supplierDetails = inwardInspectionRepo.getSupplierDetailsForInwardInspection(orgId, branch);
//		return getSupplierDetailsResponse(supplierDetails);
//	}
//
//	private List<Map<String, Object>> getSupplierDetailsResponse(Set<Object[]> supplierDetails) {
//		List<Map<String, Object>> list = new ArrayList<>();
//		for (Object[] ch : supplierDetails) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("supplierId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
//			map.put("supplierName", ch[1] != null ? ch[1].toString() : "");
//			map.put("supplierCode", ch[2] != null ? ch[2].toString() : "");
//			map.put("address", ch[3] != null ? ch[3].toString() : "");
//			map.put("pinCode", ch[4] != null ? ch[4].toString() : "");
//			map.put("gstNo", ch[5] != null ? ch[5].toString() : "");
//			map.put("stateName", ch[6] != null ? ch[6].toString() : "");
//			map.put("isRegistered", ch[7] != null ? ch[7].toString() : "");
//			map.put("country", ch[8] != null ? ch[8].toString() : "");
//			list.add(map);
//		}
//		return list;
//	}
}