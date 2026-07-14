package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.DrawingMasterAttachmentsDTO;
import com.efitops.basesetup.dto.DrawingMasterDTO;
import com.efitops.basesetup.dto.DrawingMasterDetailsDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.MachineCapacityDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.MachineTechnicalInfoDTO;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DrawingMasterAttachmentsVO;
import com.efitops.basesetup.entity.DrawingMasterDetailsVO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineCapacityVO;
import com.efitops.basesetup.entity.MachineMasterAttachmentVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineTechnicalInfoVO;
import com.efitops.basesetup.entity.PurchaseQuotationImagesVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.entity.StockLocationRepo;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.CompanyRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.DrawingMasterAttachmentsRepo;
import com.efitops.basesetup.repo.DrawingMasterDetailsRepo;
import com.efitops.basesetup.repo.DrawingMasterRepo;
import com.efitops.basesetup.repo.MachineCapacityRepo;
import com.efitops.basesetup.repo.MachineMasterAttachmentRepo;
import com.efitops.basesetup.repo.MachineMasterRepo;
import com.efitops.basesetup.repo.MachineTechnicalInfoRepo;

@Service
public class MachineMasterServiceImpl implements MachineMasterService {

	@Autowired
	MachineMasterRepo machineMasterRepo;

	@Autowired
	MachineTechnicalInfoRepo machineTechnicalInfoRepo;

	@Autowired
	MachineCapacityRepo machineCapacityRepo;

	@Autowired
	StockLocationRepo stockLocationRepo;

	@Autowired
	DrawingMasterRepo drawingMasterRepo;

	@Autowired
	DrawingMasterDetailsRepo drawingMasterDetailsRepo;

	@Autowired
	DrawingMasterAttachmentsRepo drawingMasterAttachmentsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	MachineMasterAttachmentRepo machineMasterAttachmentRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(MachineMasterServiceImpl.class);

