package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ExportPackingListTermsVO;
import com.efitops.basesetup.entity.ExportPackingListVO;

@Repository
public interface ExportPackingListTermsRepo extends JpaRepository<ExportPackingListTermsVO, Long>{

	List<ExportPackingListTermsVO> findByExportPackingListVO(ExportPackingListVO exportPackingListVO);

}
