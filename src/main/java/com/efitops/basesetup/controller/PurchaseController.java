package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.*;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.*;
import com.efitops.basesetup.service.PurchaseService;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController extends BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    PurchaseService purchaseService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    // ==================================================================
    // PURCHASE CONTRACT — original paths: /api/purchaseContract/**
    // ==================================================================

    @PostMapping(value = "/api/purchaseContract/updateCreatePurchaseContract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateCreatePurchaseContract(
            @RequestPart("purchaseContractDTO") String purchaseContractJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseContractDTO dto = objectMapper.readValue(purchaseContractJson, PurchaseContractDTO.class);
            Map<String, Object> result = purchaseService.updateCreatePurchaseContract(dto, files);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
            responseObjectsMap.put("purchaseContractVO", result.get("purchaseContractVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, "updateCreatePurchaseContract()", e.getMessage());
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract Save Failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseContract/getPurchaseContractById")
    public ResponseEntity<ResponseDTO> getPurchaseContractById(@RequestParam Long id) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseContractResponseDTO result = purchaseService.getPurchaseContractById(id);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract information retrieved successfully");
            responseObjectsMap.put("purchaseContractVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseContract/getPurchaseContractByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseContractByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            List<PurchaseContractResponseDTO> result = purchaseService.getPurchaseContractByOrgId(orgId, branchId);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract information retrieved successfully");
            responseObjectsMap.put("purchaseContractVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }



    // ==================================================================
    // PURCHASE DELIVERY SCHEDULE — original paths: /api/purchaseDeliverySchedule/**
    // ==================================================================

    @PostMapping("/api/purchaseDeliverySchedule/updateCreatePurchaseDeliverySchedule")
    public ResponseEntity<ResponseDTO> updateCreatePurchaseDeliverySchedule(@RequestBody PurchaseDeliveryScheduleDTO dto) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            Map<String, Object> result = purchaseService.updateCreatePurchaseDeliverySchedule(dto);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
            responseObjectsMap.put("purchaseDeliveryScheduleVO", result.get("purchaseDeliveryScheduleVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Delivery Schedule Save Failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseDeliverySchedule/getPurchaseDeliveryScheduleById")
    public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleById(@RequestParam Long id) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseDeliveryScheduleResponseDTO result = purchaseService.getPurchaseDeliveryScheduleById(id);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Delivery Schedule information retrieved successfully");
            responseObjectsMap.put("purchaseDeliveryScheduleVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Delivery Schedule information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseDeliverySchedule/getPurchaseDeliveryScheduleByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            List<PurchaseDeliveryScheduleResponseDTO> result = purchaseService.getPurchaseDeliveryScheduleByOrgId(orgId, branchId);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Delivery Schedule information retrieved successfully");
            responseObjectsMap.put("purchaseDeliveryScheduleVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Delivery Schedule information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }


    // ==================================================================
    // PURCHASE BILL — original paths: /api/purchaseBill/**
    // ==================================================================

    @PostMapping("/api/purchaseBill/updateCreatePurchaseBill")
    public ResponseEntity<ResponseDTO> updateCreatePurchaseBill(@RequestBody PurchaseBillDTO dto) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            Map<String, Object> result = purchaseService.updateCreatePurchaseBill(dto);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
            responseObjectsMap.put("purchaseBillVO", result.get("purchaseBillVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Bill Save Failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseBill/getPurchaseBillById")
    public ResponseEntity<ResponseDTO> getPurchaseBillById(@RequestParam Long id) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseBillResponseDTO result = purchaseService.getPurchaseBillById(id);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Bill information retrieved successfully");
            responseObjectsMap.put("purchaseBillVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Bill information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseBill/getPurchaseBillByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseBillByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            List<PurchaseBillResponseDTO> result = purchaseService.getPurchaseBillByOrgId(orgId, branchId);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Bill information retrieved successfully");
            responseObjectsMap.put("purchaseBillVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Bill information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }



    // ==================================================================
    // PURCHASE INDENT — original paths: /api/purchaseindent/**
    // ==================================================================

    @PostMapping(value = "/api/purchaseindent/createUpdatePurchaseIndent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> createUpdatePurchaseIndent(
            @RequestPart("purchaseIndent") String purchaseIndentJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseIndentDTO dto = objectMapper.readValue(purchaseIndentJson, PurchaseIndentDTO.class);
            Map<String, Object> result = purchaseService.createUpdatePurchaseIndent(dto, files);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
            responseObjectsMap.put("purchaseIndentVO", result.get("purchaseIndentVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, "createUpdatePurchaseIndent()", e.getMessage());
            responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseindent/getPurchaseIndentById")
    public ResponseEntity<ResponseDTO> getPurchaseIndentById(@RequestParam Long id) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseIndentResponseDTO result = purchaseService.getPurchaseIndentById(id);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent retrieved successfully");
            responseObjectsMap.put("purchaseIndentVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseindent/getPurchaseIndentByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseIndentByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            List<PurchaseIndentResponseDTO> result = purchaseService.getPurchaseIndentByOrgId(orgId, branch);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent list retrieved successfully");
            responseObjectsMap.put("purchaseIndentList", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    // ==================================================================
    // PURCHASE SHORT CLOSE — /api/purchaseShortClose/**
    // ==================================================================

    @PostMapping("/api/purchaseShortClose/updateCreatePurchaseShortClose")
    public ResponseEntity<ResponseDTO> updateCreatePurchaseShortClose(@RequestBody PurchaseShortCloseDTO dto) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            Map<String, Object> result = purchaseService.updateCreatePurchaseShortClose(dto);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
            responseObjectsMap.put("purchaseShortCloseVO", result.get("purchaseShortCloseVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Short Close Save Failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseShortClose/getPurchaseShortCloseById")
    public ResponseEntity<ResponseDTO> getPurchaseShortCloseById(@RequestParam Long id) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            PurchaseShortCloseResponseDTO result = purchaseService.getPurchaseShortCloseById(id);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Short Close information retrieved successfully");
            responseObjectsMap.put("purchaseShortCloseVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Short Close information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/purchaseShortClose/getPurchaseShortCloseByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseShortCloseByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            List<PurchaseShortCloseResponseDTO> result = purchaseService.getPurchaseShortCloseByOrgId(orgId, branchId);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Short Close information retrieved successfully");
            responseObjectsMap.put("purchaseShortCloseVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Short Close information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }
    // ==================================================================
    // LOCAL PURCHASE ORDER — /api/localPurchaseOrder/**
    // ==================================================================

    @PostMapping(value = "/api/localPurchaseOrder/updateCreateLocalPurchaseOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateCreateLocalPurchaseOrder(
            @RequestPart("localPurchaseOrderDTO") String localPurchaseOrderJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            LocalPurchaseOrderDTO dto = objectMapper.readValue(localPurchaseOrderJson, LocalPurchaseOrderDTO.class);
            Map<String, Object> result = purchaseService.updateCreateLocalPurchaseOrder(dto, files);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
            responseObjectsMap.put("localPurchaseOrderVO", result.get("localPurchaseOrderVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, "updateCreateLocalPurchaseOrder()", e.getMessage());
            responseDTO = createServiceResponseError(responseObjectsMap, "Local Purchase Order Save Failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/localPurchaseOrder/getLocalPurchaseOrderById")
    public ResponseEntity<ResponseDTO> getLocalPurchaseOrderById(@RequestParam Long id) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            LocalPurchaseOrderResponseDTO result = purchaseService.getLocalPurchaseOrderById(id);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Local Purchase Order information retrieved successfully");
            responseObjectsMap.put("localPurchaseOrderVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Local Purchase Order information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/localPurchaseOrder/getLocalPurchaseOrderByOrgId")
    public ResponseEntity<ResponseDTO> getLocalPurchaseOrderByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;
        try {
            List<LocalPurchaseOrderResponseDTO> result = purchaseService.getLocalPurchaseOrderByOrgId(orgId, branchId);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Local Purchase Order information retrieved successfully");
            responseObjectsMap.put("localPurchaseOrderVO", result);
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            responseDTO = createServiceResponseError(responseObjectsMap, "Local Purchase Order information retrieval failed", e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }
}