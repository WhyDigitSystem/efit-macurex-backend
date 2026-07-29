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

import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.HolidayMasterDetailsDTO;
import com.efitops.basesetup.dto.MappingDetailsDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.dto.TaxDefinitionDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.HolidayMasterDetailsVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.MappingDetailsVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionDetailsVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.HolidayMasterDetailsRepo;
import com.efitops.basesetup.repository.HolidayMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.MappingDetailsRepo;
import com.efitops.basesetup.repository.MappingPartyToAccRepo;
import com.efitops.basesetup.repository.PartyProjection;
import com.efitops.basesetup.repository.TSBankRepo;
import com.efitops.basesetup.repository.TaxDefinitionDetailsRepo;
import com.efitops.basesetup.repository.TaxDefinitionRepo;




@Service
public class TransportMasterServiceImpl implements TransportMasterService {
	
	@Autowired
	TSBankRepo tSBankRepo;
	
	@Autowired
	TaxDefinitionRepo taxDefinitionRepo;
	
	@Autowired
	ListOfValuesRepo listOfValuesRepo;
	
	@Autowired
	TaxDefinitionDetailsRepo taxDefinitionDetailsRepo;
	
	@Autowired
	HolidayMasterRepo holidayMasterRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	HolidayMasterDetailsRepo holidayMaterDetailsRepo;
	
	@Autowired
	MappingPartyToAccRepo mappingPartyToAccRepo;
	
	@Autowired
	MappingDetailsRepo mappingDetailsRepo;
	
	@Autowired
	CustomerRepo customerRepo;

	  @Override
	  @Transactional
	  public Map<String, Object> createUpdateBankMaster(@Valid TSBankDTO tSBankDTO)
	          throws ApplicationException {

	      TSBankVO tSBankVO = new TSBankVO();
	      String message;

	      if (ObjectUtils.isNotEmpty(tSBankDTO.getId())) {

	    	  tSBankVO = tSBankRepo.findById(tSBankDTO.getId())
	                  .orElseThrow(() -> new ApplicationException("Invalid Bank Rate Master Details"));

//	          if (!tSBankVO.getbank()
//	                  .equalsIgnoreCase(tSBankDTO.getBank())) {
//
//	              if (tSBankRepo.existsByBankAndOrgId(
//	            		  tSBankDTO.getBank(),
//	            		  tSBankDTO.getOrgId())) {
//
//	                  throw new ApplicationException(
//	                          "The Bank  Master : " + tSBankDTO.getBank()
//	                                  + " already exists in this Organization.");
//	              }
//	          }

	          createUpdateTSBankVOByTSBankDTO(tSBankDTO, tSBankDTO);

	          tSBankVO.setUpdatedBy(tSBankDTO.getCreatedBy());

	          message = "Bank master Updated Successfully";

	      } else {

	          if (tSBankRepo.existsByBankAndOrgId(
	        		  tSBankDTO.getBank(),
	        		  tSBankDTO.getOrgId())) {

	              throw new ApplicationException(
	                      "The Bank  Master : " + tSBankDTO.getBank()
	                              + " already exists in this Organization.");
	          }

	          createUpdateTSBankVOByTSBankDTO(tSBankDTO, tSBankDTO);

	          tSBankVO.setCreatedBy(tSBankDTO.getCreatedBy());
	          tSBankVO.setUpdatedBy(tSBankDTO.getCreatedBy());
	          tSBankVO.setBeneficiary(tSBankDTO.getBeneficiary());
	          tSBankVO.setBank(tSBankDTO.getBank());
	          tSBankVO.setAcno(tSBankDTO.getAcno());
	          tSBankVO.setIfscCode(tSBankDTO.getIfscCode());
	          tSBankVO.setBranch(tSBankDTO.getBranch()); 
	          tSBankVO.setCancelRemarks(tSBankDTO.getCancelRemarks());
	          tSBankVO.setActive(tSBankDTO.isActive());
	          tSBankVO.setOrgId(tSBankDTO.getOrgId());
	          


	          message = "Bank Master Created Successfully";
	      }

	      tSBankRepo.save(tSBankVO);

	      Map<String, Object> response = new HashMap<>();
	      response.put("tSBankVO", tSBankVO);
	      response.put("message", message);

	      return response;
	  }
	  
