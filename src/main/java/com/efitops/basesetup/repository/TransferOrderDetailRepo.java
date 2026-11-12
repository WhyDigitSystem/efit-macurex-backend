package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TransferOrderDetailVO;

public interface TransferOrderDetailRepo extends JpaRepository<TransferOrderDetailVO, Long>{
	
	
	  List<TransferOrderDetailVO> findByTransferOrderVOId(Long id);

}
