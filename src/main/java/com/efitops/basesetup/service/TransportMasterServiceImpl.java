package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ListOfValuesDetailsDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.ServiceAccMasterRepo;

@Service
public class TransportMasterServiceImpl implements TransportMasterService {

    private final ServiceAccMasterRepo serviceAccMasterRepo;

    private final GstRateMasterRepo gstRateMasterRepo;

	@Autowired
	ListOfValuesRepo listOfValuesRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;
	
	@Autowired
	BranchRepo branchRepo;

    TransportMasterServiceImpl(GstRateMasterRepo gstRateMasterRepo, ServiceAccMasterRepo serviceAccMasterRepo) {
        this.gstRateMasterRepo = gstRateMasterRepo;
        this.serviceAccMasterRepo = serviceAccMasterRepo;
    }

    @Override
    @Transactional
    public Map<String, Object> updateCreateListOfValues(@Valid ListOfValuesDTO dto)
            throws ApplicationException {

        ListOfValuesVO listVO = new ListOfValuesVO();
        String message;

        if (ObjectUtils.isNotEmpty(dto.getId())) {

            listVO = listOfValuesRepo.findById(dto.getId())
                    .orElseThrow(() ->
                            new ApplicationException("List Of Values Not Found"));

            listVO.setUpdatedBy(dto.getCreatedBy());

            if (!listVO.getListCode().equalsIgnoreCase(dto.getListCode())) {

                if (listOfValuesRepo.existsByListCodeAndOrgId(
                        dto.getListCode(),
                        dto.getOrgId())) {

                    throw new ApplicationException("List Code already exists.");
                }
            }

            createUpdateListVO(dto, listVO);

            message = "List Of Values Updated Successfully";

        } else {

            if (listOfValuesRepo.existsByListCodeAndOrgId(
                    dto.getListCode(),
                    dto.getOrgId())) {

                throw new ApplicationException("List Code already exists.");
            }

            listVO.setCreatedBy(dto.getCreatedBy());
            listVO.setUpdatedBy(dto.getCreatedBy());

            createUpdateListVO(dto, listVO);

            message = "List Of Values Created Successfully";
        }

        listVO = listOfValuesRepo.save(listVO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("listOfValuesVO", listVO);

        return response;
    }
    
    private void createUpdateListVO(
            ListOfValuesDTO dto,
            ListOfValuesVO listVO)
            throws ApplicationException {

        listVO.setListCode(dto.getListCode().toUpperCase());
        listVO.setListDescription(dto.getListDescription().toUpperCase());
        listVO.setOrgId(dto.getOrgId());
        listVO.setActive(dto.isActive());
        listVO.setCancelRemarks(dto.getCancelRemarks());

        if (dto.getBranchId() != null && dto.getBranchId() != 0) {

            BranchVO branch = branchRepo.findById(dto.getBranchId())
                    .orElseThrow(() ->
                            new ApplicationException("Branch Not Found"));

            listVO.setBranch(branch);
        }

        if (dto.getId() != null) {

            List<ListOfValuesDetailsVO> oldDetails =
                    listOfValuesDetailsRepo.findByListOfValuesVO(listVO);

            listOfValuesDetailsRepo.deleteAll(oldDetails);
        }

        List<ListOfValuesDetailsVO> detailList = new ArrayList<>();

        for (ListOfValuesDetailsDTO detailDTO : dto.getDetails()) {

            ListOfValuesDetailsVO detailVO =
                    new ListOfValuesDetailsVO();

            detailVO.setValueCode(detailDTO.getValueCode());
            detailVO.setValueDescription(detailDTO.getValueDescription());
            detailVO.setActive(detailDTO.isActive());

            detailVO.setListOfValuesVO(listVO);

            detailList.add(detailVO);
        }

        listVO.setListOfValuesDetailsVO(detailList);
        }

	@Override
	public ListOfValuesVO getListOfValuesById(Long id) {

		return listOfValuesRepo.getListOfValuesById(id);

	}

	@Override
	public List<ListOfValuesVO> getListOfValuesByOrgId(Long orgId,Long branchId) {

		return listOfValuesRepo.getListOfValuesByOrgId(orgId,branchId);
	}

	
	//GST Rate Master
	
	  @Override
	  @Transactional
	  public Map<String, Object> updateCreateGSTRateMaster(@Valid GSTRateMasterDTO gSTRateMasterDTO)
	          throws ApplicationException {

	      GSTRateMasterVO gSTRateMasterVO = new GSTRateMasterVO();
	      String message;

	      if (ObjectUtils.isNotEmpty(gSTRateMasterDTO.getId())) {

	    	  gSTRateMasterVO = gstRateMasterRepo.findById(gSTRateMasterDTO.getId())
	                  .orElseThrow(() -> new ApplicationException("Invalid GST Rate Master Details"));

	          if (!gSTRateMasterVO.getCategory()
	                  .equalsIgnoreCase(gSTRateMasterDTO.getCategory())) {

	              if (gstRateMasterRepo.existsByCategoryAndOrgId(
	            		  gSTRateMasterDTO.getCategory(),
	            		  gSTRateMasterDTO.getOrgId())) {

	                  throw new ApplicationException(
	                          "The GST Rate Master : " + gSTRateMasterDTO.getCategory()
	                                  + " already exists in this Organization.");
	              }
	          }

	          createUpdateGSTRateMasterVOByGSTRateMasterDTO(gSTRateMasterDTO, gSTRateMasterVO);

	          gSTRateMasterVO.setUpdatedBy(gSTRateMasterDTO.getCreatedBy());

	          message = "Transport Updated Successfully";

	      } else {

	          if (gstRateMasterRepo.existsByCategoryAndOrgId(
	        		  gSTRateMasterDTO.getCategory(),
	        		  gSTRateMasterDTO.getOrgId())) {

	              throw new ApplicationException(
	                      "The GST Rate Master : " + gSTRateMasterDTO.getCategory()
	                              + " already exists in this Organization.");
	          }

	          createUpdateGSTRateMasterVOByGSTRateMasterDTO(gSTRateMasterDTO, gSTRateMasterVO);

	          gSTRateMasterVO.setCreatedBy(gSTRateMasterDTO.getCreatedBy());
	          gSTRateMasterVO.setUpdatedBy(gSTRateMasterDTO.getCreatedBy());

	          message = "GST Rate Master Created Successfully";
	      }

	      gstRateMasterRepo.save(gSTRateMasterVO);

	      Map<String, Object> response = new HashMap<>();
	      response.put("gSTRateMasterVO", gSTRateMasterVO);
	      response.put("message", message);

	      return response;
	  }
	  
	  private void createUpdateGSTRateMasterVOByGSTRateMasterDTO(@Valid GSTRateMasterDTO gSTRateMasterDTO,
			GSTRateMasterVO gSTRateMasterVO) throws ApplicationException {
		
		  gSTRateMasterVO.setCategory(gSTRateMasterDTO.getCategory().toUpperCase());
		  gSTRateMasterVO.setHsncode(gSTRateMasterDTO.getHsncode());
		  gSTRateMasterVO.setOrgId(gSTRateMasterDTO.getOrgId());
		  gSTRateMasterVO.setDescription (gSTRateMasterDTO.getDescription());
		  gSTRateMasterVO.setWef(gSTRateMasterDTO.getWef());
		  gSTRateMasterVO.setIgstRate(gSTRateMasterDTO.getIgstRate());
		  gSTRateMasterVO.setCgstRate(gSTRateMasterDTO.getCgstRate());
		  gSTRateMasterVO.setSgstRate(gSTRateMasterDTO.getSgstRate());
		  gSTRateMasterVO.setRate(gSTRateMasterDTO.getRate());
		  gSTRateMasterVO.setTaxable(gSTRateMasterDTO.getTaxable());
		  gSTRateMasterVO.setCancelRemarks(gSTRateMasterDTO.getCancelRemarks());
		  gSTRateMasterVO.setFinYear(gSTRateMasterDTO.getFinYear());
		  gSTRateMasterVO.setActive(gSTRateMasterDTO.isActive());

		  if (gSTRateMasterDTO.getBranchId() != null && gSTRateMasterDTO.getBranchId() != 0) {

	            BranchVO branch = branchRepo.findById(gSTRateMasterDTO.getBranchId())
	                    .orElseThrow(() ->
	                            new ApplicationException("branch Not Found"));

	            gSTRateMasterVO.setBranch(branch);
	        }

		}
		@Override
		public GSTRateMasterVO getGSTRateMasterById(Long id) throws ApplicationException {

		    return gstRateMasterRepo.findById(id)
		            .orElseThrow(() -> new ApplicationException("Invalid GST Rate Master Details"));
		}

		@Override
		public List<GSTRateMasterVO> getGSTRateByOrgId(Long orgId,Long branchId) throws ApplicationException {

		    List<GSTRateMasterVO> gSTRateMasterVO =
		    		gstRateMasterRepo.getGSTRateByOrgId(orgId,branchId);

		    if (gSTRateMasterVO.isEmpty()) {
		        throw new ApplicationException("No GST Rate Master Details Found");
		    }

		    return gSTRateMasterVO;
		}

		//   ServiceAccMaster
		
		  @Override
		  @Transactional
		  public Map<String, Object> updateCreateServiceAccMaster(@Valid ServiceAccMasterDTO serviceAccMasterDTO)
		          throws ApplicationException {

		      ServiceAccMasterVO serviceAccMasterVO = new ServiceAccMasterVO();
		      String message;

		      if (ObjectUtils.isNotEmpty(serviceAccMasterDTO.getId())) {

		    	  serviceAccMasterVO = serviceAccMasterRepo.findById(serviceAccMasterDTO.getId())
		                  .orElseThrow(() -> new ApplicationException("Invalid Service Accounting Master Details"));

		          if (!serviceAccMasterVO.getServiceName()
		                  .equalsIgnoreCase(serviceAccMasterDTO.getServiceName())) {

		              if (serviceAccMasterRepo.existsByServiceNameAndOrgId(
		            		  serviceAccMasterDTO.getServiceName(),
		            		  serviceAccMasterDTO.getOrgId())) {

		                  throw new ApplicationException(
		                          "The Service Accounting Master : " + serviceAccMasterDTO.getServiceName()
		                                  + " already exists in this Organization.");
		              }
		          }

		          createUpdateServiceAccMasterVOByServiceAccMasterDTO(serviceAccMasterDTO, serviceAccMasterVO);

		          serviceAccMasterVO.setUpdatedBy(serviceAccMasterDTO.getCreatedBy());

		          message = "Service Accounting Master Updated Successfully";

		      } else {

		          if (serviceAccMasterRepo.existsByServiceNameAndOrgId(
		        		  serviceAccMasterDTO.getServiceName(),
		        		  serviceAccMasterDTO.getOrgId())) {

		              throw new ApplicationException(
		                      "The Service Accounting Master : " + serviceAccMasterDTO.getServiceName()
		                              + " already exists in this Organization.");
		          }

		          createUpdateServiceAccMasterVOByServiceAccMasterDTO(serviceAccMasterDTO, serviceAccMasterVO);
		          serviceAccMasterVO.setCreatedBy(serviceAccMasterDTO.getCreatedBy());
		          serviceAccMasterVO.setUpdatedBy(serviceAccMasterDTO.getCreatedBy());

		          message = "Service Accounting Master Created Successfully";
		      }

		      serviceAccMasterRepo.save(serviceAccMasterVO);

		      Map<String, Object> response = new HashMap<>();
		      response.put("serviceAccMasterVO", serviceAccMasterVO);
		      response.put("message", message);

		      return response;
		  }
		  
		  private void createUpdateServiceAccMasterVOByServiceAccMasterDTO(@Valid ServiceAccMasterDTO serviceAccMasterDTO,
				  ServiceAccMasterVO serviceAccMasterVO) throws ApplicationException {
			
			  serviceAccMasterVO.setServiceName(serviceAccMasterDTO.getServiceName().toUpperCase());
			  serviceAccMasterVO.setServiceDescription(serviceAccMasterDTO.getServiceDescription());
			  serviceAccMasterVO.setOrgId(serviceAccMasterDTO.getOrgId());
			  serviceAccMasterVO.setHsncode(serviceAccMasterDTO.getHsncode());
			  serviceAccMasterVO.setActive(serviceAccMasterDTO.isActive());
			  serviceAccMasterVO.setCancelRemarks(serviceAccMasterDTO.getCancelRemarks());
			  if (serviceAccMasterDTO.getBranchId() != null && serviceAccMasterDTO.getBranchId() != 0) {

		            BranchVO branch = branchRepo.findById(serviceAccMasterDTO.getBranchId())
		                    .orElseThrow(() ->
		                            new ApplicationException("branch Not Found"));

		            serviceAccMasterVO.setBranch(branch);
		        }

			}
		  
			@Override
			public ServiceAccMasterVO getServiceNameById(Long id) throws ApplicationException {

			    return serviceAccMasterRepo.findById(id)
			            .orElseThrow(() -> new ApplicationException("Invalid  Service Accounting Master Details"));
			}

			@Override
			public List<ServiceAccMasterVO> getServiceNameByOrgId(Long orgId,Long branchId) throws ApplicationException {

			    List<ServiceAccMasterVO> serviceAccMasterVO =
			    		serviceAccMasterRepo.findByOrgIdAndBranch(orgId,branchId);

			    if (serviceAccMasterVO.isEmpty()) {
			        throw new ApplicationException("No Service Accounting Master Details Found");
			    }

			    return serviceAccMasterVO;
			}

				
		

	

}
