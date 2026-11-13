package com.efitops.basesetup.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockDetailsService {


	Map<String, Object> getAvailableStockQtyForGRN(Long itemId, Long locationId, Long orgId, Long branchId);

}
