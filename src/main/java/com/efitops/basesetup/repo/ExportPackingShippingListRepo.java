package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ExportPackingListVO;
import com.efitops.basesetup.entity.ExportPackingShippingListVO;

@Repository
public interface ExportPackingShippingListRepo extends JpaRepository<ExportPackingShippingListVO, Long>{

	List<ExportPackingShippingListVO> findByExportPackingListVO(ExportPackingListVO exportPackingListVO);

}
