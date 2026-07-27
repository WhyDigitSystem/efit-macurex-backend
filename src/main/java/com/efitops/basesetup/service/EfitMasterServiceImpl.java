package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.BomDTO;
import com.efitops.basesetup.dto.BomDetailsDTO;
import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.EmployeeCommunicationDetailsDTO;
import com.efitops.basesetup.dto.EmployeeComplianceDetailsDTO;
import com.efitops.basesetup.dto.EmployeeDetailsDTO;
import com.efitops.basesetup.dto.EmployeeFinanceInformationDTO;
import com.efitops.basesetup.dto.EmployeeLoanDetailsDTO;
import com.efitops.basesetup.dto.EmployeeMasterDTO;
import com.efitops.basesetup.dto.EmployeePersonalDetailsDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.MaterialTypeDetailsDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomDetailsVO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.EmployeeCommunicationDetailsVO;
import com.efitops.basesetup.entity.EmployeeComplianceDetailsVO;
import com.efitops.basesetup.entity.EmployeeDetailsVO;
import com.efitops.basesetup.entity.EmployeeFinanceInformationVO;
import com.efitops.basesetup.entity.EmployeeLoanDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EmployeePersonalDetailsVO;
import com.efitops.basesetup.entity.MaterialTypeDetailsVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BomDetailsRepo;
import com.efitops.basesetup.repository.BomRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DesignationRepo;
import com.efitops.basesetup.repository.EmployeeCommunicationDetailsRepo;
import com.efitops.basesetup.repository.EmployeeComplianceDetailsRepo;
import com.efitops.basesetup.repository.EmployeeDetailsRepo;
import com.efitops.basesetup.repository.EmployeeFinanceInformationRepo;
import com.efitops.basesetup.repository.EmployeeLoanDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.EmployeePersonalDetailsRepo;
import com.efitops.basesetup.repository.MaterialTypeDetailRepo;
import com.efitops.basesetup.repository.MaterialTypeRepo;
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
	EmployeeDetailsRepo employeeDetailsRepo;

	@Autowired
	EmployeePersonalDetailsRepo employeePersonalRepo;

	@Autowired
	EmployeeCommunicationDetailsRepo employeeCommunicationRepo;

	@Autowired
	EmployeeComplianceDetailsRepo employeeComplianceRepo;

	@Autowired
	EmployeeFinanceInformationRepo employeeFinanceRepo;

	@Autowired
	EmployeeLoanDetailsRepo employeeLoanRepo;

	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	BranchRepo branchRepo;

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

	private void createUpdateDepartmentVOByDepartmentDTO(DepartmentDTO departmentDTO, DepartmentVO departmentVO) throws ApplicationException {
		departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
		departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
		departmentVO.setOrgId(departmentDTO.getOrgId());
		departmentVO.setFinYear(departmentDTO.getFinYear());
		departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
		departmentVO.setActive(departmentDTO.isActive());
		departmentVO.setCancelRemarks(departmentDTO.getCancelRemarks());	
		
		if (departmentDTO.getBranch() != null && departmentDTO.getBranch() != 0) {

            BranchVO branch = branchRepo.findById(departmentDTO.getBranch())
                    .orElseThrow(() ->
                            new ApplicationException("branch Not Found"));

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
                    .orElseThrow(() ->
                            new ApplicationException("branch Not Found"));

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
	@Transactional
	public Map<String, Object> updateCreateEmployeeMaster(EmployeeMasterDTO dto) throws Exception {

		EmployeeMasterVO vo;
		String message;

		EmployeeMasterVO oldEmployee = null;
		// ---------------- UPDATE --------------------
		if (ObjectUtils.isNotEmpty(dto.getId())) {
			
			oldEmployee = employeeMasterRepo.findById(dto.getId())
		            .orElseThrow(() -> new ApplicationException("Employee master not found"));
			
//			initializeAll(oldEmployee);

			
			oldEmployee.getEmployeeDetailsVO();
			oldEmployee.getEmployeePersonalDetailsVO();
			oldEmployee.getEmployeeCommunicationDetailsVO();
			oldEmployee.getEmployeeComplianceDetailsVO();
			oldEmployee.getEmployeeFinanceInformationVO().size();
			oldEmployee.getEmployeeLoanDetailsVO().size();
			oldEmployee.getDocuments().size();
		    entityManager.detach(oldEmployee); // detach snapshot

//			Hibernate.initialize(oldEmployee.getEmployeeDetailsVO());
//			Hibernate.initialize(oldEmployee.getEmployeePersonalDetailsVO());
//			Hibernate.initialize(oldEmployee.getEmployeeCommunicationDetailsVO());
//			Hibernate.initialize(oldEmployee.getEmployeeComplianceDetailsVO());
//
//			Hibernate.initialize(oldEmployee.getEmployeeFinanceInformationVO());
//			Hibernate.initialize(oldEmployee.getEmployeeLoanDetailsVO());
//			Hibernate.initialize(oldEmployee.getDocuments());


			vo = employeeMasterRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("EmployeeMaster Not Found with id: " + dto.getId()));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "EmployeeMaster Updated Successfully";

			// Update master fields
			mapEmployeeDTOtoVO(dto, vo);

			// Save master first
			vo = employeeMasterRepo.save(vo);

			// ================= Delete One-to-Many Children ====================
			employeeFinanceRepo.deleteByEmployeeMasterVO(vo);
			employeeFinanceRepo.flush();

			employeeLoanRepo.deleteByEmployeeMasterVO(vo);
			employeeLoanRepo.flush();

		} else {
			// ---------------- CREATE --------------------
			vo = new EmployeeMasterVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "EmployeeMaster Created Successfully";

			mapEmployeeDTOtoVO(dto, vo);

			vo = employeeMasterRepo.save(vo);
		}

		// =====================================================================
		// ========================= ONE-TO-ONE UPDATE ==========================
		// =====================================================================

		// ------------ Employee Details (One-to-One) ------------
		if (dto.getEmployeeDetailsDTO() != null) {

			EmployeeDetailsVO details = employeeDetailsRepo.findByEmployeeMasterVO(vo);

			if (details == null) {
				details = new EmployeeDetailsVO();
				details.setEmployeeMasterVO(vo);
			}

			EmployeeDetailsDTO d = dto.getEmployeeDetailsDTO();
			details.setEmployeeType(d.getEmployeeType());
			details.setDepartment(d.getDepartment());
			details.setDateOfJoining(d.getDateOfJoining());
			if (dto.getDateOfBirth() != null && d.getDateOfJoining() != null
					&& d.getDateOfJoining().isBefore(dto.getDateOfBirth())) {

				throw new ApplicationException("Joining date cannot be earlier than Date of Birth.");
			}

			details.setDateOfLeaving(d.getDateOfLeaving());
			details.setDesignation(d.getDesignation());
			details.setJobLocation(d.getJobLocation());
			details.setMinimumWageCategory(d.getMinimumWageCategory());
			details.setPayCategory(d.getPayCategory());
			details.setPtState(d.getPtState());
			details.setCountry(d.getCountry());

			employeeDetailsRepo.save(details);
		}

		// ------------ Employee Personal Details (One-to-One) ------------
		if (dto.getEmployeePersonalDetailsDTO() != null) {

			EmployeePersonalDetailsVO personal = employeePersonalRepo.findByEmployeeMasterVO(vo);

			if (personal == null) {
				personal = new EmployeePersonalDetailsVO();
				personal.setEmployeeMasterVO(vo);
			}

			EmployeePersonalDetailsDTO p = dto.getEmployeePersonalDetailsDTO();
			personal.setBirthPlace(p.getBirthPlace());
			personal.setReligion(p.getReligion());
			personal.setPassportNo(p.getPassportNo());
			personal.setHomeState(p.getHomeState());
			personal.setNationality(p.getNationality());
			personal.setExpiryDate(p.getExpiryDate());
			personal.setCountryOfOrigin(p.getCountryOfOrigin());
			personal.setPlaceOfIssue(p.getPlaceOfIssue());

			employeePersonalRepo.save(personal);
		}

		// ------------ Employee Communication Details (One-to-One) ------------
		if (dto.getEmployeeCommunicationDetailsDTO() != null) {

			EmployeeCommunicationDetailsVO comm = employeeCommunicationRepo.findByEmployeeMasterVO(vo);

			if (comm == null) {
				comm = new EmployeeCommunicationDetailsVO();
				comm.setEmployeeMasterVO(vo);
			}

			EmployeeCommunicationDetailsDTO c = dto.getEmployeeCommunicationDetailsDTO();
			comm.setAddress(c.getAddress());
			comm.setContactNumber(c.getContactNumber());
			comm.setEmailId(c.getEmailId());
			String email = dto.getEmployeeCommunicationDetailsDTO().getEmailId();

			if (email != null && !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
				throw new ApplicationException("Please enter a valid email address");
			}
			comm.setCity(c.getCity());
			comm.setState(c.getState());
			comm.setCountry(c.getCountry());

			employeeCommunicationRepo.save(comm);
		}

		// ------------ Employee Compliance Details (One-to-One) ------------
		if (dto.getEmployeeComplianceDetailsDTO() != null) {

			EmployeeComplianceDetailsVO comp = employeeComplianceRepo.findByEmployeeMasterVO(vo);

			if (comp == null) {
				comp = new EmployeeComplianceDetailsVO();
				comp.setEmployeeMasterVO(vo);
			}

			EmployeeComplianceDetailsDTO cm = dto.getEmployeeComplianceDetailsDTO();

			comp.setEsiNo(cm.getEsiNo());
			String esiNo = cm.getEsiNo();

			if (esiNo != null && !esiNo.matches("\\d{10}")) {
				throw new ApplicationException("ESI must be 10 digits");
			}

			comp.setUanNo(cm.getUanNo());
			String uanNo = cm.getUanNo();

			if (uanNo != null && !uanNo.matches("\\d{12}")) {
				throw new ApplicationException("UAN must be 12 digits");
			}

			comp.setPt(cm.isPt());
			comp.setInsuranceNumber(cm.getInsuranceNumber());
			comp.setPfNumber(cm.getPfNumber());
			String pfNo = cm.getPfNumber();

			if (pfNo != null && !pfNo.matches("^[A-Za-z0-9]+$")) {
				throw new ApplicationException("Invalid PF Number");
			}

			comp.setEsi(cm.isEsi());

			employeeComplianceRepo.save(comp);
		}

		// =====================================================================
		// ======================== ONE-TO-MANY CREATE ==========================
		// =====================================================================

		// ------------ Finance (One-to-Many) ------------
		if (dto.getEmployeeFinanceInformationDTO() != null && !dto.getEmployeeFinanceInformationDTO().isEmpty()) {

			List<EmployeeFinanceInformationVO> finList = new ArrayList<>();

			for (EmployeeFinanceInformationDTO f : dto.getEmployeeFinanceInformationDTO()) {
				EmployeeFinanceInformationVO fin = new EmployeeFinanceInformationVO();

				fin.setModeOfPayment(f.getModeOfPayment());
				fin.setAccountNumber(f.getAccountNumber());
				fin.setIfscCode(f.getIfscCode());
				fin.setBankName(f.getBankName());
				fin.setBankBranchName(f.getBankBranchName());
				fin.setPayBill(f.getPayBill());
				fin.setDate(f.getDate());
				fin.setEmployeeMasterVO(vo);

				finList.add(fin);
			}

			employeeFinanceRepo.saveAll(finList);
		}

		// ------------ Loan (One-to-Many) ------------
		if (dto.getEmployeeLoanDetailsDTO() != null && !dto.getEmployeeLoanDetailsDTO().isEmpty()) {

			List<EmployeeLoanDetailsVO> loanList = new ArrayList<>();

			for (EmployeeLoanDetailsDTO l : dto.getEmployeeLoanDetailsDTO()) {

				EmployeeLoanDetailsVO loan = new EmployeeLoanDetailsVO();

				loan.setFinYear(l.getFinYear());
				loan.setOpeningBalance(l.getOpeningBalance());
				loan.setJanuary(l.getJanuary());
				loan.setFebruary(l.getFebruary());
				loan.setMarch(l.getMarch());
				loan.setApril(l.getApril());
				loan.setMay(l.getMay());
				loan.setJune(l.getJune());
				loan.setJuly(l.getJuly());
				loan.setAugust(l.getAugust());
				loan.setSeptember(l.getSeptember());
				loan.setOctober(l.getOctober());
				loan.setNovember(l.getNovember());
				loan.setDecember(l.getDecember());
				loan.setEmployeeMasterVO(vo);

				loanList.add(loan);
			}

			employeeLoanRepo.saveAll(loanList);
		}

		// =====================================================================
		// ============================== RESPONSE =============================
		// =====================================================================

		EmployeeMasterVO saved = employeeMasterRepo.findById(vo.getId()).get();

		
//		createUpdateEmployeeMasterNotification( vo , dto);

//		createUpdateEmployeeMasterNotification(vo, dto);

		saved.setEmployeeDetailsVO(employeeDetailsRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeePersonalDetailsVO(employeePersonalRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeeCommunicationDetailsVO(employeeCommunicationRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeeComplianceDetailsVO(employeeComplianceRepo.findByEmployeeMasterVO(saved));

		saved.setEmployeeFinanceInformationVO(employeeFinanceRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeeLoanDetailsVO(employeeLoanRepo.findByEmployeeMasterVO(saved));
		
//		commonNotificationService.generateNotification(saved.getScreenCode(), saved.getId(), oldEmployee, saved);


		Map<String, Object> response = new HashMap<>();
		response.put("employeeMasterVO", saved);
		response.put("message", message);

		return response;
	}

	private void mapEmployeeDTOtoVO(EmployeeMasterDTO dto, EmployeeMasterVO vo) throws ApplicationException {

		// Prevent duplicate Employee Code
		if (dto.getEmployeeCode() != null) {

			boolean exists = employeeMasterRepo.existsByEmployeeCodeAndOrgId(dto.getEmployeeCode(), dto.getOrgId());

			if (exists && (vo.getId() == null || !dto.getEmployeeCode().equalsIgnoreCase(vo.getEmployeeCode()))) {

				throw new ApplicationException("Employee Code already exists.");
			}
		}

		vo.setEmployeeCode(dto.getEmployeeCode());

		// MAIN TABLE
		vo.setEmployeeCode(dto.getEmployeeCode());
		vo.setFirstName(dto.getFirstName());
		vo.setLastName(dto.getLastName());
		vo.setEmployeeName(dto.getEmployeeName());
		vo.setFatherName(dto.getFatherName());
		vo.setGender(dto.getGender());
		vo.setBloodGroup(dto.getBloodGroup());
		if (!dto.getBloodGroup().matches("^(A|B|AB|O)[+-]$")) {
			throw new ApplicationException("Invalid Blood Group");
		}
		vo.setSalutation(dto.getSalutation());
		String aadhaar = dto.getAadhaarNo();

		if (aadhaar != null && aadhaar.matches("\\d{12}")) {
			vo.setAadhaarNo(aadhaar);
		} else {
			throw new ApplicationException("Aadhaar number must be exactly 12 digits");
		}
		vo.setDateOfBirth(dto.getDateOfBirth());

		if (dto.getDateOfBirth() != null && dto.getDateOfBirth().isAfter(LocalDate.now())) {
			throw new ApplicationException("Date of Birth cannot be a future date");
		}

		vo.setDateOfBirth(dto.getDateOfBirth());
		vo.setMaritalStatus(dto.getMaritalStatus());
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());
		vo.setActive(dto.isActive());

	}

//	private void createUpdateEmployeeMasterNotification(EmployeeMasterVO employeeMasterVO,
//			EmployeeMasterDTO employeeMasterDTO) {
//
//		String msg;
//		if (employeeMasterDTO.getId() != null) {
//			msg = " employeeMaster is Updated that Employee : " + employeeMasterVO.getEmployeeName();
//		} else {
//			msg = "New employeeMaster is Created that Employee  : " + employeeMasterVO.getEmployeeName();
//		}

//		NotificationDesignationDetailsVO detailsVO = notificationDesignationDetailsRepo
//				.findByScreenCode(employeeMasterVO.getScreenCode());
//
//		if (detailsVO == null) {
//			throw new RuntimeException("No record found for screenCode: " + employeeMasterVO.getScreenCode());
//		}
//
//		NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();

//		String codes = headerVO.getDesignationcode();
//		String names = headerVO.getDesignationname();
//
//		List<String> codeList = Arrays.asList(codes.split(","));
//		List<String> nameList = Arrays.asList(names.split(","));
//
//		if (codeList.size() != nameList.size()) {
//			throw new RuntimeException("Mismatch in designation data");
//		}

//		// Step 1: Get employees
//		List<EmployeeMasterVO> employees = employeeMasterRepo.findByDesignationIn(nameList);
//
//		// Step 2: Get employeeCodes
//		List<String> employeeCodes = employees.stream().map(EmployeeMasterVO::getEmployeeCode).toList();
//
//		// Step 3: Get userIds
//		List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//		if (userIds == null || userIds.isEmpty()) {
//			throw new RuntimeException("No users found for given employee codes");
//		}

//		// ✅ Step 4: Save notification for each user
//		for (Long userId : userIds) {
//
//			NotificationVO n = new NotificationVO();
//
//			n.setUserid(userId);
//			n.setMessage(msg);
//			n.setNotificationType(employeeMasterVO.getScreenName());
//
//			notificationRepo.save(n);
//		}
//	}
	
//	private void createUpdateEmployeeMasterNotification(EmployeeMasterVO employeeMasterVO ,EmployeeMasterDTO employeeMasterDTO) {
//
//		String msg;
//		if(employeeMasterDTO.getId() != null) {
//			 msg = " employeeMaster is Updated that Employee : " + employeeMasterVO.getEmployeeName();
//		}else
//		{
//			 msg = "New employeeMaster is Created that Employee  : " + employeeMasterVO.getEmployeeName();
//		}
//
//	    NotificationDesignationDetailsVO detailsVO =
//	        notificationDesignationDetailsRepo.findByScreenCode(employeeMasterVO.getScreenCode());
//
//	    if (detailsVO == null) {
//	        throw new RuntimeException("No record found for screenCode: " + employeeMasterVO.getScreenCode());
//	    }
//
//	    NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//	    String codes = headerVO.getDesignationcode();
//	    String names = headerVO.getDesignationname();
//
//	    List<String> codeList = Arrays.asList(codes.split(","));
//	    List<String> nameList = Arrays.asList(names.split(","));
//
//	    if (codeList.size() != nameList.size()) {
//	        throw new RuntimeException("Mismatch in designation data");
//	    }
//
//	    // Step 1: Get employees
//	    List<EmployeeMasterVO> employees =
//	        employeeMasterRepo.findByDesignationIn(nameList);
//
//	    // Step 2: Get employeeCodes
//	    List<String> employeeCodes = employees.stream()
//	            .map(EmployeeMasterVO::getEmployeeCode)
//	            .toList();
//
//	    // Step 3: Get userIds
//	    List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//	    if (userIds == null || userIds.isEmpty()) {
//	        throw new RuntimeException("No users found for given employee codes");
//	    }
//
//	    // ✅ Step 4: Save notification for each user
//	    for (Long userId : userIds) {
//
//	        NotificationVO n = new NotificationVO();
//
//	        n.setUserid(userId);
//	        n.setMessage(msg);
//	        n.setNotificationType(employeeMasterVO.getScreenName());
//
//	        notificationRepo.save(n);
//	    }
//	}

	@Override
	public List<EmployeeMasterVO> getAllEmployeeMasterByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return employeeMasterRepo.getAllEmployeeMasterByOrgId(orgId, branchCode);
	}

	@Override
	public List<EmployeeMasterVO> getEmployeeMasterById(Long id) {
		// TODO Auto-generated method stub
		return employeeMasterRepo.getEmployeeMasterById(id);
	}

}
