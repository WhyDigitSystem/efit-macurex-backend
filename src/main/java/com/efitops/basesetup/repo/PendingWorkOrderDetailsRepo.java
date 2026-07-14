package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PendingWorkOrderDetailsVO;

@Repository
public interface PendingWorkOrderDetailsRepo extends JpaRepository<PendingWorkOrderDetailsVO, Long> {

	List<PendingWorkOrderDetailsVO> findBySourceId(Long sourceId);

}
