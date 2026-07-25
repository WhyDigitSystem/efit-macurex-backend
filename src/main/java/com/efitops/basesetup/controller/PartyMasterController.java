package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PartyMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.service.PartyMasterService;

@RestController
@RequestMapping("/api/partyMaster")
@CrossOrigin(origins = "*")
public class PartyMasterController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterController.class);

    @Autowired
    private PartyMasterService partyMasterService;

//    @PostMapping("/createUpdatePartyMaster")
//    public ResponseEntity<ResponseDTO> createUpdatePartyMaster(
//            @Valid @RequestBody PartyMasterDTO partyMasterDTO) {
//
//        String methodName = "createUpdatePartyMaster()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        String errorMsg = null;
//        ResponseDTO responseDTO = null;
//
//        try {
//
//            Map<String, Object> responseMap =
//            		partyMasterService.createUpdatePartyMaster(partyMasterDTO);
//
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//                    responseMap.get("message"));
//            responseObjectsMap.put("partyMaster",
//                    responseMap.get("partyMaster"));
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName,
//                    errorMsg);
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap,
//                    errorMsg,
//                    errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
//    @GetMapping("/getPartyMasterById")
//    public ResponseEntity<ResponseDTO> getPartyMasterById(
//            @RequestParam Long id) {
//
//        String methodName = "getPartyMasterById()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        String errorMsg = null;
//        ResponseDTO responseDTO = null;
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//
//        PartyMasterVO partyMasterVO = new PartyMasterVO();
//
//        try {
//
//            partyMasterVO = partyMasterService.getPartyMasterById(id);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName,
//                    errorMsg);
//        }
//
//        if (StringUtils.isBlank(errorMsg)) {
//
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//                    "Party Master information retrieved successfully");
//
//            responseObjectsMap.put("partyMasterVO", partyMasterVO);
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } else {
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap,
//                    "Party Master information retrieval failed",
//                    errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
//    @GetMapping("/getPartyMasterByOrgId")
//    public ResponseEntity<ResponseDTO> getPartyMasterByOrgId(
//            @RequestParam Long orgId,
//            @RequestParam Long branch) {
//
//        String methodName = "getPartyMasterByOrgId()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        String errorMsg = null;
//        ResponseDTO responseDTO = null;
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//
//        try {
//
//            responseObjectsMap.put(
//                    "partyMasterList",
//                    partyMasterService.getPartyMasterByOrgId(orgId, branch));
//
//            responseObjectsMap.put(
//                    CommonConstant.STRING_MESSAGE,
//                    "Party Master list retrieved successfully");
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName,
//                    errorMsg);
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap,
//                    "Party Master retrieval failed",
//                    errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok().body(responseDTO);
//    }

}
