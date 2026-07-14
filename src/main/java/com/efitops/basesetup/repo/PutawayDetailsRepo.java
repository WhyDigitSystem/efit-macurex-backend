package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PutawayDetailsVO;
import com.efitops.basesetup.entity.PutawayVO;

@Repository
public interface PutawayDetailsRepo extends JpaRepository<PutawayDetailsVO, Long>{

	List<PutawayDetailsVO> findByPutawayVO(PutawayVO putawayVO);

}
