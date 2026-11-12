package com.efitops.basesetup.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.EngineeringChangeNoteResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EngineeringChangeNoteDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.EngineeringChangeNoteService;

@RestController
@RequestMapping("/api/engineeringchangenote")
public class EngineeringChangeNoteController extends BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(EngineeringChangeNoteController.class);

    @Autowired
    private EngineeringChangeNoteService engineeringChangeNoteService;

    @GetMapping("/getEngineeringChangeNoteByOrgId")
    public ResponseEntity<ResponseDTO> getEngineeringChangeNoteByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {
        String methodName = "getEngineeringChangeNoteByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            List<EngineeringChangeNoteResponseDTO> engineeringChangeNoteList = engineeringChangeNoteService
                    .getEngineeringChangeNoteByOrgId(orgId, branch);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Engineering Change Note retrieved successfully");
            responseObjectsMap.put("engineeringChangeNoteVO", engineeringChangeNoteList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

            responseDTO = createServiceResponseError(responseObjectsMap, "Engineering Change Note retrieval failed",
                    e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getEngineeringChangeNoteById")
    public ResponseEntity<ResponseDTO> getEngineeringChangeNoteById(@RequestParam Long id) {
        String methodName = "getEngineeringChangeNoteById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            EngineeringChangeNoteResponseDTO engineeringChangeNoteResponse = engineeringChangeNoteService
                    .getEngineeringChangeNoteById(id);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Engineering Change Note retrieved successfully");
            responseObjectsMap.put("engineeringChangeNoteVO", engineeringChangeNoteResponse);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

            responseDTO = createServiceResponseError(responseObjectsMap, "Engineering Change Note retrieval failed",
                    e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping(value = "/createUpdateEngineeringChangeNote", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> createUpdateEngineeringChangeNote(
//    		@RequestBody EngineeringChangeNoteDTO engineeringChangeNoteDTO,
          @RequestPart("engineeringChangeNote") EngineeringChangeNoteDTO engineeringChangeNoteDTO,
            @RequestPart(value = "drawingFiles", required = false) MultipartFile[] drawingFiles,
            @RequestPart(value = "bomFiles", required = false) MultipartFile[] bomFiles) {

        String methodName = "createUpdateEngineeringChangeNote()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            Map<String, Object> engineeringChangeNoteMap = engineeringChangeNoteService
                    .createUpdateEngineeringChangeNote(engineeringChangeNoteDTO, drawingFiles, bomFiles);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, engineeringChangeNoteMap.get("message"));
            responseObjectsMap.put("engineeringChangeNoteVO", engineeringChangeNoteMap.get("engineeringChangeNoteVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

            responseDTO = createServiceResponseError(responseObjectsMap,
                    "Engineering Change Note creation/update failed", e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/viewDrawingFile/**")
    public ResponseEntity<byte[]> viewDrawingFile(HttpServletRequest request) {
        String methodName = "viewDrawingFile()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        try {
            return engineeringChangeNoteService.viewDrawingFile(request);

        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/viewBomFile/**")
    public ResponseEntity<byte[]> viewBomFile(HttpServletRequest request) {
        String methodName = "viewBomFile()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        try {
            return engineeringChangeNoteService.viewBomFile(request);

        } catch (Exception e) {
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getEngineeringChangeNoteDocId")
    public ResponseEntity<ResponseDTO> getEngineeringChangeNoteDocId(@RequestParam Long orgId,
            @RequestParam String financialYear) {

        String methodName = "getEngineeringChangeNoteDocId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        String docId = "";

        try {
            docId = engineeringChangeNoteService.getEngineeringChangeNoteDocId(orgId, financialYear);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId information retrieved successfully");
            responseObjectsMap.put("engineeringChangeNoteDocId", docId);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocId", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }


}