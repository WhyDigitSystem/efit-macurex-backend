package com.efitops.basesetup.service;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DeleteStockService {

	void grnCancelApprove(Long orgId, Long id, String docId, String action, String actionBy, String supplierName)
			throws ApplicationException;

}
