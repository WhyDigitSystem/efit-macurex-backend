package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnTaxDetailsVO;

@Repository
public interface SalesReturnTaxDetailsRepo extends JpaRepository<SalesReturnTaxDetailsVO, Long> {

    List<SalesReturnTaxDetailsVO> findBySalesReturnId(Long salesReturnId);

    void deleteBySalesReturnId(Long salesReturnId);

}