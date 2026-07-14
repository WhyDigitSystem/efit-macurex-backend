package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PackingListDetailsVO;
import com.efitops.basesetup.entity.PackingListVO;

public interface PackingListDetailsRepo extends JpaRepository<PackingListDetailsVO, Long> {

	List<PackingListDetailsVO> findByPackingListVO(PackingListVO packingListVO);

}
