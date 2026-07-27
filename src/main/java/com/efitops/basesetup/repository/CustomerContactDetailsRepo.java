package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CustomerContactDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;

@Repository
public interface CustomerContactDetailsRepo extends JpaRepository <CustomerContactDetailsVO, Long>{

	List<CustomerContactDetailsVO> findByCustomerVO(CustomerVO customerVO);

}