	@Override
	public Map<String, Object> updateCreateMachineMaster(@Valid MachineMasterDTO machineMasterDTO)
			throws ApplicationException {

		MachineMasterVO machineMasterVO;
		String screenCode = "MM";
		String message = null;

		if (ObjectUtils.isEmpty(machineMasterDTO.getId())) {

			machineMasterVO = new MachineMasterVO();

			machineMasterVO.setCreatedBy(machineMasterDTO.getCreatedBy());
			machineMasterVO.setUpdatedBy(machineMasterDTO.getCreatedBy());

			String docId = machineMasterRepo.getMachineMasterByDocId(machineMasterDTO.getOrgId(),
					machineMasterDTO.getFinYear(), machineMasterDTO.getBranchCode(), screenCode);

			machineMasterVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(machineMasterDTO.getOrgId(),
							machineMasterDTO.getFinYear(), machineMasterDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "MachineMaster Creation Success";
		}

		else {
			machineMasterVO = machineMasterRepo.findById(machineMasterDTO.getId()).orElseThrow(
					() -> new ApplicationException("Machine Master Not Found with id: " + machineMasterDTO.getId()));
			machineMasterVO.setUpdatedBy(machineMasterDTO.getCreatedBy());

			message = "MachineMaster Updation Successfully";
		}

		machineMasterVO = getMachineMasterVOFrommachineMasterDTO(machineMasterVO, machineMasterDTO);
		machineMasterRepo.save(machineMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("machineMasterVO", machineMasterVO);
		return response;

	}

	private MachineMasterVO getMachineMasterVOFrommachineMasterDTO(MachineMasterVO machineMasterVO,
			@Valid MachineMasterDTO machineMasterDTO) throws ApplicationException {

		machineMasterVO.setDepartment(machineMasterDTO.getDepartment());
		machineMasterVO.setType(machineMasterDTO.getType());
		machineMasterVO.setMachineNo(machineMasterDTO.getMachineNo());
		machineMasterVO.setMachineName(machineMasterDTO.getMachineName());
		machineMasterVO.setCalibrationRequired(machineMasterDTO.isCalibrationRequired());
		machineMasterVO.setLocation(machineMasterDTO.getLocation());
		machineMasterVO.setProcessNo(machineMasterDTO.getProcessNo());
		machineMasterVO.setMachineCategory(machineMasterDTO.getMachineCategory());
		machineMasterVO.setSection(machineMasterDTO.getSection());
		machineMasterVO.setModel(machineMasterDTO.getModel());
		machineMasterVO.setSerialNo(machineMasterDTO.getSerialNo());
		machineMasterVO.setStatus(machineMasterDTO.getStatus());
		machineMasterVO.setManufacturedBy(machineMasterDTO.getManufacturedBy());
		machineMasterVO.setMadeIn(machineMasterDTO.getMadeIn());
		machineMasterVO.setPurchasedFrom(machineMasterDTO.getPurchasedFrom());
		machineMasterVO.setModeOfPurchase(machineMasterDTO.getModeOfPurchase());
		machineMasterVO.setMachineIncharge(machineMasterDTO.getMachineIncharge());
		machineMasterVO.setMachineUsedFor(machineMasterDTO.getMachineUsedFor());
		machineMasterVO.setPmCheckListNo(machineMasterDTO.getPmCheckListNo());
		machineMasterVO.setRemarks(machineMasterDTO.getRemarks());
		machineMasterVO.setOrgId(machineMasterDTO.getOrgId());
		machineMasterVO.setInstrumentName(machineMasterDTO.getInstrumentName());
		machineMasterVO.setFilePath(machineMasterDTO.getFilePath());
		machineMasterVO.setBranch(machineMasterDTO.getBranch());
		machineMasterVO.setBranchCode(machineMasterDTO.getBranchCode());
		machineMasterVO.setFinYear(machineMasterDTO.getFinYear());

		if (machineMasterDTO.getId() != null) {

			List<MachineTechnicalInfoVO> machineTechnicalInfoVOs = machineTechnicalInfoRepo
					.findByMachineMasterVO(machineMasterVO);
			machineTechnicalInfoRepo.deleteAll(machineTechnicalInfoVOs);

			List<MachineCapacityVO> machineCapacityVOs = machineCapacityRepo.findByMachineMasterVO(machineMasterVO);
			machineCapacityRepo.deleteAll(machineCapacityVOs);

//			List<MachineMasterVO3> machineMasterVO3s = machineMasterRepo3.findByMachineMasterVO(machineMasterVO);
//			machineMasterRepo3.deleteAll(machineMasterVO3s);

		}

		List<MachineTechnicalInfoVO> MachineTechnicalInfoVOs = new ArrayList<>();
		for (MachineTechnicalInfoDTO machineTechnicalInfoDTO : machineMasterDTO.getMachineTechnicalInfoDTO()) {
			MachineTechnicalInfoVO machineTechnicalInfoVO = new MachineTechnicalInfoVO();

			machineTechnicalInfoVO.setInstallationDate(machineTechnicalInfoDTO.getInstallationDate());

			validateGreaterThanZero(machineTechnicalInfoDTO.getPowerConsumption(), "Power Consumption");
			validateGreaterThanZero(machineTechnicalInfoDTO.getConsumption(), "Consumption");
			validateGreaterThanZero(machineTechnicalInfoDTO.getPowerProduced(), "Power Produced");
			validateGreaterThanZero(machineTechnicalInfoDTO.getCapacity(), "Capacity");

			machineTechnicalInfoVO.setPowerConsumption(machineTechnicalInfoDTO.getPowerConsumption());
			machineTechnicalInfoVO.setConsumption(machineTechnicalInfoDTO.getConsumption());
			machineTechnicalInfoVO.setPowerProduced(machineTechnicalInfoDTO.getPowerProduced());
			machineTechnicalInfoVO.setCapacity(machineTechnicalInfoDTO.getCapacity());

			machineTechnicalInfoVO.setConsumption(machineTechnicalInfoDTO.getConsumption());
			machineTechnicalInfoVO.setPowerProduced(machineTechnicalInfoDTO.getPowerProduced());
			machineTechnicalInfoVO.setCapacity(machineTechnicalInfoDTO.getCapacity());
			machineTechnicalInfoVO.setUnit(machineTechnicalInfoDTO.getUnit());
			machineTechnicalInfoVO.setBedSize(machineTechnicalInfoDTO.getBedSize());
			machineTechnicalInfoVO.setCurrentInAmps(machineTechnicalInfoDTO.getCurrentInAmps());
			machineTechnicalInfoVO.setVoltage(machineTechnicalInfoDTO.getVoltage());
			machineTechnicalInfoVO.setCushionTonnage(machineTechnicalInfoDTO.getCushionTonnage());
			machineTechnicalInfoVO.setMachineType(machineTechnicalInfoDTO.getMachineType());
			machineTechnicalInfoVO.setHourlyRate(machineTechnicalInfoDTO.getHourlyRate());
			machineTechnicalInfoVO.setInstrumentWt(machineTechnicalInfoDTO.getInstrumentWt());
			machineTechnicalInfoVO.setUom(machineTechnicalInfoDTO.getUom());
			machineTechnicalInfoVO.setWarrantyStDate(machineTechnicalInfoDTO.getWarrantyStDate());
			machineTechnicalInfoVO.setWarrantyEndDate(machineTechnicalInfoDTO.getWarrantyEndDate());
			machineTechnicalInfoVO.setLastCalibratedDate(machineTechnicalInfoDTO.getLastCalibratedDate());
			machineTechnicalInfoVO.setNextDueDate(machineTechnicalInfoDTO.getNextDueDate());
			machineTechnicalInfoVO.setLifeCycle(machineTechnicalInfoDTO.getLifeCycle());
			machineTechnicalInfoVO.setRangeInfo(machineTechnicalInfoDTO.getRangeInfo());
			machineTechnicalInfoVO.setErrorAllowed(machineTechnicalInfoDTO.getErrorAllowed());
			machineTechnicalInfoVO.setFrequenceOfCalibration(machineTechnicalInfoDTO.getFrequenceOfCalibration());
			machineTechnicalInfoVO.setMaintenanceDate(machineTechnicalInfoDTO.getMaintenanceDate());

			machineTechnicalInfoVO.setMachineMasterVO(machineMasterVO);
			MachineTechnicalInfoVOs.add(machineTechnicalInfoVO);
		}
		machineMasterVO.setMachineTechnicalInfoVO(MachineTechnicalInfoVOs);

		List<MachineCapacityVO> MachineCapacityVOs = new ArrayList<>();
		for (MachineCapacityDTO machineCapacityDTO : machineMasterDTO.getMachineCapacityDTO()) {
			MachineCapacityVO machineCapacityVO = new MachineCapacityVO();

			machineCapacityVO.setItemDescription(machineCapacityDTO.getItemDescription());
			machineCapacityVO.setCycleTime(machineCapacityDTO.getCycleTime());
			machineCapacityVO.setProdQtyHr(machineCapacityDTO.getProdQtyHr());
			machineCapacityVO.setOperationName(machineCapacityDTO.getOperationName());
			machineCapacityVO.setItemId(machineCapacityDTO.getItemId());

			machineCapacityVO.setRemarks(machineCapacityDTO.getRemarks());

			machineCapacityVO.setMachineMasterVO(machineMasterVO);
			MachineCapacityVOs.add(machineCapacityVO);
		}
		machineMasterVO.setMachineCapacityVO(MachineCapacityVOs);

		return machineMasterVO;

	}

	private void validateGreaterThanZero(Long value, String fieldName) throws ApplicationException {
		if (value == null || value <= 0) {
			throw new ApplicationException(fieldName + " must be greater than zero.");
		}
	}

	@Override
	public String getMachineMasterDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "MM";
		String result = machineMasterRepo.getMachineMasterByDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<MachineMasterVO> getAllMachineMasterByOrgId(Long orgId, String branchCode) {

		return machineMasterRepo.getMachineMasterByOrgId(orgId, branchCode);
	}

	@Override
	public MachineMasterVO getMachineMasterByDocId(Long orgId, String docId) {

		return machineMasterRepo.findALLMachineMasterByDocId(orgId, docId);
	}

	@Override
	public Optional<MachineMasterVO> getAllMachineMasterById(Long id) {

		return machineMasterRepo.getMachineMasterById(id);
	}

	@Override
	public MachineMasterVO uploadMachineAttachementsInBloob(MultipartFile file, Long id) throws IOException {
		MachineMasterVO machineMasterVO = machineMasterRepo.findById(id).get();
		machineMasterVO.setAttachements(file.getBytes());
		return machineMasterRepo.save(machineMasterVO);
	}

	// STOCK LOCATION

	@Override
	public Map<String, Object> updateCreateStockLocation(@Valid StockLocationDTO stockLocationDTO)
			throws ApplicationException {

		StockLocationVO stockLocationVO;

		String message = null;

		if (ObjectUtils.isEmpty(stockLocationDTO.getId())) {

			if (stockLocationRepo.existsByLocationCodeAndOrgId(stockLocationDTO.getLocationCode(),
					stockLocationDTO.getOrgId())) {

				String errorMessage = String.format("This Location Code: %s Already Exists in This Organization",
						stockLocationDTO.getLocationCode());

				throw new ApplicationException(errorMessage);
			}

			if (stockLocationRepo.existsByLocationNameAndOrgId(stockLocationDTO.getLocationName(),
					stockLocationDTO.getOrgId())) {

				String errorMessage = String.format("This Location Name: %s Already Exists in This Organization",
						stockLocationDTO.getLocationName());

				throw new ApplicationException(errorMessage);
			}
			stockLocationVO = new StockLocationVO();

			stockLocationVO.setCreatedBy(stockLocationDTO.getCreatedBy());

			stockLocationVO.setUpdatedBy(stockLocationDTO.getCreatedBy());

			message = "Stock Location Created succesfull";

		}

		else {

			stockLocationVO = stockLocationRepo.findById(stockLocationDTO.getId()).orElseThrow(
					() -> new ApplicationException("Stock Location Not Found with id: " + stockLocationDTO.getId()));
			stockLocationVO.setUpdatedBy(stockLocationDTO.getCreatedBy());

			if (!stockLocationVO.getLocationCode().equalsIgnoreCase(stockLocationDTO.getLocationCode())) {

				if (stockLocationRepo.existsByLocationCodeAndOrgId(stockLocationDTO.getLocationCode(),
						stockLocationDTO.getOrgId())) {

					String errorMessage = String.format("This Location Code: %s Already Exists in This Organization",
							stockLocationDTO.getLocationCode());

					throw new ApplicationException(errorMessage);
				}

				stockLocationVO.setLocationCode(stockLocationDTO.getLocationCode().toUpperCase());
			}

			if (!stockLocationVO.getLocationName().equalsIgnoreCase(stockLocationDTO.getLocationName())) {

				if (stockLocationRepo.existsByLocationNameAndOrgId(stockLocationDTO.getLocationName(),
						stockLocationDTO.getOrgId())) {

					String errorMessage = String.format("This Location Name: %s Already Exists in This Organization",
							stockLocationDTO.getLocationName());

					throw new ApplicationException(errorMessage);
				}

				stockLocationVO.setLocationName(stockLocationDTO.getLocationName().toUpperCase());
			}

			message = "Stock Location Updation Successfully";

		}

		stockLocationVO = getStockLocationVOFromStockLocationDTO(stockLocationVO, stockLocationDTO);
		stockLocationRepo.save(stockLocationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("stockLocationVO", stockLocationVO);
		return response;

	}

	private StockLocationVO getStockLocationVOFromStockLocationDTO(StockLocationVO stockLocationVO,
			@Valid StockLocationDTO stockLocationDTO) throws ApplicationException {

		stockLocationVO.setLocationCode(stockLocationDTO.getLocationCode());
		stockLocationVO.setLocationName(stockLocationDTO.getLocationName());
		stockLocationVO.setCompany(stockLocationDTO.getCompany());
		stockLocationVO.setBranch(stockLocationDTO.getBranch());
		stockLocationVO.setStartDate(stockLocationDTO.getStartDate());
		if (stockLocationDTO.getClosedDate() != null) {
			if (stockLocationDTO.getStartDate() != null
					&& stockLocationDTO.getClosedDate().isBefore(stockLocationDTO.getStartDate())) {
				throw new ApplicationException("Closed date must be after or equal to start date");
			}
			stockLocationVO.setClosedDate(stockLocationDTO.getClosedDate());
		}
		stockLocationVO.setActive(stockLocationDTO.isActive());
		stockLocationVO.setOrgId(stockLocationDTO.getOrgId());

		return stockLocationVO;
	}

	@Override
	public List<StockLocationVO> getAllStockLocationByOrgId(Long orgId) {
		return stockLocationRepo.getStockLocationByOrgId(orgId);
	}

	@Override
	public Optional<StockLocationVO> getAllStockLocationById(Long id) {
		return stockLocationRepo.getStockLocationById(id);
	}

	@Override
	public Map<String, Object> updateDrawingMaster(@Valid DrawingMasterDTO drawingMasterDTO)
			throws ApplicationException {

		DrawingMasterVO drawingMasterVO = new DrawingMasterVO();
		String screenCode = "DM";
		String message = null;

		if (ObjectUtils.isEmpty(drawingMasterDTO.getId())) {

			if (drawingMasterRepo.existsByDrawingNoAndOrgId(drawingMasterDTO.getDrawingNo(),
					drawingMasterDTO.getOrgId())) {
				String errorMessage = String.format("This DrawingNo: %s Already Exists in This Organization",
						drawingMasterDTO.getDrawingNo());
				throw new ApplicationException(errorMessage);
			}

			drawingMasterVO = new DrawingMasterVO();

			// GETDOCID API
			String docId = drawingMasterRepo.getDrawingMasterDocId(drawingMasterDTO.getOrgId(),
					drawingMasterDTO.getFinYear(), drawingMasterDTO.getBranchCode(), screenCode);

			drawingMasterVO.setDocId(docId);

//						        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(drawingMasterDTO.getOrgId(),
							drawingMasterDTO.getFinYear(), drawingMasterDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			drawingMasterVO.setCreatedBy(drawingMasterDTO.getCreatedBy());

			drawingMasterVO.setUpdatedBy(drawingMasterDTO.getCreatedBy());

			message = "Drawing Master Created succesfull";

		}

		else {

			drawingMasterVO = drawingMasterRepo.findById(drawingMasterDTO.getId()).orElseThrow(
					() -> new ApplicationException("Drawing Master Not Found with id: " + drawingMasterDTO.getId()));
			drawingMasterVO.setUpdatedBy(drawingMasterDTO.getCreatedBy());

			if (!drawingMasterVO.getDrawingNo().equalsIgnoreCase(drawingMasterDTO.getDrawingNo())) {
				if (drawingMasterRepo.existsByDrawingNoAndOrgId(drawingMasterDTO.getDrawingNo(),
						drawingMasterDTO.getOrgId())) {
					String errorMessage = String.format("This DrawingNo: %s Already Exists in This Organization",
							drawingMasterDTO.getDrawingNo());
					throw new ApplicationException(errorMessage);
				}
				drawingMasterVO.setDrawingNo(drawingMasterDTO.getDrawingNo().toUpperCase());
			}

			message = "Drawing Master Updation Successfully";

		}

		drawingMasterVO = getDrawingMasterVOFromDrawingMasterDTO(drawingMasterVO, drawingMasterDTO);
		drawingMasterRepo.save(drawingMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("drawingMasterVO", drawingMasterVO);
		return response;

	}

	private DrawingMasterVO getDrawingMasterVOFromDrawingMasterDTO(DrawingMasterVO drawingMasterVO,
			@Valid DrawingMasterDTO drawingMasterDTO) {
		drawingMasterVO.setPartNo(drawingMasterDTO.getPartNo());
		drawingMasterVO.setDrawingNo(drawingMasterDTO.getDrawingNo());
		drawingMasterVO.setDrawingRevNo(drawingMasterDTO.getDrawingRevNo());
		drawingMasterVO.setEffDate(drawingMasterDTO.getEffDate());
		drawingMasterVO.setFgPartNo(drawingMasterDTO.getFgPartNo());
		drawingMasterVO.setFgPartName(drawingMasterDTO.getFgPartName());
		drawingMasterVO.setCreatedBy(drawingMasterDTO.getCreatedBy());
		drawingMasterVO.setCancelRemarks(drawingMasterDTO.getCancelRemarks());
		drawingMasterVO.setOrgId(drawingMasterDTO.getOrgId());
		drawingMasterVO.setBranch(drawingMasterDTO.getBranch());
		drawingMasterVO.setBranchCode(drawingMasterDTO.getBranchCode());
		drawingMasterVO.setFinYear(drawingMasterDTO.getFinYear());

		drawingMasterVO.setActive(drawingMasterDTO.isActive());

//		if (drawingMasterDTO.getId() != null) {
//
//			List<DrawingMasterDetailsVO> drawingMasterDetailsVOs = drawingMasterDetailsRepo
//					.findByDrawingMasterVO(drawingMasterVO);
//			drawingMasterDetailsRepo.deleteAll(drawingMasterDetailsVOs);
//
//			List<DrawingMasterAttachmentsVO> drawingMasterAttachmentsVOs = drawingMasterAttachmentsRepo
//					.findByDrawingMasterVO(drawingMasterVO);
//			drawingMasterAttachmentsRepo.deleteAll(drawingMasterAttachmentsVOs);
//
//		}
//
//		List<DrawingMasterDetailsVO> drawingMasterDetailsVOs = new ArrayList<>();
//		for (DrawingMasterDetailsDTO drawingMasterDetailsDTO : drawingMasterDTO.getDrawingMasterDetailsDTO()) {
//
//			DrawingMasterDetailsVO drawingMasterDetailsVO = new DrawingMasterDetailsVO();
//			// drawingMaster1VO.setAttachements(drawingMaster1DTO.getAttachements());
//			drawingMasterDetailsVO.setFileName(drawingMasterDetailsDTO.getFileName());
//
//			drawingMasterDetailsVO.setDrawingMasterVO(drawingMasterVO);
//			drawingMasterDetailsVOs.add(drawingMasterDetailsVO);
//		}
//
//		drawingMasterVO.setDrawingMasterDetailsVO(drawingMasterDetailsVOs);
//
//		List<DrawingMasterAttachmentsVO> drawingMasterAttachmentsVOs = new ArrayList<>();
//		for (DrawingMasterAttachmentsDTO drawingMasterAttachmentsDTO : drawingMasterDTO
//				.getDrawingMasterAttachmentsDTO()) {
//
//			DrawingMasterAttachmentsVO drawingMasterAttachmentsVO = new DrawingMasterAttachmentsVO();
//			// drawingMaster2VO.setAttachements(drawingMaster2DTO.getAttachements());
//			drawingMasterAttachmentsVO.setFileName(drawingMasterAttachmentsDTO.getFileName());
//
//			drawingMasterAttachmentsVO.setDrawingMasterVO(drawingMasterVO);
//			drawingMasterAttachmentsVOs.add(drawingMasterAttachmentsVO);
//		}
//
//		drawingMasterVO.setDrawingMasterAttachmentsVO(drawingMasterAttachmentsVOs);

		return drawingMasterVO;
	}

	@Override
	public List<DrawingMasterVO> getAllDrawingMasterByOrgId(Long orgId, String branchCode) {
		return drawingMasterRepo.getDrawingMasterByOrgId(orgId, branchCode);
	}

	@Override
	public Optional<DrawingMasterVO> getAllDrawingMasterById(Long id) {
		return drawingMasterRepo.getDrawingMasterById(id);
	}

	@Override
	public DrawingMasterAttachmentsVO uploadAttachementsInBloob1(MultipartFile file, Long id) throws IOException {
		DrawingMasterAttachmentsVO drawingMasterAttachmentsVO = drawingMasterAttachmentsRepo.findById(id).get();
		drawingMasterAttachmentsVO.setAttachements(file.getBytes());
		return drawingMasterAttachmentsRepo.save(drawingMasterAttachmentsVO);
	}

	@Override
	public List<Map<String, Object>> getCompanyForStockLocation(Long orgId) {

		Set<Object[]> getCompany = companyRepo.findCompanyForStockLocation(orgId);
		return getCompanyDetails(getCompany);
	}

	private List<Map<String, Object>> getCompanyDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyCode", ch[0] != null ? ch[0].toString() : "");
			map.put("companyName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getFGSFGPartDetailsForDrawingMaster(Long orgId) {
		Set<Object[]> drawingMasterVO = drawingMasterRepo.findFGSFGPartDetailsForDrawingMaster(orgId);
		return getFGSFGPartDetailsForDrawingMaster(drawingMasterVO);
	}

	private List<Map<String, Object>> getFGSFGPartDetailsForDrawingMaster(Set<Object[]> drawingMasterVO) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingMasterVO) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public String getDrawingMasterDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "DM";
		String result = drawingMasterRepo.getDrawingMasterDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	// Drawingmaster MultipleFile Upload

	@Override
	public List<DrawingMasterAttachmentsVO> uploadDrawingAttachmentsInBloob(Long headerId, List<MultipartFile> files)
			throws IOException {
		// List to store the updated or newly created attachment entities
		List<DrawingMasterAttachmentsVO> attachmentsList = new ArrayList<>();

		// Fetch the parent entity (header) using headerId
		DrawingMasterVO header = drawingMasterRepo.findById(headerId)
				.orElseThrow(() -> new EntityNotFoundException("Header ID not found: " + headerId));

		// Fetch existing child rows based on the header
		List<DrawingMasterAttachmentsVO> existingAttachments = drawingMasterAttachmentsRepo
				.findByDrawingMasterVO(header);

		// Iterate over the uploaded files and update the child rows
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);

			// Validate if the file is not empty
			if (file.isEmpty()) {
				throw new IllegalArgumentException("File is empty: " + file.getOriginalFilename());
			}

			DrawingMasterAttachmentsVO attachment;

			attachment = existingAttachments.get(i);
			attachment.setAttachements(file.getBytes());

			// Add the updated/new attachment to the list
			attachmentsList.add(attachment);
		}

		return drawingMasterAttachmentsRepo.saveAll(attachmentsList);
	}

	@Override
	public List<DrawingMasterDetailsVO> uploadDrawingDetailsAttachmentsInBloob(Long headerId, List<MultipartFile> files)
			throws IOException {
		// List to store the updated or newly created attachment entities
		List<DrawingMasterDetailsVO> attachmentsList = new ArrayList<>();

		// Fetch the parent entity (header) using headerId
		DrawingMasterVO header = drawingMasterRepo.findById(headerId)
				.orElseThrow(() -> new EntityNotFoundException("Header ID not found: " + headerId));

		// Fetch existing child rows based on the header
		List<DrawingMasterDetailsVO> existingAttachments = drawingMasterDetailsRepo.findByDrawingMasterVO(header);

		// Iterate over the uploaded files and update the child rows
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);

			// Validate if the file is not empty
			if (file.isEmpty()) {
				throw new IllegalArgumentException("File is empty: " + file.getOriginalFilename());
			}

			DrawingMasterDetailsVO attachment;

			attachment = existingAttachments.get(i);
			attachment.setAttachements(file.getBytes());

			// Add the updated/new attachment to the list
			attachmentsList.add(attachment);
		}

