package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SubContractService {

	Map<String, Object> createUpdateSupplierRateContract(SupplierRateContractDTO supplierRateContractDTO) throws ApplicationException;

	List<Map<String, Object>> getCustomerForSupplierRateContract(Long orgId, Long branch);

	List<Map<String, Object>> getServiceForSupplierRateContract(Long orgId, Long branch);

	SupplierRateContractResponseDTO getSupplierRateContractById(Long id) throws ApplicationException;

	List<SupplierRateContractResponseDTO> getSupplierRateContractByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getSupplierRateContractDocId(Long orgId, String financialYear);

	List<Map<String, Object>> getSupplierRateContractItemDropdown(Long orgId, Long branch);

	Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO, MultipartFile[] files) throws ApplicationException;

	List<Map<String, Object>> getSupplierRateContractDropdown(Long customer, Long orgId, Long branch);

	List<Map<String, Object>> getSupplierRateContractItemDetailsForJobOrder(String docId, Long orgId, Long branch);

	String getJobOrderDocId(Long orgId, String financialYear);

	List<JobOrderResponseDTO> getJobOrderByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	JobOrderResponseDTO getJobOrderById(Long id) throws ApplicationException;

	Map<String, Object> createUpdateJobOrderAmendment(JobOrderAmendmentDTO jobOrderAmendmentDTO)
			throws ApplicationException;

	List<Map<String, Object>> getJobOrderNoAndDateForJobOrderAmd(Long branch, Long orgId, Long customer);

	Integer getNextRevisionNoForJobOrderAmd(String jobOrderNo, Long branch, Long orgId);

	List<Map<String, Object>> getJobOrderItemDetailsForJobOrderAmd(String jobOrderNo, Long branch, Long orgId,
			Long customer);

	JobOrderAmendmentResponseDTO getJobOrderAmendmentById(Long id) throws ApplicationException;

	List<JobOrderAmendmentResponseDTO> getJobOrderAmendmentByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getJobOrderAmendmentDocId(Long orgId, String financialYear);

	Map<String, Object> createUpdateDeliveryChallanSubcontracting(
			DeliveryChallanSubcontractingDTO deliveryChallanSubcontractingDTO) throws ApplicationException;

	List<Map<String, Object>> getLocationForDeliverChallanSubContract(Long orgId, Long branch);

	List<Map<String, Object>> getItemDetailsforDeliveryChallanSubContract(String jobOrderNo, Long branch, Long orgId,
			Long vendor);

	//SubContractSupplySchedule
	Map<String, Object> createUpdateSubContractSupplySchedule(
			SubContractSupplyScheduleDTO subContractSupplyScheduleDTO) throws ApplicationException;

	List<Map<String, Object>> getJobOrderNoAndDateForSubContractSupplySch(Long branch, Long orgId, String contractNo);

	SubContractSupplyScheduleResponseDTO getSubContractSupplyScheduleById(Long id) throws ApplicationException;

	List<SubContractSupplyScheduleResponseDTO> getSubContractSupplyScheduleByOrgIdAndBranch(Long orgId, Long branch) throws ApplicationException;

	String getSubContractSupplyScheduleDocId(Long orgId, String financialYear);

	//SupplierRateContractAmd
	Map<String, Object> createUpdateSupplierRateContractAmendment(
			SupplierRateContractAmendmentDTO supplierRateContractAmendmentDTO) throws ApplicationException;

	String getSupplierRateContractAmendmentDocId(Long orgId, String financialYear);

	SupplierRateContractAmendmentResponseDTO getSupplierRateContractAmendmentById(Long id) throws ApplicationException;

	List<SupplierRateContractAmendmentResponseDTO> getSupplierRateContractAmendmentByOrgIdAndBranch(
			Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getRevisionNoDetailsForSupplierRateContractAmd(String contractNo, Long orgId,
			Long branch);

	List<Map<String, Object>> getSupplierRateContractItemDetailsForSRCAmd(String contractNo, Long orgId, Long branch);






}
