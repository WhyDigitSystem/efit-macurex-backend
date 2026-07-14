package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GlOpeningBalanceVO;
import com.efitops.basesetup.entity.ParticularsGlOpeningBalanceVO;
@Repository
public interface ParticularsGlOpeningBalanceRepo extends JpaRepository<ParticularsGlOpeningBalanceVO,Long> {

	List<ParticularsGlOpeningBalanceVO> findByGlOpeningBalanceVO(GlOpeningBalanceVO glOpeningBalanceVO);

}
