package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.WorkOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.WorkOrderShortCloseVO;

@Repository
public interface WorkOrderShortCloseDetailsRepo extends JpaRepository<WorkOrderShortCloseDetailsVO, Long> {

	List<WorkOrderShortCloseDetailsVO> findByWorkOrderShortCloseVO(WorkOrderShortCloseVO workOrderShortCloseVO);

}
