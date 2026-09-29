package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.repository.StockValuationRepo;

@Service
public class StockDetailsServiceImpl implements StockDetailsService{

	@Autowired
	StockValuationRepo stockValuationRepo;
	
	@Override
	public Map<String, Object> getAvailableStockQtyForGRN(
	        Long itemId,
	        Long locationId,
	        Long orgId,
	        Long branchId) {

	    BigDecimal availableStock =
	            stockValuationRepo.getAvailableStockQtyForGRN(
	                    itemId,
	                    locationId,
	                    orgId,
	                    branchId);

	    Map<String, Object> details = new HashMap<>();

	    details.put("itemId", itemId);
	    details.put("locationId", locationId);
	    details.put(
	            "availableStock",
	            availableStock != null
	                    ? availableStock
	                    : BigDecimal.ZERO);

	    return details;
	}
}
