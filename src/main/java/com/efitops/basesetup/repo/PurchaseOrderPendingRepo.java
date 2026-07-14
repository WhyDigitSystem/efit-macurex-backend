package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseOrderPendingVO;

@Repository
public interface PurchaseOrderPendingRepo extends JpaRepository<PurchaseOrderPendingVO, Long>{


	List<PurchaseOrderPendingVO> findBySourceId(Long sourceId);




}
