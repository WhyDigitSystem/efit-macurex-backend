package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.TransportRepo;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ListOfValuesDetailsDTO;
import com.efitops.basesetup.dto.TransportMasterDTO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.exception.ApplicationException;


@Service
public class TransportMasterServiceImpl implements TransportMasterService {

    private final ListOfValuesDetailsRepo listOfValuesDetailsRepo;

    private final ListOfValuesRepo listOfValuesRepo;
	
	@Autowired
	TransportRepo transportMasterrepo;


    TransportMasterServiceImpl(ListOfValuesRepo listOfValuesRepo, ListOfValuesDetailsRepo listOfValuesDetailsRepo) {
        this.listOfValuesRepo = listOfValuesRepo;
        this.listOfValuesDetailsRepo = listOfValuesDetailsRepo;
    }
	
	
	@Override
	@Transactional
	public Map<String, Object> updateCreateTransportMaster(@Valid TransportMasterDTO transportMasterDTO)
	        throws ApplicationException {

	    String screenCode = "TM";
	    TransportMasterVO transportMasterVO = new TransportMasterVO();
	    String message;

	    if (ObjectUtils.isNotEmpty(transportMasterDTO.getId())) {

	        transportMasterVO = transportMasterrepo.findById(transportMasterDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Transport not found"));

	        if (!transportMasterVO.getTransportName()
	                .equalsIgnoreCase(transportMasterDTO.getTransportName())) {

	            if (transportMasterrepo.existsByTransportNameAndOrgId(
	                    transportMasterDTO.getTransportName(),
	                    transportMasterDTO.getOrgId())) {

	                throw new ApplicationException(
	                        "The Transport : " + transportMasterDTO.getTransportName()
	                                + " already exists in this Organization.");
	            }
	        }

	        createUpdateTransportMasterVOByTransportMasterDTO(
	                transportMasterDTO, transportMasterVO);

	        transportMasterVO.setCreatedBy(transportMasterDTO.getCreatedBy());

	        message = "Transport Updated Successfully";

	    } else {

	        if (transportMasterrepo.existsByTransportNameAndOrgId(
	                transportMasterDTO.getTransportName(),
	                transportMasterDTO.getOrgId())) {

	            throw new ApplicationException(
	                    "The Transport : " + transportMasterDTO.getTransportName()
	                            + " already exists in this Organization.");
	        }

	        createUpdateTransportMasterVOByTransportMasterDTO(
	                transportMasterDTO, transportMasterVO);

	       

	       
	        transportMasterVO.setTransportName(transportMasterDTO.getTransportName());
	        transportMasterVO.setAddress(transportMasterDTO.getAddress());
	        transportMasterVO.setCreatedBy(transportMasterDTO.getCreatedBy());
	        transportMasterVO.setUpdated_By(transportMasterDTO.getCreatedBy());
	        transportMasterVO.setActive(transportMasterDTO.getActive());
	        transportMasterVO.setOrgId(transportMasterDTO.getOrgId());
	        transportMasterVO.setBranchCode(transportMasterDTO.getBranchCode());
	        transportMasterVO.setCancel(transportMasterDTO.isCancel());
	        transportMasterVO.setCancelRemarks(transportMasterDTO.getCancelRemarks());
	        

	        message = "Transport Created Successfully";
	    }

	    transportMasterrepo.save(transportMasterVO);

	    Map<String, Object> response = new HashMap<>();
	    response.put("transportMasterVO", transportMasterVO);
	    response.put("message", message);

	    return response;
	}


	private void createUpdateTransportMasterVOByTransportMasterDTO(@Valid TransportMasterDTO transportMasterDTO,
			TransportMasterVO transportMasterVO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object getTransportNameById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getTransportNameByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}
//           --------------ListOfVlaues----------------	
//	public Map<String, Object> updateCreateListOfValues(@Valid ListOfValuesDTO listOfValuesDTO)
//	        throws ApplicationException {
//
//		ListOfValuesVO listOfValuesVO = new ListOfValuesVO();
//	    String message;
//
//	    if (ObjectUtils.isNotEmpty(listOfValuesDTO.getId())) {
//
//	    	listOfValuesVO = listOfValuesRepo.findById(listOfValuesDTO.getId())
//	                .orElseThrow(() -> new ApplicationException("List not found"));
//
//	        if (!listOfValuesVO.getListCode().equalsIgnoreCase(listOfValuesDTO.getListCode())) {
//
//	            if (listOfValuesRepo.existsByListCodeAndOrgId(
//	            		listOfValuesDTO.getListCode(),
//	            		listOfValuesDTO.getOrgId())) {
//
//	                throw new ApplicationException(
//	                        "List of VAlues Code already exists.");
//	            }
//	        }
//
//	        copyListDTOToVO(listOfValuesDTO, listOfValuesVO);
//
//	        listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
//
//	        message = "List Updated Successfully";
//
//	    } else {
//
//	        if (listOfValuesRepo.existsByListCodeAndOrgId(
//	        		listOfValuesDTO.getListCode(),
//	        		listOfValuesDTO.getOrgId())) {
//
//	            throw new ApplicationException(
//	                    "List of Values Code already exists.");
//	        }
//
//	        copyListDTOToVO(listOfValuesDTO, listOfValuesVO);
//
//	        listOfValuesVO.setCreatedBy(listOfValuesDTO.getCreatedBy());
//	        listOfValuesVO.setUpdatedBy(listOfValuesDTO.getCreatedBy());
//
//	        listOfValuesVO = listOfValuesRepo.save(listOfValuesVO);
//
//	        message = "List Created Successfully";
//	    }
//
//	    listOfValuesVO = listOfValuesRepo.save(listOfValuesVO);
//
//	    //---------------- Save Details ----------------//
//
//	    listOfValuesDetailsRepo.deleteByListOfValuesVO_Id(listOfValuesVO.getId());
//
//	    List<ListOfValuesDetailsVO> detailsList = new ArrayList<>();
//
//	    for (ListOfValuesDetailsDTO detailDTO : listOfValuesDTO.getDetails()) {
//
//	    	ListOfValuesDetailsVO detailVO = new ListOfValuesDetailsVO();
//
//	        detailVO.setId(listOfValuesVO.getId());
//	        detailVO.setValueCode(detailDTO.getValueCode());
//	        detailVO.setValueDescription(detailDTO.getValueDescription());
//	        detailVO.setActive(detailDTO.isActive());
//
//	        detailsList.add(detailVO);
//	    }
//
//	    listOfValuesDetailsRepo .saveAll(detailsList);
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    response.put("message", message);
//	    response.put("listOfValuesVO", listOfValuesVO);
//	    response.put("ListOfValuesDetailsVO", detailsList);
//
//	    return response;
//	}
//
//
//	private void copyListDTOToVO(@Valid ListOfValuesDTO listOfValuesDTO, ListOfValuesVO listOfValuesVO) {
//		listOfValuesVO.setListCode(listOfValuesDTO.getListCode());
//		listOfValuesVO.setListDescription(listOfValuesDTO.getListDescription());
//		listOfValuesVO.setActive(listOfValuesDTO.isActive());
//		listOfValuesVO.setOrgId(listOfValuesDTO.getOrgId());
//		
//	}
//
//
//	@Override
//	public Map<String, Object> getById(Long id) throws ApplicationException  {
//		ListOfValuesVO listOfValuesVO = listOfValuesRepo.findById(id)
//		            .orElseThrow(() ->
//		                    new ApplicationException("List of values not found"));
//
//		    Optional<ListOfValuesDetailsVO> details =
//		    		listOfValuesDetailsRepo.findById(id);
//
//		    Map<String, Object> response = new HashMap<>();
//
//		    response.put("listOfValuesVO", listOfValuesVO);
//		    response.put("listDetailsVO", details);
//
//		    return response;
//		
//		
//	}


//	@Override
//	public Map<String, Object> getByOrgId(Long orgId) throws ApplicationException{
//		 List<ListOfValuesVO> list =
//				 listOfValuesRepo.findByOrgId(orgId);
//
//		    Map<String, Object> response = new HashMap<>();
//
//		    response.put("listOfValuesVO", list);
//
//		    return response;
//		
//		
//	}
}

