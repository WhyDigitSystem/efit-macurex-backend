package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BillOfMaterialDetailsVO;
import com.efitops.basesetup.entity.BillOfMaterialVO;

@Repository
public interface BillOfMaterialDetailsRepo extends JpaRepository<BillOfMaterialDetailsVO, Long> {

	List<BillOfMaterialDetailsVO> findByBillOfMaterialVO(BillOfMaterialVO vo);

}
