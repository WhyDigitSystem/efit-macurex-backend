package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseVO;

@Repository
public interface SalesOrderShortCloseDetailsRepo extends JpaRepository<SalesOrderShortCloseDetailsVO, Long> {

	List<SalesOrderShortCloseDetailsVO> findBySalesOrderShortCloseVO(SalesOrderShortCloseVO salesOrderShortCloseVO);

}
