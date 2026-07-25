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

import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.LocationDTO;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;

import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.*;

@Service
public class TransportMasterServiceImpl implements TransportMasterService {

    private final FinancialYearRepo financialYearRepo;


	@Autowired
	BranchRepo branchRepo;
    @Autowired
	LocationRepo locationRepo;
    @Autowired 
    ListOfValuesRepo listOfValuesRepo;
    @Autowired
    CurrencyRepo currencyRepo;
    
    @Autowired
    LMERepo lMERepo;

    TransportMasterServiceImpl(FinancialYearRepo financialYearRepo) {
        this.financialYearRepo = financialYearRepo;
    }

	 @Override
	  @Transactional
	  public Map<String, Object> updateCreateLocationMaster(@Valid LocationDTO locationDTO)
	          throws ApplicationException {

	      LocationVO locationVO = new LocationVO();
	      String message;

	      if (ObjectUtils.isNotEmpty(locationDTO.getId())) {

	    	  locationVO = locationRepo.findById(locationDTO.getId())
	                  .orElseThrow(() -> new ApplicationException("Invalid Location Details"));

	          if (!locationVO.getLocationId()
	                  .equalsIgnoreCase(locationDTO.getLocationId())) {

	              if (locationRepo.existsByLocationIdAndOrgId(
	            		  locationDTO.getLocationId(),
	            		  locationDTO.getOrgId())) {

	                  throw new ApplicationException(
	                          "The Location : " + locationDTO.getLocationId()
	                                  + " already exists in this Organization.");
	              }
	          }

	          createUpdateLocationVOByLocationDTO(locationDTO, locationVO);

	          locationVO.setUpdatedBy(locationDTO.getCreatedBy());

	          message = "Location Updated Successfully";

	      } else {

	          if (locationRepo.existsByLocationIdAndOrgId(
	        		  locationDTO.getLocationId(),
	        		  locationDTO.getOrgId())) {

	              throw new ApplicationException(
	                      "The Location : " + locationDTO.getLocationId()
	                              + " already exists in this Organization.");
	          }

	          createUpdateLocationVOByLocationDTO(locationDTO, locationVO);

	          locationVO.setCreatedBy(locationDTO.getCreatedBy());
	          locationVO.setUpdatedBy(locationDTO.getCreatedBy());

	          message = "Location Created Successfully";
	      }

	      locationRepo.save(locationVO);

	      Map<String, Object> response = new HashMap<>();
	      response.put("locationVO", locationVO);
	      response.put("message", message);

	      return response;
	  }
	  
	  private void createUpdateLocationVOByLocationDTO(
			  LocationDTO locationDTO,
			  LocationVO locationVO) throws ApplicationException {

		  locationVO.setLocationId(locationDTO.getLocationId().toUpperCase());
		  locationVO.setOrgId(locationDTO.getOrgId());
		  locationVO.setPhoneNo(locationDTO.getPhoneNo());
		  locationVO.setFaxNo(locationDTO.getFaxNo());
		  locationVO.setEmail(locationDTO.getEmail());
		  locationVO.setConsiderMrp(locationDTO.getConsiderMrp());
		  locationVO.setAddress(locationDTO.getAddress());
		  locationVO.setPhoneNo(locationDTO.getPhoneNo());
		  

		  locationVO.setCancelRemarks(locationDTO.getCancelRemarks());
		    if (locationDTO.getBranch() != null && locationDTO.getBranch() != 0) {

	            BranchVO branch = branchRepo.findById(locationDTO.getBranch())
	                    .orElseThrow(() ->
	                            new ApplicationException("branch Not Found"));

	            locationVO.setBranch(branch);
	        }
		    if (locationDTO.getLocationType() != null && locationDTO.getLocationType() != 0) {

	            ListOfValuesVO listOfValues = listOfValuesRepo.findById(locationDTO.getLocationType())
	                    .orElseThrow(() ->
	                            new ApplicationException("location type Not Found"));

	            locationVO.setLocationType(listOfValues);
	        }
		    if (locationDTO.getBelongsTo() != null && locationDTO.getBelongsTo() != 0) {

	            ListOfValuesVO listOfValues = listOfValuesRepo.findById(locationDTO.getBelongsTo())
	                    .orElseThrow(() ->
	                            new ApplicationException(" BelongsTo Not Found"));

	            locationVO.setBelongsTo(listOfValues);
	        }
		   
		    
		    
		}


