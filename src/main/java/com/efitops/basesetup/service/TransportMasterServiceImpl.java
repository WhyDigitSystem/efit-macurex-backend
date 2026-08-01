package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import com.efitops.basesetup.security.TokenProvider;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResonse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.CustomerDTO;
import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.ItemMasterDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.dto.ServiceAccMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerComplaintRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DailyExchangeRateRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;

@Service
public class TransportMasterServiceImpl implements TransportMasterService {

	private final TokenProvider tokenProvider;
	@Autowired
	CustomerComplaintRepo customerComplaintRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	ItemMasterRepo itemMasterRepo;

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	TransportMasterServiceImpl(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	// CustomerComplaintEntry

	@Override
	@Transactional
	public Map<String, Object> updateCreateCustomerComplaint(CustomerComplaintDTO customerComplaintDTO)
			throws ApplicationException {

		CustomerComplaintEntryVO customerComplaintEntryVO = new CustomerComplaintEntryVO();
		String message;

		if (ObjectUtils.isNotEmpty(customerComplaintDTO.getId())) {

			customerComplaintEntryVO = customerComplaintRepo.findById(customerComplaintDTO.getId())
					.orElseThrow(() -> new ApplicationException("Customer Complaint  Not Found"));

			customerComplaintEntryVO.setUpdated_By(customerComplaintDTO.getCreatedBy());

			createUpdateCustomerComplaintEntryVOByCustomerComplaintDTO(customerComplaintDTO, customerComplaintEntryVO);

			message = "Customer Complaint  Updated Successfully";

		} else {

			customerComplaintEntryVO.setCreatedBy(customerComplaintDTO.getCreatedBy());
			customerComplaintEntryVO.setUpdated_By(customerComplaintDTO.getCreatedBy());

			createUpdateCustomerComplaintEntryVOByCustomerComplaintDTO(customerComplaintDTO, customerComplaintEntryVO);

			message = "Customer Complaint Entry Created Successfully";
		}

		CustomerComplaintEntryVO savedCustomerComplaint = customerComplaintRepo.save(customerComplaintEntryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("customerComplaintEntryVO", customerComplaintResponse(savedCustomerComplaint));

		return response;
	}

	private CustomerComplaintResponseDTO customerComplaintResponse(CustomerComplaintEntryVO customerComplaintEntryVO) {

		CustomerComplaintResponseDTO responseDTO = new CustomerComplaintResponseDTO();

		responseDTO.setId(customerComplaintEntryVO.getId());
		responseDTO.setComplaintNo(customerComplaintEntryVO.getComplaintNo());
		responseDTO.setComplaintType(customerComplaintEntryVO.getComplaintType());
		responseDTO.setQtyNo(customerComplaintEntryVO.getQtyNo());
		responseDTO.setImage(customerComplaintEntryVO.getImage());
		responseDTO.setRemarks(customerComplaintEntryVO.getRemarks());
		responseDTO.setActive(customerComplaintEntryVO.isActive());

		responseDTO.setBuyerName(customerComplaintEntryVO.getBuyerName());

		responseDTO.setDetailsOfComplaint(customerComplaintEntryVO.getDetailsOfComplaint());

		responseDTO.setPreparedBy(customerComplaintEntryVO.getPreparedBy());

		responseDTO.setUserCategory(customerComplaintEntryVO.getUserCategory());

		responseDTO.setFinancialYear(customerComplaintEntryVO.getFinancialYear());

		responseDTO.setPrefix(customerComplaintEntryVO.getPrefix());

		responseDTO.setComplaintDate(customerComplaintEntryVO.getComplaintDate());

		responseDTO.setCustomerRefNo(customerComplaintEntryVO.getCustomerRefNo());

		if (customerComplaintEntryVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(customerComplaintEntryVO.getBranch().getId());
			branchDTO.setBranchName(customerComplaintEntryVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (customerComplaintEntryVO.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();
			departmentDTO.setId(customerComplaintEntryVO.getDepartment().getId());
			departmentDTO.setDepartmentName(customerComplaintEntryVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(departmentDTO);
		}
		if (customerComplaintEntryVO.getCustomerName() != null) {

		    CustomerResonse1DTO customerDTO = new CustomerResonse1DTO();

		    customerDTO.setId(customerComplaintEntryVO.getCustomerName().getId());
		    customerDTO.setCustomerName(customerComplaintEntryVO.getCustomerName().getCustomerName());

		    responseDTO.setCustomerName(customerDTO);
		}
		if (customerComplaintEntryVO.getCustomerId() != null) {

		    CustomerResonse1DTO customerDTO = new CustomerResonse1DTO();

		    customerDTO.setId(customerComplaintEntryVO.getCustomerId().getId());
		    customerDTO.setCustomerName(customerComplaintEntryVO.getCustomerId().getCustomerName());

		    responseDTO.setCustomerId(customerDTO);
		}
		if (customerComplaintEntryVO.getItem() != null) {

		    ItemResponse1DTO itemDTO = new ItemResponse1DTO();

		    itemDTO.setId(customerComplaintEntryVO.getItem().getId());
		    itemDTO.setItemCode(customerComplaintEntryVO.getItem().getItemCode());

		    responseDTO.setItem(itemDTO);
		}

		responseDTO.setOrgId(customerComplaintEntryVO.getOrgId());

		responseDTO.setCreatedBy(customerComplaintEntryVO.getCreatedBy());
		responseDTO.setUpdatedby(customerComplaintEntryVO.getUpdated_By());

		responseDTO.setCancelRemarks(customerComplaintEntryVO.getCancelRemarks());

		return responseDTO;
	}

	private void createUpdateCustomerComplaintEntryVOByCustomerComplaintDTO(
			@Valid CustomerComplaintDTO customerComplaintDTO, CustomerComplaintEntryVO customerComplaintEntryVO)
			throws ApplicationException {

		customerComplaintEntryVO.setQtyNo(customerComplaintDTO.getQtyNo());
		customerComplaintEntryVO.setRemarks(customerComplaintDTO.getRemarks());
		customerComplaintEntryVO.setOrgId(customerComplaintDTO.getOrgId());

		customerComplaintEntryVO.setBuyerName(customerComplaintDTO.getBuyerName());
		customerComplaintEntryVO.setDetailsOfComplaint(customerComplaintDTO.getDetailsOfComplaint());
		customerComplaintEntryVO.setPreparedBy(customerComplaintDTO.getPreparedBy());
		customerComplaintEntryVO.setUserCategory(customerComplaintDTO.getUserCategory());
		customerComplaintEntryVO.setFinancialYear(customerComplaintDTO.getFinancialYear());
		customerComplaintEntryVO.setPrefix(customerComplaintDTO.getPrefix());
		customerComplaintEntryVO.setComplaintNo(customerComplaintDTO.getComplaintNo());
		customerComplaintEntryVO.setComplaintDate(customerComplaintDTO.getComplaintDate());
		customerComplaintEntryVO.setComplaintType(customerComplaintDTO.getComplaintType());
		customerComplaintEntryVO.setCustomerRefNo(customerComplaintDTO.getCustomerRefNo());
		customerComplaintEntryVO.setActive(customerComplaintDTO.isActive());
		customerComplaintEntryVO.setCancelRemarks(customerComplaintDTO.getCancelRemarks());

		if (customerComplaintDTO.getBranch() != null && customerComplaintDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(customerComplaintDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			customerComplaintEntryVO.setBranch(branch);
		}
		System.out.println("customerName = " + customerComplaintDTO.getCustomerName());
		if (customerComplaintDTO.getCustomerName() != null && customerComplaintDTO.getCustomerName() != 0) {

			System.out.println("Customer Name block executed");

			CustomerVO customerVO = customerRepo.findById(customerComplaintDTO.getCustomerName())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			customerComplaintEntryVO.setCustomerName(customerVO);
		}

		if (customerComplaintDTO.getCustomerId() != null && customerComplaintDTO.getCustomerId() != 0) {

			System.out.println("Customer ID block executed");

			CustomerVO customerVO = customerRepo.findById(customerComplaintDTO.getCustomerId())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			customerComplaintEntryVO.setCustomerId(customerVO);
		}
		if (customerComplaintDTO.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(customerComplaintDTO.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			customerComplaintEntryVO.setDepartment(department);
		}
		if (customerComplaintDTO.getItem() != null) {

			ItemMasterVO item = itemMasterRepo.findById(customerComplaintDTO.getItem())
					.orElseThrow(() -> new ApplicationException("Item Not Found"));

			customerComplaintEntryVO.setItem(item);
		}
		// ================= IMAGE UPLOAD =================

		if (customerComplaintDTO.getImages() != null && customerComplaintDTO.getImages().length > 0) {

			for (MultipartFile file : customerComplaintDTO.getImages()) {

			}

			List<String> imageNames = new ArrayList<>();

			String uploadPath = "C:/Uploads/Complaint/";

			File directory = new File(uploadPath);

			if (!directory.exists()) {
				directory.mkdirs();
			}

			for (MultipartFile file : customerComplaintDTO.getImages()) {

				if (file.isEmpty()) {
					continue;
				}

				try {

					String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

					Path path = Paths.get(uploadPath + fileName);

					Files.write(path, file.getBytes());

					imageNames.add(fileName);

				} catch (IOException e) {

					e.printStackTrace(); // <-- Add this

					throw new ApplicationException("Unable To Upload Image : " + e.getMessage());

				}
			}

			customerComplaintEntryVO.setImage(String.join(",", imageNames));
		}
	}

	@Override
	public CustomerComplaintResponseDTO getCustomerComplaintById(Long id) throws ApplicationException {

		CustomerComplaintEntryVO customerComplaintEntryVO = customerComplaintRepo.getCustomerComplaintById(id);

		if (customerComplaintEntryVO == null) {
			throw new ApplicationException("Customer Complaint  Not Found");
		}

		return customerComplaintResponse(customerComplaintEntryVO);
	}

//	@Override
//	public List<CustomerComplaintResponseDTO> getCustomerComplaintByOrgId(Long orgId, Long branch)
//			throws ApplicationException {
//
//		List<CustomerComplaintEntryVO> employeeList = customerComplaintRepo.getCustomerComplaintByOrgId(orgId, branch);
//
//		if (employeeList == null || employeeList.isEmpty()) {
//			throw new ApplicationException("Customer Complaint Not Found");
//		}
//
//		List<CustomerComplaintResponseDTO> responseList = new ArrayList<>();
//
//		
//		return customerComplaintResponse(responseList);
//	}

	@Override
	public List<CustomerComplaintResponseDTO> getCustomerComplaintByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<CustomerComplaintEntryVO> employeeList = customerComplaintRepo.getCustomerComplaintByOrgId(orgId, branch);

		if (employeeList == null || employeeList.isEmpty()) {
			throw new ApplicationException("Customer Not Found");
		}

		List<CustomerComplaintResponseDTO> responseList = new ArrayList<>();

		for (CustomerComplaintEntryVO employeeMasterVO : employeeList) {
			responseList.add(customerComplaintResponse(employeeMasterVO));
		}

		return responseList;

	}

	// dropdown api
	@Override

	public Map<String, Object> getPreparedBy(Long departmentId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> employeeList = employeeMasterRepo.getPreparedBy(departmentId);

		List<Map<String, Object>> preparedByList = new ArrayList<>();

		for (Object[] obj : employeeList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("employeeId", obj[1]);
			map.put("employeeName", obj[2]);

			preparedByList.add(map);
		}

		responseMap.put("preparedByList", preparedByList);

		return responseMap;
	}

	// dropdown for item
	@Override
	public Map<String, Object> getItem() throws ApplicationException {

		List<Object[]> list = itemMasterRepo.getItem();

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("itemCode", obj[1]);

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("itemList", responseList);

		return response;
	}

	@Override
	public Map<String, Object> getItemDetails(Long itemId) throws ApplicationException {

		List<Object[]> list = itemMasterRepo.getItemDetails(itemId);

		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Item Not Found");
		}

		Object[] obj = list.get(0);

		Map<String, Object> itemMap = new HashMap<>();

		itemMap.put("id", obj[0]);
		itemMap.put("itemCode", obj[1]);
		itemMap.put("itemDescription", obj[2]);
		itemMap.put("customerPartNo", obj[3]);

		Map<String, Object> response = new HashMap<>();
		response.put("itemDetails", itemMap);

		return response;
	}

	// dropdown for branch

	@Override
	public Map<String, Object> getBranch() throws ApplicationException {

		List<Object[]> list = branchRepo.getBranch();

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("branchCode", obj[1]);
			map.put("branchName", obj[2]);

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("branchList", responseList);

		return response;
	}

	// belongs to dropdown
	@Override
	public Map<String, Object> getTypeDropdown() throws ApplicationException {

		List<Object[]> list = customerComplaintRepo.getTypeDropdown();

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();
			map.put("type", obj[0]);

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("typeList", responseList);

		return response;
	}

	// department dropdown

	@Override
	public Map<String, Object> getDepartment() throws ApplicationException {

		List<Object[]> list = departmentRepo.getDepartment();

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0]);
			map.put("departmentName", obj[1]);

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("departmentList", responseList);

		return response;
	}

	// dropdown for cuatomer
	@Override
	public Map<String, Object> getCustomer() throws ApplicationException {

		List<Object[]> list = customerRepo.getCustomer();

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("customerId", obj[0]);
			map.put("customerName", obj[1]);

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("customerList", responseList);

		return response;
	}

	@Override
	public Map<String, Object> getCustomerDetails(String customerId) throws ApplicationException {

		List<Object[]> list = customerRepo.getCustomerDetails(customerId);

		if (list.isEmpty()) {
			throw new ApplicationException("Customer Not Found");
		}

		Object[] obj = list.get(0);

		Map<String, Object> customerMap = new HashMap<>();

		customerMap.put("customerId", obj[0]);
		customerMap.put("customerName", obj[1]);

		Map<String, Object> response = new HashMap<>();
		response.put("customerDetails", customerMap);

		return response;
	}

}