		return drawingMasterDetailsRepo.saveAll(attachmentsList);
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateMachineMaster(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		MachineMasterVO inprocessInspectionVO = machineMasterRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		inprocessInspectionVO = machineMasterRepo.save(inprocessInspectionVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<MachineMasterAttachmentVO> oldDocs = machineMasterAttachmentRepo
				.findByMachineMasterVO(inprocessInspectionVO);
		machineMasterAttachmentRepo.deleteAll(oldDocs);

		if (inprocessInspectionVO.getDocuments() != null) {
			inprocessInspectionVO.getDocuments().clear();
		} else {
			inprocessInspectionVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (MachineMasterAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(inprocessInspectionVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("inprocessInspectionVO", inprocessInspectionVO);

		return response;
	}

	private void replaceDocuments(MachineMasterVO inprocessInspection, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(inprocessInspection, files, docFolder, docId);
	}

	private void saveFiles(MachineMasterVO machine, MultipartFile[] files, Path docFolder, String docId) {

		try {
			String safeDocId = docId.replace("/", "_");

			createDirectory(docFolder);

			for (MultipartFile file : files) {

				String originalName = file.getOriginalFilename();
				if (originalName == null)
					originalName = "file";

				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + safeDocId + extension;

				Path filePath = docFolder.resolve(fileName);

				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/machinemaster/files/")
						.toUriString();

				String relativePath = uploadBasePath.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				MachineMasterAttachmentVO attach = new MachineMasterAttachmentVO();
				attach.setMachineMasterVO(machine);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				machine.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafely(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectory(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFile(request, "/api/machinemaster/files/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException, java.io.IOException {

		String uri = request.getRequestURI();

//Remove API prefix
		String relativePath = uri.replace(apiPrefix, "");

//Decode URL
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

//If DB path contains /uploads, ensure consistency
		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

//🔐 Security check
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
	public List<ImageResponseDTO> getMachineMasterImages(Long id) throws Exception {

		MachineMasterVO record = machineMasterRepo.getAllMachineMasterById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<MachineMasterAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (MachineMasterAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			ImageResponseDTO dto = new ImageResponseDTO();
			dto.setFileName(attachment.getFilename());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateDrawingMasterDocumentImage(MultipartFile[] files, String docId,
			String screenName, String module, List<String> fileNames) throws ApplicationException, IOException {

		DrawingMasterVO drawingMasterVO = drawingMasterRepo.findByDocId(docId);

		drawingMasterVO = drawingMasterRepo.save(drawingMasterVO);

		// Create folder
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectoryDrawing(docFolder);

		// Delete old DB attachments
		List<DrawingMasterDetailsVO> oldDocs = drawingMasterDetailsRepo.findByDrawingMasterVO(drawingMasterVO);
		drawingMasterDetailsRepo.deleteAll(oldDocs);

		if (drawingMasterVO.getDocuments() != null) {
			drawingMasterVO.getDocuments().clear();
		} else {
			drawingMasterVO.setDocuments(new ArrayList<>());
		}

		// Delete old physical files
		for (DrawingMasterDetailsVO doc : oldDocs) {
			deleteFileSafelyDocuments(doc.getFilePath());
		}

		// Save new files
		replaceDocuments(drawingMasterVO, files, docFolder, docId, fileNames);

		Map<String, Object> response = new HashMap<>();
		response.put("drawingMasterVO", drawingMasterVO);
		response.put("message", "Drawing Master Document Uploaded Successfully");
		return response;
	}

	private void replaceDocuments(DrawingMasterVO drawingMasterVO, MultipartFile[] files, Path docFolder, String docId,
			List<String> fileNames) throws IOException {

		if (files == null || files.length == 0) {
			return;
		}

		saveFiles(drawingMasterVO, files, docFolder, docId, fileNames);
	}

	private void saveFiles(DrawingMasterVO drawingMasterVO, MultipartFile[] files, Path docFolder, String docId,
			List<String> fileNames) throws IOException {

		try {
			createDirectoryDrawing(docFolder);

			for (int i = 0; i < files.length; i++) {

				MultipartFile file = files[i];

				String currentFileName = null;
				if (fileNames != null && fileNames.size() > i) {
					currentFileName = fileNames.get(i);
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}

				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + docId + extension;

				Path filePath = docFolder.resolve(fileName);

				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/machinemaster/viewDocumets/").toUriString();

				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				DrawingMasterDetailsVO attach = new DrawingMasterDetailsVO();
				attach.setDrawingMasterVO(drawingMasterVO);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setFileNames(currentFileName);
				attach.setUploadOn(LocalDateTime.now());

				drawingMasterVO.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyDocuments(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryDrawing(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewDocumets(HttpServletRequest request) throws IOException {
		return serveFileDocumets(request, "/api/machinemaster/viewDocumets/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileDocumets(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {

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
	@Transactional
	public Map<String, Object> createUpdateDrawingMasterDocumentSubImage(MultipartFile[] files, String docId,
			String screenName, String module, List<String> fileNames) throws ApplicationException, IOException {

		DrawingMasterVO drawingMasterVO = drawingMasterRepo.findByDocId(docId);

		drawingMasterVO = drawingMasterRepo.save(drawingMasterVO);

		// Create folder
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectoryDrawingSub(docFolder);

		// Delete old DB attachments
		List<DrawingMasterAttachmentsVO> oldDocs = drawingMasterAttachmentsRepo.findByDrawingMasterVO(drawingMasterVO);
		drawingMasterAttachmentsRepo.deleteAll(oldDocs);

		if (drawingMasterVO.getAttachments() != null) {
			drawingMasterVO.getAttachments().clear();
		} else {
			drawingMasterVO.getAttachments();
		}

		// Delete old physical files
		for (DrawingMasterAttachmentsVO doc : oldDocs) {
			deleteFileSafelyDocumentsSub(doc.getFilePath());
		}

		// Save new files
		replaceDocumentsSub(drawingMasterVO, files, docFolder, docId, fileNames);

		Map<String, Object> response = new HashMap<>();
		response.put("drawingMasterVO", drawingMasterVO);

		return response;
	}

	private void replaceDocumentsSub(DrawingMasterVO drawingMasterVO, MultipartFile[] files, Path docFolder,
			String docId, List<String> fileNames) throws IOException {

		if (files == null || files.length == 0) {
			return;
		}

		saveFilesSub(drawingMasterVO, files, docFolder, docId, fileNames);
	}

	private void saveFilesSub(DrawingMasterVO drawingMasterVO, MultipartFile[] files, Path docFolder, String docId,
			List<String> fileNames) throws IOException {

		try {
			createDirectoryDrawing(docFolder);

			for (int i = 0; i < files.length; i++) {

				MultipartFile file = files[i];

				String currentFileName = null;
				if (fileNames != null && fileNames.size() > i) {
					currentFileName = fileNames.get(i);
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}

				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + docId + extension;

				Path filePath = docFolder.resolve(fileName);

				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/machinemaster/viewDocumetsSub/").toUriString();

				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				DrawingMasterAttachmentsVO attach = new DrawingMasterAttachmentsVO();
				attach.setDrawingMasterVO(drawingMasterVO);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setFileNames(currentFileName);
				attach.setUploadOn(LocalDateTime.now());

				drawingMasterVO.getAttachments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyDocumentsSub(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryDrawingSub(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewDocumetsSub(HttpServletRequest request) throws IOException {
		return serveFileDocumetsSub(request, "/api/machinemaster/viewDocumetsSub/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileDocumetsSub(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {

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
}