	  private void createUpdateTSBankVOByTSBankDTO(@Valid TSBankDTO tSBankDTO,
			@Valid TSBankDTO tSBankDTO2) throws ApplicationException {
		
		  tSBankDTO2.setBank(tSBankDTO.getBank().toUpperCase());
		  tSBankDTO2.setBeneficiary(tSBankDTO.getBeneficiary());
		  tSBankDTO2.setOrgId(tSBankDTO.getOrgId());
		  tSBankDTO2.setAcno(tSBankDTO.getAcno()); 
		  tSBankDTO2.setBranch(tSBankDTO.getBranch());
		  tSBankDTO2.setIfscCode(tSBankDTO.getIfscCode());
		  tSBankDTO2.setCreatedBy(tSBankDTO.getCreatedBy());
		  tSBankDTO2.setCancelRemarks(tSBankDTO.getCancelRemarks());
		  tSBankDTO2.setActive(tSBankDTO.isActive());

		}
		@Override
		public TSBankVO getBankMasterById(Long id) throws ApplicationException {

		    return tSBankRepo.findById(id)
		            .orElseThrow(() -> new ApplicationException("Invalid Bank  Master Details"));
		}

		@Override
		public List<TSBankVO> getBankMasterByOrgId(Long orgId) throws ApplicationException {

		    List<TSBankVO> tSBankVO =
		    		tSBankRepo.getBankMasterByOrgId(orgId);

		    if (tSBankVO.isEmpty()) {
		        throw new ApplicationException("No Bank Master Details Found");
		    }

		    return tSBankVO;
		}

		// TAX Definition
		@Override
		@Transactional
		public Map<String, Object> updateCreateTaxDefinition(@Valid TaxDefinitionDTO dto)
		        throws ApplicationException {

		    TaxDefinitionVO taxDefinitionVO = new TaxDefinitionVO();
		    String message;

		    if (ObjectUtils.isNotEmpty(dto.getId()) && dto.getId() != 0) {

		        taxDefinitionVO = taxDefinitionRepo.findById(dto.getId())
		                .orElseThrow(() ->
		                        new ApplicationException("Invalid Tax Definition"));

		        taxDefinitionVO.setUpdatedBy(dto.getCreatedBy());

		        if (!taxDefinitionVO.getTaxNo().equals(dto.getTaxNo())) {

		            if (taxDefinitionRepo.existsByTaxNoAndOrgId(dto.getTaxNo(), dto.getOrgId())) {

		                throw new ApplicationException("Tax No already exists.");
		            }
		        }

		        createUpdateTaxDefinitionVO(dto, taxDefinitionVO);

		        message = "Tax Definition Updated Successfully";

		    } else {

		        if (taxDefinitionRepo.existsByTaxNoAndOrgId(dto.getTaxNo(), dto.getOrgId())) {

		            throw new ApplicationException("Tax No already exists.");
		        }

		       
		        createUpdateTaxDefinitionVO(dto, taxDefinitionVO);

		        message = "Tax Definition Created Successfully";
		    }

		    taxDefinitionRepo.save(taxDefinitionVO);

		    Map<String, Object> response = new HashMap<>();
		    response.put("taxDefinitionVO", taxDefinitionVO);
		    response.put("message", message);

		    return response;
		}
		private void createUpdateTaxDefinitionVO(TaxDefinitionDTO dto,
		        TaxDefinitionVO taxDefinitionVO) {

			if (dto.getBranch() != null) {

		        BranchVO lov = branchRepo.findById(dto.getBranch())
		                .orElseThrow(() ->
		                        new RuntimeException("Branch Not Found"));

		        taxDefinitionVO.setBranch(lov);
		    }
		    // Parent Mapping
		    if (dto.getModule() != null) {

		        ListOfValuesVO lov = listOfValuesRepo.findById(dto.getModule())
		                .orElseThrow(() ->
		                        new RuntimeException("Module Not Found"));

		        taxDefinitionVO.setModule(lov);
		    }

		    taxDefinitionVO.setTaxNo(dto.getTaxNo());
		    taxDefinitionVO.setTaxDescription(dto.getTaxDescription());
		    taxDefinitionVO.setDocDate(dto.getDocDate());
		    taxDefinitionVO.setEffectiveDate(dto.getEffectiveDate());
		    taxDefinitionVO.setFillCopyOF(dto.getFillCopyOF());
		    taxDefinitionVO.setPrintName(dto.getPrintName());
		    taxDefinitionVO.setActive(dto.isActive());
		    taxDefinitionVO.setCancelRemarks(dto.getCancelRemarks());
		    taxDefinitionVO.setOrgId(dto.getOrgId());

		    // Remove old details during update
		    if (taxDefinitionVO.getId() != null) {

		        List<TaxDefinitionDetailsVO> oldDetails =
		                taxDefinitionDetailsRepo.findByTaxDefinitionVO(taxDefinitionVO);

		        taxDefinitionDetailsRepo.deleteAll(oldDetails);
		    }

		    // New Details
		    List<TaxDefinitionDetailsVO> detailsList = new ArrayList<>();

		    if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

		        for (TaxDefinitionDetailsDTO detailDTO : dto.getDetails()) {

		            TaxDefinitionDetailsVO detailVO = new TaxDefinitionDetailsVO();

		            if (detailDTO.getTaxType() != null) {

		                ListOfValuesVO detailLov = listOfValuesRepo
		                        .findById(detailDTO.getTaxType())
		                        .orElseThrow(() ->
		                                new RuntimeException("List Of Value Not Found"));

		                detailVO.setTaxType(detailLov);
		            }
		            if (detailDTO.getTaxName() != null) {

		                ListOfValuesVO detailLov = listOfValuesRepo
		                        .findById(detailDTO.getTaxName())
		                        .orElseThrow(() ->
		                                new RuntimeException("List Of Value Not Found"));

		                detailVO.setTaxName(detailLov);
		            }

		            detailVO.setAddLess(detailDTO.getAddLess());
		            detailVO.setTaxPercent(detailDTO.getTaxPercent());
		            detailVO.setTaxId(detailDTO.getTaxId());
		            detailVO.setFormula(detailDTO.getFormula());
		            detailVO.setPostToFinance(detailDTO.getPostToFinance());
		            detailVO.setDbCr(detailDTO.getDbCr());
		            detailVO.setGlAccountName(detailDTO.getGlAccountName());
		            detailVO.setPrint(detailDTO.getPrint());
		            detailVO.setTaxPost(detailDTO.getTaxPost());

		            // Parent Mapping
		            detailVO.setTaxDefinitionVO(taxDefinitionVO);

		            detailsList.add(detailVO);
		        }
		    }

