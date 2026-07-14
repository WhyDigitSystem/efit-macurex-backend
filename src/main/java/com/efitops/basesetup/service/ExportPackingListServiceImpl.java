package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ExportPackingListDTO;
import com.efitops.basesetup.dto.ExportPackingListDetailsDTO;
import com.efitops.basesetup.dto.ExportPackingListTermsDTO;
import com.efitops.basesetup.dto.ExportPackingShippingListDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ExportPackingListDetailsVO;
import com.efitops.basesetup.entity.ExportPackingListTermsVO;
import com.efitops.basesetup.entity.ExportPackingListVO;
import com.efitops.basesetup.entity.ExportPackingShippingListVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ExportPackingListDetailsRepo;
import com.efitops.basesetup.repo.ExportPackingListRepo;
import com.efitops.basesetup.repo.ExportPackingListTermsRepo;
import com.efitops.basesetup.repo.ExportPackingShippingListRepo;

@Service
public class ExportPackingListServiceImpl implements ExportPackingListService {

	@Autowired
	ExportPackingListRepo exportPackingListRepo;

	@Autowired
	ExportPackingListDetailsRepo exportPackingListDetailRepo;

	@Autowired
	ExportPackingShippingListRepo exportPackingShippingListRepo;

	@Autowired
	ExportPackingListTermsRepo exportPackingListTermsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public List<ExportPackingListVO> getExportPackingListByOrgId(Long orgid, String finYear, String branchCode) {
		
		return exportPackingListRepo.findExportPackingListByOrgId(orgid, finYear, branchCode);
	}

	@Override
	public ExportPackingListVO getExportPackingListById(Long id) {

		return exportPackingListRepo.findExportPackingListById(id);
	}

	@Override
	public String getExportPackingListDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "EPL";
		String result = exportPackingListRepo.getExportPackingListDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> updateCreateExportPackingList(@Valid ExportPackingListDTO exportPackingListDTO)
			throws ApplicationException {

		ExportPackingListVO exportPackingListVO;
		String message = null;
		String screenCode = "EPL";
		ExportPackingListVO oldExportPackingList   = null;

		if (ObjectUtils.isEmpty(exportPackingListDTO.getId())) {			
		exportPackingListVO = new ExportPackingListVO();
			exportPackingListVO.setCreatedBy(exportPackingListDTO.getCreatedBy());
			exportPackingListVO.setUpdatedBy(exportPackingListDTO.getCreatedBy());

			String docId = exportPackingListRepo.getExportPackingListDocId(exportPackingListDTO.getOrgId(), exportPackingListDTO.getFinYear(),
					exportPackingListDTO.getBranchCode(), screenCode);
			exportPackingListVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(exportPackingListDTO.getOrgId(), exportPackingListDTO.getFinYear(),
							exportPackingListDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "PurchaseIndent Creation SuccessFully";

		} else {
			
			oldExportPackingList = exportPackingListRepo.findById(exportPackingListDTO.getId())
		            .orElseThrow(() -> new ApplicationException("exportPackingList  not found"));

			oldExportPackingList.getExportPackingListDetailsVO().size();// load
			oldExportPackingList.getExportPackingShippingListVO().size();
			oldExportPackingList.getExportPackingListTermsVO().size();
			
			
		    entityManager.detach(oldExportPackingList); // detach snapshot
			

			exportPackingListVO = exportPackingListRepo.findById(exportPackingListDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"ExportPackingList  Not Found with id: " + exportPackingListDTO.getId()));
			exportPackingListVO.setUpdatedBy(exportPackingListDTO.getCreatedBy());

			message = "ExportPackingList Updation Successfully";
		}

		exportPackingListVO = getExportPackingListVOFromExportPackingListDTO(exportPackingListVO, exportPackingListDTO);
		exportPackingListRepo.save(exportPackingListVO);
		
		commonNotificationService.generateNotification(exportPackingListVO.getScreenCode(), exportPackingListVO.getId(), oldExportPackingList, exportPackingListVO);


		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("exportPackingListVO", exportPackingListVO);
		return response;

	}

