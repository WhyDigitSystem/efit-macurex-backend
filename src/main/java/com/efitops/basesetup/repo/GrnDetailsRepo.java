package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnDetailsVO;
import com.efitops.basesetup.entity.GrnVO;
@Repository
public interface GrnDetailsRepo extends JpaRepository<GrnDetailsVO, Long> {

	List<GrnDetailsVO> findByGrnVO(GrnVO grnVO);

}
