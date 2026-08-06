package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResonse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceDetResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDetailResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferChallanResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DespatchInstructionDTO;
import com.efitops.basesetup.dto.DespatchInstructionDetailsDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.DocketInvoiceDetailsDTO;
import com.efitops.basesetup.dto.ResponseDTO;
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
	
	public Map<String, Object> updateCreateCustomerComplaint(
	        CustomerComplaintDTO customerComplaintDTO,
	        MultipartFile[] images)
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

		    CustomerResonse1DTO customerDTO = new CustomerResonse1DTO();

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
	private void saveImages(MultipartFile[] images,
	        CustomerComplaintEntryVO customerComplaintEntryVO)
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

	            Files.copy(
	                    image.getInputStream(),
	                    path,
	                    StandardCopyOption.REPLACE_EXISTING);

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

	public Map<String, Object> getPreparedBy(Long orgId, Long branch ,Long departmentId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> employeeList = employeeMasterRepo.getPreparedBy(orgId , branch , departmentId);

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
	public Map<String, Object> getCustomerComplaintItemDetails(Long orgId, Long branch)
	        throws ApplicationException {

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
	public Map<String, Object> getCustomerDetails(Long orgId , Long branch) throws ApplicationException {

		List<Object[]> list = customerRepo.getCustomerDetails(orgId,branch);

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

	@Override
	@Transactional
	public Map<String, Object> updateCreateSalesContractAmendment(
			SalesContractAmendmentDTO salesContractAmendmentDTO) throws ApplicationException {

		SalesContractAmendmentVO salesContractAmendmentVO = new SalesContractAmendmentVO();

		String message;

		if (salesContractAmendmentDTO.getId() != null) {

			salesContractAmendmentVO = salesContractAmendmentRepo.findById(salesContractAmendmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Sales Contract Amendment Details"));

			salesContractAmendmentVO.setUpdated_By(salesContractAmendmentDTO.getCreatedBy());

			message = "Sales Contract Amendment Updated Successfully";

		} else {

			salesContractAmendmentVO.setCreatedBy(salesContractAmendmentDTO.getCreatedBy());
			salesContractAmendmentVO.setUpdated_By(salesContractAmendmentDTO.getCreatedBy());

			message = "Sales Contract Amendment Created Successfully";
		}
		createUpdateSalesContractAmendmentVO(
		        salesContractAmendmentDTO,
		        salesContractAmendmentVO);

		SalesContractAmendmentVO savedSalesContractAmendment =
		        salesContractAmendmentRepo.save(salesContractAmendmentVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("salesContractAmendmentVO", salesContractResponseResponse(savedSalesContractAmendment));


		return response;
	}
	private SalesContractAmdResponseDTO salesContractResponseResponse(
			SalesContractAmendmentVO salesContractAmendmentVO) {

		SalesContractAmdResponseDTO responseDTO = new SalesContractAmdResponseDTO();

		responseDTO.setId(salesContractAmendmentVO.getId());
		responseDTO.setContractAmdNo(salesContractAmendmentVO.getContractAmdNo());
		responseDTO.setDate(salesContractAmendmentVO.getDate());
		responseDTO.setContractNo(salesContractAmendmentVO.getContractNo());
		responseDTO.setContractDate(salesContractAmendmentVO.getContractDate());
		responseDTO.setCustPoNo(salesContractAmendmentVO.getCustPoNo());
		responseDTO.setCustPoDate(salesContractAmendmentVO.getCustPoDate());
		responseDTO.setRevisionNo(salesContractAmendmentVO.getRevisionNo());
		responseDTO.setRemarks(salesContractAmendmentVO.getRemarks());

		if (salesContractAmendmentVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(salesContractAmendmentVO.getBranch().getId());
			branchDTO.setBranchName(salesContractAmendmentVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		responseDTO.setOrgId(salesContractAmendmentVO.getOrgId());
		responseDTO.setCreatedBy(salesContractAmendmentVO.getCreatedBy());
		responseDTO.setCancelRemarks(salesContractAmendmentVO.getCancelRemarks());

		List<SalesContractDetailResponseDTO> detailResponseList = new ArrayList<>();

		if (salesContractAmendmentVO.getSalesContractAmdDetailsVO() != null
				&& !salesContractAmendmentVO.getSalesContractAmdDetailsVO().isEmpty()) {

			for (SalesContractAmdDetailsVO detailVO : salesContractAmendmentVO.getSalesContractAmdDetailsVO()) {

				SalesContractDetailResponseDTO detailDTO = new SalesContractDetailResponseDTO();

				detailDTO.setId(detailVO.getId());

				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					detailDTO.setItem(itemDTO);
				}

				detailDTO.setOldRate(detailVO.getOldRate());
				detailDTO.setNewRate(detailVO.getNewRate());
				detailDTO.setValidFrom(detailVO.getValidFrom());
				detailDTO.setValidTo(detailVO.getValidTo());
				detailDTO.setNewValidDate(detailVO.getNewValidDate());

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setSalesContractDetailResponseDTO(detailResponseList);

		return responseDTO;
	}
	private void createUpdateSalesContractAmendmentVO(
			SalesContractAmendmentDTO dto,
			SalesContractAmendmentVO salesContractAmendmentVO)
			throws ApplicationException {

		salesContractAmendmentVO.setContractAmdNo(dto.getContractAmdNo());
		salesContractAmendmentVO.setDate(dto.getDate());
		salesContractAmendmentVO.setContractNo(dto.getContractNo());
		salesContractAmendmentVO.setContractDate(dto.getContractDate());
		salesContractAmendmentVO.setCustPoNo(dto.getCustPoNo());
		salesContractAmendmentVO.setCustPoDate(dto.getCustPoDate());
		salesContractAmendmentVO.setRevisionNo(dto.getRevisionNo());
		salesContractAmendmentVO.setRemarks(dto.getRemarks());

		salesContractAmendmentVO.setOrgId(dto.getOrgId());
		salesContractAmendmentVO.setActive(dto.getActive());
		salesContractAmendmentVO.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			salesContractAmendmentVO.setBranch(branch);
		}

		//----------------------------------------------------
		// Delete old child records while updating
		//----------------------------------------------------

		if (dto.getId() != null) {

			List<SalesContractAmdDetailsVO> oldList = salesContractAmdDetailsRepo
					.findBySalesContractAmendmentVO(salesContractAmendmentVO);

			salesContractAmdDetailsRepo.deleteAll(oldList);
		}

		List<SalesContractAmdDetailsVO> detailList = new ArrayList<>();

		if (dto.getSalesContractAmdDetailsDTO() != null
				&& !dto.getSalesContractAmdDetailsDTO().isEmpty()) {

			for (SalesContractAmdDetailsDTO detailDTO : dto.getSalesContractAmdDetailsDTO()) {

				SalesContractAmdDetailsVO detailVO = new SalesContractAmdDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				detailVO.setOldRate(detailDTO.getOldRate());
				detailVO.setNewRate(detailDTO.getNewRate());
				detailVO.setValidFrom(detailDTO.getValidFrom());
				detailVO.setValidTo(detailDTO.getValidTo());
				detailVO.setNewValidDate(detailDTO.getNewValidDate());

				// Parent Mapping
				detailVO.setSalesContractAmendmentVO(salesContractAmendmentVO);

				detailList.add(detailVO);
			}

			// Set child list to parent
			salesContractAmendmentVO.setSalesContractAmdDetailsVO(detailList);
		}
	}
	
	@Override
	public SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id)
			throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		SalesContractAmendmentVO salesContractAmendmentVO = salesContractAmendmentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Sales Contract Amendment Not Found"));

		return salesContractResponseResponse(salesContractAmendmentVO);
	}
	
	@Override
	public List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId,Long branch)
	        throws ApplicationException {

	    List<SalesContractAmendmentVO> salesContractAmendmentList =
	            salesContractAmendmentRepo.getSalesContractAmendmentByOrgId(orgId,branch);

	    if (salesContractAmendmentList.isEmpty()) {
	        throw new ApplicationException("No Sales Contract Amendment Details Found");
	    }

	    List<SalesContractAmdResponseDTO> responseList = new ArrayList<>();

	    for (SalesContractAmendmentVO salesContractAmendmentVO : salesContractAmendmentList) {

	        responseList.add(
	                salesContractResponseResponse(salesContractAmendmentVO));
	    }

	    return responseList;
	}
	
	//dropdown for sales Contract Amendment
	
	@Override
	public Map<String, Object> getContractNo() throws ApplicationException {

	    String methodName = "getContractNo()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    try {

	        List<SalesContractVO> contractList = salesContractRepo.getContractNo();

	        if (contractList != null && !contractList.isEmpty()) {

	            for (SalesContractVO vo : contractList) {

	                Map<String, Object> map = new HashMap<>();

	                map.put("id", vo.getId());
	                map.put("contractNo", vo.getCustomerContractNo());
	                map.put("cust_po_no", vo.getCustomerPoNo());
	                map.put("cust_po_date", vo.getCustomerPoDate());

	                responseList.add(map);
	            }
	        }

	    } catch (Exception e) {

	        LOGGER.error("Error in getContractNoDropdown()", e);
	        throw new ApplicationException(e.getMessage());
	    }

	    Map<String, Object> responseMap = new HashMap<>();
	    responseMap.put("message", "Contract No Dropdown Loaded Successfully");
	    responseMap.put("contractList", responseList);

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return responseMap;
	}
	
	//Despatch Instruction 
	@Override
	@Transactional
	public Map<String, Object> updateCreateDespatchInstruction(
	        DespatchInstructionDTO despatchInstructionDTO)
	        throws ApplicationException {

	    DespatchInstructionVO despatchInstructionVO = new DespatchInstructionVO();

	    String message;

	    if (ObjectUtils.isNotEmpty(despatchInstructionDTO.getId())) {

	        despatchInstructionVO = despatchInstructionRepo
	                .findById(despatchInstructionDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException("Invalid Despatch Instruction Details"));

	        despatchInstructionVO.setUpdated_By(
	                despatchInstructionDTO.getCreatedBy());

	        message = "Despatch Instruction Updated Successfully";

	    } else {

	        despatchInstructionVO.setCreatedBy(
	                despatchInstructionDTO.getCreatedBy());

	        despatchInstructionVO.setUpdated_By(
	                despatchInstructionDTO.getCreatedBy());

	        message = "Despatch Instruction Created Successfully";
	    }

	    createUpdateDespatchInstructionVO(
	            despatchInstructionDTO,
	            despatchInstructionVO);

	    DespatchInstructionVO savedDespatchInstruction =
	            despatchInstructionRepo.save(despatchInstructionVO);

	    Map<String, Object> response = new HashMap<>();

	    response.put("message", message);

	    response.put(
	            "despatchInstructionVO",
	            despatchInstructionResponse(savedDespatchInstruction));

	    return response;   
	}
	private DespatchInstructionResponseDTO despatchInstructionResponse(
	        DespatchInstructionVO despatchInstructionVO) {

	    DespatchInstructionResponseDTO responseDTO = new DespatchInstructionResponseDTO();

	    responseDTO.setId(despatchInstructionVO.getId());
	    responseDTO.setDiNo(despatchInstructionVO.getDiNo());
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

	        CurrencyResponseDTO customerDTO = new CurrencyResponseDTO();

	        customerDTO.setId(despatchInstructionVO.getCustomer().getId());
	        customerDTO.setCurrencyName(despatchInstructionVO.getCustomer().getCurrency());

	        responseDTO.setCurrency(customerDTO);
	    }

	    if (despatchInstructionVO.getLocation() != null) {

	        LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

	        locationDTO.setId(despatchInstructionVO.getLocation().getId());
	        locationDTO.setLocationName(despatchInstructionVO.getLocation().getLocationName());

	        responseDTO.setLocation(locationDTO);
	    }

	    List<DespatchInstDetailsResponseDTO> detailResponseList = new ArrayList<>();

	    if (despatchInstructionVO.getDetails() != null
	            && !despatchInstructionVO.getDetails().isEmpty()) {

	        for (DespatchInstructionDetailsVO detailVO : despatchInstructionVO.getDetails()) {

	            DespatchInstDetailsResponseDTO detailDTO =
	                    new DespatchInstDetailsResponseDTO();

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
	
	private void createUpdateDespatchInstructionVO(
	        DespatchInstructionDTO dto,
	        DespatchInstructionVO despatchInstructionVO)
	        throws ApplicationException {

	    despatchInstructionVO.setDiNo(dto.getDiNo());
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

	    if (dto.getCustomer() != null && dto.getCustomer() != 0) {

	        CurrencyVO customer = CurrencyRepo.findById(dto.getCustomer())
	                .orElseThrow(() -> new ApplicationException("Customer Not Found"));

	        despatchInstructionVO.setCustomer(customer);
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

	        List<DespatchInstructionDetailsVO> oldList =
	                despatchInstructionDetailsRepo
	                        .findByDespatchInstructionVO(despatchInstructionVO);

	        despatchInstructionDetailsRepo.deleteAll(oldList);
	    }

	    // ======================================
	    // Child Save
	    // ======================================

	    List<DespatchInstructionDetailsVO> detailList =
	            new ArrayList<>();

	    if (dto.getDespatchInstructionDetailsDTO() != null
	            && !dto.getDespatchInstructionDetailsDTO().isEmpty()) {

	        for (DespatchInstructionDetailsDTO detailDTO :
	                dto.getDespatchInstructionDetailsDTO()) {

	            DespatchInstructionDetailsVO detailVO =
	                    new DespatchInstructionDetailsVO();

	            detailVO.setOrdAccpContrNo(detailDTO.getOrdAccpContrNo());
	            detailVO.setDate(detailDTO.getDate());

	            if (detailDTO.getItem() != null
	                    && detailDTO.getItem() != 0) {

	                ItemMasterVO item =
	                        itemMasterRepo.findById(detailDTO.getItem())
	                                .orElseThrow(() ->
	                                        new ApplicationException("Item Not Found"));

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
	public DespatchInstructionResponseDTO getDespatchInstructionById(Long id)
	        throws ApplicationException {

	    if (ObjectUtils.isEmpty(id)) {
	        throw new ApplicationException("Invalid Id");
	    }

	    DespatchInstructionVO despatchInstructionVO = despatchInstructionRepo
	            .findById(id)
	            .orElseThrow(() ->
	                    new ApplicationException("Despatch Instruction Not Found"));

	    return despatchInstructionResponse(despatchInstructionVO);
	}
	
	
	@Override
	public List<DespatchInstructionResponseDTO> getDespatchInstructionByOrgId(
	        Long orgId,
	        Long branch)
	        throws ApplicationException {

	    List<DespatchInstructionVO> despatchInstructionList =
	            despatchInstructionRepo.getDespatchInstructionByOrgId(orgId, branch);

	    if (despatchInstructionList.isEmpty()) {
	        throw new ApplicationException("No Despatch Instruction Details Found");
	    }

	    List<DespatchInstructionResponseDTO> responseList = new ArrayList<>();

	    for (DespatchInstructionVO despatchInstructionVO : despatchInstructionList) {

	        responseList.add(
	                despatchInstructionResponse(despatchInstructionVO));
	    }

	    return responseList;
	}
	
	//Docket Invoice
	
	@Override
	@Transactional
	public Map<String, Object> updateCreateDocketInvoice(
	        DocketInvoiceDTO docketInvoiceDTO)
	        throws ApplicationException {

	    DocketInvoiceVO docketInvoiceVO = new DocketInvoiceVO();

	    String message;

	    if (ObjectUtils.isNotEmpty(docketInvoiceDTO.getId())) {

	        docketInvoiceVO = docketInvoiceRepo
	                .findById(docketInvoiceDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException("Invalid Docket Invoice Details"));

	        docketInvoiceVO.setUpdatedBy(
	                docketInvoiceDTO.getCreatedBy());

	        message = "Docket Invoice Updated Successfully";

	    } else {

	        docketInvoiceVO.setCreatedBy(
	                docketInvoiceDTO.getCreatedBy());

	        docketInvoiceVO.setUpdatedBy(
	                docketInvoiceDTO.getCreatedBy());

	        message = "Docket Invoice Created Successfully";
	    }

	    createUpdateDocketInvoiceVO(
	            docketInvoiceDTO,
	            docketInvoiceVO);

	    DocketInvoiceVO savedDocketInvoice =
	            docketInvoiceRepo.save(docketInvoiceVO);

	    Map<String, Object> response = new HashMap<>();

	    response.put("message", message);

	    response.put(
	            "docketInvoiceVO",
	            docketInvoiceResponse(savedDocketInvoice));

	    return response;
	}
	
	private DocketInvoiceResponseDTO docketInvoiceResponse(
	        DocketInvoiceVO docketInvoiceVO) {

	    DocketInvoiceResponseDTO responseDTO =
	            new DocketInvoiceResponseDTO();

	    responseDTO.setId(docketInvoiceVO.getId());
	    responseDTO.setDocNo(docketInvoiceVO.getDocNo());
	    responseDTO.setDocDate(docketInvoiceVO.getDocDate());
	    responseDTO.setBillNo(docketInvoiceVO.getBillNo());
	    responseDTO.setBillDate(docketInvoiceVO.getBillDate());
	    responseDTO.setTotalAmount(docketInvoiceVO.getTotalAmount());
	    responseDTO.setOrgId(docketInvoiceVO.getOrgId());
	    responseDTO.setActive(docketInvoiceVO.isActive());
	    responseDTO.setCreatedBy(docketInvoiceVO.getCreatedBy());
	    responseDTO.setCancelRemarks(docketInvoiceVO.getCancelRemarks());

	    // =========================
	    // Branch Response
	    // =========================

	    if (docketInvoiceVO.getBranch() != null) {

	        BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

	        branchResponseDTO.setId(docketInvoiceVO.getBranch().getId());
	        branchResponseDTO.setBranchName(docketInvoiceVO.getBranch().getBranchName());

	        responseDTO.setBranch(branchResponseDTO);
	    }

	    // =========================
	    // Transport Response
	    // =========================

	    if (docketInvoiceVO.getTransport() != null) {

	        TransportResponseDTO transportResponseDTO = new TransportResponseDTO();

	        transportResponseDTO.setId(docketInvoiceVO.getTransport().getId());
	        transportResponseDTO.setTransportName(docketInvoiceVO.getTransport().getTransportName());

	        responseDTO.setTransport(transportResponseDTO);
	    }

	    // =========================
	    // Child Response
	    // =========================

	    List<DocketInvoiceDetResponseDTO> detailResponseList =
	            new ArrayList<>();

	    if (docketInvoiceVO.getDetails() != null
	            && !docketInvoiceVO.getDetails().isEmpty()) {

	        for (DocketInvoiceDetailsVO detailVO
	                : docketInvoiceVO.getDetails()) {

	            DocketInvoiceDetResponseDTO detailDTO =
	                    new DocketInvoiceDetResponseDTO();

	            detailDTO.setDocketNo(detailVO.getDocketNo());
	            detailDTO.setDocketDate(detailVO.getDocketDate());
	            detailDTO.setInvoiceNo(detailVO.getInvoiceNo());
	            detailDTO.setNoOfQty(detailVO.getNoOfQty());
	            detailDTO.setWeight(detailVO.getWeight());
	            detailDTO.setTotalValue(detailVO.getTotalValue());
	            detailDTO.setCumulativeValue(detailVO.getCumulativeValue());
	            detailDTO.setMode(detailVO.getMode());

	            detailResponseList.add(detailDTO);
	        }
	    }

	    responseDTO.setDocketInvoiceDetResponseDTO(
	            detailResponseList);

	    return responseDTO;
	}
	
	private void createUpdateDocketInvoiceVO(
	        DocketInvoiceDTO dto,
	        DocketInvoiceVO docketInvoiceVO)
	        throws ApplicationException {

	    docketInvoiceVO.setDocNo(dto.getDocNo());
	    docketInvoiceVO.setDocDate(dto.getDocDate());
	    docketInvoiceVO.setBillNo(dto.getBillNo());
	    docketInvoiceVO.setBillDate(dto.getBillDate());
	    docketInvoiceVO.setTotalAmount(dto.getTotalAmount());

	    docketInvoiceVO.setOrgId(dto.getOrgId());
	    docketInvoiceVO.setActive(dto.getActive());
	    docketInvoiceVO.setCancelRemarks(dto.getCancelRemarks());

	    // =========================
	    // Branch Mapping
	    // =========================

	    if (dto.getBranch() != null && dto.getBranch() != 0) {

	        BranchVO branch = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() ->
	                        new ApplicationException("Branch Not Found"));

	        docketInvoiceVO.setBranch(branch);
	    }

	    // =========================
	    // Transport Mapping
	    // =========================

	    if (dto.getTransport() != null && dto.getTransport() != 0) {

	        TransportMasterVO transport = transportRepo
	                .findById(dto.getTransport())
	                .orElseThrow(() ->
	                        new ApplicationException("Transport Not Found"));

	        docketInvoiceVO.setTransport(transport);
	    }

	    // ======================================
	    // Delete Existing Child During Update
	    // ======================================

	    if (dto.getId() != null) {

	        List<DocketInvoiceDetailsVO> oldList =
	        		docketInvoiceDetRepo
	                        .findByDocketInvoiceVO(docketInvoiceVO);

	        docketInvoiceDetRepo.deleteAll(oldList);
	    }

	    // ======================================
	    // Child Save
	    // ======================================

	    List<DocketInvoiceDetailsVO> detailList =
	            new ArrayList<>();

	    if (dto.getDocketInvoiceDetailsDTO() != null
	            && !dto.getDocketInvoiceDetailsDTO().isEmpty()) {

	        for (DocketInvoiceDetailsDTO detailDTO
	                : dto.getDocketInvoiceDetailsDTO()) {

	            DocketInvoiceDetailsVO detailVO =
	                    new DocketInvoiceDetailsVO();

	            detailVO.setDocketNo(detailDTO.getDocketNo());
	            detailVO.setDocketDate(detailDTO.getDocketDate());
	            detailVO.setInvoiceNo(detailDTO.getInvoiceNo());
	            detailVO.setNoOfQty(detailDTO.getNoOfQty());
	            detailVO.setWeight(detailDTO.getWeight());
	            detailVO.setTotalValue(detailDTO.getTotalValue());
	            detailVO.setCumulativeValue(detailDTO.getCumulativeValue());
	            detailVO.setMode(detailDTO.getMode());

	            // Parent Mapping
	            detailVO.setDocketInvoiceVO(docketInvoiceVO);

	            detailList.add(detailVO);
	        }

	        docketInvoiceVO.setDetails(detailList);
	    }
	    
	}
	@Override
	public DocketInvoiceResponseDTO getDocketInvoiceById(Long id)
	        throws ApplicationException {

	    if (ObjectUtils.isEmpty(id)) {
	        throw new ApplicationException("Invalid Id");
	    }

	    DocketInvoiceVO docketInvoiceVO = docketInvoiceRepo
	            .findById(id)
	            .orElseThrow(() ->
	                    new ApplicationException("Docket Invoice Not Found"));

	    return docketInvoiceResponse(docketInvoiceVO);
	}
	
	@Override
	public List<DocketInvoiceResponseDTO> getDocketInvoiceByOrgId(
	        Long orgId,
	        Long branch)
	        throws ApplicationException {

	    List<DocketInvoiceVO> docketInvoiceList =
	            docketInvoiceRepo.getDocketInvoiceByOrgId(orgId, branch);

	    if (docketInvoiceList.isEmpty()) {
	        throw new ApplicationException("No Docket Invoice Details Found");
	    }

	    List<DocketInvoiceResponseDTO> responseList =
	            new ArrayList<>();

	    for (DocketInvoiceVO docketInvoiceVO : docketInvoiceList) {

	        responseList.add(
	                docketInvoiceResponse(docketInvoiceVO));
	    }

	    return responseList;
	}
	
	// Stock Transfer Challan
//	@Override
//	@Transactional
//	public Map<String, Object> updateCreateStockTransferChallan(
//	        StockTransferChallanDTO stockTransferChallanDTO)
//	        throws ApplicationException {
//
//	    StockTransferChallanVO stockTransferChallanVO =
//	            new StockTransferChallanVO();
//
//	    String message;
//
//	    if (ObjectUtils.isNotEmpty(stockTransferChallanDTO.getId())) {
//
//	        stockTransferChallanVO = stockTransferChallanRepo
//	                .findById(stockTransferChallanDTO.getId())
//	                .orElseThrow(() ->
//	                        new ApplicationException("Invalid Stock Transfer Challan Details"));
//
//	        stockTransferChallanVO.setUpdated_By(
//	                stockTransferChallanDTO.getCreatedBy());
//
//	        message = "Stock Transfer Challan Updated Successfully";
//
//	    } else {
//
//	        stockTransferChallanVO.setCreatedBy(
//	                stockTransferChallanDTO.getCreatedBy());
//
//	        stockTransferChallanVO.setUpdated_By(
//	                stockTransferChallanDTO.getCreatedBy());
//
//	        message = "Stock Transfer Challan Created Successfully";
//	    }
//
//	    createUpdateStockTransferChallanVO(
//	            stockTransferChallanDTO,
//	            stockTransferChallanVO);
//
//	    StockTransferChallanVO savedStockTransferChallan =
//	            stockTransferChallanRepo.save(stockTransferChallanVO);
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    response.put("message", message);
//
//	    response.put(
//	            "stockTransferChallanVO",
//	            stockTransferChallanResponse(savedStockTransferChallan));
//
//	    return response;
//	}
//	private StockTransferChallanResponseDTO stockTransferChallanResponse(
//	        StockTransferChallanVO stockTransferChallanVO) {
//
//	    StockTransferChallanResponseDTO responseDTO =
//	            new StockTransferChallanResponseDTO();
//
//	    responseDTO.setId(stockTransferChallanVO.getId());
//	    responseDTO.setDocID(stockTransferChallanVO.getDocID());
//	    responseDTO.setTransferDate(stockTransferChallanVO.getTransferDate());
//	    responseDTO.setStockPosting(stockTransferChallanVO.getStockPosting());
//	    responseDTO.setDate(stockTransferChallanVO.getDate());
//	    responseDTO.setNoOfPackages(stockTransferChallanVO.getNoOfPackages());
//	    responseDTO.setOtherPackages(stockTransferChallanVO.getOtherPackages());
//	    responseDTO.setImportLocal(stockTransferChallanVO.getImportLocal());
//
//	    if (stockTransferChallanVO.get() != null) {
//	        responseDTO.setListOfValues(
//	                stockTransferChallanVO.getListOfValues().getId());
//	    }
//	    if (stockTransferChallanVO.getListOfValues() != null) {
//	        responseDTO.setListOfValues(
//	                stockTransferChallanVO.getListOfValues().getId());
//	    }
//
//	    if (stockTransferChallanVO.getCustomer() != null) {
//	        responseDTO.setCustomer(
//	                stockTransferChallanVO.getCustomer().getId());
//	    }
//
//	    if (stockTransferChallanVO.getLocation() != null) {
//	        responseDTO.setLocation(
//	                stockTransferChallanVO.getLocation().getId());
//	    }
//
//	    return responseDTO;
//	}
//	
//	private void createUpdateStockTransferChallanVO(
//	        StockTransferChallanDTO dto,
//	        StockTransferChallanVO stockTransferChallanVO)
//	        throws ApplicationException {
//
//	    stockTransferChallanVO.setDocID(dto.getDocID());
//	    stockTransferChallanVO.setTransferDate(dto.getTransferDate());
//	    stockTransferChallanVO.setStockPosting(dto.getStockPosting());
//	    stockTransferChallanVO.setDate(dto.getDate());
//	    stockTransferChallanVO.setNoOfPackages(dto.getNoOfPackages());
//	    stockTransferChallanVO.setOtherPackages(dto.getOtherPackages());
//	    stockTransferChallanVO.setImportLocal(dto.getImportLocal());
//	    
//	    //branch mapping
//	    if (dto.getBranch() != null && dto.getBranch() != 0) {
//
//	        BranchVO branchVO =
//	                branchRepo.findById(dto.getBranch())
//	                .orElseThrow(() ->
//	                        new ApplicationException("branch Not Found"));
//
//	        stockTransferChallanVO.setBranch(branchVO);
//	    }
//
//
//	    // =========================
//	    // List Of Values Mapping
//	    // =========================
//
//	    if (dto.getListOfValues() != null && dto.getListOfValues() != 0) {
//
//	        ListOfValuesVO listOfValuesVO =
//	                listOfValuesRepo.findById(dto.getListOfValues())
//	                .orElseThrow(() ->
//	                        new ApplicationException("List Of Values Not Found"));
//
//	        stockTransferChallanVO.setListOfValues(listOfValuesVO);
//	    }
//
//	    // =========================
//	    // Customer Mapping
//	    // =========================
//
//	    if (dto.getCustomer() != null && dto.getCustomer() != 0) {
//
//	        CustomerVO customerVO =
//	                customerRepo.findById(dto.getCustomer())
//	                .orElseThrow(() ->
//	                        new ApplicationException("Customer Not Found"));
//
//	        stockTransferChallanVO.setCustomer(customerVO);
//	    }
//
//	    // =========================
//	    // Location Mapping
//	    // =========================
//
//	    if (dto.getLocation() != null && dto.getLocation() != 0) {
//
//	        LocationVO locationVO =
//	                locationRepo.findById(dto.getLocation())
//	                .orElseThrow(() ->
//	                        new ApplicationException("Location Not Found"));
//
//	        stockTransferChallanVO.setLocation(locationVO);
//	    }
//	}
//	
}