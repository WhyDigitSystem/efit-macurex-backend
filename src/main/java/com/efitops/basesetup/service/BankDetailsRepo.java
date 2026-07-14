package com.efitops.basesetup.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BankDetailsVO;
import com.efitops.basesetup.entity.CompanyVO;

@Repository
public interface BankDetailsRepo extends JpaRepository<BankDetailsVO, Long> {

	List<BankDetailsVO> findByCompanyVO(CompanyVO companyVO);
}
