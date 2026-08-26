package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnTaxDetailsVO;
import com.efitops.basesetup.entity.GrnVO;

@Repository
public interface GrnTaxDetailsRepo extends JpaRepository<GrnTaxDetailsVO, Long> {

	List<GrnTaxDetailsVO> findByGrnVO(GrnVO vo);

}
