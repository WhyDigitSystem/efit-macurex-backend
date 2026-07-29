package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MappingDetailsVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;

public interface MappingDetailsRepo extends JpaRepository<MappingDetailsVO, Long>{

	List<MappingDetailsVO> findByMappingOfPartyToAccVO(MappingOfPartyToAccVO mappingOfPartyToAccVO);

}
