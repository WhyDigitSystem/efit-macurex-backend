package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.BomDTO;
import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.EmployeeMasterDTO;
import com.efitops.basesetup.dto.EmployeeMasterResponseDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface EfitMasterService {

	// Department

	Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException;

	List<DepartmentVO> getAllDepartmentByOrgId(Long orgId);

	List<DepartmentVO> getDepartmentById(Long id);

	String getDepartmentDocId(Long orgId, String finYear, Long branch);

	// Material Type

	Map<String, Object> createUpdateMaterialType(MaterialTypeDTO materialTypeDTO) throws ApplicationException;

	List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId);

	List<MaterialTypeVO> getMaterialTypeById(Long id);

	// Designation

	List<DesignationVO> getDesignationByOrgId(Long orgId);

	List<DesignationVO> getDesignationById(Long id);

	Map<String, Object> updateCreateDesignation(DesignationDTO designationdto) throws ApplicationException;

	String getDesignationDocId(Long orgId, String finYear, Long branch);

	// UOM

	List<UomVO> getUomByOrgId(Long orgId);

	List<UomVO> getUomById(Long id);

	Map<String, Object> updateCreateUom(@Valid UomDTO uomDTO) throws ApplicationException;

	// Bom Master

	Map<String, Object> createUpdateBom(BomDTO bomDTO) throws ApplicationException;

	List<BomVO> getAllBomOrgId(Long orgId, String branchCode);

	List<BomVO> getAllBomId(Long id);

	String getBomDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getFGSFGPartDetailsForBOM(Long orgId, String productType);

	List<Map<String, Object>> getSFGItemDetailsForBOM(Long orgId);

	// EmployeeMaster

	Map<String, Object> updateCreateEmployeeMaster(@Valid EmployeeMasterDTO employeeMasterDTO) throws Exception;

	EmployeeMasterResponseDTO getEmployeeMasterById(Long id) throws ApplicationException;

	List<EmployeeMasterResponseDTO> getEmployeeMasterByOrgId(Long orgId) throws ApplicationException;

	String getEmployeeByDocId(Long orgId);

}
