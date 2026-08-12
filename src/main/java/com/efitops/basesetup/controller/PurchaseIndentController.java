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

import com.efitops.basesetup.ResponseDTO.PurchaseIndentResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.PurchaseIndentService;

@CrossOrigin
@RestController
@RequestMapping("/api/purchaseindent")
public class PurchaseIndentController extends BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseIndentController.class);

    @Autowired
    PurchaseIndentService purchaseIndentService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @PostMapping(value = "/createUpdatePurchaseIndent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> createUpdatePurchaseIndent(
            @RequestPart("purchaseIndent") String purchaseIndentJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        System.out.println("PurchaseIndent Controller Hit");
        String methodName = "createUpdatePurchaseIndent()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            // ----------------------------------------------------------------
            // TEMP DIAGNOSTIC LOGGING - remove once duplicate-create issue is
            // confirmed fixed. Prints the RAW JSON exactly as received, before
            // Jackson parses it, so we can see the exact key/value the client
            // is sending for id (e.g. "id" vs "purchaseindent_id" vs missing).
            // ----------------------------------------------------------------
            LOGGER.info("createUpdatePurchaseIndent() raw purchaseIndentJson = {}", purchaseIndentJson);

            PurchaseIndentDTO purchaseIndentDTO = objectMapper.readValue(purchaseIndentJson, PurchaseIndentDTO.class);

            LOGGER.info("createUpdatePurchaseIndent() parsed dto.getId() = {}", purchaseIndentDTO.getId());

            Map<String, Object> purchaseIndentMap = purchaseIndentService
                    .createUpdatePurchaseIndent(purchaseIndentDTO, files);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseIndentMap.get("message"));
            responseObjectsMap.put("purchaseIndentVO", purchaseIndentMap.get("purchaseIndentVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
            responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getPurchaseIndentById")
    public ResponseEntity<ResponseDTO> getPurchaseIndentById(@RequestParam Long id) {

        String methodName = "getPurchaseIndentById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {

            PurchaseIndentResponseDTO purchaseIndentVO = purchaseIndentService.getPurchaseIndentById(id);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent retrieved successfully");
            responseObjectsMap.put("purchaseIndentVO", purchaseIndentVO);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent retrieval failed",
                    e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getPurchaseIndentByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseIndentByOrgId(@RequestParam Long orgId,
                                                                @RequestParam Long branch) {

        String methodName = "getPurchaseIndentByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {

            List<PurchaseIndentResponseDTO> purchaseIndentList = purchaseIndentService
                    .getPurchaseIndentByOrgId(orgId, branch);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent list retrieved successfully");
            responseObjectsMap.put("purchaseIndentList", purchaseIndentList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent retrieval failed",
                    e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }
}