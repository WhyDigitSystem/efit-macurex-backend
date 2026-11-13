package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CustomerItemDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;

@Repository
public interface CustomerItemDetailsRepo extends JpaRepository<CustomerItemDetailsVO, Long>{

	List<CustomerItemDetailsVO> findByCustomerVO(CustomerVO customerVO);

}
