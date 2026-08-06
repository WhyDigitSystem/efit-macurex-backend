package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.OrderAcceptanceDTO;
import com.efitops.basesetup.dto.OrderAcceptanceResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface OrderAcceptanceService {

	OrderAcceptanceResponseDTO getOrderAcceptanceById(Long id) throws ApplicationException;

	List<OrderAcceptanceResponseDTO> getOrderAcceptanceByOrgId(Long orgId, Long branchId) throws ApplicationException;

	Map<String, Object> createUpdateOrderAcceptance(OrderAcceptanceDTO orderAcceptanceDTO, MultipartFile[] files)
			throws ApplicationException;

	SalesOrderShortCloseResponseDTO getSalesOrderShortCloseById(Long id) throws ApplicationException;

	List<SalesOrderShortCloseResponseDTO> getSalesOrderShortCloseByOrgId(Long orgId, Long branchId)
			throws ApplicationException;

	Map<String, Object> createUpdateSalesOrderShort(SalesOrderShortCloseDTO salesOrderShortCloseDTO,
			MultipartFile[] files) throws ApplicationException;

}