	private ExportPackingListVO getExportPackingListVOFromExportPackingListDTO(ExportPackingListVO exportPackingListVO,
			@Valid ExportPackingListDTO exportPackingListDTO) {

		BigDecimal totalQuantitySum = BigDecimal.ZERO;
		BigDecimal totalWeightSum = BigDecimal.ZERO;

		exportPackingListVO.setCustomerAddress(exportPackingListDTO.getCustomerAddress());
		exportPackingListVO.setCustomerName(exportPackingListDTO.getCustomerName());
		exportPackingListVO.setCustomerCode(exportPackingListDTO.getCustomerCode());

		if (exportPackingListDTO.getSalesOrderNo() != null && !exportPackingListDTO.getSalesOrderNo().isEmpty()) {
			String salesOrderNum = String.join(",", exportPackingListDTO.getSalesOrderNo());
			exportPackingListVO.setSalesOrderNo(salesOrderNum);
		} else {
			exportPackingListVO.setSalesOrderNo(null);
		}

		exportPackingListVO.setDeliveryPlace(exportPackingListDTO.getDeliveryPlace());
		exportPackingListVO.setCountryOfOrginGoods(exportPackingListDTO.getCountryOfOrginGoods());
		exportPackingListVO.setNoOfPackage(exportPackingListDTO.getNoOfPackage());
		exportPackingListVO.setTypeOfPackage(exportPackingListDTO.getTypeOfPackage());
		exportPackingListVO.setDestinationCountry(exportPackingListDTO.getDestinationCountry());
		exportPackingListVO.setStatus(exportPackingListDTO.getStatus());

		exportPackingListVO.setLutNo(exportPackingListDTO.getLutNo());
		exportPackingListVO.setTotalQuantity(exportPackingListDTO.getTotalQuantity());
		exportPackingListVO.setTotalGrossWeight(exportPackingListDTO.getTotalGrossWeight());
		exportPackingListVO.setBoxType(exportPackingListDTO.getBoxType());
		exportPackingListVO.setBoxDimention(exportPackingListDTO.getBoxDimention());
		exportPackingListVO.setNarration(exportPackingListDTO.getNarration());
		exportPackingListVO.setBoxQuantity(exportPackingListDTO.getBoxQuantity());
		exportPackingListVO.setOrgId(exportPackingListDTO.getOrgId());
		exportPackingListVO.setBranch(exportPackingListDTO.getBranch());
		exportPackingListVO.setBranchCode(exportPackingListDTO.getBranchCode());
		exportPackingListVO.setFinYear(exportPackingListDTO.getFinYear());
		

		if (exportPackingListDTO.getId() != null) {

			List<ExportPackingListDetailsVO> exportPackingListDetailsVOs = exportPackingListDetailRepo
					.findByExportPackingListVO(exportPackingListVO);
			exportPackingListDetailRepo.deleteAll(exportPackingListDetailsVOs);

			List<ExportPackingShippingListVO> exportPackingShippingListVOs = exportPackingShippingListRepo
					.findByExportPackingListVO(exportPackingListVO);
			exportPackingShippingListRepo.deleteAll(exportPackingShippingListVOs);

			List<ExportPackingListTermsVO> exportPackingListTermsVOs = exportPackingListTermsRepo
					.findByExportPackingListVO(exportPackingListVO);
			exportPackingListTermsRepo.deleteAll(exportPackingListTermsVOs);
		}

		List<ExportPackingListDetailsVO> exportPackingListDetailsVOs = new ArrayList<ExportPackingListDetailsVO>();
		for (ExportPackingListDetailsDTO exportPackingListDetailsDTO : exportPackingListDTO
				.getExportPackingListDetailsDTO()) {

			ExportPackingListDetailsVO exportPackingListDetailsVO = new ExportPackingListDetailsVO();
			exportPackingListDetailsVO.setPartNo(exportPackingListDetailsDTO.getPartNo());
			exportPackingListDetailsVO.setPartDesc(exportPackingListDetailsDTO.getPartDesc());
			exportPackingListDetailsVO.setCustpo(exportPackingListDetailsDTO.getCustpo());
			exportPackingListDetailsVO.setCustomerPoItem(exportPackingListDetailsDTO.getCustomerPoItem());
			exportPackingListDetailsVO.setHsnCode(exportPackingListDetailsDTO.getHsnCode());
			exportPackingListDetailsVO.setPoNo(exportPackingListDetailsDTO.getPoNo());
			exportPackingListDetailsVO.setQuantity(exportPackingListDetailsDTO.getQuantity());
			exportPackingListDetailsVO.setUnit(exportPackingListDetailsDTO.getUnit());
			exportPackingListDetailsVO.setWeightKg(exportPackingListDetailsDTO.getWeightKg());
			exportPackingListDetailsVO.setPrice(exportPackingListDetailsDTO.getPrice());
			exportPackingListDetailsVO.setSano(exportPackingListDetailsDTO.getSano());
			exportPackingListDetailsVO.setWono1(exportPackingListDetailsDTO.getWono1());
			exportPackingListDetailsVO.setPoQuantity(exportPackingListDetailsDTO.getPoQuantity());

			exportPackingListDetailsVO.setExportPackingListVO(exportPackingListVO);
			exportPackingListDetailsVOs.add(exportPackingListDetailsVO);

			 totalQuantitySum = totalQuantitySum.add(
			            exportPackingListDetailsDTO.getQuantity() != null ? exportPackingListDetailsDTO.getQuantity() : BigDecimal.ZERO);
			        totalWeightSum = totalWeightSum.add(
			            exportPackingListDetailsDTO.getWeightKg() != null ? exportPackingListDetailsDTO.getWeightKg() : BigDecimal.ZERO);
		}

		exportPackingListVO.setExportPackingListDetailsVO(exportPackingListDetailsVOs);

		// Set the total quantities and weights in the main VO
		exportPackingListVO.setTotalQuantity(totalQuantitySum);
		exportPackingListVO.setTotalGrossWeight(totalWeightSum);

		List<ExportPackingShippingListVO> exportPackingShippingListVOs = new ArrayList<ExportPackingShippingListVO>();
		for (ExportPackingShippingListDTO exportPackingShippingListDTO : exportPackingListDTO
				.getExportPackingShippingListDTO()) {

			ExportPackingShippingListVO exportPackingShippingListVO = new ExportPackingShippingListVO();

			exportPackingShippingListVO.setPrecarriage(exportPackingShippingListDTO.getPrecarriage());
			exportPackingShippingListVO.setPlcOfRecpByPreCarr(exportPackingShippingListDTO.getPlcOfRecpByPreCarr());
			exportPackingShippingListVO.setVesselNo(exportPackingShippingListDTO.getVesselNo());
			exportPackingShippingListVO.setPortOfLoading(exportPackingShippingListDTO.getPortOfLoading());
			exportPackingShippingListVO.setPortOfUnloading(exportPackingShippingListDTO.getPortOfUnloading());
			exportPackingShippingListVO.setPlaceOfDelivery(exportPackingShippingListDTO.getPlaceOfDelivery());
			exportPackingShippingListVO.setContainerNo(exportPackingShippingListDTO.getContainerNo());
			exportPackingShippingListVO.setNoOfPackage(exportPackingShippingListDTO.getNoOfPackage());

			exportPackingShippingListVO.setExportPackingListVO(exportPackingListVO);
			exportPackingShippingListVOs.add(exportPackingShippingListVO);
		}
		exportPackingListVO.setExportPackingShippingListVO(exportPackingShippingListVOs);

		List<ExportPackingListTermsVO> exportPackingListTermsVOs = new ArrayList<ExportPackingListTermsVO>();
		for (ExportPackingListTermsDTO exportPackingListTermsDTO : exportPackingListDTO
				.getExportPackingListTermsDTO()) {

			ExportPackingListTermsVO exportPackingListTermsVO = new ExportPackingListTermsVO();

			exportPackingListTermsVO.setTerm(exportPackingListTermsDTO.getTerm());
			exportPackingListTermsVO.setDescription(exportPackingListTermsDTO.getDescription());

			exportPackingListTermsVO.setExportPackingListVO(exportPackingListVO);
			exportPackingListTermsVOs.add(exportPackingListTermsVO);
		}
		exportPackingListVO.setExportPackingListTermsVO(exportPackingListTermsVOs);

		return exportPackingListVO;
	}