		    taxDefinitionVO.setTaxDefinitionDetailsVO(detailsList);
		}

		@Override
		public TaxDefinitionVO getTaxDefinitionById(Long id)
		        throws ApplicationException {

		    if (ObjectUtils.isEmpty(id)) {
		        throw new ApplicationException("Invalid Id");
		    }

		    return taxDefinitionRepo.findById(id)
		            .orElseThrow(() ->
		                    new ApplicationException("Tax Definition Not Found"));
		}
		@Override
		public List<TaxDefinitionVO> getTaxDefinitionByOrgId(Long orgId,Long branch)
		        throws ApplicationException {

		    List<TaxDefinitionVO> list =
		            taxDefinitionRepo.getTaxDefinitionByOrgId(orgId ,branch);

		    if (list.isEmpty()) {
		        throw new ApplicationException("No Tax Definition Found");
		    }

		    return list;
		}

		//Holliday Master
		

		 @Override
		    @Transactional
		    public Map<String, Object> updateCreateHolidayMaster(@Valid HolidayMasterDTO dto)
		            throws ApplicationException {

		        HolidayMasterVO holidayMasterVO = new HolidayMasterVO();
		        String message;

		        if (ObjectUtils.isNotEmpty(dto.getId())) {

		        	holidayMasterVO = holidayMasterRepo.findById(dto.getId())
		                    .orElseThrow(() ->
		                            new ApplicationException("Holiday Master Not Found"));

		        	holidayMasterVO.setUpdatedBy(dto.getCreatedBy());


		            createUpdateholidayMasterVO(dto, holidayMasterVO);

		            message = "Holiday MAster Updated Successfully";

		        } else {

		            
		        	holidayMasterVO.setCreatedBy(dto.getCreatedBy());
		            holidayMasterVO.setUpdatedBy(dto.getCreatedBy());

		            createUpdateholidayMasterVO(dto, holidayMasterVO);

		            message = "List Of Values Created Successfully";
		        }

		        holidayMasterVO = holidayMasterRepo.save(holidayMasterVO);

		        Map<String, Object> response = new HashMap<>();
		        response.put("message", message);
		        response.put("holidayMasterVO", holidayMasterVO);

		        return response;
		    }
		    
		    private void createUpdateholidayMasterVO(
		            HolidayMasterDTO dto,
		            HolidayMasterVO holidayMasterVO)
		            throws ApplicationException {

		    	holidayMasterVO.setDate(dto.getDate());
		    	holidayMasterVO.setOrgId(dto.getOrgId());
		    	holidayMasterVO.setActive(dto.getActive());
		    	holidayMasterVO.setCancelRemarks(dto.getCancelRemarks());

		        if (dto.getBranch() != null && dto.getBranch() != 0) {

		            BranchVO branch = branchRepo.findById(dto.getBranch())
		                    .orElseThrow(() ->
		                            new ApplicationException("Branch Not Found"));

		            holidayMasterVO.setBranch(branch);
		        }

		        if (dto.getId() != null) {

		            List<HolidayMasterDetailsVO> oldDetails =
		            		holidayMaterDetailsRepo.findByHolidayMasterVO(holidayMasterVO);

		            holidayMaterDetailsRepo.deleteAll(oldDetails);
		        }

		        List<HolidayMasterDetailsVO> detailList = new ArrayList<>();

		        for (HolidayMasterDetailsDTO detailDTO : dto.getDetails()) {

		        	HolidayMasterDetailsVO detailVO =
		                    new HolidayMasterDetailsVO();

		            detailVO.setHolidayDate(detailDTO.getHolidayDate());
		            detailVO.setDay(detailDTO.getDay());
		            detailVO.setHolidayType(detailDTO.getHolidayType());
		            detailVO.setRemarks(detailDTO.getRemarks());
		            detailVO.setCompensatory(detailDTO.getCompensatory());
		            detailVO.setCompensatoryDate(detailDTO.getCompensatoryDate());



		            detailVO.setHolidayMasterVO(holidayMasterVO);

		            detailList.add(detailVO);
		        }

		        holidayMasterVO.setHolidayMasterDetailsVO(detailList);
		        }

			@Override
			public HolidayMasterVO getHolidayMasterById(Long id) {

				return holidayMasterRepo.getHolidayMasterById(id);

			}

			@Override
			public List<HolidayMasterVO> getHolidayMasterByOrgId(Long orgId,Long branch) {

				return holidayMasterRepo.getHolidayMasterByOrgId(orgId,branch);
			}

			
