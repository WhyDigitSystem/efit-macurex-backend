package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ItemIssueToProductionDTO;
import com.efitops.basesetup.dto.ItemIssueToProductionDetailsDTO;
import com.efitops.basesetup.dto.PickListDTO;
import com.efitops.basesetup.dto.PickListDetailsDTO;
import com.efitops.basesetup.dto.PutawayDTO;
import com.efitops.basesetup.dto.PutawayDetailsDTO;
import com.efitops.basesetup.dto.RouteCardClosureDTO;
import com.efitops.basesetup.dto.RouteCardEngDeptDTO;
import com.efitops.basesetup.dto.RouteCardEntryDTO;
import com.efitops.basesetup.dto.RouteCardEntryDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemIssueToProductionDetailsVO;
import com.efitops.basesetup.entity.ItemIssueToProductionVO;
import com.efitops.basesetup.entity.PickListDetailsVO;
import com.efitops.basesetup.entity.PickListVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.entity.PutawayDetailsVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.RackStockDetailsVO;
import com.efitops.basesetup.entity.RouteCardClosureVO;
import com.efitops.basesetup.entity.RouteCardEngDeptVO;
import com.efitops.basesetup.entity.RouteCardEntryAttachmentVO;
import com.efitops.basesetup.entity.RouteCardEntryDetailsVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.entity.StockDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ItemIssueToProductionDetailsRepo;
import com.efitops.basesetup.repo.ItemIssueToProductionRepo;
import com.efitops.basesetup.repo.PickListDetailsRepo;
import com.efitops.basesetup.repo.PickListRepo;
import com.efitops.basesetup.repo.PutawayDetailsRepo;
import com.efitops.basesetup.repo.PutawayRepo;
import com.efitops.basesetup.repo.RackStockDetailsRepo;
import com.efitops.basesetup.repo.RouteCardClosureRepo;
import com.efitops.basesetup.repo.RouteCardEngDeptRepo;
import com.efitops.basesetup.repo.RouteCardEntryAttachmentRepo;
import com.efitops.basesetup.repo.RouteCardEntryDetailsRepo;
import com.efitops.basesetup.repo.RouteCardEntryRepo;
import com.efitops.basesetup.repo.StockDetailsRepo;