		@Override
		public LocationVO getLocationById(Long id) throws ApplicationException {

		    return locationRepo.findById(id)
		            .orElseThrow(() -> new ApplicationException("Invalid Location Details"));
		}

		@Override
		public List<LocationVO> getLocationByOrgId(Long orgId,Long branchCode) throws ApplicationException {

		    List<LocationVO> transportList =
		    		locationRepo.findByOrgIdAndBranch(orgId,branchCode);

		    if (transportList.isEmpty()) {
		        throw new ApplicationException("No Location Details Found");
		    }

		    return transportList;
		}

		
//LME
		 @Override
		  @Transactional
		  public Map<String, Object> updateCreateLMEMaster(@Valid LMEDTO lMEDTO)
		          throws ApplicationException {

		      LMEVO lMEVO = new LMEVO();
		      String message;

		      if (ObjectUtils.isNotEmpty(lMEDTO.getId())) {

		    	  lMEVO = lMERepo.findById(lMEDTO.getId())
		                  .orElseThrow(() -> new ApplicationException("Invalid LME Details"));

		          if (!lMEVO.getId()
		                  .equals(lMEDTO.getId())) {

		              if (lMERepo.existsByIdAndOrgId(
		            		  lMEDTO.getId(),
		            		  lMEDTO.getOrgId())) {

		                  throw new ApplicationException(
		                          "The LME : " + lMEDTO.getId()
		                                  + " already exists in this Organization.");
		              }
		          }

		          createUpdateLMEVOByLMEDTO(lMEDTO, lMEVO);

		          lMEVO.setUpdatedBy(lMEDTO.getCreatedBy());

		          message = "LME Updated Successfully";

		      } else {

		          if (lMERepo.existsByIdAndOrgId(
		        		  lMEDTO.getId(),
		        		  lMEDTO.getOrgId())) {

		              throw new ApplicationException(
		                      "The LME : " + lMEDTO.getId()
		                              + " already exists in this Organization.");
		          }

		          createUpdateLMEVOByLMEDTO(lMEDTO, lMEVO);

		          lMEVO.setCreatedBy(lMEDTO.getCreatedBy());
		          lMEVO.setUpdatedBy(lMEDTO.getCreatedBy());

		          message = "LME Created Successfully";
		      }

		      lMERepo.save(lMEVO);

		      Map<String, Object> response = new HashMap<>();
		      response.put("lMEVO", lMEVO);
		      response.put("message", message);

		      return response;
		  }
		  
		  private void createUpdateLMEVOByLMEDTO(
				  LMEDTO lMEDTO,
				  LMEVO lMEVO) throws ApplicationException {

			  lMEVO.setOrgId(lMEDTO.getOrgId());
			  lMEVO.setLmeRate(lMEDTO.getLmeRate());
			  lMEVO.setLmeDateFrom(lMEDTO.getLmeDateFrom());
			  lMEVO.setElmeDateTo(lMEDTO.getElmeDateTo());
			  lMEVO.setFinYear(lMEDTO.getFinyear());
			  lMEVO.setActive(lMEDTO.getActive());
			  lMEVO.setCreatedBy(lMEDTO.getCreatedBy());

			  lMEVO.setCancelRemarks(lMEDTO.getCancelRemarks());
			    if (lMEDTO.getBranch() != null && lMEDTO.getBranch() != 0) {

		            BranchVO branch = branchRepo.findById(lMEDTO.getBranch())
		                    .orElseThrow(() ->
		                            new ApplicationException("branch Not Found"));

		            lMEVO.setBranch(branch);
		        }
			    if (lMEDTO.getCurrencyName() != null && lMEDTO.getCurrencyName() != 0) {

			    	CurrencyVO currencyVO = currencyRepo.findById(lMEDTO.getCurrencyName())
		                    .orElseThrow(() ->
		                            new ApplicationException(" Currency Name Not Found"));

			    	lMEVO.setCurrencyName(currencyVO);
		        }
			   
			    
			    
			}