// Mapping Party to Account
			
			 @Override
			    @Transactional
			    public Map<String, Object> updateCreateMappingOfPartyToAcc(@Valid MappingOfPartyToAccDTO dto)
			            throws ApplicationException {

			        MappingOfPartyToAccVO mappingOfPartyToAccVO = new MappingOfPartyToAccVO();
			        String message;

			        if (ObjectUtils.isNotEmpty(dto.getId())) {

			        	mappingOfPartyToAccVO = mappingPartyToAccRepo.findById(dto.getId())
			                    .orElseThrow(() ->
			                            new ApplicationException("Mapping  Not Found"));

			        	mappingOfPartyToAccVO.setUpdatedBy(dto.getCreatedBy());


			            createUpdatemappingOfPartyToAccVO(dto, mappingOfPartyToAccVO);

			            message = "Mapping  Updated Successfully";

			        } else {

			            
			        	mappingOfPartyToAccVO.setCreatedBy(dto.getCreatedBy());
			        	mappingOfPartyToAccVO.setUpdatedBy(dto.getCreatedBy());

			            createUpdatemappingOfPartyToAccVO(dto, mappingOfPartyToAccVO);

			            message = "Mapping Created Successfully";
			        }

			        mappingOfPartyToAccVO = mappingPartyToAccRepo.save(mappingOfPartyToAccVO);

			        Map<String, Object> response = new HashMap<>();
			        response.put("message", message);
			        response.put("mappingOfPartyToAccVO", mappingOfPartyToAccVO);

			        return response;
			    }
			    
			    private void createUpdatemappingOfPartyToAccVO(
			    		MappingOfPartyToAccDTO dto,
			            MappingOfPartyToAccVO mappingOfPartyToAccVO)
			            throws ApplicationException {

			    	
			    	mappingOfPartyToAccVO.setDocDate(dto.getDocDate());
			    	mappingOfPartyToAccVO.setAsOnDate(dto.getAsOnDate());
			    	mappingOfPartyToAccVO.setOrgId(dto.getOrgId());
			    	mappingOfPartyToAccVO.setActive(dto.isActive());
			    	mappingOfPartyToAccVO.setCancelRemarks(dto.getCancelRemarks());
			    	

			        if (dto.getBranch() != null && dto.getBranch() != 0) {

			            BranchVO branch = branchRepo.findById(dto.getBranch())
			                    .orElseThrow(() ->
			                            new ApplicationException("Branch Not Found"));

			            mappingOfPartyToAccVO.setBranch(branch);
			        }
			        if (dto.getCategory() != null && dto.getCategory() != 0) {

			            ListOfValuesVO category = listOfValuesRepo.findById(dto.getCategory())
			                    .orElseThrow(() ->
			                            new ApplicationException("Category Not Found"));

			            mappingOfPartyToAccVO.setCategory(category);
			        }

			        if (dto.getId() != null) {

			            List<MappingDetailsVO> oldDetails =
			            		mappingDetailsRepo.findByMappingOfPartyToAccVO(mappingOfPartyToAccVO);

			            mappingDetailsRepo.deleteAll(oldDetails);
			        }

			        List<MappingDetailsVO> detailList = new ArrayList<>();

			        for (MappingDetailsDTO detailDTO : dto.getDetails()) {

			        	MappingDetailsVO detailVO =
			                    new MappingDetailsVO();

			        	  if (detailDTO.getPartyId() != null) {

				                CustomerVO detailLov = customerRepo
				                        .findById(detailDTO.getPartyId())
				                        .orElseThrow(() ->
				                                new RuntimeException("Party details Not Found"));

				                detailVO.setPartId(detailLov);
				            }
			            detailVO.setAccountName(detailDTO.getAccountName());
			            
			           



			            detailVO.setMappingOfPartyToAccVO(mappingOfPartyToAccVO);

			            detailList.add(detailVO);
			        }

			        mappingOfPartyToAccVO.setMappingDetailsVO(detailList);
			        }

				@Override
				public MappingOfPartyToAccVO getMappingOfPartyToAccById(Long id) {

					return mappingPartyToAccRepo.getMappingOfPartyToAccById(id);

				}

				@Override
				public List<MappingOfPartyToAccVO> getMappingOfPartyToAccByOrgId(Long orgId,Long branch) {

					return mappingPartyToAccRepo.getMappingOfPartyToAccByOrgId(orgId,branch);
				}

				//dropdown api for category
				
