package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchCustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstructionScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchSalesContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchScheduleMonthResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceDetResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.PendingQtyResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDetailResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferChallanResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferCustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DespatchInstructionDTO;
import com.efitops.basesetup.dto.DespatchInstructionDetailsDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.DocketInvoiceDetailsDTO;
//github.com/WhyDigitSystem/efit-macurex-backend.git
import com.efitops.basesetup.dto.SalesContractAmdDetailsDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.StockTransferChallanDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DespatchInstructionDetailsVO;
import com.efitops.basesetup.entity.DespatchInstructionVO;
import com.efitops.basesetup.entity.DocketInvoiceDetailsVO;
import com.efitops.basesetup.entity.DocketInvoiceVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesContractVO;
import com.efitops.basesetup.entity.StockTransferChallanVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerComplaintRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DespatchInstructionDetailsRepo;
import com.efitops.basesetup.repository.DespatchInstructionRepo;
import com.efitops.basesetup.repository.DocketInvoiceDetRepo;
import com.efitops.basesetup.repository.DocketInvoiceRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.SalesContractAmdDetailsRepo;
import com.efitops.basesetup.repository.SalesContractAmdRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.StockTransferChallanRepo;
import com.efitops.basesetup.repository.TransportRepo;
import com.efitops.basesetup.security.TokenProvider;

