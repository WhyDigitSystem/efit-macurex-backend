package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.OrderAcceptanceDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDocIdResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceItemDetailsResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceItemDropdownResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseResponseDTO;
import com.efitops.basesetup.dto.ShortCloseItemResponseDTO;
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

	ResponseEntity<byte[]> viewOrderAcceptanceFile(HttpServletRequest request) throws IOException;

	List<OrderAcceptanceItemDropdownResponseDTO> getOrderAcceptanceItemDetails(Long orgId, Long branch)
			throws ApplicationException;

	List<ShortCloseItemResponseDTO> getSalesOrderItemDetails(Long orgId, Long branch, String docId)
			throws ApplicationException;

	Map<String, Object> createUpdateSalesOrderShort(SalesOrderShortCloseDTO salesOrderShortCloseDTO)
			throws ApplicationException;

	List<OrderAcceptanceDocIdResponseDTO> getOrderAcceptanceDocIdDetails(Long customer, String docId)
			throws ApplicationException;

	List<OrderAcceptanceItemDetailsResponseDTO> getOrderAcceptanceItemDetailsDetails(String docId)
			throws ApplicationException;

}
