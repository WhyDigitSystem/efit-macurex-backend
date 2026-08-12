package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnDetailsVO;

@Repository
public interface SalesReturnDetailsRepo extends JpaRepository<SalesReturnDetailsVO, Long> {

    List<SalesReturnDetailsVO> findBySalesReturnId(Long salesReturnId);

}