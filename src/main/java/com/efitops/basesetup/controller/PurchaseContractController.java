package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.ResponseDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/purchaseContract")
public class PurchaseContractController extends BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseContractController.class);

    @Autowired
    com.efitops.basesetup.service.PurchaseContractService purchaseContractService;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    // multipart/form-data: "purchaseContractDTO" part = JSON body, "files" part(s) = attachments
    @PostMapping(value = "/updateCreatePurchaseContract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateCreatePurchaseContract(
            @RequestPart("purchaseContractDTO") String purchaseContractJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        System.out.println("PurchaseContract Controller Hit");

        String methodName = "updateCreatePurchaseContract()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {

            LOGGER.info("updateCreatePurchaseContract() raw purchaseContractJson = {}", purchaseContractJson);

            PurchaseContractDTO purchaseContractDTO = objectMapper.readValue(
                    purchaseContractJson,
                    PurchaseContractDTO.class
            );

            LOGGER.info("updateCreatePurchaseContract() parsed dto.getId() = {}",
                    purchaseContractDTO.getId());


            Map<String, Object> purchaseContractMap =
                    purchaseContractService.updateCreatePurchaseContract(
                            purchaseContractDTO,
                            files
                    );


            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    purchaseContractMap.get("message")
            );

            responseObjectsMap.put(
                    "purchaseContractVO",
                    purchaseContractMap.get("purchaseContractVO")
            );


            responseDTO = createServiceResponse(responseObjectsMap);


        } catch (Exception e) {

            LOGGER.error(
                    UserConstants.ERROR_MSG_METHOD_NAME,
                    methodName,
                    e.getMessage()
            );

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    "Purchase Contract Save Failed",
                    e.getMessage()
            );
        }


        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getPurchaseContractById")
    public ResponseEntity<ResponseDTO> getPurchaseContractById(@RequestParam Long id) {

        String methodName = "getPurchaseContractById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            PurchaseContractResponseDTO result = purchaseContractService.getPurchaseContractById(id);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract information retrieved successfully");
            responseObjectsMap.put("purchaseContractVO", result);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract information retrieval failed",
                    errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getPurchaseContractByOrgId")
    public ResponseEntity<ResponseDTO> getPurchaseContractByOrgId(@RequestParam Long orgId,
                                                                  @RequestParam Long branchId) {

        String methodName = "getPurchaseContractByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            List<PurchaseContractResponseDTO> result = purchaseContractService.getPurchaseContractByOrgId(orgId,
                    branchId);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract information retrieved successfully");
            responseObjectsMap.put("purchaseContractVO", result);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract information retrieval failed",
                    errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }

    // used by the UI to preview the auto-generated Contract No before save
    @GetMapping("/getPurchaseContractDocId")
    public ResponseEntity<ResponseDTO> getPurchaseContractDocId(@RequestParam Long orgId,
                                                                @RequestParam String finYear, @RequestParam Long branch) {

        String methodName = "getPurchaseContractDocId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            String docId = purchaseContractService.getPurchaseContractDocId(orgId, finYear, branch);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Contract No generated successfully");
            responseObjectsMap.put("contractNo", docId);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, "Contract No generation failed", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }
}