//				@Override
//				public Map<String, Object> getCustomerCategory(Long orgId)
//				        throws ApplicationException {
//
//				    Map<String, Object> response = new HashMap<>();
//
//				    List<ListOfValuesVO> customerCategory =
//				            listOfValuesRepo.getCustomerCategory(orgId);
//
//				    if (customerCategory.isEmpty()) {
//				        throw new ApplicationException("No Customer Category Found");
//				    }
//
//				    response.put("customerCategory", customerCategory);
//
//				    return response;
//				}
				
				@Override
				public Map<String, Object> getCustomerCategory(Long orgId)
				        throws ApplicationException {

				    List<Object[]> customerCategory =
				            listOfValuesRepo.getCustomerCategory(orgId);

				    if (customerCategory.isEmpty()) {
				        throw new ApplicationException("No Customer Category Found");
				    }

				    return getCustomerCategoryResponse(customerCategory);
				}

				private Map<String, Object> getCustomerCategoryResponse(List<Object[]> customerCategory) {

				    Map<String, Object> response = new HashMap<>();

				    List<Map<String, Object>> categoryList = new ArrayList<>();

				    for (Object[] category : customerCategory) {

				        Map<String, Object> map = new HashMap<>();
				        map.put("id", category[0]);
				        map.put("listCode", category[1]);
				        map.put("listDescription", category[2]);

				        categoryList.add(map);
				    }

				    response.put("customerCategory", categoryList);

				    return response;
				}
		
				// Dropdown for Party Id
				@Override
				public Map<String, Object> getParty(Long category,
				                                    Long orgId,
				                                    Long branch)
				        throws ApplicationException {

				    Map<String, Object> response = new HashMap<>();

				    List<PartyProjection> partyList =
				            customerRepo.getParty(category, orgId, branch);

				    if (partyList.isEmpty()) {
				        throw new ApplicationException("No Party Found");
				    }

				    response.put("partyList", partyList);

				    return response;
				}
}