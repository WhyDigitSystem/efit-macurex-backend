package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.SalesOrderAmendmentVO;

public interface SalesOrderAmendmentDetailsRepo 
extends JpaRepository<SalesOrderAmendmentDetailsVO, Long> {

    List<SalesOrderAmendmentDetailsVO> findBySalesOrderAmendmentVO(
            SalesOrderAmendmentVO salesOrderAmendmentVO);

    void deleteBySalesOrderAmendmentVO(
            SalesOrderAmendmentVO salesOrderAmendmentVO);

}