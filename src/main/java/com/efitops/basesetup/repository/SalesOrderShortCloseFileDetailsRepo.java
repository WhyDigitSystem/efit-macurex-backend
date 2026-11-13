package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderShortCloseFileDetailsVO;

@Repository
public interface SalesOrderShortCloseFileDetailsRepo extends JpaRepository<SalesOrderShortCloseFileDetailsVO, Long> {

//	List<SalesOrderShortCloseFileDetailsVO> findBySalesOrderShortCloseVO(SalesOrderShortCloseVO salesOrderShortCloseVO);

}
