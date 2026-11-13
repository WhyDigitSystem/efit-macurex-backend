package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnFileUploadDetailsVO;
import com.efitops.basesetup.entity.GrnVO;

@Repository
public interface GrnFileUploadDetailsRepo extends JpaRepository<GrnFileUploadDetailsVO, Long> {

	List<GrnFileUploadDetailsVO> findByGrnVO(GrnVO vo);

}
