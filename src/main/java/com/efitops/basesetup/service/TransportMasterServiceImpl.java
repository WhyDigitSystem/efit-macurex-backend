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
	
	


    TransportMasterServiceImpl(ListOfValuesRepo listOfValuesRepo, ListOfValuesDetailsRepo listOfValuesDetailsRepo) {
        this.listOfValuesRepo = listOfValuesRepo;
        this.listOfValuesDetailsRepo = listOfValuesDetailsRepo;
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

