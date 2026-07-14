package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.WorkOrderDetailsVO;
import com.efitops.basesetup.entity.WorkOrderVO;

@Repository
public interface WorkOrderDetailsRepo extends JpaRepository<WorkOrderDetailsVO, Long> {

	List<WorkOrderDetailsVO> findByWorkOrderVO(WorkOrderVO workOrderVO);

}
