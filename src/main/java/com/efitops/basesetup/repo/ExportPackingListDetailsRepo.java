package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ExportPackingListDetailsVO;
import com.efitops.basesetup.entity.ExportPackingListVO;

@Repository
public interface ExportPackingListDetailsRepo extends JpaRepository<ExportPackingListDetailsVO,Long>{

	List<ExportPackingListDetailsVO> findByExportPackingListVO(ExportPackingListVO exportPackingListVO);

}
