package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CustomerShippingDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;

@Repository
public interface CustomerShippingDetailsRepo  extends JpaRepository<CustomerShippingDetailsVO, Long>{

	List<CustomerShippingDetailsVO> findByCustomerVO(CustomerVO customerVO);

}