@Service
public class InventoryServiceImpl implements InventoryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	PutawayRepo putawayRepo;

	@Autowired
	PutawayDetailsRepo putawayDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	RouteCardEntryRepo routeCardEntryRepo;

	@Autowired
	RouteCardClosureRepo routeCardClosureRepo;

	@Autowired
	RouteCardEngDeptRepo routeCardEngDeptRepo;

	@Autowired
	RouteCardEntryDetailsRepo routeCardEntryDetailsRepo;

	@Autowired
	PickListRepo pickListRepo;

	@Autowired
	PickListDetailsRepo pickListDetailsRepo;

	@Autowired
	ItemIssueToProductionRepo itemIssueToProductionRepo;

	@Autowired
	ItemIssueToProductionDetailsRepo itemIssueToProductionDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	RackStockDetailsRepo rackStockDetailsRepo;

	@Autowired
	RouteCardEntryAttachmentRepo routeCardEntryAttachmentRepo;

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	// Putaway

	@Override
	public List<PutawayVO> getPutawayById(Long id) {
		List<PutawayVO> putawayVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Item BY Id : {}", id);
			putawayVO = putawayRepo.findPutawayById(id);
		}
		return putawayVO;
	}

	@Override
	public List<PutawayVO> getPutawayByOrgId(Long orgId, String finYear, String branchCode) {
		List<PutawayVO> putawayVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			putawayVO = putawayRepo.findPutawayByOrgId(orgId, finYear, branchCode);
		}
		return putawayVO;
	}

	@Override
	public Map<String, Object> updateCreatePutaway(@Valid PutawayDTO putawayDTO) throws ApplicationException {
		String message;
		String screenCode = "PUT";
		PutawayVO oldPutaway = null;

		PutawayVO putawayVO = new PutawayVO();

		if (putawayDTO.getId() != null) {

			oldPutaway = putawayRepo.findById(putawayDTO.getId())
					.orElseThrow(() -> new ApplicationException("Putaway not found"));

			oldPutaway.getPutawayDetailsVO().size(); // load

			entityManager.detach(oldPutaway); // detach snapshot

			putawayVO = putawayRepo.findById(putawayDTO.getId())
					.orElseThrow(() -> new ApplicationException("Putaway not found"));
			putawayVO.setUpdatedBy(putawayDTO.getCreatedBy());
			createUpdatePutawayVOByPutawayDTO(putawayDTO, putawayVO);
			message = "Putaway Updated Successfully";

			List<PutawayDetailsVO> putawayDetailsVOs = putawayDetailsRepo.findByPutawayVO(putawayVO);
			putawayDetailsRepo.deleteAll(putawayDetailsVOs);

		} else {

			// ---------- GET DOC ID ----------
			String docId = putawayRepo.getPutawayDocId(putawayDTO.getOrgId(), putawayDTO.getFinYear(),
					putawayDTO.getBranchCode(), screenCode);
			putawayVO.setDocId(docId);

			// ---------- UPDATE LAST NO ----------
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(putawayDTO.getOrgId(), putawayDTO.getFinYear(),
							putawayDTO.getBranchCode(), screenCode);

			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// ---------- SAVE PUTAWAY ----------
			putawayVO.setCreatedBy(putawayDTO.getCreatedBy());
			putawayVO.setUpdatedBy(putawayDTO.getCreatedBy());
			createUpdatePutawayVOByPutawayDTO(putawayDTO, putawayVO);

			PutawayVO savedPutawayVO = putawayRepo.save(putawayVO);

			message = "Putaway Created Successfully";

			// ---------- GROUP BY ITEM ----------
			Map<String, List<PutawayDetailsVO>> groupedByItem = savedPutawayVO.getPutawayDetailsVO().stream()
					.collect(Collectors.groupingBy(d -> d.getItem() + "||" + d.getItemDesc()));

			// ---------- PROCESS EACH ITEM ----------
			for (Map.Entry<String, List<PutawayDetailsVO>> entry : groupedByItem.entrySet()) {

				List<PutawayDetailsVO> itemDetails = entry.getValue();

				PutawayDetailsVO first = itemDetails.get(0);

				// ✅ ITEM-WISE SUM (THIS IS THE FIX)
				BigDecimal itemPutawayQty = itemDetails.stream().map(PutawayDetailsVO::getPutawayQty)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

				/* ================= STOCK MINUS (INB) ================= */
				StockDetailsVO stockMinus = new StockDetailsVO();
				stockMinus.setOrgId(savedPutawayVO.getOrgId());
				stockMinus.setStockDate(savedPutawayVO.getGrnDate());
				stockMinus.setDocId(savedPutawayVO.getDocId());
				stockMinus.setDocDate(savedPutawayVO.getDocDate());
				stockMinus.setRefDate(savedPutawayVO.getDocDate());
				stockMinus.setRefNo(savedPutawayVO.getId());
				stockMinus.setSourceId(savedPutawayVO.getId());
				stockMinus.setSourceScreenCode(savedPutawayVO.getScreenCode());
				stockMinus.setSourceScreenName(savedPutawayVO.getScreenName());
				stockMinus.setPlusOrMinus("m");

				stockMinus.setQty(itemPutawayQty.negate());

				stockMinus.setLocation(savedPutawayVO.getFromLocation());
				stockMinus.setPartyName(savedPutawayVO.getSupplier());
				stockMinus.setPartType(savedPutawayVO.getGoodsType());
				stockMinus.setPartno(first.getItem());
				stockMinus.setPartDesc(first.getItemDesc());
				stockMinus.setStatus(savedPutawayVO.getStatus());
				stockMinus.setCreatedBy(savedPutawayVO.getCreatedBy());
				stockMinus.setUpdatedBy(savedPutawayVO.getUpdatedBy());
				stockMinus.setBranch(savedPutawayVO.getBranch());
				stockMinus.setFinYear(savedPutawayVO.getFinYear());
				stockMinus.setBranchCode(savedPutawayVO.getBranchCode());
				stockMinus.setActive(true);
				stockMinus.setCancel(false);

				stockDetailsRepo.save(stockMinus);

				// ---------- STOCK PLUS (STORE) ----------
				StockDetailsVO stockPlus = new StockDetailsVO();
				stockPlus.setOrgId(savedPutawayVO.getOrgId());
				stockPlus.setStockDate(savedPutawayVO.getGrnDate());
				stockPlus.setDocId(savedPutawayVO.getDocId());
				stockPlus.setDocDate(savedPutawayVO.getDocDate());
				stockPlus.setRefDate(savedPutawayVO.getDocDate());
				stockPlus.setRefNo(savedPutawayVO.getId());
				stockPlus.setSourceId(savedPutawayVO.getId());
				stockPlus.setSourceScreenCode(savedPutawayVO.getScreenCode());
				stockPlus.setSourceScreenName(savedPutawayVO.getScreenName());
				stockPlus.setPlusOrMinus("p");
				stockPlus.setQty(itemPutawayQty);
				stockPlus.setPartType(savedPutawayVO.getGoodsType());
				stockPlus.setLocation(savedPutawayVO.getToLocation());
				stockPlus.setPartyName(savedPutawayVO.getSupplier());
				stockPlus.setPartno(first.getItem());
				stockPlus.setPartDesc(first.getItemDesc());
				stockPlus.setStatus(savedPutawayVO.getStatus());
				stockPlus.setCreatedBy(savedPutawayVO.getCreatedBy());
				stockPlus.setUpdatedBy(savedPutawayVO.getUpdatedBy());
				stockPlus.setBranch(savedPutawayVO.getBranch());
				stockPlus.setFinYear(savedPutawayVO.getFinYear());
				stockPlus.setBranchCode(savedPutawayVO.getBranchCode());
				stockPlus.setActive(true);
				stockPlus.setCancel(false);

				stockDetailsRepo.save(stockPlus);

				// ---------- RACK STOCK (MULTIPLE ROWS) ----------
				for (PutawayDetailsVO detail : entry.getValue()) {

					RackStockDetailsVO rackStock = new RackStockDetailsVO();
					rackStock.setOrgId(savedPutawayVO.getOrgId());
					rackStock.setStockDate(savedPutawayVO.getDocDate());
					rackStock.setDocId(savedPutawayVO.getDocId());
					rackStock.setDocDate(savedPutawayVO.getDocDate());
					rackStock.setRefDate(savedPutawayVO.getDocDate());
					rackStock.setRefNo(savedPutawayVO.getId());
					rackStock.setSourceId(savedPutawayVO.getId());
					rackStock.setFinYear(savedPutawayVO.getFinYear());
					rackStock.setSourceScreenCode(savedPutawayVO.getScreenCode());
					rackStock.setSourceScreenName(savedPutawayVO.getScreenName());
					rackStock.setPlusOrMinus("p");

					// 🔑 EACH RACK SAVED SEPARATELY
					rackStock.setQty(detail.getPutawayQty());
					rackStock.setRackNo(detail.getRackNo());

					rackStock.setLocation(savedPutawayVO.getToLocation());
					rackStock.setPartno(detail.getItem());
					rackStock.setPartDesc(detail.getItemDesc());
					rackStock.setStatus(savedPutawayVO.getStatus());
					rackStock.setCreatedBy(savedPutawayVO.getCreatedBy());
					rackStock.setUpdatedBy(savedPutawayVO.getUpdatedBy());
					rackStock.setBranch(savedPutawayVO.getBranch());
					rackStock.setPartType(savedPutawayVO.getGoodsType());
					rackStock.setBranchCode(savedPutawayVO.getBranchCode());
					rackStock.setActive(true);
					rackStock.setCancel(false);
					rackStockDetailsRepo.save(rackStock);
				}

			}
		}
		// ---------- RESPONSE ----------

		commonNotificationService.generateNotification(putawayVO.getScreenCode(), putawayVO.getId(), oldPutaway,
				putawayVO);
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("putawayVO", putawayVO);
		return response;

	}

	private void createUpdatePutawayVOByPutawayDTO(@Valid PutawayDTO putawayDTO, PutawayVO putawayVO)
			throws ApplicationException {
		putawayVO.setGrnNo(putawayDTO.getGrnNo());
		putawayVO.setGrnDate(putawayDTO.getGrnDate());
		putawayVO.setSupplier(putawayDTO.getSupplier());
		putawayVO.setVehicleNo(putawayDTO.getVehicleNo());
		putawayVO.setFromLocation(putawayDTO.getFromLocation());
		putawayVO.setToLocation(putawayDTO.getToLocation());
		putawayVO.setGoodsType(putawayDTO.getGoodsType());
		putawayVO.setDcNo(putawayDTO.getDcNo());
		putawayVO.setNarration(putawayDTO.getNarration());
		putawayVO.setOrgId(putawayDTO.getOrgId());
		putawayVO.setBranch(putawayDTO.getBranch());
		putawayVO.setBranchCode(putawayDTO.getBranchCode());
		putawayVO.setFinYear(putawayDTO.getFinYear());
		putawayVO.setStatus(putawayDTO.getStatus());

		BigDecimal totalPutawayQty = BigDecimal.ZERO;
	    BigDecimal sumPutawayQty = BigDecimal.ZERO;
	    BigDecimal qty = BigDecimal.ZERO;

		List<PutawayDetailsVO> putawayDetailsVOs = new ArrayList<>();
		for (PutawayDetailsDTO putawayDetailsDTO : putawayDTO.getPutawayDetailsDTO()) {
			PutawayDetailsVO putawayDetailsVO = new PutawayDetailsVO();
			putawayDetailsVO.setItem(putawayDetailsDTO.getItem());
			putawayDetailsVO.setItemDesc(putawayDetailsDTO.getItemDesc());
			putawayDetailsVO.setUnit(putawayDetailsDTO.getUnit());
			qty= putawayDetailsDTO.getRecQty();
			putawayDetailsVO.setRecQty(putawayDetailsDTO.getRecQty());
			if (putawayDetailsDTO.getPutawayQty() == null
					|| putawayDetailsDTO.getPutawayQty().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Qty must be greater than zero.");
			}
	        sumPutawayQty = sumPutawayQty.add(putawayDetailsDTO.getPutawayQty());
			putawayDetailsVO.setPutawayQty(putawayDetailsDTO.getPutawayQty());
			putawayDetailsVO.setRackNo(putawayDetailsDTO.getRackNo());
			totalPutawayQty = totalPutawayQty.add(putawayDetailsVO.getPutawayQty());
			putawayDetailsVO.setPutawayVO(putawayVO);
			putawayDetailsVOs.add(putawayDetailsVO);
		}
		
		if (sumPutawayQty.compareTo(qty) > 0) {
			throw new ApplicationException("PutawayQty must be Lesser than Qty.");
		}
		putawayVO.setTotalPutawayQty(totalPutawayQty);
		putawayVO.setPutawayDetailsVO(putawayDetailsVOs);

	}

	@Override
	public String getPutawayDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PUT";
		String result = putawayRepo.getPutawayDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getGrnDetailsForPutaway(Long orgId) {

		Set<Object[]> result = putawayRepo.findGrnDetailsForPutaway(orgId);
		return getGrnDetailsForPutaway(result);
	}

	private List<Map<String, Object>> getGrnDetailsForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");
			part.put("supplierName", fs[2] != null ? fs[2].toString() : "");
			part.put("invDcNo", fs[3] != null ? fs[3].toString() : "");
			part.put("invoiceNo", fs[4] != null ? fs[4].toString() : "");
			part.put("vehicleNo", fs[5] != null ? fs[5].toString() : "");
