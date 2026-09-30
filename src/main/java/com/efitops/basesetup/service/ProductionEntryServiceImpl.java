package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.ResponseDTO.BillOfMaterialDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseImportDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineResponseDTO;
import com.efitops.basesetup.ResponseDTO.PreDeliveryInspectionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PreDeliveryInspectionResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionEntryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ReasonResponseDTO;
import com.efitops.basesetup.ResponseDTO.ReworkReasonResponseDTO;
import com.efitops.basesetup.ResponseDTO.ScrapDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.StoppageReasonResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolMasterResponseMasterDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.PreDeliveryInspectionDTO;
import com.efitops.basesetup.dto.PreDeliveryInspectionDetailsDTO;
import com.efitops.basesetup.dto.ProductionEntryDTO;
import com.efitops.basesetup.dto.ProductionEntryDetailsDTO;
import com.efitops.basesetup.dto.ReworkReasonDTO;
import com.efitops.basesetup.dto.ScrapDetailsDTO;
import com.efitops.basesetup.dto.StoppageReasonDTO;
import com.efitops.basesetup.dto.ToolDetailsDTO;
import com.efitops.basesetup.entity.BillOfMaterialVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.PreDeliveryInspectionDetailsVO;
import com.efitops.basesetup.entity.PreDeliveryInspectionVO;
import com.efitops.basesetup.entity.ProductionEntryDetailsVO;
import com.efitops.basesetup.entity.ProductionEntryVO;
import com.efitops.basesetup.entity.ReasonMasterVO;
import com.efitops.basesetup.entity.ReworkReasonVO;
import com.efitops.basesetup.entity.ScrapDetailsVO;
import com.efitops.basesetup.entity.StoppageReasonVO;
import com.efitops.basesetup.entity.ToolDetailsVO;
import com.efitops.basesetup.entity.ToolMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BillOfMaterialRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MachineMasterRepo;
import com.efitops.basesetup.repository.PreDeliveryInspectionDetailsRepo;
import com.efitops.basesetup.repository.PreDeliveryInspectionRepo;
import com.efitops.basesetup.repository.ProductionEntryDetailsRepo;
import com.efitops.basesetup.repository.ProductionEntryRepo;
import com.efitops.basesetup.repository.ReasonMasterRepo;
import com.efitops.basesetup.repository.ReworkReasonRepo;
import com.efitops.basesetup.repository.ScrapDetailsRepo;
import com.efitops.basesetup.repository.StoppageReasonRepo;
import com.efitops.basesetup.repository.ToolDetailsRepo;
import com.efitops.basesetup.repository.ToolMasterRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class ProductionEntryServiceImpl implements ProductionEntryService {

	@Autowired
	private ProductionEntryRepo productionEntryRepo;
	@Autowired
	private ProductionEntryDetailsRepo productionEntryDetailsRepo;
	@Autowired
	private ToolDetailsRepo toolDetailsRepo;
	@Autowired
	private StoppageReasonRepo stoppageReasonRepo;
	@Autowired
	private ReworkReasonRepo reworkReasonRepo;
	@Autowired
	private ScrapDetailsRepo scrapDetailsRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;
	@Autowired
	private LocationRepo locationRepo;
	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;
	@Autowired
	private BillOfMaterialRepo billOfMaterialRepo;
	@Autowired
	private BranchRepo branchRepo;
	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	@Autowired
	private PreDeliveryInspectionRepo preDeliveryInspectionRepo;

	@Autowired
	private PreDeliveryInspectionDetailsRepo pdiDetailsRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private MachineMasterRepo machineMasterRepo;

	@Autowired
	private ReasonMasterRepo reasonMasterRepo;

	@Autowired
	private ToolMasterRepo toolMasterRepo;

	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Override
	@Transactional
	public Map<String, Object> createUpdateProductionEntry(ProductionEntryDTO dto) throws ApplicationException {
		String screenCode = "PE";
		ProductionEntryVO vo = new ProductionEntryVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = productionEntryRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Production Entry Not Found"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Production Entry Updated Successfully";
		} else {
			String docId = productionEntryRepo.getProductionEntryDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);
			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO docMapping = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			docMapping.setLastNo(docMapping.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(docMapping);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Production Entry Created Successfully";
		}

		createUpdateProductionEntryVOByDTO(dto, vo);
		vo = productionEntryRepo.save(vo);

		ProductionEntryResponseDTO responseDTO = buildProductionEntryResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("productionEntryVO", responseDTO);
		return response;
	}

	private void createUpdateProductionEntryVOByDTO(ProductionEntryDTO dto, ProductionEntryVO vo)
			throws ApplicationException {

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setShift(dto.getShift());
		vo.setProductionQty(dto.getProductionQty());
		vo.setShiftTimeFrom(LocalTime.parse(dto.getShiftTimeFrom()));
		vo.setShiftTimeTo(LocalTime.parse(dto.getShiftTimeTo()));
		vo.setSchOrderNo(dto.getSchOrderNo());
		vo.setProcessSheetNo(dto.getProcessSheetNo());
		vo.setNarration(dto.getNarration());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getFgItem() != null && dto.getFgItem() != 0) {

			ItemMasterVO item = itemMasterRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));

			vo.setFgItem(item);
		}

		if (dto.getLocation() != null && dto.getLocation() != 0) {

			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			vo.setLocation(location);
		}

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Not Found"));

			vo.setPreparedBy(employee);
		}

		if (dto.getApprovedBy() != null && dto.getApprovedBy() != 0) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getApprovedBy())
					.orElseThrow(() -> new ApplicationException("Approved By Not Found"));

			vo.setApprovedBy(employee);
		}

		if (dto.getBom() != null && dto.getBom() != 0) {

			BillOfMaterialVO bom = billOfMaterialRepo.findById(dto.getBom())
					.orElseThrow(() -> new ApplicationException("BOM Not Found"));

			vo.setBom(bom);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}
		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<ProductionEntryDetailsVO> oldDetails = productionEntryDetailsRepo.findByProductionEntryVO(vo);

			if (oldDetails != null && !oldDetails.isEmpty()) {
				productionEntryDetailsRepo.deleteAll(oldDetails);
			}

			List<ToolDetailsVO> oldToolDetails = toolDetailsRepo.findByProductionEntryVO(vo);

			if (oldToolDetails != null && !oldToolDetails.isEmpty()) {

				toolDetailsRepo.deleteAll(oldToolDetails);
			}

			List<StoppageReasonVO> oldStoppageDetails = stoppageReasonRepo.findByProductionEntryVO(vo);

			if (oldStoppageDetails != null && !oldStoppageDetails.isEmpty()) {

				stoppageReasonRepo.deleteAll(oldStoppageDetails);
			}

			List<ReworkReasonVO> oldReworkDetails = reworkReasonRepo.findByProductionEntryVO(vo);

			if (oldReworkDetails != null && !oldReworkDetails.isEmpty()) {

				reworkReasonRepo.deleteAll(oldReworkDetails);
			}

			List<ScrapDetailsVO> oldScrapDetails = scrapDetailsRepo.findByProductionEntryVO(vo);

			if (oldScrapDetails != null && !oldScrapDetails.isEmpty()) {

				scrapDetailsRepo.deleteAll(oldScrapDetails);
			}
		}

		BigDecimal totalLabourCost = BigDecimal.ZERO;

		BigDecimal totalMachineCost = BigDecimal.ZERO;

		BigDecimal totalToolCost = BigDecimal.ZERO;

		BigDecimal totalConsumablesCost = BigDecimal.ZERO;

		List<ProductionEntryDetailsVO> prodDetailsList = new ArrayList<>();
		if (dto.getProductionEntryDetailsDTO() != null) {
			for (ProductionEntryDetailsDTO d : dto.getProductionEntryDetailsDTO()) {
				ProductionEntryDetailsVO detailsVO = new ProductionEntryDetailsVO();
				detailsVO.setOperationNo(d.getOperationNo());
				if (d.getMachine() != null && d.getMachine() != 0) {

					MachineMasterVO branch = machineMasterRepo.findById(d.getMachine())
							.orElseThrow(() -> new ApplicationException("MachineMaster Not Found"));

					detailsVO.setMachine(branch);
				}

				detailsVO.setMachineHourRate(d.getMachineHourRate());
				detailsVO.setLabourHourRate(d.getLabourHourRate());
				detailsVO.setOperationName(d.getOperationName());
				detailsVO.setFrTimeHrs(d.getFrTimeHrs());
				detailsVO.setFrTimeMins(d.getFrTimeMins());
				detailsVO.setToTimeHrs(d.getToTimeHrs());
				detailsVO.setToTimeMins(d.getToTimeMins());
				detailsVO.setLunchTimeMins(d.getLunchTimeMins());
				detailsVO.setTotTimeMins(d.getFrTimeMins().add(d.getToTimeMins()).add(d.getLunchTimeMins()));
				detailsVO.setStoppageTimeMins(d.getStoppageTimeMins());
				detailsVO.setProductiveHrsMins(detailsVO.getTotTimeMins().subtract(d.getStoppageTimeMins()));
				detailsVO.setQtyProduced(d.getQtyProduced());
				detailsVO.setQtyPassed(d.getQtyPassed());
				detailsVO.setQtyRejected(d.getQtyProduced().subtract(d.getQtyPassed()));

				if (d.getReason() != null && d.getReason() != 0) {

					ReasonMasterVO branch = reasonMasterRepo.findById(d.getReason())
							.orElseThrow(() -> new ApplicationException("ReasonMaster Not Found"));

					detailsVO.setReason(branch);
				}

				detailsVO.setQtyRework(d.getQtyRework());
				detailsVO.setNoOfTools(d.getNoOfTools());
				detailsVO.setQtyScrap(d.getQtyScrap());
				if (d.getOperationBy() != null && d.getOperationBy() != 0) {

					EmployeeMasterVO preparedBy = employeeMasterRepo.findById(d.getOperationBy())
							.orElseThrow(() -> new ApplicationException("Employee Not Found"));

					detailsVO.setOperationBy(preparedBy);
				}

				detailsVO.setRemarks(d.getRemarks());
				detailsVO.setStdRunTimePcsInSec(d.getStdRunTimePcsInSec());
				detailsVO.setStdLabourCost(d.getStdLabourCost());
				detailsVO.setStdMcCost(d.getStdMcCost());
				detailsVO.setRunningActCostLabour(d.getRunningActCostLabour());
				totalLabourCost = totalLabourCost.add(detailsVO.getRunningActCostLabour());
				detailsVO.setRunningActCostMc(d.getRunningActCostMc());
				totalMachineCost = totalMachineCost.add(detailsVO.getRunningActCostMc());
				detailsVO.setStdToolCost(d.getStdToolCost());
				detailsVO.setRunningActCostTool(d.getRunningActCostTool());
				totalToolCost = totalToolCost.add(detailsVO.getRunningActCostTool());
				detailsVO.setStdConsumCost(d.getStdConsumCost());
				detailsVO.setRunningActCostConsum(d.getRunningActCostConsum());
				totalConsumablesCost = totalConsumablesCost.add(detailsVO.getRunningActCostConsum());
				detailsVO.setProductionEntryVO(vo);
				prodDetailsList.add(detailsVO);
			}
		}
		vo.setProductionEntryDetailsVO(prodDetailsList);

		// 2. Map Tool Details
		List<ToolDetailsVO> toolDetailsList = new ArrayList<>();
		if (dto.getToolDetailsDTO() != null) {
			for (ToolDetailsDTO d : dto.getToolDetailsDTO()) {
				ToolDetailsVO detailsVO = new ToolDetailsVO();

				if (d.getToolNo() != null && d.getToolNo() != 0) {

					ToolMasterVO preparedBy = toolMasterRepo.findById(d.getToolNo())
							.orElseThrow(() -> new ApplicationException("ToolMaster Not Found"));

					detailsVO.setToolNo(preparedBy);
				}
				detailsVO.setStrokes(d.getStrokes());
				detailsVO.setStrokesRate(d.getStrokesRate());
				detailsVO.setToolValue(d.getStrokes().multiply(d.getStrokesRate()));
				detailsVO.setProductionEntryVO(vo);
				toolDetailsList.add(detailsVO);
			}
		}
		vo.setToolDetailsVO(toolDetailsList);

		List<StoppageReasonVO> stoppageList = new ArrayList<>();
		if (dto.getStoppageReasonDTO() != null) {
			for (StoppageReasonDTO d : dto.getStoppageReasonDTO()) {
				StoppageReasonVO detailsVO = new StoppageReasonVO();
				detailsVO.setFrTimeHrs(d.getFrTimeHrs());
				detailsVO.setFrTimeMins(d.getFrTimeMins());
				detailsVO.setToTimeHrs(d.getToTimeHrs());
				detailsVO.setToTimeMins(d.getToTimeMins());
				detailsVO.setTotTimeInMins(d.getTotTimeInMins());
				if (d.getReason() != null && d.getReason() != 0) {

					ReasonMasterVO branch = reasonMasterRepo.findById(d.getReason())
							.orElseThrow(() -> new ApplicationException("Branch Not Found"));

					detailsVO.setReason(branch);
				}

				detailsVO.setStoppageMcCost(d.getStoppageMcCost());
				detailsVO.setStoppageLabourCost(d.getStoppageLabourCost());
				detailsVO.setRemarks(d.getRemarks());
				detailsVO.setProductionEntryVO(vo);
				stoppageList.add(detailsVO);
			}
		}
		vo.setStoppageReasonVO(stoppageList);

		// 4. Map Rework Reason
		List<ReworkReasonVO> reworkList = new ArrayList<>();
		if (dto.getReworkReasonDTO() != null) {
			for (ReworkReasonDTO d : dto.getReworkReasonDTO()) {
				ReworkReasonVO detailsVO = new ReworkReasonVO();
				if (d.getReason() != null && d.getReason() != 0) {

					ReasonMasterVO branch = reasonMasterRepo.findById(d.getReason())
							.orElseThrow(() -> new ApplicationException("ReasonMaster Not Found"));

					detailsVO.setReason(branch);
				}
				detailsVO.setReasonDescription(d.getReasonDescription());
				detailsVO.setQty(d.getQty());
				detailsVO.setTimePerQty(d.getTimePerQty());
				detailsVO.setReworkProdHrs(d.getQty().multiply(d.getTimePerQty()));
				detailsVO.setReworkMcCost(d.getReworkMcCost());
				detailsVO.setReworkLabourCost(d.getReworkLabourCost());
				detailsVO.setProductionEntryVO(vo);
				reworkList.add(detailsVO);
			}
		}
		vo.setReworkReasonVO(reworkList);

		List<ScrapDetailsVO> scrapList = new ArrayList<>();
		if (dto.getScrapDetailsDTO() != null) {
			for (ScrapDetailsDTO d : dto.getScrapDetailsDTO()) {
				ScrapDetailsVO detailsVO = new ScrapDetailsVO();
				if (d.getScrap() != null && d.getScrap() != 0) {

					ListOfValuesDetailsVO item = listOfValuesDetailsRepo.findById(d.getScrap())
							.orElseThrow(() -> new ApplicationException("Scrap Not Found"));

					detailsVO.setScrap(item);
				}
				detailsVO.setWeight(d.getWeight());
				detailsVO.setQty(d.getQty());
				detailsVO.setProductionEntryVO(vo);
				scrapList.add(detailsVO);
			}
		}
		vo.setScrapDetailsVO(scrapList);
		vo.setTotalLabourCost(totalLabourCost);
		vo.setTotalMachineCost(totalMachineCost);
		vo.setTotalToolCost(totalToolCost);
		vo.setTotalConsumablesCost(totalConsumablesCost);
	}

	private ProductionEntryResponseDTO buildProductionEntryResponse(ProductionEntryVO vo) {
		ProductionEntryResponseDTO responseDTO = new ProductionEntryResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setShiftTimeFrom(vo.getShiftTimeFrom());
		responseDTO.setShiftTimeTo(vo.getShiftTimeTo());
		responseDTO.setShift(vo.getShift());
		responseDTO.setProductionQty(vo.getProductionQty());
		responseDTO.setSchOrderNo(vo.getSchOrderNo());
		responseDTO.setProcessSheetNo(vo.getProcessSheetNo());
		responseDTO.setTotalLabourCost(vo.getTotalLabourCost());
		responseDTO.setTotalMachineCost(vo.getTotalMachineCost());
		responseDTO.setTotalToolCost(vo.getTotalToolCost());
		responseDTO.setTotalConsumablesCost(vo.getTotalConsumablesCost());
		responseDTO.setNarration(vo.getNarration());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getFgItem() != null) {
			ItemMasterDetailsResponseImportDTO dto = new ItemMasterDetailsResponseImportDTO();
			dto.setId(vo.getFgItem().getId());
			dto.setItemCode(vo.getFgItem().getItemCode());
			dto.setItemDescription(vo.getFgItem().getItemDescription());
			responseDTO.setFgItemCode(dto);
		}
		if (vo.getLocation() != null) {
			LocationMasterResponseDTO dto = new LocationMasterResponseDTO();
			dto.setId(vo.getLocation().getId());
			dto.setLocationName(vo.getLocation().getLocationName());
			responseDTO.setLocation(dto);
		}
		if (vo.getPreparedBy() != null) {
			EmployeeMasterResponseDetailsDTO dto = new EmployeeMasterResponseDetailsDTO();
			dto.setId(vo.getPreparedBy().getId());
			dto.setEmployeeCode(vo.getPreparedBy().getEmployeeId());
			dto.setEmployeeName(vo.getPreparedBy().getEmployeeName());
			responseDTO.setPreparedBy(dto);
		}
		if (vo.getApprovedBy() != null) {
			EmployeeMasterResponseDetailsDTO dto = new EmployeeMasterResponseDetailsDTO();
			dto.setId(vo.getApprovedBy().getId());
			dto.setEmployeeCode(vo.getApprovedBy().getEmployeeId());
			dto.setEmployeeName(vo.getApprovedBy().getEmployeeName());
			responseDTO.setApprovedBy(dto);
		}
		if (vo.getBom() != null) {
			BillOfMaterialDropdownResponseDTO dto = new BillOfMaterialDropdownResponseDTO();
			dto.setId(vo.getBom().getId());
			dto.setDocId(vo.getBom().getDocId());
			responseDTO.setBomId(dto);
		}
		if (vo.getBranch() != null) {
			BranchResponseDTO dto = new BranchResponseDTO();
			dto.setId(vo.getBranch().getId());
			dto.setBranchCode(vo.getBranch().getBranchCode());
			dto.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(dto);
		}

		List<ProductionEntryDetailsResponseDTO> prodDetailsResponse = new ArrayList<>();
		if (vo.getProductionEntryDetailsVO() != null) {
			for (ProductionEntryDetailsVO d : vo.getProductionEntryDetailsVO()) {
				ProductionEntryDetailsResponseDTO rDto = new ProductionEntryDetailsResponseDTO();
				rDto.setId(d.getId());
				rDto.setOperationNo(d.getOperationNo());

				if (d.getMachine() != null) {
					MachineResponseDTO unitDTO = new MachineResponseDTO();
					unitDTO.setId(d.getMachine().getId());
					unitDTO.setMachineName(d.getMachine().getMachineInstrumentName());
					rDto.setMachine(unitDTO);
				}

				rDto.setMachineHourRate(d.getMachineHourRate());
				rDto.setLabourHourRate(d.getLabourHourRate());
				rDto.setOperationName(d.getOperationName());
				rDto.setFrTimeHrs(d.getFrTimeHrs());
				rDto.setFrTimeMins(d.getFrTimeMins());
				rDto.setToTimeHrs(d.getToTimeHrs());
				rDto.setTotTimeMins(d.getTotTimeMins());
				rDto.setStoppageTimeMins(d.getStoppageTimeMins());
				rDto.setProductiveHrsMins(d.getProductiveHrsMins());
				rDto.setQtyProduced(d.getQtyProduced());
				rDto.setQtyPassed(d.getQtyPassed());
				rDto.setQtyRejected(d.getQtyRejected());
				if (d.getReason() != null) {
					ReasonResponseDTO unitDTO = new ReasonResponseDTO();
					unitDTO.setId(d.getReason().getId());
					unitDTO.setReasonCode(d.getReason().getReasonCode());
					unitDTO.setReasonDescription(d.getReason().getReasonDescription());
					rDto.setReason(unitDTO);
				}
				rDto.setQtyRework(d.getQtyRework());
				rDto.setNoOfTools(d.getNoOfTools());
				rDto.setQtyScrap(d.getQtyScrap());
				if (d.getOperationBy() != null) {
					EmployeeMasterResponseDetailsDTO preparedByDTO = new EmployeeMasterResponseDetailsDTO();
					preparedByDTO.setId(d.getOperationBy().getId());
					preparedByDTO.setEmployeeCode(d.getOperationBy().getEmployeeId());
					preparedByDTO.setEmployeeName(d.getOperationBy().getEmployeeName());
					rDto.setOperationBy(preparedByDTO);
				}

				rDto.setRemarks(d.getRemarks());
				rDto.setStdRunTimePcsInSec(d.getStdRunTimePcsInSec());
				rDto.setStdLabourCost(d.getStdLabourCost());
				rDto.setStdMcCost(d.getStdMcCost());
				rDto.setRunningActCostLabour(d.getRunningActCostLabour());
				rDto.setRunningActCostMc(d.getRunningActCostMc());
				rDto.setStdToolCost(d.getStdToolCost());
				rDto.setRunningActCostTool(d.getRunningActCostTool());
				rDto.setStdConsumCost(d.getStdConsumCost());
				rDto.setRunningActCostConsum(d.getRunningActCostConsum());
				prodDetailsResponse.add(rDto);
			}
		}
		responseDTO.setProductionEntryDetailsResponseDTO(prodDetailsResponse);

		// Map Tool Details
		List<ToolDetailsResponseDTO> toolResponse = new ArrayList<>();
		if (vo.getToolDetailsVO() != null) {
			for (ToolDetailsVO d : vo.getToolDetailsVO()) {
				ToolDetailsResponseDTO rDto = new ToolDetailsResponseDTO();
				rDto.setId(d.getId());

				if (d.getToolNo() != null) {
					ToolMasterResponseMasterDTO preparedByDTO = new ToolMasterResponseMasterDTO();
					preparedByDTO.setId(d.getToolNo().getId());
					preparedByDTO.setToolName(d.getToolNo().getToolName());
					preparedByDTO.setToolDescription(d.getToolNo().getToolDescription());
					rDto.setToolNo(preparedByDTO);
				}

				rDto.setStrokes(d.getStrokes());
				rDto.setStrokesRate(d.getStrokesRate());
				rDto.setToolValue(d.getToolValue());
				toolResponse.add(rDto);
			}
		}
		responseDTO.setToolDetailsResponseDTO(toolResponse);

		List<StoppageReasonResponseDTO> stoppageResponse = new ArrayList<>();
		if (vo.getStoppageReasonVO() != null) {
			for (StoppageReasonVO d : vo.getStoppageReasonVO()) {
				StoppageReasonResponseDTO rDto = new StoppageReasonResponseDTO();
				rDto.setId(d.getId());
				rDto.setFrTimeHrs(d.getFrTimeHrs());
				rDto.setFrTimeMins(d.getFrTimeMins());
				rDto.setToTimeHrs(d.getToTimeHrs());
				rDto.setToTimeMins(d.getToTimeMins());
				rDto.setTotTimeInMins(d.getTotTimeInMins());
				if (d.getReason() != null) {
					ReasonResponseDTO unitDTO = new ReasonResponseDTO();
					unitDTO.setId(d.getReason().getId());
					unitDTO.setReasonCode(d.getReason().getReasonCode());
					unitDTO.setReasonDescription(d.getReason().getReasonDescription());
					rDto.setReason(unitDTO);
				}
				rDto.setStoppageMcCost(d.getStoppageMcCost());
				rDto.setStoppageLabourCost(d.getStoppageLabourCost());
				rDto.setRemarks(d.getRemarks());
				stoppageResponse.add(rDto);
			}
		}
		responseDTO.setStoppageReasonResponseDTO(stoppageResponse);

		List<ReworkReasonResponseDTO> reworkResponse = new ArrayList<>();
		if (vo.getReworkReasonVO() != null) {
			for (ReworkReasonVO d : vo.getReworkReasonVO()) {
				ReworkReasonResponseDTO rDto = new ReworkReasonResponseDTO();
				rDto.setId(d.getId());
				if (d.getReason() != null) {
					ReasonResponseDTO unitDTO = new ReasonResponseDTO();
					unitDTO.setId(d.getReason().getId());
					unitDTO.setReasonCode(d.getReason().getReasonCode());
					unitDTO.setReasonDescription(d.getReason().getReasonDescription());
					rDto.setReason(unitDTO);
				}

				rDto.setQty(d.getQty());
				rDto.setTimePerQty(d.getTimePerQty());
				rDto.setReworkProdHrs(d.getReworkProdHrs());
				rDto.setReworkMcCost(d.getReworkMcCost());
				rDto.setReworkLabourCost(d.getReworkLabourCost());
				reworkResponse.add(rDto);
			}
		}
		responseDTO.setReworkReasonResponseDTO(reworkResponse);

		// Map Scrap Details
		List<ScrapDetailsResponseDTO> scrapResponse = new ArrayList<>();
		if (vo.getScrapDetailsVO() != null) {
			for (ScrapDetailsVO d : vo.getScrapDetailsVO()) {
				ScrapDetailsResponseDTO rDto = new ScrapDetailsResponseDTO();
				rDto.setId(d.getId());
				if (d.getScrap() != null) {
					ListOfValuesDetailsResponseDTO lovDto = new ListOfValuesDetailsResponseDTO();
					lovDto.setId(d.getScrap().getId());
					lovDto.setDescription(d.getScrap().getValueDescription());
					rDto.setScrap(lovDto);
				}
				rDto.setWeight(d.getWeight());
				rDto.setQty(d.getQty());
				scrapResponse.add(rDto);
			}
		}
		responseDTO.setScrapDetailsResponseDTO(scrapResponse);

		return responseDTO;
	}

	@Override
	public String getProductionEntryDocId(Long orgId, String financialYear) {
		String screenCode = "PE";
		return productionEntryRepo.getProductionEntryDocId(orgId, financialYear, screenCode);
	}

	@Override
	public ProductionEntryResponseDTO getProductionEntryById(Long id) throws ApplicationException {
		ProductionEntryVO vo = productionEntryRepo.getProductionEntryById(id);
		if (vo == null) {
			throw new ApplicationException("Production Entry Not Found");
		}
		return buildProductionEntryResponse(vo);
	}

	@Override
	public List<ProductionEntryResponseDTO> getProductionEntryByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<ProductionEntryVO> list = productionEntryRepo.getProductionEntryByOrgId(orgId, branch);
		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Production Entry Not Found");
		}
		List<ProductionEntryResponseDTO> responseList = new ArrayList<>();
		for (ProductionEntryVO vo : list) {
			responseList.add(buildProductionEntryResponse(vo));
		}
		return responseList;
	}

	@Override
	public List<Map<String, Object>> getSchNoFromProductionEntry(Long orgId, Long branch, Long fgItem) {
		Set<Object[]> chType = productionEntryRepo.getSchNoFromProductionEntry(orgId, branch, fgItem);
		return getSchNoFromProductionEntry(chType);
	}

	private List<Map<String, Object>> getSchNoFromProductionEntry(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getBomNoFromProductionEntry(Long orgId, Long branch, Long fgItem) {

		Set<Object[]> chType = productionEntryRepo.getBomNoFromProductionEntry(orgId, branch, fgItem);

		return getBomNoFromProductionEntry(chType);

	}

	private List<Map<String, Object>> getBomNoFromProductionEntry(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");

			map.put("docDate", ch[1] != null ? ch[1].toString() : "");

			map.put("bomId", ch[2] != null ? ((Number) ch[2]).longValue() : null);

			list.add(map);
		}

		return list;

	}

	@Override
	public List<Map<String, Object>> getProcessSheetProductionEntry(Long orgId, Long branch, Long fgItem) {

		Set<Object[]> chType = productionEntryRepo.getProcessSheetProductionEntry(orgId, branch, fgItem);

		return getProcessSheetProductionEntry(chType);
	}

	private List<Map<String, Object>> getProcessSheetProductionEntry(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getProcessSheetOpreationDetailsProductionEntry(Long orgId, Long branch,
			String processSheet) {

		Set<Object[]> chType = productionEntryRepo.getProcessSheetOpreationDetailsProductionEntry(orgId, branch,
				processSheet);

		return getProcessSheetOpreationDetailsProductionEntry(chType);
	}

	private List<Map<String, Object>> getProcessSheetOpreationDetailsProductionEntry(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("operation", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("description", ch[1] != null ? ch[1].toString() : "");
			map.put("machineEquipmentsMasterId", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			map.put("machineInstrumentName", ch[3] != null ? ch[3].toString() : "");
			map.put("machineInstrumentNo", ch[4] != null ? ch[4].toString() : "");

			list.add(map);
		}

		return list;
	}

	// Pre

	@Override
	@Transactional
	public Map<String, Object> createUpdatePreDeliveryInspection(PreDeliveryInspectionDTO dto)
			throws ApplicationException {
		String screenCode = "PDI";
		PreDeliveryInspectionVO vo = new PreDeliveryInspectionVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = preDeliveryInspectionRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Pre-Delivery Inspection Not Found"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Pre-Delivery Inspection Updated Successfully";
		} else {
			String docId = preDeliveryInspectionRepo.getPreDeliveryInspectionDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);
			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			if (documentTypeMappingDetailsVO != null) {
				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Pre-Delivery Inspection Created Successfully";
		}

		createUpdatePreDeliveryInspectionVOByDTO(dto, vo);

		vo = preDeliveryInspectionRepo.save(vo);

		PreDeliveryInspectionResponseDTO responseDTO = buildPreDeliveryInspectionResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("preDeliveryInspectionVO", responseDTO);

		return response;
	}

	private void createUpdatePreDeliveryInspectionVOByDTO(PreDeliveryInspectionDTO dto, PreDeliveryInspectionVO vo)
			throws ApplicationException {

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setItemDrawingNo(dto.getItemDrawingNo());
		vo.setProdSchOrdNo(dto.getProdSchOrdNo());
		vo.setTransferSlipNo(dto.getTransferSlipNo());
		vo.setTransferSlipDate(dto.getTransferSlipDate());
		vo.setStock(dto.getStock());
		vo.setCustomerCode(dto.getCustomerCode());
		vo.setLcDeliverySchNo(dto.getLcDeliverySchNo());
		vo.setPoNo(dto.getPoNo());
		vo.setInvNo(dto.getInvNo());
		vo.setInvDate(dto.getInvDate());
		vo.setInitialPlanNo(dto.getInitialPlanNo());
		vo.setDate(dto.getDate());
		vo.setSchOrdDate(dto.getSchOrdDate());
		vo.setSchQty(dto.getSchQty());
		vo.setProducedQty(dto.getProducedQty());
		vo.setCustomerName(dto.getCustomerName());
		vo.setCustPartNo(dto.getCustPartNo());
		vo.setQtyInspected(dto.getQtyInspected());
		vo.setQtyPassed(dto.getQtyPassed());
		vo.setRate(dto.getRate());
		vo.setRejQty(dto.getRejQty());
		vo.setReasonForRejection(dto.getReasonForRejection());
		vo.setScrapQty(dto.getScrapQty());
		vo.setReworkQty(dto.getReworkQty());
		vo.setReasonForRework(dto.getReasonForRework());
		vo.setRemarks(dto.getRemarks());
		if (dto.getInspectedBy() != null && dto.getInspectedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getInspectedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));

			vo.setInspectedBy(preparedBy);
		}
		if (dto.getCheckedBy() != null && dto.getCheckedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getCheckedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));

			vo.setCheckedBy(preparedBy);
		}
		vo.setActive(dto.isActive());
		vo.setCancel(dto.isCancel());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getItem() != null && dto.getItem() != 0) {
			ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
					.orElseThrow(() -> new ApplicationException("Item Code Not Found"));
			vo.setItem(item);
		}

		if (dto.getFromLocation() != null && dto.getFromLocation() != 0) {
			LocationVO fromLocation = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));
			vo.setFromLocation(fromLocation);
		}

		if (dto.getToLocationId() != null && dto.getToLocationId() != 0) {
			LocationVO toLocation = locationRepo.findById(dto.getToLocationId())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));
			vo.setToLocation(toLocation);
		}

		if (dto.getRejectedLocation() != null && dto.getRejectedLocation() != 0) {
			LocationVO rejectedLocation = locationRepo.findById(dto.getRejectedLocation())
					.orElseThrow(() -> new ApplicationException("Rejected Location Not Found"));
			vo.setRejectedLocation(rejectedLocation);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<PreDeliveryInspectionDetailsVO> existingDetails = pdiDetailsRepo.findByPreDeliveryInspectionVO(vo);
			if (existingDetails != null && !existingDetails.isEmpty()) {
				pdiDetailsRepo.deleteAll(existingDetails);
			}
		}

		List<PreDeliveryInspectionDetailsVO> detailsList = new ArrayList<>();
		if (dto.getInspectionDetails() != null) {
			for (PreDeliveryInspectionDetailsDTO d : dto.getInspectionDetails()) {
				PreDeliveryInspectionDetailsVO detailsVO = new PreDeliveryInspectionDetailsVO();
				detailsVO.setParameter(d.getParameter());
				detailsVO.setParameterType(d.getParameterType());
				detailsVO.setSpecification(d.getSpecification());
				detailsVO.setInstrumentName(d.getInstrumentName());
				if (d.getUnit() != null && d.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found in Details"));
					detailsVO.setUnit(unit);
				}

				detailsVO.setTol(d.getTol());
				detailsVO.setMethod(d.getMethod());
				detailsVO.setObs1(d.getObs1());
				detailsVO.setObs2(d.getObs2());
				detailsVO.setObs3(d.getObs3());
				detailsVO.setObs4(d.getObs4());

				detailsVO.setPreDeliveryInspectionVO(vo);
				detailsList.add(detailsVO);
			}
		}
		vo.setInspectionDetails(detailsList);
	}

	private PreDeliveryInspectionResponseDTO buildPreDeliveryInspectionResponse(PreDeliveryInspectionVO vo) {
		PreDeliveryInspectionResponseDTO responseDTO = new PreDeliveryInspectionResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setItemDrawingNo(vo.getItemDrawingNo());
		responseDTO.setProdSchOrdNo(vo.getProdSchOrdNo());
		responseDTO.setStock(vo.getStock());
		responseDTO.setCustomerCode(vo.getCustomerCode());
		responseDTO.setLcDeliverySchNo(vo.getLcDeliverySchNo());
		responseDTO.setPoNo(vo.getPoNo());
		responseDTO.setInvNo(vo.getInvNo());
		responseDTO.setInvDate(vo.getInvDate());
		responseDTO.setInitialPlanNo(vo.getInitialPlanNo());
		responseDTO.setDate(vo.getDate());
		responseDTO.setSchOrdDate(vo.getSchOrdDate());
		responseDTO.setSchQty(vo.getSchQty());
		responseDTO.setProducedQty(vo.getProducedQty());
		responseDTO.setCustomerName(vo.getCustomerName());
		responseDTO.setCustPartNo(vo.getCustPartNo());
		responseDTO.setTime(vo.getTime());
		responseDTO.setTransferSlipNo(vo.getTransferSlipNo());
		responseDTO.setTransferSlipDate(vo.getTransferSlipDate());

		responseDTO.setQtyInspected(vo.getQtyInspected());
		responseDTO.setQtyPassed(vo.getQtyPassed());
		responseDTO.setRate(vo.getRate());
		responseDTO.setRejQty(vo.getRejQty());
		responseDTO.setReasonForRejection(vo.getReasonForRejection());
		responseDTO.setScrapQty(vo.getScrapQty());
		responseDTO.setReworkQty(vo.getReworkQty());
		responseDTO.setReasonForRework(vo.getReasonForRework());
		responseDTO.setRemarks(vo.getRemarks());

		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getInspectedBy() != null) {
			EmployeeMasterResponseDetailsDTO preparedByDTO = new EmployeeMasterResponseDetailsDTO();
			preparedByDTO.setId(vo.getInspectedBy().getId());
			preparedByDTO.setEmployeeCode(vo.getInspectedBy().getEmployeeId());
			preparedByDTO.setEmployeeName(vo.getInspectedBy().getEmployeeName());
			responseDTO.setInspectedBy(preparedByDTO);
		}

		if (vo.getCheckedBy() != null) {
			EmployeeMasterResponseDetailsDTO preparedByDTO = new EmployeeMasterResponseDetailsDTO();
			preparedByDTO.setId(vo.getInspectedBy().getId());
			preparedByDTO.setEmployeeCode(vo.getCheckedBy().getEmployeeId());
			preparedByDTO.setEmployeeName(vo.getCheckedBy().getEmployeeName());
			responseDTO.setCheckedBy(preparedByDTO);
		}

		if (vo.getItem() != null) {
			ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
			itemDTO.setId(vo.getItem().getId());
			itemDTO.setItemCode(vo.getItem().getItemCode());
			itemDTO.setItemDescription(vo.getItem().getItemDescription());
			responseDTO.setItemCode(itemDTO);
		}

		if (vo.getFromLocation() != null) {
			LocationMasterResponseDTO locDTO = new LocationMasterResponseDTO();
			locDTO.setId(vo.getFromLocation().getId());
			locDTO.setLocationName(vo.getFromLocation().getLocationName());
			responseDTO.setFromLocation(locDTO);
		}

		if (vo.getToLocation() != null) {
			LocationMasterResponseDTO locDTO = new LocationMasterResponseDTO();
			locDTO.setId(vo.getToLocation().getId());
			locDTO.setLocationName(vo.getToLocation().getLocationName());
			responseDTO.setToLocation(locDTO);
		}

		if (vo.getRejectedLocation() != null) {
			LocationMasterResponseDTO locDTO = new LocationMasterResponseDTO();
			locDTO.setId(vo.getRejectedLocation().getId());
			locDTO.setLocationName(vo.getRejectedLocation().getLocationName());
			responseDTO.setRejectedLocation(locDTO);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		List<PreDeliveryInspectionDetailsResponseDTO> detailsList = new ArrayList<>();
		if (vo.getInspectionDetails() != null) {
			for (PreDeliveryInspectionDetailsVO detailsVO : vo.getInspectionDetails()) {
				PreDeliveryInspectionDetailsResponseDTO detailsDTO = new PreDeliveryInspectionDetailsResponseDTO();
				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setParameter(detailsVO.getParameter());
				detailsDTO.setParameterType(detailsVO.getParameterType());
				detailsDTO.setSpecification(detailsVO.getSpecification());
				detailsDTO.setInstrumentName(detailsVO.getInstrumentName());
				if (detailsVO.getUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailsVO.getUnit().getId());
					unitDTO.setUnitId(detailsVO.getUnit().getUnitId());
					detailsDTO.setUnit(unitDTO);
				}

				detailsDTO.setTol(detailsVO.getTol());
				detailsDTO.setMethod(detailsVO.getMethod());
				detailsDTO.setObs1(detailsVO.getObs1());
				detailsDTO.setObs2(detailsVO.getObs2());
				detailsDTO.setObs3(detailsVO.getObs3());
				detailsDTO.setObs4(detailsVO.getObs4());
				detailsList.add(detailsDTO);
			}
		}
		responseDTO.setInspectionDetails(detailsList);

		return responseDTO;
	}

	@Override
	public String getPreDeliveryInspectionDocId(Long orgId, String financialYear) throws ApplicationException {
		String screenCode = "PDI";
		return preDeliveryInspectionRepo.getPreDeliveryInspectionDocId(orgId, financialYear, screenCode);
	}

	@Override
	public PreDeliveryInspectionResponseDTO getPreDeliveryInspectionById(Long id) throws ApplicationException {
		PreDeliveryInspectionVO vo = preDeliveryInspectionRepo.getPreDeliveryInspectionById(id);
		if (vo == null) {
			throw new ApplicationException("Pre-Delivery Inspection Not Found");
		}
		return buildPreDeliveryInspectionResponse(vo);
	}

	@Override
	public List<PreDeliveryInspectionResponseDTO> getPreDeliveryInspectionByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<PreDeliveryInspectionVO> list = preDeliveryInspectionRepo.getPreDeliveryInspectionByOrgId(orgId, branch);
		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Pre-Delivery Inspection Not Found");
		}
		List<PreDeliveryInspectionResponseDTO> responseList = new ArrayList<>();
		for (PreDeliveryInspectionVO vo : list) {
			responseList.add(buildPreDeliveryInspectionResponse(vo));
		}
		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgTransferSlipNo(Long orgId, Long branch) {

		Set<Object[]> chType = preDeliveryInspectionRepo.getFgTransferSlipNo(orgId, branch);

		return getFgTransferSlipNo(chType);
	}

	private List<Map<String, Object>> getFgTransferSlipNo(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsFromFgTransferSlipNo(Long orgId, Long branch,
			String transferSlipNo) {

		Set<Object[]> chType = preDeliveryInspectionRepo.getItemDetailsFromFgTransferSlipNo(orgId, branch,
				transferSlipNo);

		return getItemDetailsFromFgTransferSlipNo(chType);
	}

	private List<Map<String, Object>> getItemDetailsFromFgTransferSlipNo(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("fgItem", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("scheduleNo", ch[3] != null ? ch[3].toString() : "");
			map.put("scheduleDate", ch[4] != null ? ch[4].toString() : "");
			map.put("drawingNo", ch[5] != null ? ch[5].toString() : "");
			map.put("scheduledQty", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);
			map.put("customerPartNo", ch[7] != null ? ch[7].toString() : "");
			map.put("customer", ch[8] != null ? ((Number) ch[8]).longValue() : null);
			map.put("customerName", ch[9] != null ? ch[9].toString() : "");
			map.put("customerCode", ch[10] != null ? ch[10].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getInitialPlanningNo(Long orgId, Long item) {

		Set<Object[]> chType = preDeliveryInspectionRepo.getInitialPlanningNo(orgId, item);

		return getInitialPlanningNo(chType);
	}

	private List<Map<String, Object>> getInitialPlanningNo(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getInspectionDetailsFromFgTransferSlipNo(Long orgId, Long item,
			String transferSlipNo) {

		Set<Object[]> chType = preDeliveryInspectionRepo.getInspectionDetailsFromFgTransferSlipNo(orgId, item,
				transferSlipNo);

		return getInspectionDetailsFromFgTransferSlipNo(chType);
	}

	private List<Map<String, Object>> getInspectionDetailsFromFgTransferSlipNo(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("parameter", ch[0] != null ? ch[0].toString() : "");
			map.put("specification", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			map.put("accCriteria", ch[3] != null ? ch[3].toString() : "");
			map.put("inspectionMethod", ch[4] != null ? ch[4].toString() : "");
			map.put("uomDescription", ch[5] != null ? ch[5].toString() : "");

			list.add(map);
		}

		return list;
	}

}