	@Override
	public List<Map<String, Object>> getCustomerNameAndCodeForExportPackingList(Long orgId) {
		Set<Object[]> customerDetails = exportPackingListRepo.findCustomerNameAndCodeForExportPackingList(orgId);
		return getCustomerNameAndCodeForExportPackingList(customerDetails);
	}

	private List<Map<String, Object>> getCustomerNameAndCodeForExportPackingList(Set<Object[]> customerDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : customerDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getCustomerDetailsForExportPackingList(Long orgId, String customerCode) {
		Set<Object[]> customerDetails = exportPackingListRepo.findCustomerDetailsForExportPackingList(orgId,
				customerCode);
		return getCustomerDetailsForExportPackingList(customerDetails);
	}

	private List<Map<String, Object>> getCustomerDetailsForExportPackingList(Set<Object[]> customerDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : customerDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerAddress", ch[0] != null ? ch[0].toString() : "");
			map.put("deliveryPlace", ch[1] != null ? ch[1].toString() : "");
			map.put("destinationCountry", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getAllCountryForExportPackingList(Long orgId) {
		Set<Object[]> countryName = exportPackingListRepo.findAllCountryForExportPackingList(orgId);
		return getAllCountryForExportPackingList(countryName);
	}

	private List<Map<String, Object>> getAllCountryForExportPackingList(Set<Object[]> countryName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : countryName) {
			Map<String, Object> map = new HashMap<>();
			map.put("country", ch[0] != null ? ch[0].toString() : "");
			map.put("countryCode", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSalesOrderNoForExportPackingList(Long orgId, String customerCode) {
		Set<Object[]> salesOrderNo = exportPackingListRepo.findSalesOrderNoForExportPackingList(orgId, customerCode);
		return getSalesOrderNoForExportPackingList(salesOrderNo);
	}

	private List<Map<String, Object>> getSalesOrderNoForExportPackingList(Set<Object[]> salesOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : salesOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesOrderNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSalesOrderDetailsForExportPackingList(
	        Long orgId, String salesOrderNo) {

	    List<String> salesOrderNos = Arrays.stream(salesOrderNo.split(","))
	            .map(String::trim)
	            .filter(s -> !s.isEmpty())
	            .collect(Collectors.toList());

	    Set<Object[]> salesOrderDtls =
	            exportPackingListRepo.findSalesOrderDetailsForExportPackingList(
	                    orgId,
	                    salesOrderNos
	            );

	    return mapSalesOrderDetails(salesOrderDtls);
	}


	private List<Map<String, Object>> mapSalesOrderDetails(Set<Object[]> salesOrderDtls) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : salesOrderDtls) {
			Map<String, Object> map = new HashMap<>();
			map.put("partName", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");
			map.put("unitPrice", ch[3] != null ? ch[3].toString() : "");
			map.put("qtyofferd", ch[4] != null ? ch[4].toString() : "");
			map.put("hsncode", ch[5] != null ? ch[5].toString() : "");
			map.put("salesOrderNo", ch[6] != null ? ch[6].toString() : "");
			map.put("workOrderNo", ch[7] != null ? ch[7].toString() : "");
			map.put("customerPoNo", ch[8] != null ? ch[8].toString() : "");

			list.add(map);
		}
		return list;
	}
	
	
	@Override
	public List<Map<String, Object>> getCustomerDetailsForExportPackingListReport(
	        Long orgId, String CustomerCode) {


	    Set<Object[]> customerDts =
	            exportPackingListRepo.getCustomerDetailsForExportPackingListReport(
	                    orgId,
	                    CustomerCode
	            );

	    return getCustomerDetailsForExportPackingListReport(customerDts);
	}


	private List<Map<String, Object>> getCustomerDetailsForExportPackingListReport(Set<Object[]> customerDts) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : customerDts) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyName", ch[0] != null ? ch[0].toString() : "");
			map.put("partyCode", ch[1] != null ? ch[1].toString() : "");
			map.put("gstIn", ch[2] != null ? ch[2].toString() : "");
			map.put("billingAddress", ch[3] != null ? ch[3].toString() : "");
			map.put("billingCity", ch[4] != null ? ch[4].toString() : "");
			map.put("billingState", ch[5] != null ? ch[5].toString() : "");
			map.put("billingPincode", ch[6] != null ? ch[6].toString() : "");
			map.put("shippingAddress", ch[7] != null ? ch[7].toString() : "");
			map.put("shippingCity", ch[8] != null ? ch[8].toString() : "");
			map.put("shippingState", ch[9] != null ? ch[9].toString() : "");
			map.put("shippingPincode", ch[10] != null ? ch[10].toString() : "");
			map.put("country", ch[11] != null ? ch[11].toString() : "");

			list.add(map);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getItemDetailsFromExportPackingListReport(Long orgId, String salesOrderNo,String exportPackingListDocid) {

	

	    // 2️⃣ Call repo ONCE with IN clause
	    Set<Object[]> chType = exportPackingListRepo
	            .getItemDetailsFromExportPackingListReport(orgId, salesOrderNo,exportPackingListDocid);

	    return getItemDetailsFromExportPackingListReport(chType);
	}
	private List<Map<String, Object>> getItemDetailsFromExportPackingListReport(Set<Object[]> chType) {
	    List<Map<String, Object>> list = new ArrayList<>();
	    for (Object[] ch : chType) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("article", ch[0] != null ? ch[0].toString() : "");
	        map.put("itemName", ch[1] != null ? ch[1].toString() : "");
	        map.put("itemDesc", ch[2] != null ? ch[2].toString() : "");
	        map.put("htsCode", ch[3] != null ? ch[3].toString() : "");
	        map.put("primaryUnit", ch[4] != null ? ch[4].toString() : "");
	        map.put("qtyOffered", ch[5] != null ? ch[5].toString() : "");
	        map.put("weight", ch[6] != null ? ch[6].toString() : "");

	        list.add(map);
	    }
	    return list;
	}
	
//	@Override
//	public List<Map<String, Object>> getExportPackingListReport(Long orgId, String fromdate, String todate,String customername,String salesorderno) {
//		Set<Object[]> exportpackingReport = exportPackingListRepo.getExportPackingListReport(orgId, fromdate, todate,customername,salesorderno);
//		return getExportPackingListReport(exportpackingReport);
//	}
//
//	private List<Map<String, Object>> getExportPackingListReport(Set<Object[]> exportpackingReport) {
//		List<Map<String, Object>> List1 = new ArrayList<>();
//		for (Object[] ch : exportpackingReport) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("exportpakinglistid", ch[0] != null ? ch[0].toString() : "");
//			map.put("docid", ch[1] != null ? ch[1].toString() : "");
//			map.put("orgid", ch[2] != null ? ch[2].toString() : "");
//			map.put("salesorderno", ch[3] != null ? ch[3].toString() : "");
//			map.put("customername", ch[4] != null ? ch[4].toString() : "");
//			map.put("customerpoitem", ch[5] != null ? ch[5].toString() : "");
//			map.put("custpo", ch[6] != null ? ch[6].toString() : "");
//			map.put("partno", ch[7] != null ? ch[7].toString() : "");
//			map.put("partdesc", ch[8] != null ? ch[8].toString() : "");
//			map.put("poquantity", ch[9] != null ? ch[9].toString() : "");
//			map.put("quantity", ch[10] != null ? ch[10].toString() : "");
//
//			List1.add(map);
//		}
//
//		return List1;
//	}
	
	@Override
	public List<Map<String, Object>> getExportPackingListReport(
	        Long orgId, 
	        String fromdate,    
	        String todate,
	        String customername,
	        String salesorderno) {
	    
	    List<Object[]> exportpackingReport = exportPackingListRepo.getExportPackingListReport(
	        orgId, fromdate, todate, customername, salesorderno);
	    
	    return mapExportPackingListReport(exportpackingReport);
	}

	private List<Map<String, Object>> mapExportPackingListReport(List<Object[]> exportpackingReport) {
	    List<Map<String, Object>> reportList = new ArrayList<>();
	    
	    for (Object[] row : exportpackingReport) {
	        Map<String, Object> map = new HashMap<>();
	        
	        map.put("exportpackinglistid", row[0] != null ? row[0].toString() : "");
	        map.put("docid", row[1] != null ? row[1].toString() : "");
	        map.put("orgid", row[2] != null ? row[2].toString() : "");
	        map.put("salesorderno", row[3] != null ? row[3].toString() : "");
	        map.put("customername", row[4] != null ? row[4].toString() : "");
	        map.put("customerpoitem", row[5] != null ? row[5].toString() : "");
	        map.put("custpo", row[6] != null ? row[6].toString() : "");
	        map.put("partno", row[7] != null ? row[7].toString() : "");
	        map.put("partdesc", row[8] != null ? row[8].toString() : "");
	        map.put("poquantity", row[9] != null ? row[9].toString() : "");
	        map.put("quantity", row[10] != null ? row[10].toString() : "");
	        
	        reportList.add(map);
	    }
	    
	    return reportList;
	}

}