//			part.put("id",fs[6]!=null ? Integer.parseInt(fs[6].toString()):0);

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getLocationCodeForPutaway(Long orgId) {

		Set<Object[]> result = putawayRepo.findLocationCodeForPutaway(orgId);
		return getLocationCodeForPutaway(result);
	}

	private List<Map<String, Object>> getLocationCodeForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("locationCode", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getRackNoForPutaway(Long orgId) {

		Set<Object[]> result = putawayRepo.findRackNoForPutaway(orgId);
		return getRackNoForPutaway(result);
	}

	private List<Map<String, Object>> getRackNoForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("rackNo", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getRackDetails(Long orgId, String item) {
		Set<Object[]> result = putawayRepo.getRackDetails(orgId, item);
		return getRackDetails(result);
	}

	private List<Map<String, Object>> getRackDetails(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("rackNo", fs[0] != null ? fs[0].toString() : "");
			part.put("putawayQty", fs[1] != null ? fs[1].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getFillGridForPutaway(Long orgId, String grnNo) {

		Set<Object[]> result = putawayRepo.findFillGridForPutaway(orgId, grnNo);
		return getFillGridForPutaway(result);
	}

	private List<Map<String, Object>> getFillGridForPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("item", fs[0] != null ? fs[0].toString() : "");
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("primaryUnit", fs[2] != null ? fs[2].toString() : "");
			part.put("acceptqty", fs[3] != null ? fs[3].toString() : "");
			part.put("putawayqty", fs[4] != null ? fs[4].toString() : "");
			part.put("remainingqty", fs[5] != null ? fs[5].toString() : "");
//			part.put("id", fs[4] != null ? Integer.parseInt(fs[4].toString()) : 0);

			details1.add(part);
		}
		return details1;
	}

	// Routecardentry

	@Override
	public List<RouteCardEntryVO> getRouteCardEntryById(Long id) {
		List<RouteCardEntryVO> routeCardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received RouteCardEntry BY Id : {}", id);
			routeCardEntryVO = routeCardEntryRepo.findRouteCardEntryById(id);
		}
		return routeCardEntryVO;
	}

	@Override
	public List<RouteCardEntryVO> getRouteCardEntryByOrgId(Long orgId, String finYear, String branchCode) {
		List<RouteCardEntryVO> routeCardEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received RouteCardEntry BY OrgId : {}", orgId);
			routeCardEntryVO = routeCardEntryRepo.findRouteCardEntryByOrgId(orgId, finYear, branchCode);
		}
		return routeCardEntryVO;
	}

	@Override
	public Map<String, Object> updateCreateRouteCardEntry(@Valid RouteCardEntryDTO routeCardEntryDTO)
			throws ApplicationException {
		String message;
		String screenCode = "RCE";
		RouteCardEntryVO oldRouteCardEntry = null;
		
		RouteCardEntryVO routeCardEntryVO = new RouteCardEntryVO();

		if (routeCardEntryDTO.getId() != null) {
			oldRouteCardEntry = routeCardEntryRepo.findById(routeCardEntryDTO.getId())
		            .orElseThrow(() -> new ApplicationException("RouteCardEntry not found"));

			oldRouteCardEntry.getRouteCardEntryDetailsVO().size(); // load
			oldRouteCardEntry.getRouteCardEngDeptVO().size(); // load
			oldRouteCardEntry.getRouteCardClosureVO().size(); // load
			
			entityManager.detach(oldRouteCardEntry); // detach snapshot
			
			routeCardEntryVO = routeCardEntryRepo.findById(routeCardEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("RouteCardEntry not found"));
			routeCardEntryVO.setUpdatedBy(routeCardEntryDTO.getCreatedBy());
			createUpdateRouteCardEntryVOByRouteCardEntryDTO(routeCardEntryDTO, routeCardEntryVO);
			message = "RouteCardEntry Updated Successfully";

			List<RouteCardEntryDetailsVO> routeCardEntryDetailsVOs = routeCardEntryDetailsRepo
					.findByRouteCardEntryVO(routeCardEntryVO);
			routeCardEntryDetailsRepo.deleteAll(routeCardEntryDetailsVOs);

			List<RouteCardEngDeptVO> routeCardEngDeptVOs = routeCardEngDeptRepo
					.findByRouteCardEntryVO(routeCardEntryVO);
			routeCardEngDeptRepo.deleteAll(routeCardEngDeptVOs);

			List<RouteCardClosureVO> routeCardClosureVOs = routeCardClosureRepo
					.findByRouteCardEntryVO(routeCardEntryVO);
			routeCardClosureRepo.deleteAll(routeCardClosureVOs);

		} else {

			// GETDOCID API
			String docId = routeCardEntryRepo.getRouteCardEntryDocId(routeCardEntryDTO.getOrgId(),
					routeCardEntryDTO.getFinYear(), routeCardEntryDTO.getBranchCode(), screenCode);
			routeCardEntryVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(routeCardEntryDTO.getOrgId(),
							routeCardEntryDTO.getFinYear(), routeCardEntryDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			routeCardEntryVO.setCreatedBy(routeCardEntryDTO.getCreatedBy());
			routeCardEntryVO.setUpdatedBy(routeCardEntryDTO.getCreatedBy());
			createUpdateRouteCardEntryVOByRouteCardEntryDTO(routeCardEntryDTO, routeCardEntryVO);
			message = "RouteCardEntry Created Successfully";
		}

		routeCardEntryRepo.save(routeCardEntryVO);
		
		commonNotificationService.generateNotification(routeCardEntryVO.getScreenCode(), routeCardEntryVO.getId(), oldRouteCardEntry,
				routeCardEntryVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("routeCardEntryVO", routeCardEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateRouteCardEntryVOByRouteCardEntryDTO(@Valid RouteCardEntryDTO routeCardEntryDTO,
			RouteCardEntryVO routeCardEntryVO) {
		routeCardEntryVO.setCustomerName(routeCardEntryDTO.getCustomerName());
		routeCardEntryVO.setCustomerCode(routeCardEntryDTO.getCustomerCode());
		
		routeCardEntryVO.setWoNo(routeCardEntryDTO.getWoNo());
		routeCardEntryVO.setFgPartName(routeCardEntryDTO.getFgPartName());
		routeCardEntryVO.setFgPartDesc(routeCardEntryDTO.getFgPartDesc());
		routeCardEntryVO.setFgQty(routeCardEntryDTO.getFgQty());
		routeCardEntryVO.setBatchQty(routeCardEntryDTO.getBatchQty());
		routeCardEntryVO.setRmType(routeCardEntryDTO.getRmType());
		routeCardEntryVO.setRmSize(routeCardEntryDTO.getRmSize());
		routeCardEntryVO.setRmbatchNo(routeCardEntryDTO.getRmbatchNo());
		routeCardEntryVO.setRmQty(routeCardEntryDTO.getRmQty());
		routeCardEntryVO.setNarration(routeCardEntryDTO.getNarration());
		routeCardEntryVO.setOrgId(routeCardEntryDTO.getOrgId());
		routeCardEntryVO.setBranch(routeCardEntryDTO.getBranch());
		routeCardEntryVO.setBranchCode(routeCardEntryDTO.getBranchCode());
		routeCardEntryVO.setFinYear(routeCardEntryDTO.getFinYear());
		routeCardEntryVO.setInvoice(routeCardEntryDTO.getInvoice());
		routeCardEntryVO.setInvoiceDate(routeCardEntryDTO.getInvoiceDate());
		routeCardEntryVO.setQty(routeCardEntryDTO.getQty());
		routeCardEntryVO.setStockQty(routeCardEntryDTO.getStockQty());
		routeCardEntryVO.setStatus(routeCardEntryDTO.getStatus());

		// RouteCardEntryDetails
		List<RouteCardEntryDetailsVO> routeCardEntryDetailsVOs = new ArrayList<>();
		for (RouteCardEntryDetailsDTO routeCardEntryDetailsDTO : routeCardEntryDTO.getRouteCardEntryDetailsDTO()) {
			RouteCardEntryDetailsVO routeCardEntryDetailsVO = new RouteCardEntryDetailsVO();
			routeCardEntryDetailsVO.setOperationDesc(routeCardEntryDetailsDTO.getOperationDesc());
			routeCardEntryDetailsVO.setMachineCenter(routeCardEntryDetailsDTO.getMachineCenter());
			routeCardEntryDetailsVO.setProcessStart(routeCardEntryDetailsDTO.getProcessStart());
			routeCardEntryDetailsVO.setProcessEnd(routeCardEntryDetailsDTO.getProcessEnd());
			routeCardEntryDetailsVO.setAcceptedQty(routeCardEntryDetailsDTO.getAcceptedQty());
			routeCardEntryDetailsVO.setQtyRework(routeCardEntryDetailsDTO.getQtyRework());
			routeCardEntryDetailsVO.setReject(routeCardEntryDetailsDTO.getReject());
			routeCardEntryDetailsVO.setOptr(routeCardEntryDetailsDTO.getOptr());
			routeCardEntryDetailsVO.setRemarks(routeCardEntryDetailsDTO.getRemarks());

			routeCardEntryDetailsVO.setRouteCardEntryVO(routeCardEntryVO); // Set the reference in child entity
			routeCardEntryDetailsVOs.add(routeCardEntryDetailsVO);
		}
		routeCardEntryVO.setRouteCardEntryDetailsVO(routeCardEntryDetailsVOs);

		// RouteCardEntryClosure
		List<RouteCardClosureVO> routeCardClosureVOs = new ArrayList<>();
		for (RouteCardClosureDTO routeCardClosureDTO : routeCardEntryDTO.getRouteCardClosureDTO()) {
			RouteCardClosureVO routeCardClosureVO = new RouteCardClosureVO();
			routeCardClosureVO.setQaManagerSign(routeCardClosureDTO.getQaManagerSign());
			routeCardClosureVO.setQaManagerSignDate(routeCardClosureDTO.getQaManagerSignDate());
			routeCardClosureVO.setPlantManagerSign(routeCardClosureDTO.getPlantManagerSign());
			routeCardClosureVO.setPlantManagerSignDate(routeCardClosureDTO.getPlantManagerSignDate());

			routeCardClosureVO.setRouteCardEntryVO(routeCardEntryVO); // Set the reference in child entity
			routeCardClosureVOs.add(routeCardClosureVO);
		}
		routeCardEntryVO.setRouteCardClosureVO(routeCardClosureVOs);

		// RouteCardEngDept
		List<RouteCardEngDeptVO> routeCardEngDeptVOs = new ArrayList<>();
		for (RouteCardEngDeptDTO routeCardEngDeptDTO : routeCardEntryDTO.getRouteCardEngDeptDTO()) {
			RouteCardEngDeptVO routeCardEngDeptVO = new RouteCardEngDeptVO();
			routeCardEngDeptVO.setPreparedBy(routeCardEngDeptDTO.getPreparedBy());
			routeCardEngDeptVO.setPreparedDate(routeCardEngDeptDTO.getPreparedDate());
			routeCardEngDeptVO.setApprovedBy(routeCardEngDeptDTO.getApprovedBy());
			routeCardEngDeptVO.setApprovedDate(routeCardEngDeptDTO.getApprovedDate());

			routeCardEngDeptVO.setRouteCardEntryVO(routeCardEntryVO); // Set the reference in child entity
			routeCardEngDeptVOs.add(routeCardEngDeptVO);
		}
		routeCardEntryVO.setRouteCardEngDeptVO(routeCardEngDeptVOs);

	}

	@Override
	public String getRouteCardEntryDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "RCE";
		String result = routeCardEntryRepo.getRouteCardEntryDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getCustomerNameAndCodeFromRouteCardEntry(Long orgId) {
		Set<Object[]> customerDetails = routeCardEntryRepo.findCustomerNameAndCodeFromRouteCardEntry(orgId);
		return getCustomerNameAndCodeFromRouteCardEntry(customerDetails);
	}

	private List<Map<String, Object>> getCustomerNameAndCodeFromRouteCardEntry(Set<Object[]> customerDetails) {
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
	public List<Map<String, Object>> getOptrSignFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findOptrSignFromRouteCardEntry(orgId);
		return getOptrSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getOptrSignFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("optrSign", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPreparedByFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findPreparedByFromRouteCardEntry(orgId);
		return findPreparedByFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> findPreparedByFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("preparedBy", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getApprovedByFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findApprovedByFromRouteCardEntry(orgId);
		return getApprovedByFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getApprovedByFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("approvedBy", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getQAManagerSignFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findQAManagerSignFromRouteCardEntry(orgId);
		return getQAManagerSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getQAManagerSignFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("qaManagerSign", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPlantManagerSignFromRouteCardEntry(Long orgId) {
		Set<Object[]> employeeName = routeCardEntryRepo.findPlantManagerSignFromRouteCardEntry(orgId);
		return getPlantManagerSignFromRouteCardEntry(employeeName);
	}

	private List<Map<String, Object>> getPlantManagerSignFromRouteCardEntry(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("plantManagerSign", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderNoFromRouteCardEntry(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = routeCardEntryRepo.findWorkOrderNoFromRouteCardEntry(orgId, customerCode);
		return getWorkOrderNoFromRouteCardEntry(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoFromRouteCardEntry(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getFgPartNameAndDescAndQtyFromRouteCardEntry(Long orgId, String workOrderNo) {
		Set<Object[]> fgDetails = routeCardEntryRepo.findFgPartNameAndDescAndQtyFromRouteCardEntry(orgId, workOrderNo);
		return getFgPartNameAndDescAndQtyFromRouteCardEntry(fgDetails);
	}

	private List<Map<String, Object>> getFgPartNameAndDescAndQtyFromRouteCardEntry(Set<Object[]> fgDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : fgDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("fgPartName", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPartDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("fgQt", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public RouteCardEntryVO uploadFileForRouteCardEntry(MultipartFile file, Long id) throws IOException {
		RouteCardEntryVO routeCardEntryVO = routeCardEntryRepo.findById(id).get();
		routeCardEntryVO.setAttachements(file.getBytes());
		return routeCardEntryRepo.save(routeCardEntryVO);
	}

	// PickList

	@Override
	public List<PickListVO> getPickListById(Long id) {
		List<PickListVO> pickListVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received PickList BY Id : {}", id);
			pickListVO = pickListRepo.findPickListById(id);
		}
		return pickListVO;
	}

	@Override
	public List<PickListVO> getPickListByOrgId(Long orgId, String finYear, String branchCode) {
		List<PickListVO> pickListVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received PickList BY OrgId : {}", orgId);
			pickListVO = pickListRepo.findPickListByOrgId(orgId, finYear, branchCode);
		}
		return pickListVO;
	}

	@Override
	public Map<String, Object> updateCreatePickList(@Valid PickListDTO pickListDTO) throws ApplicationException {
		String message;
		String screenCode = "PL";
		PickListVO oldPickList = null;

		PickListVO pickListVO = new PickListVO();

		if (pickListDTO.getId() != null) {

			oldPickList = pickListRepo.findById(pickListDTO.getId())
					.orElseThrow(() -> new ApplicationException("PickList not found"));

			oldPickList.getPickListDetailsVO().size(); // load

			entityManager.detach(oldPickList); // detach snapshot

			pickListVO = pickListRepo.findById(pickListDTO.getId())
					.orElseThrow(() -> new ApplicationException("PickList not found"));
			pickListVO.setUpdatedBy(pickListDTO.getCreatedBy());
			createUpdatePickListVOByPickListDTO(pickListDTO, pickListVO);
			message = "PickList Updated Successfully";

			List<PickListDetailsVO> pickListDetailsVOs = pickListDetailsRepo.findByPickListVO(pickListVO);
			pickListDetailsRepo.deleteAll(pickListDetailsVOs);

		} else {

			// GETDOCID API
			String docId = pickListRepo.getPickListDocId(pickListDTO.getOrgId(), pickListDTO.getFinYear(),
					pickListDTO.getBranchCode(), screenCode);
			pickListVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(pickListDTO.getOrgId(), pickListDTO.getFinYear(),
							pickListDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			pickListVO.setCreatedBy(pickListDTO.getCreatedBy());
			pickListVO.setUpdatedBy(pickListDTO.getCreatedBy());
			createUpdatePickListVOByPickListDTO(pickListDTO, pickListVO);
			message = "PickList Created Successfully";
		}

		PickListVO savedPicked = pickListRepo.save(pickListVO);

		commonNotificationService.generateNotification(pickListVO.getScreenCode(), pickListVO.getId(), oldPickList,
				pickListVO);
		for (PickListDetailsVO detail : savedPicked.getPickListDetailsVO()) {
			RackStockDetailsVO rackStock = new RackStockDetailsVO();
			rackStock.setOrgId(savedPicked.getOrgId());
			rackStock.setStockDate(savedPicked.getDocDate());
			rackStock.setDocId(savedPicked.getDocId());
			rackStock.setDocDate(savedPicked.getDocDate());
			rackStock.setRefDate(savedPicked.getDocDate());
			rackStock.setRefNo(savedPicked.getId());
			rackStock.setSourceId(savedPicked.getId());
			rackStock.setSourceScreenCode(savedPicked.getScreenCode());
			rackStock.setSourceScreenName(savedPicked.getScreenName());
			rackStock.setPlusOrMinus("m");
			rackStock.setQty(detail.getPickedQty().multiply(BigDecimal.valueOf(-1)));
			rackStock.setLocation(savedPicked.getLocation());
			rackStock.setRackNo(detail.getRackNo());
			rackStock.setPartno(detail.getItem());
			rackStock.setPartDesc(detail.getItemName());
			rackStock.setActive(true);
			rackStock.setCancel(false);
			rackStock.setStatus(savedPicked.getStatus());
			rackStock.setCreatedBy(savedPicked.getCreatedBy());
			rackStock.setUpdatedBy(savedPicked.getUpdatedBy());
			rackStock.setBranch(savedPicked.getBranch());
			rackStock.setBranchCode(savedPicked.getBranchCode());
			rackStock.setFinYear(savedPicked.getFinYear());
			rackStockDetailsRepo.save(rackStock);

		}
		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("pickListVO", pickListVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePickListVOByPickListDTO(@Valid PickListDTO pickListDTO, PickListVO pickListVO)
			throws ApplicationException {
		pickListVO.setCustomerName(pickListDTO.getCustomerName());
		pickListVO.setRouteCardNo(pickListDTO.getRouteCardNo());
		pickListVO.setWorkOrderNo(pickListDTO.getWorkOrderNo());
		pickListVO.setItemIssueToProductionNo(pickListDTO.getItemIssueToProductionNo());
		pickListVO.setDepartment(pickListDTO.getDepartment());
		pickListVO.setLocation(pickListDTO.getLocation());
		pickListVO.setShift(pickListDTO.getShift());
		pickListVO.setPickedBy(pickListDTO.getPickedBy());
		pickListVO.setFgPartNo(pickListDTO.getFgPartNo());
		pickListVO.setRemarks(pickListDTO.getRemarks());
		pickListVO.setOrgId(pickListDTO.getOrgId());
		pickListVO.setCustomerCode(pickListDTO.getCustomerCode());
		pickListVO.setBranch(pickListDTO.getBranch());
		pickListVO.setStatus(pickListDTO.getStatus());
		pickListVO.setBranchCode(pickListDTO.getBranchCode());
		pickListVO.setFinYear(pickListDTO.getFinYear());

		List<PickListDetailsVO> pickListDetailsVOs = new ArrayList<>();
		for (PickListDetailsDTO pickListDetailsDTO : pickListDTO.getPickListDetailsDTO()) {
			PickListDetailsVO pickListDetailsVO = new PickListDetailsVO();
			pickListDetailsVO.setItem(pickListDetailsDTO.getItem());
			pickListDetailsVO.setItemName(pickListDetailsDTO.getItemName());
			pickListDetailsVO.setUnit(pickListDetailsDTO.getUnit());
			pickListDetailsVO.setRackNo(pickListDetailsDTO.getRackNo());
			pickListDetailsVO.setRackQty(pickListDetailsDTO.getRackQty());
			BigDecimal issuedQty = pickListDetailsDTO.getIssuedQty();
			BigDecimal pickedQty = pickListDetailsDTO.getPickedQty();

			if (issuedQty == null || pickedQty == null) {
				throw new ApplicationException("Issued Qty and Picked Qty must not be null");
			}

			if (pickedQty.compareTo(issuedQty) > 0) {
				throw new ApplicationException("Picked Qty cannot be greater than Issued Qty");
			}

			pickListDetailsVO.setIssuedQty(issuedQty);
			pickListDetailsVO.setPickedQty(pickedQty);

			pickListDetailsVO
					.setRemainingQty(pickListDetailsDTO.getIssuedQty().subtract(pickListDetailsDTO.getPickedQty()));
			pickListDetailsVO.setActualQty(pickListDetailsDTO.getActualQty());
			pickListDetailsVO.setFlag(pickListDetailsDTO.isFlag());

			pickListDetailsVO.setPickListVO(pickListVO);
			pickListDetailsVOs.add(pickListDetailsVO);
		}
		pickListVO.setPickListDetailsVO(pickListDetailsVOs);

	}

	@Override
	public String getPickListDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PL";
		String result = pickListRepo.getPickListDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getItemIssueToProductionDetailsfromPickList(Long orgId,
			String itemIssueToProduction) {
		Set<Object[]> itemIssueToProductionDtls = pickListRepo.findItemIssueToProductionDetailsfromPickList(orgId,
				itemIssueToProduction);
		return getItemIssueToProductionDetailsfromPickList(itemIssueToProductionDtls);
	}

	private List<Map<String, Object>> getItemIssueToProductionDetailsfromPickList(
			Set<Object[]> itemIssueToProductionDtls) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProductionDtls) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("issueQty", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo) {
		Set<Object[]> itemIssueToProductionDtls = pickListRepo.findItemIssueToProductionNofromPickList(orgId,
				routeCardEntryNo);
		return getItemIssueToProductionNofromPickList(itemIssueToProductionDtls);
	}

	private List<Map<String, Object>> getItemIssueToProductionNofromPickList(Set<Object[]> itemIssueToProductionDtls) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProductionDtls) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemIssueToProductionNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	// ItemissueToProduction

	@Override
	public List<ItemIssueToProductionVO> getItemIssToProdById(Long id) {
		List<ItemIssueToProductionVO> itemIssueToProductionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ItemIssueToProduction BY Id : {}", id);
			itemIssueToProductionVO = itemIssueToProductionRepo.findItemIssueToProductionById(id);
		}
		return itemIssueToProductionVO;
	}

	@Override
	public List<ItemIssueToProductionVO> getItemIssueToProductionByOrgId(Long orgId, String finYear,
			String branchCode) {
		List<ItemIssueToProductionVO> itemIssueToProductionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ItemIssueToProduction BY Id : {}", orgId);
			itemIssueToProductionVO = itemIssueToProductionRepo.getItemIssueToProductionByOrgId(orgId, finYear,
					branchCode);
		}
		return itemIssueToProductionVO;
	}

	@Override
	public Map<String, Object> updateCreateItemIssToProd(@Valid ItemIssueToProductionDTO itemIssueToProductionDTO)
			throws ApplicationException {
		String message;
		String screenCode = "IITP";
		ItemIssueToProductionVO oldItemIssueToProduction = null;

		ItemIssueToProductionVO itemIssueToProductionVO = new ItemIssueToProductionVO();

		if (itemIssueToProductionDTO.getId() != null) {
			oldItemIssueToProduction = itemIssueToProductionRepo.findById(itemIssueToProductionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Putaway not found"));

			oldItemIssueToProduction.getItemIssueToProductionDetailsVO().size(); // load

			entityManager.detach(oldItemIssueToProduction); // detach snapshot

			itemIssueToProductionVO = itemIssueToProductionRepo.findById(itemIssueToProductionDTO.getId())
					.orElseThrow(() -> new ApplicationException("ItemIssueToProduction not found"));
			itemIssueToProductionVO.setUpdatedBy(itemIssueToProductionDTO.getCreatedBy());
			createUpdateItemIssueToProductionVOByItemIssueToProductionDTO(itemIssueToProductionDTO,
					itemIssueToProductionVO);
			message = "ItemIssueToProduction Updated Successfully";

			List<ItemIssueToProductionDetailsVO> itemIssueToProductionDetailsVOs = itemIssueToProductionDetailsRepo
					.findByItemIssueToProductionVO(itemIssueToProductionVO);
			itemIssueToProductionDetailsRepo.deleteAll(itemIssueToProductionDetailsVOs);

		} else {

			// GETDOCID API
			String docId = itemIssueToProductionRepo.getItemIssueToProductionDocId(itemIssueToProductionDTO.getOrgId(),
					itemIssueToProductionDTO.getFinYear(), itemIssueToProductionDTO.getBranchCode(), screenCode);
			itemIssueToProductionVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(itemIssueToProductionDTO.getOrgId(),
							itemIssueToProductionDTO.getFinYear(), itemIssueToProductionDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			itemIssueToProductionVO.setCreatedBy(itemIssueToProductionDTO.getCreatedBy());
			itemIssueToProductionVO.setUpdatedBy(itemIssueToProductionDTO.getCreatedBy());
			createUpdateItemIssueToProductionVOByItemIssueToProductionDTO(itemIssueToProductionDTO,
					itemIssueToProductionVO);

			ItemIssueToProductionVO savedPicked = itemIssueToProductionRepo.save(itemIssueToProductionVO);
			for (ItemIssueToProductionDetailsVO detail : savedPicked.getItemIssueToProductionDetailsVO()) {
				StockDetailsVO stockPlus = new StockDetailsVO();
				stockPlus.setOrgId(savedPicked.getOrgId());
				stockPlus.setStockDate(savedPicked.getDocDate());
				stockPlus.setDocId(savedPicked.getDocId());
				stockPlus.setDocDate(savedPicked.getDocDate());
				stockPlus.setRefDate(savedPicked.getDocDate());
				stockPlus.setRefNo(savedPicked.getId());
				stockPlus.setSourceId(savedPicked.getId());
				stockPlus.setSourceScreenCode(savedPicked.getScreenCode());
				stockPlus.setSourceScreenName(savedPicked.getScreenName());
				stockPlus.setPlusOrMinus("m");
				stockPlus.setActive(true);
				stockPlus.setCancel(false);
				stockPlus.setQty(detail.getIssueQty().multiply(BigDecimal.valueOf(-1)));
				stockPlus.setLocation(savedPicked.getFromLocation());
//				stockPlus.setSupplierName(savedPicked.getCustomerName());
				stockPlus.setPartno(detail.getItem());
				stockPlus.setPartDesc(detail.getItemDesc());
				stockPlus.setCreatedBy(savedPicked.getCreatedBy());
				stockPlus.setUpdatedBy(savedPicked.getUpdatedBy());
				stockPlus.setBranch(savedPicked.getBranch());
				stockPlus.setBranchCode(savedPicked.getBranchCode());
				stockDetailsRepo.save(stockPlus);

				StockDetailsVO stockMinus = new StockDetailsVO();
				stockMinus.setOrgId(savedPicked.getOrgId());
				stockMinus.setStockDate(savedPicked.getDocDate());
				stockMinus.setDocId(savedPicked.getDocId());
				stockMinus.setDocDate(savedPicked.getDocDate());
				stockMinus.setRefDate(savedPicked.getDocDate());
				stockMinus.setRefNo(savedPicked.getId());
				stockMinus.setSourceId(savedPicked.getId());
				stockMinus.setSourceScreenCode(savedPicked.getScreenCode());
				stockMinus.setSourceScreenName(savedPicked.getScreenName());
				stockMinus.setPlusOrMinus("p");
				stockMinus.setActive(true);
				stockMinus.setCancel(false);
				stockMinus.setQty(detail.getIssueQty().multiply(BigDecimal.valueOf(1)));
				stockMinus.setLocation(savedPicked.getToLocation());
				stockMinus.setPartno(detail.getItem());
				stockMinus.setPartDesc(detail.getItemDesc());
				stockMinus.setCreatedBy(savedPicked.getCreatedBy());
				stockMinus.setUpdatedBy(savedPicked.getUpdatedBy());
				stockMinus.setBranch(savedPicked.getBranch());
				stockMinus.setBranchCode(savedPicked.getBranchCode());
				stockDetailsRepo.save(stockMinus);

			}
			message = "ItemIssueToProduction Created Successfully";

		}

		itemIssueToProductionRepo.save(itemIssueToProductionVO);
		commonNotificationService.generateNotification(itemIssueToProductionVO.getScreenCode(),
				itemIssueToProductionVO.getId(), oldItemIssueToProduction, itemIssueToProductionVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("itemIssToProdVO", itemIssueToProductionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateItemIssueToProductionVOByItemIssueToProductionDTO(
			@Valid ItemIssueToProductionDTO itemIssueToProductionDTO, ItemIssueToProductionVO itemIssueToProductionVO) {
		itemIssueToProductionVO.setRouteCardNo(itemIssueToProductionDTO.getRouteCardNo());
		itemIssueToProductionVO.setWorkorder(itemIssueToProductionDTO.getWorkorder());
		itemIssueToProductionVO.setFgItemId(itemIssueToProductionDTO.getFgItemId());
		itemIssueToProductionVO.setFgItemDesc(itemIssueToProductionDTO.getFgItemDesc());
		itemIssueToProductionVO.setFgQty(itemIssueToProductionDTO.getFgQty());
		itemIssueToProductionVO.setFromLocation(itemIssueToProductionDTO.getFromLocation());
		itemIssueToProductionVO.setRemarks(itemIssueToProductionDTO.getRemarks());
		itemIssueToProductionVO.setPreparedBy(itemIssueToProductionDTO.getPreparedBy());
		itemIssueToProductionVO.setOrgId(itemIssueToProductionDTO.getOrgId());
		itemIssueToProductionVO.setBranch(itemIssueToProductionDTO.getBranch());
		itemIssueToProductionVO.setToLocation(itemIssueToProductionDTO.getToLocation());
		itemIssueToProductionVO.setBranchCode(itemIssueToProductionDTO.getBranchCode());
		itemIssueToProductionVO.setFinYear(itemIssueToProductionDTO.getFinYear());

		List<ItemIssueToProductionDetailsVO> itemIssueToProductionDetailsVOs = new ArrayList<>();
		for (ItemIssueToProductionDetailsDTO itemIssueToProductionDetailsDTO : itemIssueToProductionDTO
				.getItemIssueToProductionDetailsDTO()) {
			ItemIssueToProductionDetailsVO itemIssueToProductionDetailsVO = new ItemIssueToProductionDetailsVO();
			itemIssueToProductionDetailsVO.setItem(itemIssueToProductionDetailsDTO.getItem());
			itemIssueToProductionDetailsVO.setItemDesc(itemIssueToProductionDetailsDTO.getItemDesc());
			itemIssueToProductionDetailsVO.setUnit(itemIssueToProductionDetailsDTO.getUnit());
			itemIssueToProductionDetailsVO.setHoldQty(itemIssueToProductionDetailsDTO.getHoldQty());
			itemIssueToProductionDetailsVO.setReqQty(itemIssueToProductionDetailsDTO.getReqQty());

			Set<Object[]> itemIssueQtyt = itemIssueToProductionRepo.getItemIssueQty(itemIssueToProductionVO.getOrgId(),
					itemIssueToProductionVO.getRouteCardNo(), itemIssueToProductionVO.getWorkorder(),
					itemIssueToProductionDetailsVO.getItem());

			BigDecimal issuedQty = BigDecimal.ZERO;

			if (itemIssueQtyt != null && !itemIssueQtyt.isEmpty()) {
				for (Object[] b : itemIssueQtyt) {
					if (b[0] != null) {
						issuedQty = (BigDecimal) b[0]; // ✅ Correct
					}
				}
			}

			itemIssueToProductionDetailsVO.setIssued(issuedQty);

//			itemIssueToProductionDetailsVO.setAvgQty(itemIssueToProductionDetailsDTO.getAvgQty());	
			itemIssueToProductionDetailsVO.setIssueQty(itemIssueToProductionDetailsDTO.getIssueQty());
			BigDecimal reqQty = itemIssueToProductionDetailsDTO.getReqQty() != null
					? itemIssueToProductionDetailsDTO.getReqQty()
					: BigDecimal.ZERO;

			BigDecimal issueQty = itemIssueToProductionDetailsDTO.getIssueQty() != null
					? itemIssueToProductionDetailsDTO.getIssueQty()
					: BigDecimal.ZERO;

			itemIssueToProductionDetailsVO.setPendingQty(reqQty.subtract(issueQty));

			itemIssueToProductionDetailsVO.setPickQty(itemIssueToProductionDetailsDTO.getPickQty());
//			itemIssueToProductionDetailsVO.setAvgQty(itemIssueToProductionDetailsDTO.getAvgQty());

			itemIssueToProductionDetailsVO.setItemIssueToProductionVO(itemIssueToProductionVO); // Set the reference in
																								// child entity
			itemIssueToProductionDetailsVOs.add(itemIssueToProductionDetailsVO);
		}
		itemIssueToProductionVO.setItemIssueToProductionDetailsVO(itemIssueToProductionDetailsVOs);

	}

	@Override
	public String getItemIssueToProductionDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "IITP";
		String result = itemIssueToProductionRepo.getItemIssueToProductionDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

//	@Override
//
//	public List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Long orgId,String customerCode) {
//		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo
//				.findRouteCardEntryNoForItemIssueToProduction(orgId,customerCode);
//		return getRouteCardEntryNoForItemIssueToProduction(itemIssueToProduction);
//	}
//
//	private List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Set<Object[]> itemIssueToProduction) {
//		List<Map<String, Object>> List1 = new ArrayList<>();
//		for (Object[] ch : itemIssueToProduction) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("routeCardEntryNo", ch[0] != null ? ch[0].toString() : "");
//			List1.add(map);
//		}
//		return List1;
//	}

	@Override

	public List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Long orgId) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo
				.findRouteCardEntryNoForItemIssueToProduction(orgId);
		return getRouteCardEntryNoForItemIssueToProduction(itemIssueToProduction);
	}

	private List<Map<String, Object>> getRouteCardEntryNoForItemIssueToProduction(Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProduction) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardEntryNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardEntryDetailsForItemIssueToProduction(Long orgId, String routeCardNo) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo
				.findRouteCardEntryDetailsForItemIssueToProduction(orgId, routeCardNo);
		return getRouteCardEntryDetailsForItemIssueToProduction(itemIssueToProduction);
	}

	private List<Map<String, Object>> getRouteCardEntryDetailsForItemIssueToProduction(
			Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProduction) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgItemId", ch[1] != null ? ch[1].toString() : "");
			map.put("fgItemDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("fgQty", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemIssueQty(Long orgId, String routeCardNo, String workorder, String item) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo.getItemIssueQty(orgId, routeCardNo, workorder,
				item);
		return getItemIssueQty(itemIssueToProduction);
	}

	private List<Map<String, Object>> getItemIssueQty(Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> list = new ArrayList<>();
		BigDecimal issuedQty = BigDecimal.ZERO;
		if (itemIssueToProduction != null && !itemIssueToProduction.isEmpty()) {
			for (Object[] ch : itemIssueToProduction) {
				if (ch[0] != null) {
					issuedQty = new BigDecimal(ch[0].toString());
				}
			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put("issuedQty", issuedQty);
		list.add(map);

		return list;
	}

	@Override
	public List<Map<String, Object>> getItemIssueToProductionDetailsfromBom(Long orgId, String fgItemId) {
		Set<Object[]> itemIssueToProduction = itemIssueToProductionRepo.findItemIssueToProductionDetailsfromBom(orgId,
				fgItemId);
		return getItemIssueToProductionDetailsfromBom(itemIssueToProduction);
	}

	private List<Map<String, Object>> getItemIssueToProductionDetailsfromBom(Set<Object[]> itemIssueToProduction) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProduction) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			map.put("bomQty", ch[3] != null ? ch[3].toString() : "");
//			map.put("issued", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override

	public List<Map<String, Object>> getRouteCardEntryNoForPickList(Long orgId, String CustomerCode) {
		Set<Object[]> pickList = pickListRepo.getRouteCardEntryNoForPickList(orgId, CustomerCode);
		return getRouteCardEntryNoForPickList(pickList);
	}

	private List<Map<String, Object>> getRouteCardEntryNoForPickList(Set<Object[]> pickList) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : pickList) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardEntryNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRackNoForRackDetails(Long orgId, String branchCode, String itemCode) {
		Set<Object[]> itemIssueToProductionDtls = pickListRepo.getRackNoForRackDetails(orgId, branchCode, itemCode);
		return getRackNoForRackDetails(itemIssueToProductionDtls);
	}

	private List<Map<String, Object>> getRackNoForRackDetails(Set<Object[]> itemIssueToProductionDtls) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssueToProductionDtls) {
			Map<String, Object> map = new HashMap<>();
			map.put("qty", ch[0] != null ? ch[0].toString() : "");
			map.put("rackNo", ch[1] != null ? ch[1].toString() : "");
			map.put("partNo", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPutAwayDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode, String grnNo) {
		Set<Object[]> chType = putawayRepo.getPutAwayDetails(orgId, supplierName, fromDate, toDate, branchCode, grnNo);
		return getPutAwayDetails(chType);
	}

	private List<Map<String, Object>> getPutAwayDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("dcNo", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("fromLocation", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("goodsType", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("grnNo", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("toLocation", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("vehileNo", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("supplier", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("totalPutAwayQty", ch[9] != null ? new BigDecimal(ch[9].toString()) : BigDecimal.ZERO); // 9
			map.put("item", ch[10] != null ? ch[10].toString() : ""); // 5
			map.put("itemDesc", ch[11] != null ? ch[11].toString() : ""); // 6
			map.put("rackNo", ch[12] != null ? ch[12].toString() : ""); // 7
			map.put("putAwayQty", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 10
			map.put("recQty", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 11
			map.put("putawayId", ch[15] != null ? ch[15].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardEntryReport(Long orgId, String ststus) {

		Set<Object[]> reportData = routeCardEntryRepo.getRouteCardEntryReport(orgId, ststus);

		return mapRouteCardEntryReport(reportData);
	}

	private List<Map<String, Object>> mapRouteCardEntryReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("routeCardId", ch[0] != null ? ch[0].toString() : "");
			map.put("routeCardNo", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("workOrderNo", ch[4] != null ? ch[4].toString() : "");

			map.put("fgPartName", ch[5] != null ? ch[5].toString() : "");
			map.put("fgPartDesc", ch[6] != null ? ch[6].toString() : "");
			map.put("fgQty", ch[7] != null ? ch[7].toString() : "");
			map.put("batchQty", ch[8] != null ? ch[8].toString() : "");

			map.put("rmType", ch[9] != null ? ch[9].toString() : "");
			map.put("rmSize", ch[10] != null ? ch[10].toString() : "");
			map.put("rmBatchNo", ch[11] != null ? ch[11].toString() : "");
			map.put("rmQty", ch[12] != null ? ch[12].toString() : "");

			map.put("status", ch[13] != null ? ch[13].toString() : "");

			list.add(map);
		}

		return list;
	}

	// PickListReport

	@Override
	public List<Map<String, Object>> getItemIssueToProductionNoforPickList(Long orgId, String routeCardEntryNo,
			String branchCode) {
		Set<Object[]> chType = pickListRepo.getItemIssueToProductionNoforPickList(orgId, routeCardEntryNo, branchCode);
		return getItemIssueToProductionNoforPickList(chType);
	}

	private List<Map<String, Object>> getItemIssueToProductionNoforPickList(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemIssueToProductionNo", ch[0] != null ? ch[0].toString() : ""); // 0

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPickListReport(Long orgId, String itemIssueToProductionNo, String branchCode,
			String routeCardEntryNo) {
		Set<Object[]> result = pickListRepo.getPickListReport(orgId, itemIssueToProductionNo, branchCode,
				routeCardEntryNo);

		return getPickListReport(result);
	}

	private List<Map<String, Object>> getPickListReport(Set<Object[]> result) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : result) {
			Map<String, Object> map = new HashMap<>();

			map.put("picklistId", ch[0] != null ? ch[0].toString() : "");
			map.put("customerName", ch[1] != null ? ch[1].toString() : "");
			map.put("department", ch[2] != null ? ch[2].toString() : "");
			map.put("docDate", ch[3] != null ? ch[3].toString() : "");
			map.put("docId", ch[4] != null ? ch[4].toString() : "");

			map.put("fgPartNo", ch[5] != null ? ch[5].toString() : "");
			map.put("itemIssueToProductionNo", ch[6] != null ? ch[6].toString() : "");
			map.put("location", ch[7] != null ? ch[7].toString() : "");
			map.put("orgId", ch[8] != null ? ch[8].toString() : "");
			map.put("pickedBy", ch[9] != null ? ch[9].toString() : "");

			map.put("routeCardNo", ch[10] != null ? ch[10].toString() : "");
			map.put("shift", ch[11] != null ? ch[11].toString() : "");
			map.put("workOrderNo", ch[12] != null ? ch[12].toString() : "");
			map.put("branch", ch[13] != null ? ch[13].toString() : "");
			map.put("branchCode", ch[14] != null ? ch[14].toString() : "");

			map.put("finYear", ch[15] != null ? ch[15].toString() : "");
			map.put("status", ch[16] != null ? ch[16].toString() : "");
			map.put("customerCode", ch[17] != null ? ch[17].toString() : "");

			// ---- Detail fields (same row) ----
			map.put("item", ch[18] != null ? ch[18].toString() : "");
			map.put("itemName", ch[19] != null ? ch[19].toString() : "");
			map.put("unit", ch[20] != null ? ch[20].toString() : "");
			map.put("rackNo", ch[21] != null ? ch[21].toString() : "");
			map.put("rackQty", ch[22] != null ? ch[22].toString() : "");
			map.put("pickedQty", ch[23] != null ? ch[23].toString() : "");
			map.put("issuedQty", ch[24] != null ? ch[24].toString() : "");
			map.put("remainingQty", ch[25] != null ? ch[25].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemIssuedProductionDetails(Long orgId, String routecardno) {
		Set<Object[]> itemIssuedProductionDetails = itemIssueToProductionRepo.getItemIssuedProductionDetails(orgId,
				routecardno);
		return getItemIssuedProductionDetails(itemIssuedProductionDetails);
	}

	private List<Map<String, Object>> getItemIssuedProductionDetails(Set<Object[]> itemIssuedProductionDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssuedProductionDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routecardno", ch[0] != null ? ch[0].toString() : "");
			map.put("orgid", ch[1] != null ? ch[1].toString() : "");
			map.put("workorder", ch[2] != null ? ch[2].toString() : "");
			map.put("fgitemid", ch[3] != null ? ch[3].toString() : "");
			map.put("item", ch[4] != null ? ch[4].toString() : "");
			map.put("itemDesc", ch[5] != null ? ch[5].toString() : "");
			map.put("reqqty", ch[6] != null ? ch[6].toString() : "");
			map.put("issuqty", ch[7] != null ? ch[7].toString() : "");
			map.put("pendingqty", ch[8] != null ? ch[8].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoAndItemIssueNumber(Long orgId) {
		Set<Object[]> itemIssuedProductionDetails = pickListRepo.getRouteCardNoAndItemIssueNumber(orgId);
		return getRouteCardNoAndItemIssueNumber(itemIssuedProductionDetails);
	}

	private List<Map<String, Object>> getRouteCardNoAndItemIssueNumber(Set<Object[]> itemIssuedProductionDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemIssuedProductionDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routecardno", ch[0] != null ? ch[0].toString() : "");
			map.put("itemIssuetoProductionNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateRouteCardEntry(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		RouteCardEntryVO routeCardEntryVO = routeCardEntryRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		routeCardEntryVO = routeCardEntryRepo.save(routeCardEntryVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<RouteCardEntryAttachmentVO> oldDocs = routeCardEntryAttachmentRepo
				.findByRouteCardEntryVO(routeCardEntryVO);
		routeCardEntryAttachmentRepo.deleteAll(oldDocs);

		if (routeCardEntryVO.getDocuments() != null) {
			routeCardEntryVO.getDocuments().clear();
		} else {
			routeCardEntryVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (RouteCardEntryAttachmentVO doc : oldDocs) {
			deleteFileSafelyDocument(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(routeCardEntryVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("routeCardEntryVO", routeCardEntryVO);

		return response;
	}

	private void replaceDocuments(RouteCardEntryVO routeCardEntryVO, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(routeCardEntryVO, files, docFolder, docId);
	}

	private void saveFiles(RouteCardEntryVO routeCardEntryVO, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		try {
			createDirectory(docFolder);

			for (MultipartFile file : files) {

				String originalName = file.getOriginalFilename();

				if (originalName == null) {
					originalName = "file";
				}

				// Extract extension
				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				// New file name → original_docId.ext
				String fileName = originalName + "_" + docId + extension;

				Path filePath = docFolder.resolve(fileName);

				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/inventory/viewFileRouteCard/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				RouteCardEntryAttachmentVO attach = new RouteCardEntryAttachmentVO();
				attach.setRouteCardEntryVO(routeCardEntryVO);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFilename(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (routeCardEntryVO.getDocuments() == null) {
					routeCardEntryVO.setDocuments(new ArrayList<>());
				}

				routeCardEntryVO.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyDocument(String path) {
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
	public ResponseEntity<byte[]> viewFileRouteCard(HttpServletRequest request)
			throws IOException, java.io.IOException {
		return serveFileDocument(request, "/api/inventory/viewFileRouteCard/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileDocument(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException, java.io.IOException {

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
	public List<ImageResponseDTO> getRouteCardEntryImages(Long id) throws Exception {

		RouteCardEntryVO record = routeCardEntryRepo.getAllRouteCardEntryById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<RouteCardEntryAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (RouteCardEntryAttachmentVO attachment : docs) {

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

}
