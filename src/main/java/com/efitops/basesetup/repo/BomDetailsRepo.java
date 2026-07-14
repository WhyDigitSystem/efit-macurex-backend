package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.BomDetailsVO;
import com.efitops.basesetup.entity.BomVO;

public interface BomDetailsRepo extends JpaRepository<BomDetailsVO, Long> {

	List<BomDetailsVO> findByBomVO(BomVO bomVO);

}
