package com.efitops.basesetup.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.DeleteStockVO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.StockDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DeleteStockRepo;
import com.efitops.basesetup.repo.GrnRepo;
import com.efitops.basesetup.repo.StockDetailsRepo;

@Service
public class DeleteStockServiceImpl implements DeleteStockService {
	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	GrnRepo grnRepo;

	@Autowired
	DeleteStockRepo deleteStockRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(DeleteStockServiceImpl.class);

	DeleteStockServiceImpl(StockDetailsRepo stockDetailsRepo) {
		this.stockDetailsRepo = stockDetailsRepo;
	}

	@Transactional
	public void grnCancelApprove(Long orgId, Long id, String docId, String action, String actionBy, String supplierName)
			throws ApplicationException {

		GrnVO grnVO = grnRepo.findByOrgIdAndIdAndGrnNoAndSupplierName(orgId, id, docId, supplierName);

		if (grnVO == null) {
			throw new ApplicationException("GRN data not available");
		}

		if ("CANCEL".equalsIgnoreCase(grnVO.getApproveStatus())) {
			throw new ApplicationException("GRN already cancelled");
		}

		List<StockDetailsVO> stockDetailsList = stockDetailsRepo.findAllByOrgIdAndSourceIdAndDocIdAndCustomer(orgId, id,
				docId, supplierName);

		if (stockDetailsList == null || stockDetailsList.isEmpty()) {
			throw new ApplicationException("Stock details not available");
		}

		for (StockDetailsVO stockDetailsVO : stockDetailsList) {

			DeleteStockVO deleteStockVO = new DeleteStockVO();
			deleteStockVO.setOrgId(stockDetailsVO.getOrgId());
			deleteStockVO.setDocId(stockDetailsVO.getDocId());
			deleteStockVO.setDocDate(stockDetailsVO.getDocDate());
			deleteStockVO.setRefNo(stockDetailsVO.getId());
			deleteStockVO.setBranch(stockDetailsVO.getBranch());
			deleteStockVO.setBranchCode(stockDetailsVO.getBranchCode());
			deleteStockVO.setLocation(stockDetailsVO.getLocation());
			deleteStockVO.setFinYear(stockDetailsVO.getFinYear());
			deleteStockVO.setRefDate(stockDetailsVO.getDocDate());
			deleteStockVO.setQty(stockDetailsVO.getQty());
			deleteStockVO.setCreatedBy(stockDetailsVO.getCreatedBy());
			deleteStockVO.setUpdatedBy(actionBy);
			deleteStockVO.setPartno(stockDetailsVO.getPartno());
			deleteStockVO.setPartDesc(stockDetailsVO.getPartDesc());
			deleteStockVO.setCustomer(stockDetailsVO.getPartyName());
			deleteStockVO.setSourceId(stockDetailsVO.getSourceId());
			deleteStockVO.setRecQty(stockDetailsVO.getRecQty());
			deleteStockVO.setRate(stockDetailsVO.getRate());
			deleteStockVO.setAmount(stockDetailsVO.getAmount());
			deleteStockVO.setStatus("CANCELLED");
			deleteStockVO.setSourceScreenName(stockDetailsVO.getScreenName());
			deleteStockVO.setSourceScreenCode(stockDetailsVO.getScreenCode());

			deleteStockRepo.save(deleteStockVO);
		}

		grnVO.setApproveStatus(action);
		grnVO.setApproveBy(actionBy);
		grnVO.setApproveOn(
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a")).toUpperCase());
		grnVO.setCancel(true);

		grnRepo.save(grnVO);

		// 5️⃣ Delete ALL stock details
		stockDetailsRepo.deleteByOrgIdAndSourceIdAndDocIdAndCustomer(orgId, id, docId, supplierName);
	}

}
