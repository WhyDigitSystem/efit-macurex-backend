package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnLocalDetailsVO;
import com.efitops.basesetup.entity.SalesReturnLocalVO;

@Repository
public interface SalesReturnLocalDetailsRepo extends JpaRepository<SalesReturnLocalDetailsVO, Long> {

	List<SalesReturnLocalDetailsVO> findBySalesReturnLocalVO(SalesReturnLocalVO salesReturnLocalVO);

}
