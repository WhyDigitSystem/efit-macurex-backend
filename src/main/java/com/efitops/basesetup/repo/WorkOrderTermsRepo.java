package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.WorkOrderTermsVO;
import com.efitops.basesetup.entity.WorkOrderVO;

@Repository
public interface WorkOrderTermsRepo extends JpaRepository<WorkOrderTermsVO, Long> {

	List<WorkOrderTermsVO> findByWorkOrderVO(WorkOrderVO workOrderVO);

}
