package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderTermsVO;
import com.efitops.basesetup.entity.SalesVO;

@Repository
public interface SalesOrderTermsRepo extends JpaRepository<SalesOrderTermsVO, Long> {

	List<SalesOrderTermsVO> findBySalesVO(SalesVO salesVO);


}
