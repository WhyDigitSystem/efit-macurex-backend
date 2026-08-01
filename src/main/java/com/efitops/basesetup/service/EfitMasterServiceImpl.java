package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.CityResponseDTO;
import com.efitops.basesetup.ResponseDTO.CountryResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.dto.BomDTO;
import com.efitops.basesetup.dto.BomDetailsDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.DesignationResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDTO;
import com.efitops.basesetup.dto.EmployeeMasterResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.MaterialTypeDetailsDTO;
import com.efitops.basesetup.dto.StateResponseDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomDetailsVO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CityVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.MaterialTypeDetailsVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.StateVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BomDetailsRepo;
import com.efitops.basesetup.repository.BomRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CityRepo;
import com.efitops.basesetup.repository.CountryRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DesignationRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.MaterialTypeDetailRepo;
import com.efitops.basesetup.repository.MaterialTypeRepo;
import com.efitops.basesetup.repository.StateRepo;
import com.efitops.basesetup.repository.UomRepo;
import com.efitops.basesetup.repository.UserRepo;

@Service
public class EfitMasterServiceImpl implements EfitMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(EfitMasterServiceImpl.class);

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	MaterialTypeRepo materialTypeRepo;

	@Autowired
	MaterialTypeDetailRepo materialTypeDetailRepo;

	@Autowired
	DesignationRepo designationrepo;

	@Autowired
	UomRepo uomrepo;

	@Autowired
	BomRepo bomRepo;

	@Autowired
	BomDetailsRepo bomDetailsRepo;

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	CityRepo cityRepo;

	@Autowired
	DesignationRepo designationRepo;

	@Autowired
	CountryRepo countryRepo;

	@PersistenceContext
	private EntityManager entityManager;

	// Department
	@Override
	public Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException {
		DepartmentVO departmentVO = new DepartmentVO();
		String message;
		String screenCode = "DEPT";
		if (ObjectUtils.isNotEmpty(departmentDTO.getId())) {
			departmentVO = departmentRepo.findById(departmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Department details"));

			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			if (!departmentVO.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
				if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentName: %s already exists in This Organization.",
							departmentDTO.getDepartmentName());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
			}

			if (!departmentVO.getDepartmentCode().equalsIgnoreCase(departmentDTO.getDepartmentCode())) {
				if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
							departmentDTO.getDepartmentCode());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
			}
			message = "Department Updated Successfully";
		} else {

			if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentName : %s already exists in This Organization.",
						departmentDTO.getDepartmentName());
				throw new ApplicationException(errorMessage);
			}
			if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
						departmentDTO.getDepartmentCode());
				throw new ApplicationException(errorMessage);
			}
//			String docId = departmentRepo.getDepartmentDocId(departmentDTO.getOrgId(), departmentDTO.getFinYear(),
//					departmentDTO.getBranch(), screenCode);
//			departmentVO.setDocId(docId);