			@Override
			public LMEVO getLMEById(Long id) throws ApplicationException {

			    return lMERepo.findById(id)
			            .orElseThrow(() -> new ApplicationException("Invalid LMES Details"));
			}

			@Override
			public List<LMEVO> getLMEByOrgId(Long orgId,Long branchCode) throws ApplicationException {

			    List<LMEVO> transportList =
			    		lMERepo.findByOrgIdAndBranch(orgId,branchCode);

			    if (transportList.isEmpty()) {
			        throw new ApplicationException("No LME Details Found");
			    }

			    return transportList;
			}
// Financial Year 
			 @Override
			  @Transactional
			  public Map<String, Object> createUpdateFinancialYear(@Valid FinancialYearDTO financialYearDTO)
			          throws ApplicationException {

			      FinancialYearVO financialYearVO = new FinancialYearVO();
			      String message;

			      if (ObjectUtils.isNotEmpty(financialYearDTO.getId())) {

			    	  financialYearVO = financialYearRepo.findById(financialYearDTO.getId())
			                  .orElseThrow(() -> new ApplicationException("Invalid Financial Year Details"));

			          if (!financialYearVO.getId()
			                  .equals(financialYearDTO.getId())) {

			              if (financialYearRepo.existsByIdAndOrgId(
			            		  financialYearDTO.getId(),
			            		  financialYearDTO.getOrgId())) {

			                  throw new ApplicationException(
			                          "The Financial Year : " + financialYearDTO.getId()
			                                  + " already exists in this Organization.");
			              }
			          }

			          createUpdateFinancialYearVOByFinancialYearDTO(financialYearDTO, financialYearVO);

			          financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());

			          message = "Financil Year Updated Successfully";

			      } else {

			          if (financialYearRepo.existsByIdAndOrgId(
			        		  financialYearDTO.getId(),
			        		  financialYearDTO.getOrgId())) {

			              throw new ApplicationException(
			                      "The Financil Year  : " + financialYearDTO.getId()
			                              + " already exists in this Organization.");
			          }

			          createUpdateFinancialYearVOByFinancialYearDTO(financialYearDTO, financialYearVO);

			          financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
			          financialYearVO.setUpdatedBy(financialYearDTO.getCreatedBy());

			          message = "Financil Year Created Successfully";
			      }

			      financialYearRepo.save(financialYearVO);

			      Map<String, Object> response = new HashMap<>();
			      response.put("financialYearVO", financialYearVO);
			      response.put("message", message);

			      return response;
			  }
			  
			  private void createUpdateFinancialYearVOByFinancialYearDTO(
					  FinancialYearDTO financialYearDTO,
					  FinancialYearVO financialYearVO) throws ApplicationException {

				  financialYearVO.setOrgId(financialYearDTO.getOrgId());
				  financialYearVO.setFinYear(financialYearDTO.getFinYear());
				  financialYearVO.setStartDate(financialYearDTO.getStartDate());
				  financialYearVO.setEndDate(financialYearDTO.getEndDate());
				  financialYearVO.setCreatedBy(financialYearDTO.getCreatedBy());
				  financialYearVO.setActive(financialYearDTO.isActive());
				  financialYearVO.setCancelRemarks(financialYearDTO.getCancelRemarks());
				  
				  
				  
				}


				@Override
				public FinancialYearVO getFinancialYearById(Long id) throws ApplicationException {

				    return financialYearRepo.findById(id)
				            .orElseThrow(() -> new ApplicationException("Invalid Financial Year Details"));
				}

				@Override
				public List<FinancialYearVO> getFinancialYearByOrgId(Long orgId) throws ApplicationException {

				    List<FinancialYearVO> transportList =
				    		financialYearRepo.findFinancialYearByOrgId(orgId);

				    if (transportList.isEmpty()) {
				        throw new ApplicationException("No Financial Year Details Found");
				    }

				    return transportList;
				}
			

}