@Service
public class TransportMasterServiceImpl implements TransportMasterService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransportMasterServiceImpl.class);

	private final TokenProvider tokenProvider;
	@Autowired
	CustomerComplaintRepo customerComplaintRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	private SalesContractAmdRepo salesContractAmendmentRepo;

	@Autowired
	private SalesContractAmdDetailsRepo salesContractAmdDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private SalesContractRepo salesContractRepo;

	@Autowired
	private DespatchInstructionRepo despatchInstructionRepo;

	@Autowired
	private DespatchInstructionDetailsRepo despatchInstructionDetailsRepo;

	@Autowired
	private CurrencyRepo CurrencyRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private DocketInvoiceRepo docketInvoiceRepo;

	@Autowired
	private DocketInvoiceDetRepo docketInvoiceDetRepo;

	@Autowired
	private TransportRepo transportRepo;

	@Autowired
	private StockTransferChallanRepo stockTransferChallanRepo;

	@Autowired
	private ListOfValuesRepo listOfValuesRepo;

	TransportMasterServiceImpl(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	// CustomerComplaintEntry

	@Override
	@Transactional
	public Map<String, Object> updateCreateCustomerComplaint(CustomerComplaintDTO customerComplaintDTO,
			MultipartFile[] images) throws ApplicationException {
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

		// Save Images
		saveImages(images, savedCustomerComplaint);

		// Reload latest data
		savedCustomerComplaint = customerComplaintRepo.findById(savedCustomerComplaint.getId())
				.orElseThrow(() -> new ApplicationException("Customer Complaint Not Found"));

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
//		if (customerComplaintEntryVO.getCustomerName() != null) {
//
//		    CustomerResonse1DTO customerDTO = new CustomerResonse1DTO();
//
//		    customerDTO.setId(customerComplaintEntryVO.getCustomerName().getId());
//		    customerDTO.setCustomerName(customerComplaintEntryVO.getCustomerName().getCustomerName());
//
//		    responseDTO.setCustomerName(customerDTO);
//		}
		if (customerComplaintEntryVO.getCustomer() != null) {

			CustomerResponse1DTO customerDTO = new CustomerResponse1DTO();

			customerDTO.setId(customerComplaintEntryVO.getCustomer().getId());
			customerDTO.setCustomerName(customerComplaintEntryVO.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerDTO);
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
//		if (customerComplaintDTO.getCustomerName() != null && customerComplaintDTO.getCustomerName() != 0) {
//
//			System.out.println("Customer Name block executed");
//
//			CustomerVO customerVO = customerRepo.findById(customerComplaintDTO.getCustomerName())
//					.orElseThrow(() -> new ApplicationException("Customer Not Found"));
//
//			customerComplaintEntryVO.setCustomerName(customerVO);
//		}

		if (customerComplaintDTO.getCustomer() != null && customerComplaintDTO.getCustomer() != 0) {

			System.out.println("Customer ID block executed");

			CustomerVO customerVO = customerRepo.findById(customerComplaintDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			customerComplaintEntryVO.setCustomer(customerVO);
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
	}

	// ================= IMAGE UPLOAD =================
	@Value("${customer.complaint.upload.path}")
	private String uploadPath;

	private void saveImages(MultipartFile[] images, CustomerComplaintEntryVO customerComplaintEntryVO)
			throws ApplicationException {

		if (images == null || images.length == 0) {
			return;
		}

		try {

			File folder = new File(uploadPath);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			List<String> imageNames = new ArrayList<>();

			for (MultipartFile image : images) {

				if (image == null || image.isEmpty()) {
					continue;
				}

				String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

				Path path = Paths.get(uploadPath, fileName);

				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				imageNames.add(fileName);
			}

			// Save all image names as comma-separated values
			customerComplaintEntryVO.setImage(String.join(",", imageNames));

			customerComplaintRepo.save(customerComplaintEntryVO);

		} catch (IOException e) {

			throw new ApplicationException("Image Upload Failed : " + e.getMessage());
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

	public Map<String, Object> getPreparedBy(Long orgId, Long branch, Long departmentId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> employeeList = employeeMasterRepo.getPreparedBy(orgId, branch, departmentId);

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
	public Map<String, Object> getCustomerComplaintItemDetails(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> list = itemMasterRepo.getCustomerComplaintItemDetails(orgId, branch);

		List<Map<String, Object>> itemList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> itemMap = new HashMap<>();

			itemMap.put("id", obj[0]);
			itemMap.put("itemCode", obj[1]);
			itemMap.put("itemDescription", obj[2]);
			itemMap.put("customerPartNo", obj[3]);

			itemList.add(itemMap);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("itemDetails", itemList);

		return response;
	}

	// dropdown for branch

	@Override
	public Map<String, Object> getAllBranch(Long orgId) throws ApplicationException {

		List<Object[]> list = branchRepo.getAllBranch(orgId);

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

	@Override
	public Map<String, Object> getCustomerDetails(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> list = customerRepo.getCustomerDetails(orgId, branch);

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

	// Sales Contract amendment

//	@Override
//	@Transactional
//	public Map<String, Object> updateCreateSalesContractAmendment(
//			SalesContractAmendmentDTO salesContractAmendmentDTO) throws ApplicationException {
//
//		SalesContractAmendmentVO salesContractAmendmentVO = new SalesContractAmendmentVO();
//
//		String message;
//
//		if (salesContractAmendmentDTO.getId() != null) {
//
//			salesContractAmendmentVO = salesContractAmendmentRepo.findById(salesContractAmendmentDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Invalid Sales Contract Amendment Details"));
//
//			salesContractAmendmentVO.setUpdated_By(salesContractAmendmentDTO.getCreatedBy());
//
//			message = "Sales Contract Amendment Updated Successfully";
//
//		} else {
//
//			salesContractAmendmentVO.setCreatedBy(salesContractAmendmentDTO.getCreatedBy());
//			salesContractAmendmentVO.setUpdated_By(salesContractAmendmentDTO.getCreatedBy());
//
//			message = "Sales Contract Amendment Created Successfully";
//		}
//		createUpdateSalesContractAmendmentVO(
//		        salesContractAmendmentDTO,
//		        salesContractAmendmentVO);
//
//		SalesContractAmendmentVO savedSalesContractAmendment =
//		        salesContractAmendmentRepo.save(salesContractAmendmentVO);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("message", message);
//		response.put("salesContractAmendmentVO", salesContractResponseResponse(savedSalesContractAmendment));
//
//
//		return response;
//	}
//	private SalesContractAmdResponseDTO salesContractResponseResponse(
//			SalesContractAmendmentVO salesContractAmendmentVO) {
//
//		SalesContractAmdResponseDTO responseDTO = new SalesContractAmdResponseDTO();
//
//		responseDTO.setId(salesContractAmendmentVO.getId());
//		responseDTO.setContractAmdNo(salesContractAmendmentVO.getContractAmdNo());
//		responseDTO.setDate(salesContractAmendmentVO.getDate());
//		responseDTO.setContractNo(salesContractAmendmentVO.getContractNo());
//		responseDTO.setContractDate(salesContractAmendmentVO.getContractDate());
//		responseDTO.setCustPoNo(salesContractAmendmentVO.getCustPoNo());
//		responseDTO.setCustPoDate(salesContractAmendmentVO.getCustPoDate());
//		responseDTO.setRevisionNo(salesContractAmendmentVO.getRevisionNo());
//		responseDTO.setRemarks(salesContractAmendmentVO.getRemarks());
//
//		if (salesContractAmendmentVO.getBranch() != null) {
//
//			BranchResponseDTO branchDTO = new BranchResponseDTO();
//
//			branchDTO.setId(salesContractAmendmentVO.getBranch().getId());
//			branchDTO.setBranchName(salesContractAmendmentVO.getBranch().getBranchName());
//
//			responseDTO.setBranch(branchDTO);
//		}
//
//		responseDTO.setOrgId(salesContractAmendmentVO.getOrgId());
//		responseDTO.setCreatedBy(salesContractAmendmentVO.getCreatedBy());
//		responseDTO.setCancelRemarks(salesContractAmendmentVO.getCancelRemarks());
//
//		List<SalesContractDetailResponseDTO> detailResponseList = new ArrayList<>();
//
//		if (salesContractAmendmentVO.getSalesContractAmdDetailsVO() != null
//				&& !salesContractAmendmentVO.getSalesContractAmdDetailsVO().isEmpty()) {
//
//			for (SalesContractAmdDetailsVO detailVO : salesContractAmendmentVO.getSalesContractAmdDetailsVO()) {
//
//				SalesContractDetailResponseDTO detailDTO = new SalesContractDetailResponseDTO();
//
//				detailDTO.setId(detailVO.getId());
//
//				if (detailVO.getItem() != null) {
//
//					ItemResponse1DTO itemDTO = new ItemResponse1DTO();
//
//					itemDTO.setId(detailVO.getItem().getId());
//					itemDTO.setItemCode(detailVO.getItem().getItemCode());
//
//					detailDTO.setItem(itemDTO);
//				}
//
//				detailDTO.setOldRate(detailVO.getOldRate());
//				detailDTO.setNewRate(detailVO.getNewRate());
//				detailDTO.setValidFrom(detailVO.getValidFrom());
//				detailDTO.setValidTo(detailVO.getValidTo());
//				detailDTO.setNewValidDate(detailVO.getNewValidDate());
//
//				detailResponseList.add(detailDTO);
//			}
//		}
//
//		responseDTO.setSalesContractDetailResponseDTO(detailResponseList);
//
//		return responseDTO;
//	}
//	private void createUpdateSalesContractAmendmentVO(
//			SalesContractAmendmentDTO dto,
//			SalesContractAmendmentVO salesContractAmendmentVO)
//			throws ApplicationException {
//
//		salesContractAmendmentVO.setContractAmdNo(dto.getContractAmdNo());
//		salesContractAmendmentVO.setDate(dto.getDate());
//		salesContractAmendmentVO.setContractNo(dto.getContractNo());
//		salesContractAmendmentVO.setContractDate(dto.getContractDate());
//		salesContractAmendmentVO.setCustPoNo(dto.getCustPoNo());
//		salesContractAmendmentVO.setCustPoDate(dto.getCustPoDate());
//		salesContractAmendmentVO.setRevisionNo(dto.getRevisionNo());
//		salesContractAmendmentVO.setRemarks(dto.getRemarks());
//
//		salesContractAmendmentVO.setOrgId(dto.getOrgId());
//		salesContractAmendmentVO.setActive(dto.getActive());
//		salesContractAmendmentVO.setCancelRemarks(dto.getCancelRemarks());
//
//		if (dto.getBranch() != null && dto.getBranch() != 0) {
//
//			BranchVO branch = branchRepo.findById(dto.getBranch())
//					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
//
//			salesContractAmendmentVO.setBranch(branch);
//		}
//
//		//----------------------------------------------------
//		// Delete old child records while updating
//		//----------------------------------------------------
//
//		if (dto.getId() != null) {
//
//			List<SalesContractAmdDetailsVO> oldList = salesContractAmdDetailsRepo
//					.findBySalesContractAmendmentVO(salesContractAmendmentVO);
//
//			salesContractAmdDetailsRepo.deleteAll(oldList);
//		}
//
//		List<SalesContractAmdDetailsVO> detailList = new ArrayList<>();
//
//		if (dto.getSalesContractAmdDetailsDTO() != null
//				&& !dto.getSalesContractAmdDetailsDTO().isEmpty()) {
//
//			for (SalesContractAmdDetailsDTO detailDTO : dto.getSalesContractAmdDetailsDTO()) {
//
//				SalesContractAmdDetailsVO detailVO = new SalesContractAmdDetailsVO();
//
//				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {
//
//					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
//							.orElseThrow(() -> new ApplicationException("Item Not Found"));
//
//					detailVO.setItem(item);
//				}
//
//				detailVO.setOldRate(detailDTO.getOldRate());
//				detailVO.setNewRate(detailDTO.getNewRate());
//				detailVO.setValidFrom(detailDTO.getValidFrom());
//				detailVO.setValidTo(detailDTO.getValidTo());
//				detailVO.setNewValidDate(detailDTO.getNewValidDate());
//
//				// Parent Mapping
//				detailVO.setSalesContractAmendmentVO(salesContractAmendmentVO);
//
//				detailList.add(detailVO);
//			}
//
//			// Set child list to parent
//			salesContractAmendmentVO.setSalesContractAmdDetailsVO(detailList);
//		}
//	}
//	
//	@Override
//	public SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id)
//			throws ApplicationException {
//
//		if (ObjectUtils.isEmpty(id)) {
//			throw new ApplicationException("Invalid Id");
//		}
//
//		SalesContractAmendmentVO salesContractAmendmentVO = salesContractAmendmentRepo.findById(id)
//				.orElseThrow(() -> new ApplicationException("Sales Contract Amendment Not Found"));
//
//		return salesContractResponseResponse(salesContractAmendmentVO);
//	}
//	
//	@Override
//	public List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId,Long branch)
//	        throws ApplicationException {
//
//	    List<SalesContractAmendmentVO> salesContractAmendmentList =
//	            salesContractAmendmentRepo.getSalesContractAmendmentByOrgId(orgId,branch);
//
//	    if (salesContractAmendmentList.isEmpty()) {
//	        throw new ApplicationException("No Sales Contract Amendment Details Found");
//	    }
//
//	    List<SalesContractAmdResponseDTO> responseList = new ArrayList<>();
//
//	    for (SalesContractAmendmentVO salesContractAmendmentVO : salesContractAmendmentList) {
//
//	        responseList.add(
//	                salesContractResponseResponse(salesContractAmendmentVO));
//	    }
//
//	    return responseList;
//	}

//	//dropdown for sales Contract Amendment
//	
//	@Override
//	public Map<String, Object> getContractNo() throws ApplicationException {
//
//	    String methodName = "getContractNo()";
//	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//	    List<Map<String, Object>> responseList = new ArrayList<>();
//
//	    try {
//
//	        List<SalesContractVO> contractList = salesContractRepo.getContractNo();
//
//	        if (contractList != null && !contractList.isEmpty()) {
//
//	            for (SalesContractVO vo : contractList) {
//
//	                Map<String, Object> map = new HashMap<>();
//
//	                map.put("id", vo.getId());
//	                map.put("contractNo", vo.getCustomerContractNo());
//	                map.put("cust_po_no", vo.getCustomerPoNo());
//	                map.put("cust_po_date", vo.getCustomerPoDate());
//
//	                responseList.add(map);
//	            }
//	        }
//
//	    } catch (Exception e) {
//
//	        LOGGER.error("Error in getContractNoDropdown()", e);
//	        throw new ApplicationException(e.getMessage());
//	    }
//
//	    Map<String, Object> responseMap = new HashMap<>();
//	    responseMap.put("message", "Contract No Dropdown Loaded Successfully");
//	    responseMap.put("contractList", responseList);
//
//	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//	    return responseMap;
//	}

	// Despatch Instruction
	@Override
	@Transactional
	public Map<String, Object> updateCreateDespatchInstruction(DespatchInstructionDTO despatchInstructionDTO)
			throws ApplicationException {

		DespatchInstructionVO despatchInstructionVO = new DespatchInstructionVO();

		String message;

		if (ObjectUtils.isNotEmpty(despatchInstructionDTO.getId())) {

			despatchInstructionVO = despatchInstructionRepo.findById(despatchInstructionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Despatch Instruction Details"));

			despatchInstructionVO.setUpdated_By(despatchInstructionDTO.getCreatedBy());

			message = "Despatch Instruction Updated Successfully";

		} else {

			despatchInstructionVO.setCreatedBy(despatchInstructionDTO.getCreatedBy());

			despatchInstructionVO.setUpdated_By(despatchInstructionDTO.getCreatedBy());

			message = "Despatch Instruction Created Successfully";
		}

		createUpdateDespatchInstructionVO(despatchInstructionDTO, despatchInstructionVO);

		DespatchInstructionVO savedDespatchInstruction = despatchInstructionRepo.save(despatchInstructionVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("despatchInstructionVO", despatchInstructionResponse(savedDespatchInstruction));

		return response;
	}

	private DespatchInstructionResponseDTO despatchInstructionResponse(DespatchInstructionVO despatchInstructionVO) {

		DespatchInstructionResponseDTO responseDTO = new DespatchInstructionResponseDTO();

		responseDTO.setId(despatchInstructionVO.getId());
		responseDTO.setDocId(despatchInstructionVO.getDocId());
		responseDTO.setDocDate(despatchInstructionVO.getDocDate());
		responseDTO.setSchduleNo(despatchInstructionVO.getSchduleNo());
		responseDTO.setInvoiceType(despatchInstructionVO.getInvoiceType());
		responseDTO.setSchduleDate(despatchInstructionVO.getSchduleDate());
		responseDTO.setPaymentTerms(despatchInstructionVO.getPaymentTerms());
		responseDTO.setModeOfTransport(despatchInstructionVO.getModeOfTransport());
		responseDTO.setNetWeight(despatchInstructionVO.getNetWeight());
		responseDTO.setGrossWeight(despatchInstructionVO.getGrossWeight());
		responseDTO.setDeliveryInstructions(despatchInstructionVO.getDeliveryInstructions());
		responseDTO.setConsignee(despatchInstructionVO.getConsignee());

		responseDTO.setOrgId(despatchInstructionVO.getOrgId());
		responseDTO.setCreatedBy(despatchInstructionVO.getCreatedBy());
		responseDTO.setCancelRemarks(despatchInstructionVO.getCancelRemarks());

		if (despatchInstructionVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(despatchInstructionVO.getBranch().getId());
			branchDTO.setBranchName(despatchInstructionVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (despatchInstructionVO.getCustomer() != null) {

			CustomerResponse1DTO customerDTO = new CustomerResponse1DTO();

			customerDTO.setId(despatchInstructionVO.getCustomer().getId());
			customerDTO.setCustomerName(despatchInstructionVO.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerDTO);
		}

		if (despatchInstructionVO.getLocation() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(despatchInstructionVO.getLocation().getId());
			locationDTO.setLocationName(despatchInstructionVO.getLocation().getLocationName());

			responseDTO.setLocation(locationDTO);
		}

		List<DespatchInstDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (despatchInstructionVO.getDetails() != null && !despatchInstructionVO.getDetails().isEmpty()) {

			for (DespatchInstructionDetailsVO detailVO : despatchInstructionVO.getDetails()) {

				DespatchInstDetailsResponseDTO detailDTO = new DespatchInstDetailsResponseDTO();

				detailDTO.setOrdAccpContrNo(detailVO.getOrdAccpContrNo());
				detailDTO.setDate(detailVO.getDate());

				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					detailDTO.setItem(itemDTO);
				}

				detailDTO.setPdi(detailVO.getPdi());
				detailDTO.setPdiDate(detailVO.getPdiDate());
				detailDTO.setSchduleMonth(detailVO.getSchduleMonth());
				detailDTO.setPlannedQty(detailVO.getPlannedQty());
				detailDTO.setPendingQty(detailVO.getPendingQty());
				detailDTO.setAvailableQty(detailVO.getAvailableQty());
				detailDTO.setDescQty(detailVO.getDescQty());
				detailDTO.setNoOfPackage(detailVO.getNoOfPackage());
				detailDTO.setPackageType(detailVO.getPackageType());

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setDespatchInstDetailsResponseDTO(detailResponseList);

		return responseDTO;
	}

	private void createUpdateDespatchInstructionVO(DespatchInstructionDTO dto,
			DespatchInstructionVO despatchInstructionVO) throws ApplicationException {

		despatchInstructionVO.setDocId(dto.getDocId());
		despatchInstructionVO.setDocDate(dto.getDocDate());
		despatchInstructionVO.setSchduleNo(dto.getSchduleNo());
		despatchInstructionVO.setInvoiceType(dto.getInvoiceType());
		despatchInstructionVO.setSchduleDate(dto.getSchduleDate());
		despatchInstructionVO.setPaymentTerms(dto.getPaymentTerms());
		despatchInstructionVO.setModeOfTransport(dto.getModeOfTransport());
		despatchInstructionVO.setNetWeight(dto.getNetWeight());
		despatchInstructionVO.setGrossWeight(dto.getGrossWeight());
		despatchInstructionVO.setDeliveryInstructions(dto.getDeliveryInstructions());
		despatchInstructionVO.setConsignee(dto.getConsignee());

		despatchInstructionVO.setOrgId(dto.getOrgId());
		despatchInstructionVO.setActive(dto.getActive());
		despatchInstructionVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Branch Mapping
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			despatchInstructionVO.setBranch(branch);
		}

		// =========================
		// Customer Mapping
		// =========================

		if (dto.getCustomer() != null) {

			CustomerVO customerVO = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer not found"));

			despatchInstructionVO.setCustomer(customerVO);
		}
		// =========================
		// Location Mapping
		// =========================

		if (dto.getLocation() != null && dto.getLocation() != 0) {

			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			despatchInstructionVO.setLocation(location);
		}

		// ======================================
		// Delete Existing Child During Update
		// ======================================

		if (dto.getId() != null) {

			List<DespatchInstructionDetailsVO> oldList = despatchInstructionDetailsRepo
					.findByDespatchInstructionVO(despatchInstructionVO);

			despatchInstructionDetailsRepo.deleteAll(oldList);
		}

		// ======================================
		// Child Save
		// ======================================

		List<DespatchInstructionDetailsVO> detailList = new ArrayList<>();

		if (dto.getDespatchInstructionDetailsDTO() != null && !dto.getDespatchInstructionDetailsDTO().isEmpty()) {

			for (DespatchInstructionDetailsDTO detailDTO : dto.getDespatchInstructionDetailsDTO()) {

				DespatchInstructionDetailsVO detailVO = new DespatchInstructionDetailsVO();

				detailVO.setOrdAccpContrNo(detailDTO.getOrdAccpContrNo());
				detailVO.setDate(detailDTO.getDate());

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				detailVO.setPdi(detailDTO.getPdi());
				detailVO.setPdiDate(detailDTO.getPdiDate());
				detailVO.setSchduleMonth(detailDTO.getSchduleMonth());
				detailVO.setPlannedQty(detailDTO.getPlannedQty());
				detailVO.setPendingQty(detailDTO.getPendingQty());
				detailVO.setAvailableQty(detailDTO.getAvailableQty());
				detailVO.setDescQty(detailDTO.getDescQty());
				detailVO.setNoOfPackage(detailDTO.getNoOfPackage());
				detailVO.setPackageType(detailDTO.getPackageType());

				// Parent Mapping
				detailVO.setDespatchInstructionVO(despatchInstructionVO);

				detailList.add(detailVO);
			}

			despatchInstructionVO.setDetails(detailList);
		}
	}

	@Override
	public DespatchInstructionResponseDTO getDespatchInstructionById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		DespatchInstructionVO despatchInstructionVO = despatchInstructionRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Despatch Instruction Not Found"));

		return despatchInstructionResponse(despatchInstructionVO);
	}

	@Override
	public List<DespatchInstructionResponseDTO> getDespatchInstructionByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<DespatchInstructionVO> despatchInstructionList = despatchInstructionRepo
				.getDespatchInstructionByOrgId(orgId, branch);

		if (despatchInstructionList.isEmpty()) {
			throw new ApplicationException("No Despatch Instruction Details Found");
		}

		List<DespatchInstructionResponseDTO> responseList = new ArrayList<>();

		for (DespatchInstructionVO despatchInstructionVO : despatchInstructionList) {

			responseList.add(despatchInstructionResponse(despatchInstructionVO));
		}

		return responseList;
	}


	// Stock Transfer Challan
	@Override
	@Transactional
	public Map<String, Object> updateCreateStockTransferChallan(StockTransferChallanDTO stockTransferChallanDTO)
			throws ApplicationException {

		StockTransferChallanVO stockTransferChallanVO = new StockTransferChallanVO();

		String message;

		if (ObjectUtils.isNotEmpty(stockTransferChallanDTO.getId())) {

			stockTransferChallanVO = stockTransferChallanRepo.findById(stockTransferChallanDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Stock Transfer Challan Details"));

			stockTransferChallanVO.setUpdated_By(stockTransferChallanDTO.getCreatedBy());

			message = "Stock Transfer Challan Updated Successfully";

		} else {

			stockTransferChallanVO.setCreatedBy(stockTransferChallanDTO.getCreatedBy());

			stockTransferChallanVO.setUpdated_By(stockTransferChallanDTO.getCreatedBy());

			message = "Stock Transfer Challan Created Successfully";
		}

		createUpdateStockTransferChallanVO(stockTransferChallanDTO, stockTransferChallanVO);

		StockTransferChallanVO savedStockTransferChallan = stockTransferChallanRepo.save(stockTransferChallanVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("stockTransferChallanVO", stockTransferChallanResponse(savedStockTransferChallan));

		return response;
	}

	private StockTransferChallanResponseDTO stockTransferChallanResponse(
			StockTransferChallanVO stockTransferChallanVO) {

		StockTransferChallanResponseDTO responseDTO = new StockTransferChallanResponseDTO();

		// =========================
		// Basic Details
		// =========================

		responseDTO.setId(stockTransferChallanVO.getId());
		responseDTO.setDocID(stockTransferChallanVO.getDocID());
		responseDTO.setDocDate(stockTransferChallanVO.getDocDate());
		responseDTO.setStockPosting(stockTransferChallanVO.getStockPosting());
		responseDTO.setDate(stockTransferChallanVO.getDate());
		responseDTO.setNoOfPackages(stockTransferChallanVO.getNoOfPackages());
		responseDTO.setOtherPackages(stockTransferChallanVO.getOtherPackages());
		responseDTO.setImportLocal(stockTransferChallanVO.getImportLocal());
		responseDTO.setActive(stockTransferChallanVO.getActive());
		responseDTO.setOrgId(stockTransferChallanVO.getOrgId());
		responseDTO.setCreatedBy(stockTransferChallanVO.getCreatedBy());
		responseDTO.setCancelRemarks(stockTransferChallanVO.getCancelRemarks());
		responseDTO.setTimeOfTranfer(stockTransferChallanVO.getTimeOfTranfer());
		responseDTO.setTotalInsurance(stockTransferChallanVO.getTotalInsurance());
		responseDTO.setTotalFreight(stockTransferChallanVO.getTotalFreight());
		responseDTO.setTotalAssVal(stockTransferChallanVO.getTotalAssVal());
		responseDTO.setModeOfTransport(stockTransferChallanVO.getModeOfTransport());
		responseDTO.setSalesTax(stockTransferChallanVO.getSalesTax());
		responseDTO.setGrossAmount(stockTransferChallanVO.getGrossAmount());
		responseDTO.setAmountInWords(stockTransferChallanVO.getAmountInWords());
		responseDTO.setDeliverTo(stockTransferChallanVO.getDeliverTo());
		responseDTO.setPaymentTerms(stockTransferChallanVO.getPaymentTerms());
		responseDTO.setNarration(stockTransferChallanVO.getNarration());

		// =========================
		// Branch Response
		// =========================

		if (stockTransferChallanVO.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(stockTransferChallanVO.getBranch().getId());
			branchResponseDTO.setBranchName(stockTransferChallanVO.getBranch().getBranchName());

			responseDTO.setBranch(branchResponseDTO);
		}

		// =========================
		// List Of Values Response
		// =========================

		if (stockTransferChallanVO.getTypes() != null) {

			ListOfValuesResponseDTO listOfValuesResponseDTO = new ListOfValuesResponseDTO();

			listOfValuesResponseDTO.setId(stockTransferChallanVO.getTypes().getId());

			listOfValuesResponseDTO.setListCode(stockTransferChallanVO.getTypes().getListCode());

			listOfValuesResponseDTO.setListDescription(stockTransferChallanVO.getTypes().getListDescription());

			responseDTO.setTypes(listOfValuesResponseDTO);
		}

		// =========================
		// Customer Response
		// =========================

		if (stockTransferChallanVO.getCustomer() != null) {

			CustomerResponse1DTO customerResponseDTO = new CustomerResponse1DTO();

			customerResponseDTO.setId(stockTransferChallanVO.getCustomer().getId());

			customerResponseDTO.setCustomerName(stockTransferChallanVO.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerResponseDTO);
		}

		// =========================
		// Location Response
		// =========================

		if (stockTransferChallanVO.getLocation() != null) {

			LocationMasterResponseDTO locationResponseDTO = new LocationMasterResponseDTO();

			locationResponseDTO.setId(stockTransferChallanVO.getLocation().getId());

			locationResponseDTO.setLocationName(stockTransferChallanVO.getLocation().getLocationName());

			responseDTO.setLocation(locationResponseDTO);
		}

		return responseDTO;
	}

	private void createUpdateStockTransferChallanVO(StockTransferChallanDTO dto,
			StockTransferChallanVO stockTransferChallanVO) throws ApplicationException {

		stockTransferChallanVO.setDocID(dto.getDocID());
		stockTransferChallanVO.setDocDate(dto.getDocDate());
		stockTransferChallanVO.setStockPosting(dto.getStockPosting());
		stockTransferChallanVO.setDate(dto.getDate());
		stockTransferChallanVO.setNoOfPackages(dto.getNoOfPackages());
		stockTransferChallanVO.setOtherPackages(dto.getOtherPackages());
		stockTransferChallanVO.setImportLocal(dto.getImportLocal());
		stockTransferChallanVO.setActive(dto.isActive());
		stockTransferChallanVO.setOrgId(dto.getOrgId());
		stockTransferChallanVO.setCancelRemarks(dto.getCancelRemarks());
		stockTransferChallanVO.setTimeOfTranfer(dto.getTimeOfTranfer());
		stockTransferChallanVO.setTotalInsurance(dto.getTotalInsurance());
		stockTransferChallanVO.setTotalFreight(dto.getTotalFreight());
		stockTransferChallanVO.setTotalAssVal(dto.getTotalAssVal());
		stockTransferChallanVO.setModeOfTransport(dto.getModeOfTransport());
		stockTransferChallanVO.setSalesTax(dto.getSalesTax());
		stockTransferChallanVO.setGrossAmount(dto.getGrossAmount());
		stockTransferChallanVO.setAmountInWords(dto.getAmountInWords());
		stockTransferChallanVO.setDeliverTo(dto.getDeliverTo());
		stockTransferChallanVO.setPaymentTerms(dto.getPaymentTerms());
		stockTransferChallanVO.setNarration(dto.getNarration());

		// branch mapping
		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			stockTransferChallanVO.setBranch(branchVO);
		}

		// =========================
		// List Of Values Mapping
		// =========================

		if (dto.getTypes() != null && dto.getTypes() != 0) {

			ListOfValuesVO listOfValuesVO = listOfValuesRepo.findById(dto.getTypes())
					.orElseThrow(() -> new ApplicationException("List Of Values Not Found"));

			stockTransferChallanVO.setTypes(listOfValuesVO);
		}

		// =========================
		// Customer Mapping
		// =========================

		if (dto.getCustomer() != null && dto.getCustomer() != 0) {

			CustomerVO customerVO = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			stockTransferChallanVO.setCustomer(customerVO);
		}

		// =========================
		// Location Mapping
		// =========================

		if (dto.getLocation() != null && dto.getLocation() != 0) {

			LocationVO locationVO = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			stockTransferChallanVO.setLocation(locationVO);
		}
	}

	@Override
	public StockTransferChallanResponseDTO getStockTransferChallanById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		StockTransferChallanVO stockTransferChallanVO = stockTransferChallanRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Stock Transfer Challan Not Found"));

		return stockTransferChallanResponse(stockTransferChallanVO);
	}

	@Override
	public List<StockTransferChallanResponseDTO> getStockTransferChallanByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<StockTransferChallanVO> stockTransferChallanList = stockTransferChallanRepo
				.getStockTransferChallanByOrgId(orgId, branch);

		if (stockTransferChallanList.isEmpty()) {
			throw new ApplicationException("No Stock Transfer Challan Details Found");
		}

		List<StockTransferChallanResponseDTO> responseList = new ArrayList<>();

		for (StockTransferChallanVO stockTransferChallanVO : stockTransferChallanList) {

			responseList.add(stockTransferChallanResponse(stockTransferChallanVO));
		}

		return responseList;
	}

	// drowdown for stocktransfercustomer
	@Override
	public Map<String, Object> getStockTransferCustomer() throws ApplicationException {

		List<Object[]> customerList = customerRepo.getStockTransferCustomer();

		if (customerList == null || customerList.isEmpty()) {
			throw new ApplicationException("No Customer Found");
		}

		List<StockTransferCustomerResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : customerList) {

			StockTransferCustomerResponseDTO dto = new StockTransferCustomerResponseDTO();

			dto.setId(((Number) obj[0]).longValue());
			dto.setCustomerCode((String) obj[1]);
			dto.setCustomerName((String) obj[2]);
			dto.setAccountName((String) obj[3]);

			GSTStateResponseDTO gstStateResponseDTO = new GSTStateResponseDTO();

			gstStateResponseDTO.setId(((Number) obj[4]).longValue());
			gstStateResponseDTO.setStateCode((String) obj[5]);
			gstStateResponseDTO.setStateName((String) obj[6]);
			gstStateResponseDTO.setGstStateId((String) obj[7]);

			dto.setGstState(gstStateResponseDTO);

			dto.setGstApplicable((Boolean) obj[8]);
			dto.setGstNo((String) obj[9]);

			responseList.add(dto);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Customer Dropdown Retrieved Successfully");
		response.put("customerList", responseList);

		return response;

	}

	// despatch instruction sheduleno dropdown
	@Override
	public Map<String, Object> getScheduleNoDropdownForDespatchInstruction(Long customer, String monthYear, Long branch, Long orgId)
			throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> scheduleList = despatchInstructionRepo.getScheduleNoDropdownForDespatchInstruction(customer, monthYear, branch, orgId);

		List<DespatchInstructionScheduleResponseDTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : scheduleList) {

			DespatchInstructionScheduleResponseDTO dto = new DespatchInstructionScheduleResponseDTO();

			 dto.setSalesDeliveryScheduleId(
		                obj[0] != null ? ((Number) obj[0]).longValue() : null);

		        dto.setDlvNo(
		                obj[1] != null ? (String) obj[1] : null);

		        dto.setDlvdate(
		                obj[2] != null ? ((java.sql.Date) obj[2]).toLocalDate() : null);

		        dto.setInvoiceType(
		                obj[4] != null ? (String) obj[4] : null);

		        dto.setMonthOfSchedule(
		                obj[3] != null
		                        ? Month.valueOf(((String) obj[3]).toUpperCase())
		                        : null);

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Data Fetched Successfully");

		responseMap.put("scheduleBalanceList", responseDTOList);

		return responseMap;
	}

	// despatch customer dropdown
	@Override
	public Map<String, Object> getDespatchCustomer(Long branch, Long orgId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> customerList = customerRepo.getDespatchCustomer(branch, orgId);

		List<DespatchCustomerResponseDTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : customerList) {

			DespatchCustomerResponseDTO dto = new DespatchCustomerResponseDTO();

			dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : 0L);

			dto.setCustomerCode(obj[1] != null ? (String) obj[1] : "");

			dto.setCustomerName(obj[2] != null ? (String) obj[2] : "");

			dto.setPartyCreditLimit(
					obj[3] != null ? BigDecimal.valueOf(((Number) obj[3]).doubleValue()) : BigDecimal.ZERO);

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Customer List Fetched Successfully");

		responseMap.put("customerList", responseDTOList);

		return responseMap;
	}

	// despatch contract no dropdown
	@Override
	public Map<String, Object> getDespatchSalesContract(Long customerId, Long branch, Long orgId)
			throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> salesContractList = despatchInstructionRepo.getDespatchSalesContract(customerId, branch, orgId);

		List<DespatchSalesContractResponseDTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : salesContractList) {

			DespatchSalesContractResponseDTO dto = new DespatchSalesContractResponseDTO();

			dto.setCustomerContractNo(obj[0] != null ? (String) obj[0] : "");

			dto.setContractDate(obj[1] != null ? ((java.sql.Date) obj[1]).toLocalDate() : null);

			dto.setSalesContractId(obj[2] != null ? ((Number) obj[2]).longValue() : 0L);

			dto.setInvoiceType(obj[3] != null ? (String) obj[3] : "");

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Sales Contract List Fetched Successfully");

		responseMap.put("salesContractList", responseDTOList);

		return responseMap;
	}

	// Despacth Item dropdown
	@Override
	public Map<String, Object> getDespatchItems(Long branch, Long orgId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> itemList = itemMasterRepo.getDespatchItems(branch, orgId);

		List<ItemResponseDTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : itemList) {

			ItemResponseDTO dto = new ItemResponseDTO();

			dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : 0L);

			dto.setItemCode(obj[1] != null ? (String) obj[1] : "");

			dto.setItemDescription(obj[2] != null ? (String) obj[2] : "");

			UnitResponseDTO unitDTO = new UnitResponseDTO();

			unitDTO.setId(obj[3] != null ? ((Number) obj[3]).longValue() : 0L);

			dto.setUnit(unitDTO);

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Finished Goods Item List Fetched Successfully");

		responseMap.put("itemList", responseDTOList);

		return responseMap;
	}

	// Despatch Schedulemonth
	@Override
	public Map<String, Object> getDespatchScheduleMonth(Long itemId, Long branch, Long orgId)
			throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> scheduleMonthList = despatchInstructionRepo.getDespatchScheduleMonth(itemId, branch, orgId);

		List<DespatchScheduleMonthResponseDTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : scheduleMonthList) {

			DespatchScheduleMonthResponseDTO dto = new DespatchScheduleMonthResponseDTO();

			dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : 0L);

			dto.setMonthOfSchedule(obj[1] != null ? (String) obj[1] : "");

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Schedule Month Fetched Successfully");

		responseMap.put("scheduleMonthList", responseDTOList);

		return responseMap;
	}
	// despatch planned qty

	@Override
	public Map<String, Object> getDespatchPlannedQty(Long itemId, Long branch, Long orgId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		BigDecimal plannedQty = despatchInstructionRepo.getDespatchPlannedQty(itemId, branch, orgId);

		if (plannedQty == null) {
			plannedQty = BigDecimal.ZERO;
		}

		responseMap.put("message", "Planned Quantity Fetched Successfully");

		responseMap.put("plannedQty", plannedQty);

		return responseMap;
	}
	// pending qty
//	@Override
//	public Map<String, Object> getDespatchPendingQty(Long itemId,
//	                                         String month,
//	                                          Long branch,
//	                                         Long orgId,
//	                                         Long customerId)
//	        throws ApplicationException {
//
//	    Map<String, Object> responseMap = new HashMap<>();
//
//	    List<Object[]> pendingQtyList =
//	            despatchInstructionRepo.getDespatchPendingQty(
//	                    itemId,
//	                    month,
//	                    branch,
//	                    orgId,
//	                    customerId);
//
//	    List<PendingQtyResponseDTO> responseDTOList = new ArrayList<>();
//
//	    for (Object[] obj : pendingQtyList) {
//
//	        PendingQtyResponseDTO dto = new PendingQtyResponseDTO();
//
//	        dto.setPendingQty(
//	                obj[0] != null
//	                        ? ((Number) obj[0]).intValue()
//	                        : 0);
//
//	        dto.setSno(
//	                obj[1] != null
//	                        ? ((Number) obj[1]).intValue()
//	                        : 0);
//
//	        responseDTOList.add(dto);
//	    }
//
//	    responseMap.put(
//	            "message",
//	            "Pending Quantity Fetched Successfully");
//
//	    responseMap.put(
//	            "pendingQtyList",
//	            responseDTOList);
//
//	    return responseMap;
//	}

}