package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.ImportGrnDetailsVO;

@Repository
public interface ImportGrnDetailsRepo extends JpaRepository<ImportGrnDetailsVO, Long> {

	List<ImportGrnDetailsVO> findByGrnVO(GrnVO vo);

}