//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(departmentDTO.getOrgId(),
//							departmentDTO.getFinYear(), departmentDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			message = "Department Created Successfully";
		}

		createUpdateDepartmentVOByDepartmentDTO(departmentDTO, departmentVO);
		departmentRepo.save(departmentVO);
		Map<String, Object> response = new HashMap<>();
		response.put("departmentVO", departmentVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDepartmentVOByDepartmentDTO(DepartmentDTO departmentDTO, DepartmentVO departmentVO)
			throws ApplicationException {
		departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
		departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
		departmentVO.setOrgId(departmentDTO.getOrgId());
		departmentVO.setFinYear(departmentDTO.getFinYear());
		departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
		departmentVO.setActive(departmentDTO.isActive());
		departmentVO.setCancelRemarks(departmentDTO.getCancelRemarks());

		if (departmentDTO.getBranch() != null && departmentDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(departmentDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			departmentVO.setBranch(branch);
		}

	}

	@Override
	public String getDepartmentDocId(Long orgId, String finyear, Long branch) {
		String screenCode = "DEPT";
		String result = departmentRepo.getDepartmentDocId(orgId, finyear, branch, screenCode);
		return result;
	}

	@Override
	public List<DepartmentVO> getAllDepartmentByOrgId(Long orgId, Long branch) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Department BY OrgId : {}", orgId);
			departmentVO = departmentRepo.getAllDepartmentByOrgId(orgId, branch);
		}
		return departmentVO;
	}

	@Override
	public List<DepartmentVO> getDepartmentById(Long id) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Department BY Id : {}", id);
			departmentVO = departmentRepo.getDepartmentById(id);
		}
		return departmentVO;
	}

	// Material Type

	@Override
	public Map<String, Object> createUpdateMaterialType(MaterialTypeDTO materialTypeDTO) throws ApplicationException {
		MaterialTypeVO materialTypeVO = new MaterialTypeVO();
		String message;
		if (ObjectUtils.isNotEmpty(materialTypeDTO.getId())) {
			materialTypeVO = materialTypeRepo.findById(materialTypeDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid MaterialType Details"));
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());

//			if (!materialTypeVO.getItemGroup().equalsIgnoreCase(materialTypeDTO.getItemGroup())) {
//				if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
//						materialTypeDTO.getOrgId())) {
//					String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
//							materialTypeDTO.getItemGroup());
//					throw new ApplicationException(errorMessage);
//				}
//				materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toUpperCase());
//			}
			message = "MaterialType Updated Successfully";
		} else {
//			if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
//					materialTypeDTO.getOrgId())) {
//				String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
//						materialTypeDTO.getItemGroup());
//				throw new ApplicationException(errorMessage);
//			}
			materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());
			message = "MaterialType Created Successfully";
		}

		getMaterialTypeVOFromMaterialTypeDTO(materialTypeDTO, materialTypeVO);
		materialTypeRepo.save(materialTypeVO);

		Map<String, Object> response = new HashMap<>();
		response.put("materialTypeVO", materialTypeVO);
		response.put("message", message);
		return response;
	}

	private void getMaterialTypeVOFromMaterialTypeDTO(MaterialTypeDTO materialTypeDTO, MaterialTypeVO materialTypeVO)
			throws ApplicationException {

		materialTypeVO.setMaterialType(materialTypeDTO.getMaterialType().toUpperCase());
		materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toUpperCase());
		materialTypeVO.setOrgId(materialTypeDTO.getOrgId());
		materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());

		boolean isCreate = ObjectUtils.isEmpty(materialTypeDTO.getId());

		List<MaterialTypeDetailsVO> materialTypeDetailsVOs = new ArrayList<>();

		for (MaterialTypeDetailsDTO dto : materialTypeDTO.getMaterialTypeDetailDTO()) {

			String itemSubGroup = dto.getItemSubGroup().toUpperCase();

			// ✅ DUPLICATE CHECK
			if (isCreate) {
				// CREATE → full DB check
				if (materialTypeDetailRepo
						.existsByMaterialTypeVO_OrgIdAndMaterialTypeVO_MaterialTypeAndMaterialTypeVO_ItemGroupAndItemSubGroupIgnoreCase(
								materialTypeDTO.getOrgId(), materialTypeDTO.getMaterialType().toUpperCase(),
								materialTypeDTO.getItemGroup().toUpperCase(), itemSubGroup)) {

					throw new ApplicationException("Duplicate ItemSubGroup not allowed: " + itemSubGroup);
				}
			} else {
				// UPDATE → exclude same PARENT (MaterialTypeVO) ID
				if (materialTypeDetailRepo
						.existsByMaterialTypeVO_OrgIdAndMaterialTypeVO_MaterialTypeAndMaterialTypeVO_ItemGroupAndItemSubGroupIgnoreCaseAndMaterialTypeVO_IdNot(
								materialTypeDTO.getOrgId(), materialTypeDTO.getMaterialType().toUpperCase(),
								materialTypeDTO.getItemGroup().toUpperCase(), itemSubGroup, materialTypeDTO.getId() // ✅
																													// parent
																													// id
						)) {

					throw new ApplicationException("Duplicate ItemSubGroup not allowed: " + itemSubGroup);
				}

			}

			MaterialTypeDetailsVO vo = new MaterialTypeDetailsVO();
			vo.setItemSubGroup(itemSubGroup);
			vo.setMaterialTypeVO(materialTypeVO);

			materialTypeDetailsVOs.add(vo);
		}

		// 🔁 Update case: remove old details after validation
		if (!isCreate) {
			List<MaterialTypeDetailsVO> oldDetails = materialTypeDetailRepo.findByMaterialTypeVO(materialTypeVO);
			materialTypeDetailRepo.deleteAll(oldDetails);
		}

		materialTypeVO.setMaterialTypeDetailsVO(materialTypeDetailsVOs);
	}

	@Override
	public List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  MaterialType BY OrgId : {}", orgId);
			materialTypeVO = materialTypeRepo.getAllMaterialTypeByOrgId(orgId);
		}
		return materialTypeVO;
	}

	@Override
	public List<MaterialTypeVO> getMaterialTypeById(Long id) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  MaterialType BY Id : {}", id);
			materialTypeVO = materialTypeRepo.getMaterialTypeById(id);
		}
		return materialTypeVO;
	}

	@Override
	public List<DesignationVO> getDesignationByOrgId(Long orgId, Long branch) {
		List<DesignationVO> designationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ArapAdjustments BY OrgId : {}", orgId);
			designationVO = designationrepo.getDesignationByOrgId(orgId, branch);
		}
		return designationVO;
	}

	@Override
	public List<DesignationVO> getDesignationById(Long id) {
		List<DesignationVO> designationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ArapAdjustments BY Id : {}", id);
			designationVO = designationrepo.getDesignationById(id);
		}
		return designationVO;
	}

	@Override
	public Map<String, Object> updateCreateDesignation(@Valid DesignationDTO designationDTO)
			throws ApplicationException {
		String screenCode = "DSG";
		DesignationVO designationVO = new DesignationVO();
		String message;
		if (ObjectUtils.isNotEmpty(designationDTO.getId())) {
			designationVO = designationrepo.findById(designationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Designation not found"));

			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			if (!designationVO.getDesignation().equalsIgnoreCase(designationDTO.getDesignation())) {
				if (designationrepo.existsByDesignationAndOrgId(designationDTO.getDesignation(),
						designationDTO.getOrgId())) {
					String errorMessage = String.format("The Designation: %s already exists This Organization.",
							designationDTO.getDesignation());
					throw new ApplicationException(errorMessage);
				}
				designationVO.setDesignation(designationDTO.getDesignation().toUpperCase());
			}
			if (!designationVO.getDesignationCode().equalsIgnoreCase(designationDTO.getDesignationCode())) {
				if (designationrepo.existsByDesignationCodeAndOrgId(designationDTO.getDesignationCode(),
						designationDTO.getOrgId())) {
					String errorMessage = String.format("The DesignationCode: %s already exists This Organization.",
							designationDTO.getDesignationCode());
					throw new ApplicationException(errorMessage);
				}
				designationVO.setDesignationCode(designationDTO.getDesignationCode().toUpperCase());

			}
			message = "Designation  Updated Successfully";
		} else {
			createUpdateDesignationVOByDesignationDTO(designationDTO, designationVO);
//			String docId = designationrepo.getDesignationDocId(designationDTO.getOrgId(), designationDTO.getFinYear(),
//					designationDTO.getBranch(), screenCode);
//			designationVO.setDocId(docId);

//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(designationDTO.getOrgId(),
//							designationDTO.getFinYear(), designationDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			if (designationrepo.existsByDesignationAndDesignationCodeAndOrgId(designationDTO.getDesignation(),
					designationDTO.getDesignationCode(), designationDTO.getOrgId())) {
				String errorMessage = String.format(
						"The Designation: %s and DesignationCode: %s already exists This Organization.",
						designationDTO.getDesignation(), designationDTO.getDesignationCode());
				throw new ApplicationException(errorMessage);
			}

			if (designationrepo.existsByDesignationAndOrgId(designationDTO.getDesignation(),
					designationDTO.getOrgId())) {
				String errorMessage = String.format("The Designation: %s already exists This Organization.",
						designationDTO.getDesignation());
				throw new ApplicationException(errorMessage);
			}

			if (designationrepo.existsByDesignationCodeAndOrgId(designationDTO.getDesignationCode(),
					designationDTO.getOrgId())) {
				String errorMessage = String.format("The DesignationCode: %s already exists This Organization.",
						designationDTO.getDesignationCode());
				throw new ApplicationException(errorMessage);
			}

			designationVO.setCreatedBy(designationDTO.getCreatedBy());
			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			message = "Designation Created Successfully";
		}

		designationrepo.save(designationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("designationVO", designationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDesignationVOByDesignationDTO(@Valid DesignationDTO designationDTO,
			DesignationVO designationVO) throws ApplicationException {
		designationVO.setDesignation(designationDTO.getDesignation().toUpperCase());
		designationVO.setDesignationCode(designationDTO.getDesignationCode().toUpperCase());
		designationVO.setOrgId(designationDTO.getOrgId());
		designationVO.setFinYear(designationDTO.getFinYear());
		designationVO.setActive(designationDTO.isActive());
		designationVO.setCancelRemarks(designationDTO.getCancelRemarks());
		if (designationDTO.getBranch() != null && designationDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(designationDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			designationVO.setBranch(branch);
		}

	}

	@Override
	public String getDesignationDocId(Long orgId, String finYear, Long branch) {
		String screenCode = "DSG";
		String result = designationrepo.getDesignationDocId(orgId, finYear, branch, screenCode);
		return result;
	}

	@Override
	public List<UomVO> getUomByOrgId(Long orgId) {
		List<UomVO> uomVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Uom BY OrgId : {}", orgId);
			uomVO = uomrepo.getUomByOrgId(orgId);
		}
		return uomVO;
	}

	@Override
	public List<UomVO> getUomById(Long id) {
		List<UomVO> uomVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Uom BY Id : {}", id);
			uomVO = uomrepo.getUomById(id);
		}
		return uomVO;
	}

	@Override
	public Map<String, Object> updateCreateUom(@Valid UomDTO uomDTO) throws ApplicationException {
		String screenCode = "D";
		UomVO uomVO = new UomVO();
		String message;
		if (ObjectUtils.isNotEmpty(uomDTO.getId())) {
			uomVO = uomrepo.findById(uomDTO.getId()).orElseThrow(() -> new ApplicationException("Uom not found"));

			if (!uomVO.getUomCode().toUpperCase().equalsIgnoreCase(uomDTO.getUomCode().toUpperCase())) {
				if (uomrepo.existsByUomCodeAndOrgId(uomDTO.getUomCode(), uomDTO.getOrgId())) {
					String errorMessage = String.format("This UomCode: %s Already Exists in This Organization",
							uomDTO.getUomCode());
					throw new ApplicationException(errorMessage);
				}
				uomVO.setUomCode(uomDTO.getUomCode().toUpperCase());
			}

			if (!uomVO.getUomDesc().toUpperCase().equalsIgnoreCase(uomDTO.getUomDesc().toUpperCase())) {
				if (uomrepo.existsByUomDescAndOrgId(uomDTO.getUomDesc(), uomDTO.getOrgId())) {
					String errorMessage = String.format("This UomCode: %s Already Exists in This Organization",
							uomDTO.getUomDesc());
					throw new ApplicationException(errorMessage);
				}
				uomVO.setUomCode(uomDTO.getUomDesc().toUpperCase());
			}

			uomVO.setUpdatedBy(uomDTO.getCreatedBy());
			createUpdateUomVOByUomDTO(uomDTO, uomVO);
			message = "Uom  Updated Successfully";
		} else {

			if (uomrepo.existsByUomCodeAndOrgId(uomDTO.getUomCode(), uomDTO.getOrgId())) {
				String errorMessage = String.format("The UomCode: %s  already exists This Organization.",
						uomDTO.getUomCode());
				throw new ApplicationException(errorMessage);
			}

			if (uomrepo.existsByUomDescAndOrgId(uomDTO.getUomDesc(), uomDTO.getOrgId())) {
				String errorMessage = String.format("The UomDesc: %s  already exists This Organization.",
						uomDTO.getUomCode());
				throw new ApplicationException(errorMessage);
			}

			uomVO.setCreatedBy(uomDTO.getCreatedBy());
			uomVO.setUpdatedBy(uomDTO.getCreatedBy());
			createUpdateUomVOByUomDTO(uomDTO, uomVO);
			message = "Uom Created Successfully";
		}

		uomrepo.save(uomVO);
		Map<String, Object> response = new HashMap<>();
		response.put("uomVO", uomVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateUomVOByUomDTO(@Valid UomDTO uomDTO, UomVO uomVO) throws ApplicationException {
		uomVO.setUomCode(uomDTO.getUomCode().toUpperCase());
		uomVO.setUomDesc(uomDTO.getUomDesc());
		uomVO.setOrgId(uomDTO.getOrgId());
		uomVO.setActive(uomDTO.isActive());

	}

	// Bom Master

	@Override
	public Map<String, Object> createUpdateBom(BomDTO bomDTO) throws ApplicationException {
		BomVO bomVO = new BomVO();
		BomVO oldBom = null;
		String message = null;
		String screenCode = "BOM";
		if (ObjectUtils.isNotEmpty(bomDTO.getId())) {

			oldBom = bomRepo.findById(bomDTO.getId())
					.orElseThrow(() -> new ApplicationException("BOM master not found"));

			oldBom.getBomDetailsVO().size(); // load
			entityManager.detach(oldBom); // detach snapshot

			bomVO = bomRepo.findById(bomDTO.getId())
					.orElseThrow(() -> new ApplicationException("BOM  detailsNot Found with id: " + bomDTO.getId()));

			List<BomDetailsVO> bomDetailsVO1 = bomDetailsRepo.findByBomVO(bomVO);
			bomDetailsRepo.deleteAll(bomDetailsVO1);

			message = "jobWorkOut Updated Successfully";
			bomVO.setUpdatedBy(bomDTO.getCreatedBy());

		} else {

			String docId = bomRepo.getBomDocId(bomDTO.getOrgId(), bomDTO.getFinYear(), bomDTO.getBranchCode(),
					screenCode);
			bomVO.setDocid(docId);

//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(bomDTO.getOrgId(), bomDTO.getFinYear(),
//							bomDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			bomVO.setCreatedBy(bomDTO.getCreatedBy());
			bomVO.setUpdatedBy(bomDTO.getCreatedBy());

			message = "Bom Created Successfully";
		}

		createUpdatedBomVOFromBomDTO(bomDTO, bomVO);

//		commonNotificationService.generateNotification(bomVO.getScreenCode(), bomVO.getId(), oldBom, bomVO);

		bomRepo.save(bomVO);
//		createUpdateBOMMasterNotification(bomVO, bomDTO);
//		createUpdateBOMMasterNotification( bomVO , bomDTO);

		Map<String, Object> response = new HashMap<>();
		response.put("bomVO", bomVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedBomVOFromBomDTO(BomDTO bomDTO, BomVO bomVO) throws ApplicationException {
		bomVO.setProductCode(bomDTO.getProductCode());
		bomVO.setProductName(bomDTO.getProductName());
		bomVO.setProductType(bomDTO.getProductType());
		bomVO.setQty(bomDTO.getQty());
		bomVO.setUom(bomDTO.getUom());
		bomVO.setActive(bomDTO.isActive());
		bomVO.setFinYear(bomDTO.getFinYear());
		bomVO.setBranch(bomDTO.getBranch());
		bomVO.setBranchCode(bomDTO.getBranchCode());

		bomVO.setRevision(bomDTO.isRevision());
		bomVO.setCurrent(bomDTO.isCurrent());
		bomVO.setOrgId(bomDTO.getOrgId());

		List<BomDetailsVO> bomDetailsVOs = new ArrayList<>();
		for (BomDetailsDTO bomDetailsDTO : bomDTO.getBomDetailsDTO()) {
			BomDetailsVO bomDetailsVO = new BomDetailsVO();
			bomDetailsVO.setItemCode(bomDetailsDTO.getItemCode());
			bomDetailsVO.setItemDesc(bomDetailsDTO.getItemDesc());
			bomDetailsVO.setItemType(bomDetailsDTO.getItemType());
			if (bomDetailsDTO.getQty().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Qty must be greater than zero.");
			} else {
				bomDetailsVO.setQty(bomDetailsDTO.getQty());
			}
			bomDetailsVO.setUom(bomDetailsDTO.getUom());
			bomDetailsVO.setBomVO(bomVO);
			bomDetailsVOs.add(bomDetailsVO);
		}
		bomVO.setBomDetailsVO(bomDetailsVOs);
	}

	@Override
	public String getBomDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "BOM";
		String result = bomRepo.getBomDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<BomVO> getAllBomOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return bomRepo.getAllBomByOrgId(orgId, branchCode);
	}

	@Override
	public List<BomVO> getAllBomId(Long id) {
		// TODO Auto-generated method stub
		return bomRepo.getBomById(id);
	}

	@Override
	public List<Map<String, Object>> getFGSFGPartDetailsForBOM(Long orgId, String productType) {
		Set<Object[]> FgSfg = bomRepo.findFGSFGPartDetails(orgId, productType);
		return getFGSFGPartDetailsForBOM(FgSfg);
	}

	private List<Map<String, Object>> getFGSFGPartDetailsForBOM(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryunit", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getSFGItemDetailsForBOM(Long orgId) {
		Set<Object[]> SfgItem = bomRepo.findSFGItemDetails(orgId);
		return getSFGItemDetailsForBOM(SfgItem);
	}

	private List<Map<String, Object>> getSFGItemDetailsForBOM(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryunit", ch[2] != null ? ch[2].toString() : "");
			map.put("itemtype", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	// EmployeeMaster

	@Override
	public Map<String, Object> updateCreateEmployeeMaster(EmployeeMasterDTO employeeMasterDTO)
			throws ApplicationException {

		String screenCode = "MAC";
		EmployeeMasterVO employeeMasterVO = new EmployeeMasterVO();
		String message;
		if (ObjectUtils.isNotEmpty(employeeMasterDTO.getId())) {

			employeeMasterVO = employeeMasterRepo.findById(employeeMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Item not found"));
			employeeMasterVO.setUpdatedBy(employeeMasterDTO.getCreatedBy());
			createUpdateEmployeeMasterVOByEmployeeMasterDTO(employeeMasterDTO, employeeMasterVO);
			message = "Employee Updated Successfully";
		} else {
//
			String docId = employeeMasterRepo.getEmployeeByDocId(employeeMasterDTO.getOrgId(), screenCode);

			employeeMasterVO.setEmployeeId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(employeeMasterDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			employeeMasterVO.setCreatedBy(employeeMasterDTO.getCreatedBy());
			employeeMasterVO.setUpdatedBy(employeeMasterDTO.getCreatedBy());
			createUpdateEmployeeMasterVOByEmployeeMasterDTO(employeeMasterDTO, employeeMasterVO);
			message = "Item Created Successfully";
		}

		EmployeeMasterVO savedItemMaster = employeeMasterRepo.save(employeeMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("employeeMasterVO", buildEmployeeMasterResponse(savedItemMaster));

		return response;
	}

	private EmployeeMasterResponseDTO buildEmployeeMasterResponse(EmployeeMasterVO employeeMasterVO) {

		EmployeeMasterResponseDTO responseDTO = new EmployeeMasterResponseDTO();

		responseDTO.setId(employeeMasterVO.getId());

		responseDTO.setSurName(employeeMasterVO.getSurName());
		responseDTO.setMiddleName(employeeMasterVO.getMiddleName());
		responseDTO.setFatherHusbandName(employeeMasterVO.getFatherHusbandName());
		responseDTO.setTitle(employeeMasterVO.getTitle());
		responseDTO.setAccountHead(employeeMasterVO.getAccountHead());
		responseDTO.setSex(employeeMasterVO.getSex());
		responseDTO.setDateOfBirth(employeeMasterVO.getDateOfBirth());
		responseDTO.setTelephone(employeeMasterVO.getTelephone());
		responseDTO.setMobile(employeeMasterVO.getMobile());
		responseDTO.setEmail(employeeMasterVO.getEmail());
		responseDTO.setQualification(employeeMasterVO.getQualification());
		responseDTO.setGrade(employeeMasterVO.getGrade());
		responseDTO.setPassportNo(employeeMasterVO.getPassportNo());
		responseDTO.setPanNo(employeeMasterVO.getPanNo());
		responseDTO.setBloodGroup(employeeMasterVO.getBloodGroup());
		responseDTO.setNominee(employeeMasterVO.getNominee());

		responseDTO.setTempAddressLine(employeeMasterVO.getTempAddressLine());

		if (employeeMasterVO.getTempCity() != null) {
			CityResponseDTO primaryUnitDTO = new CityResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getTempCity().getId());
			primaryUnitDTO.setCityName(employeeMasterVO.getTempCity().getCityName());
			responseDTO.setTempCitys(primaryUnitDTO);
		}

		if (employeeMasterVO.getTempState() != null) {
			StateResponseDTO primaryUnitDTO = new StateResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getTempState().getId());
			primaryUnitDTO.setStateName(employeeMasterVO.getTempState().getStateName());
			responseDTO.setTempState(primaryUnitDTO);
		}

		if (employeeMasterVO.getTempCountry() != null) {
			CountryResponseDTO primaryUnitDTO = new CountryResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getTempCountry().getId());
			primaryUnitDTO.setCountryName(employeeMasterVO.getTempCountry().getCountryName());
			responseDTO.setTempCountry(primaryUnitDTO);
		}

		responseDTO.setTempPincode(employeeMasterVO.getTempPincode());

		responseDTO.setPermanentAddressLine(employeeMasterVO.getPermanentAddressLine());

		if (employeeMasterVO.getPermanentCity() != null) {
			CityResponseDTO primaryUnitDTO = new CityResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getTempCity().getId());
			primaryUnitDTO.setCityName(employeeMasterVO.getTempCity().getCityName());
			responseDTO.setPermanentCitys(primaryUnitDTO);
		}

		if (employeeMasterVO.getPermanentState() != null) {
			StateResponseDTO primaryUnitDTO = new StateResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getPermanentState().getId());
			primaryUnitDTO.setStateName(employeeMasterVO.getPermanentState().getStateName());
			responseDTO.setPermanentState(primaryUnitDTO);
		}

		if (employeeMasterVO.getPermanentCountry() != null) {
			CountryResponseDTO primaryUnitDTO = new CountryResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getPermanentCountry().getId());
			primaryUnitDTO.setCountryName(employeeMasterVO.getPermanentCountry().getCountryName());
			responseDTO.setPermanentCountry(primaryUnitDTO);
		}

		responseDTO.setPermanentPincode(employeeMasterVO.getPermanentPincode());

		// Other Information

		responseDTO.setCardNo(employeeMasterVO.getCardNo());
		responseDTO.setTemporaryCardNo(employeeMasterVO.getTemporaryCardNo());
		responseDTO.setDateOfJoining(employeeMasterVO.getDateOfJoining());

		if (employeeMasterVO.getPlantId() != null) {
			BranchResponseDTO primaryUnitDTO = new BranchResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getPlantId().getId());
			primaryUnitDTO.setBranchName(employeeMasterVO.getPlantId().getBranchName());
			responseDTO.setPlant(primaryUnitDTO);
		}

		if (employeeMasterVO.getDepartment() != null) {
			DepartmentResponseDTO primaryUnitDTO = new DepartmentResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getDepartment().getId());
			primaryUnitDTO.setDepartmentName(employeeMasterVO.getDepartment().getDepartmentName());
			responseDTO.setDepartment(primaryUnitDTO);
		}

		if (employeeMasterVO.getDesignation() != null) {
			DesignationResponseDTO primaryUnitDTO = new DesignationResponseDTO();
			primaryUnitDTO.setId(employeeMasterVO.getDesignation().getId());
			primaryUnitDTO.setDesignationName(employeeMasterVO.getDesignation().getDesignation());
			responseDTO.setDesignation(primaryUnitDTO);
		}

		responseDTO.setNatureOfEmployment(employeeMasterVO.getNatureOfEmployment());
		responseDTO.setOverTimeApplicable(employeeMasterVO.getOverTimeApplicable());
		responseDTO.setReferenceBy(employeeMasterVO.getReferenceBy());

		if (employeeMasterVO.getOkdBy() != null) {

			EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
			employeeDTO.setId(employeeMasterVO.getOkdBy().getId());
			employeeDTO.setEmployeeName(employeeMasterVO.getOkdBy().getEmployeeName());

			responseDTO.setOkdBy(employeeDTO);
		}

		if (employeeMasterVO.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(employeeMasterVO.getBranch().getId());
			branchDTO.setBranchName(employeeMasterVO.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}
		responseDTO.setModeOfPayment(employeeMasterVO.getModeOfPayment());
		responseDTO.setBankAccountNo(employeeMasterVO.getBankAccountNo());
		responseDTO.setBankName(employeeMasterVO.getBankName());
		responseDTO.setPfNo(employeeMasterVO.getPfNo());
		responseDTO.setEsiNo(employeeMasterVO.getEsiNo());
		responseDTO.setEsiDispName(employeeMasterVO.getEsiDispName());
		responseDTO.setVpfPercentage(employeeMasterVO.getVpfPercentage());
		responseDTO.setDateOfConfirmation(employeeMasterVO.getDateOfConfirmation());
		responseDTO.setInformationActive(employeeMasterVO.getInformation_active());
		responseDTO.setTrainingStartDate(employeeMasterVO.getTrainingStartDate());
		responseDTO.setTrainingEndDate(employeeMasterVO.getTrainingEndDate());
		responseDTO.setNoticePeriod(employeeMasterVO.getNoticePeriod());
		responseDTO.setCurrentSalaryPeriodStart(employeeMasterVO.getCurrentSalaryPeriodStart());
		responseDTO.setCurrentSalaryPeriodEnd(employeeMasterVO.getCurrentSalaryPeriodEnd());

		// Common Fields

		responseDTO.setCreatedBy(employeeMasterVO.getCreatedBy());
		responseDTO.setUpdatedBy(employeeMasterVO.getUpdatedBy());
		responseDTO.setCancelRemarks(employeeMasterVO.getCancelRemarks());
		responseDTO.setScreenName(employeeMasterVO.getScreenName());
		responseDTO.setScreenCode(employeeMasterVO.getScreenCode());
		responseDTO.setOrgId(employeeMasterVO.getOrgId());
		responseDTO.setFinancialYear(employeeMasterVO.getFinancialYear());
		responseDTO.setEmployeeName(employeeMasterVO.getEmployeeName());
		responseDTO.setEmployeeId(employeeMasterVO.getEmployeeId());

		responseDTO.setActive(employeeMasterVO.getActive());

		return responseDTO;
	}

	private void createUpdateEmployeeMasterVOByEmployeeMasterDTO(EmployeeMasterDTO employeeMasterDTO,
			EmployeeMasterVO employeeMasterVO) throws ApplicationException {

		employeeMasterVO.setSurName(employeeMasterDTO.getSurName());
		employeeMasterVO.setMiddleName(employeeMasterDTO.getMiddleName());
		employeeMasterVO.setFatherHusbandName(employeeMasterDTO.getFatherHusbandName());
		employeeMasterVO.setTitle(employeeMasterDTO.getTitle());
		employeeMasterVO.setAccountHead(employeeMasterDTO.getAccountHead());
		employeeMasterVO.setSex(employeeMasterDTO.getSex());
		employeeMasterVO.setDateOfBirth(employeeMasterDTO.getDateOfBirth());
		employeeMasterVO.setTelephone(employeeMasterDTO.getTelephone());
		employeeMasterVO.setMobile(employeeMasterDTO.getMobile());
		employeeMasterVO.setEmail(employeeMasterDTO.getEmail());
		employeeMasterVO.setQualification(employeeMasterDTO.getQualification());
		employeeMasterVO.setGrade(employeeMasterDTO.getGrade());
		employeeMasterVO.setPassportNo(employeeMasterDTO.getPassportNo());
		employeeMasterVO.setPanNo(employeeMasterDTO.getPanNo());
		employeeMasterVO.setBloodGroup(employeeMasterDTO.getBloodGroup());
		employeeMasterVO.setNominee(employeeMasterDTO.getNominee());

		// Temporary Address

		employeeMasterVO.setTempAddressLine(employeeMasterDTO.getTempAddressLine());

		if (employeeMasterDTO.getTempCityId() != null && employeeMasterDTO.getTempCityId() != 0) {

			CityVO cityVO = cityRepo.findById(employeeMasterDTO.getTempCityId())
					.orElseThrow(() -> new ApplicationException("City Not Found"));

			employeeMasterVO.setTempCity(cityVO);
		}

		if (employeeMasterDTO.getTempStateId() != null && employeeMasterDTO.getTempStateId() != 0) {

			StateVO state = stateRepo.findById(employeeMasterDTO.getTempStateId())
					.orElseThrow(() -> new ApplicationException("Temporary State Not Found"));

			employeeMasterVO.setTempState(state);
		}

		if (employeeMasterDTO.getTempCountryId() != null && employeeMasterDTO.getTempCountryId() != 0) {

			CountryVO city = countryRepo.findById(employeeMasterDTO.getTempCountryId())
					.orElseThrow(() -> new ApplicationException("Temporary Country Not Found"));

			employeeMasterVO.setTempCountry(city);
		}

		employeeMasterVO.setTempPincode(employeeMasterDTO.getTempPincode());
		employeeMasterVO.setPermanentAddressLine(employeeMasterDTO.getPermanentAddressLine());

		if (employeeMasterDTO.getPermanentCity() != null && employeeMasterDTO.getPermanentCity() != 0) {

			CityVO city = cityRepo.findById(employeeMasterDTO.getPermanentCity())
					.orElseThrow(() -> new ApplicationException("PermanentCity Not Found"));

			employeeMasterVO.setPermanentCity(city);
		}

		if (employeeMasterDTO.getPermanentStateId() != null && employeeMasterDTO.getPermanentStateId() != 0) {

			StateVO state = stateRepo.findById(employeeMasterDTO.getPermanentStateId())
					.orElseThrow(() -> new ApplicationException("PermanentState Not Found"));

			employeeMasterVO.setPermanentState(state);
		}

		if (employeeMasterDTO.getPermanentCountryId() != null && employeeMasterDTO.getPermanentCountryId() != 0) {

			CountryVO country = countryRepo.findById(employeeMasterDTO.getPermanentCountryId())
					.orElseThrow(() -> new ApplicationException("Permanent Country Not Found"));

			employeeMasterVO.setPermanentCountry(country);
		}

		employeeMasterVO.setPermanentPincode(employeeMasterDTO.getPermanentPincode());

		// Other Information

		employeeMasterVO.setCardNo(employeeMasterDTO.getCardNo());
		employeeMasterVO.setTemporaryCardNo(employeeMasterDTO.getTemporaryCardNo());
		employeeMasterVO.setDateOfJoining(employeeMasterDTO.getDateOfJoining());

		if (employeeMasterDTO.getPlantId() != null && employeeMasterDTO.getPlantId() != 0) {

			BranchVO plant = branchRepo.findById(employeeMasterDTO.getPlantId())
					.orElseThrow(() -> new ApplicationException("Plant Not Found"));

			employeeMasterVO.setPlantId(plant);
		}

		if (employeeMasterDTO.getDepartmentId() != null && employeeMasterDTO.getDepartmentId() != 0) {

			DepartmentVO department = departmentRepo.findById(employeeMasterDTO.getDepartmentId())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			employeeMasterVO.setDepartment(department);
		}

		if (employeeMasterDTO.getDesignationId() != null && employeeMasterDTO.getDesignationId() != 0) {

			DesignationVO designation = designationRepo.findById(employeeMasterDTO.getDesignationId())
					.orElseThrow(() -> new ApplicationException("Designation Not Found"));

			employeeMasterVO.setDesignation(designation);
		}

		employeeMasterVO.setNatureOfEmployment(employeeMasterDTO.getNatureOfEmployment());
		employeeMasterVO.setOverTimeApplicable(employeeMasterDTO.getOverTimeApplicable());
		employeeMasterVO.setReferenceBy(employeeMasterDTO.getReferenceBy());

		if (employeeMasterDTO.getOkdById() != null && employeeMasterDTO.getOkdById() > 0) {

			EmployeeMasterVO employee = employeeMasterRepo.findById(employeeMasterDTO.getOkdById())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));

			employeeMasterVO.setOkdBy(employee);
		}

		employeeMasterVO.setModeOfPayment(employeeMasterDTO.getModeOfPayment());
		employeeMasterVO.setBankAccountNo(employeeMasterDTO.getBankAccountNo());
		employeeMasterVO.setBankName(employeeMasterDTO.getBankName());
		employeeMasterVO.setPfNo(employeeMasterDTO.getPfNo());
		employeeMasterVO.setEsiNo(employeeMasterDTO.getEsiNo());
		employeeMasterVO.setEsiDispName(employeeMasterDTO.getEsiDispName());
		employeeMasterVO.setVpfPercentage(employeeMasterDTO.getVpfPercentage());
		employeeMasterVO.setDateOfConfirmation(employeeMasterDTO.getDateOfConfirmation());
		employeeMasterVO.setInformation_active(employeeMasterDTO.getInformation_active());
		employeeMasterVO.setTrainingStartDate(employeeMasterDTO.getTrainingStartDate());
		employeeMasterVO.setTrainingEndDate(employeeMasterDTO.getTrainingEndDate());
		employeeMasterVO.setNoticePeriod(employeeMasterDTO.getNoticePeriod());
		employeeMasterVO.setCurrentSalaryPeriodStart(employeeMasterDTO.getCurrentSalaryPeriodStart());
		employeeMasterVO.setCurrentSalaryPeriodEnd(employeeMasterDTO.getCurrentSalaryPeriodEnd());

		employeeMasterVO.setCreatedBy(employeeMasterDTO.getCreatedBy());
		employeeMasterVO.setUpdatedBy(employeeMasterDTO.getUpdatedBy());
		employeeMasterVO.setCancelRemarks(employeeMasterDTO.getCancelRemarks());
		employeeMasterVO.setScreenName(employeeMasterDTO.getScreenName());
		employeeMasterVO.setScreenCode(employeeMasterDTO.getScreenCode());
		employeeMasterVO.setOrgId(employeeMasterDTO.getOrgId());
		employeeMasterVO.setFinancialYear(employeeMasterDTO.getFinancialYear());
		employeeMasterVO.setActive(employeeMasterDTO.isActive());

		employeeMasterVO
				.setEmployeeName(String
						.join(" ", employeeMasterDTO.getSurName() == null ? "" : employeeMasterDTO.getSurName(),
								employeeMasterDTO.getMiddleName() == null ? "" : employeeMasterDTO.getMiddleName())
						.trim());

		if (employeeMasterDTO.getBranchId() != null && employeeMasterDTO.getBranchId() != 0) {

			BranchVO branch = branchRepo.findById(employeeMasterDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			employeeMasterVO.setBranch(branch);
		}
	}

	@Override
	public EmployeeMasterResponseDTO getEmployeeMasterById(Long id) throws ApplicationException {

		EmployeeMasterVO employeeMasterVO = employeeMasterRepo.getEmployeeMasterById(id);

		if (employeeMasterVO == null) {
			throw new ApplicationException("Employee Master Not Found");
		}

		return buildEmployeeMasterResponse(employeeMasterVO);
	}

	@Override
	public List<EmployeeMasterResponseDTO> getEmployeeMasterByOrgId(Long orgId) throws ApplicationException {

		List<EmployeeMasterVO> employeeList = employeeMasterRepo.getEmployeeMasterByOrgId(orgId);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("Employee Master Not Found");
		}

		List<EmployeeMasterResponseDTO> responseList = new ArrayList<>();

		for (EmployeeMasterVO employeeMasterVO : employeeList) {
			responseList.add(buildEmployeeMasterResponse(employeeMasterVO));
		}

		return responseList;
	}

	@Override
	public String getEmployeeByDocId(Long orgId, String screenCode) {
		String result = employeeMasterRepo.getEmployeeByDocId(orgId, screenCode);
		return result;
	}

}
