package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderTaxDetailsVO;
import com.efitops.basesetup.entity.JobOrderVO;

@Repository
public interface JobOrderTaxDetailsRepo extends JpaRepository<JobOrderTaxDetailsVO, Long>{

	List<JobOrderTaxDetailsVO> findByJobOrder(JobOrderVO jobOrderVO);

}
