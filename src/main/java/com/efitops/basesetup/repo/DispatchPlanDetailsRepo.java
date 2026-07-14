package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DispatchPlanDetailsVO;
import com.efitops.basesetup.entity.DispatchPlanVO;

@Repository
public interface DispatchPlanDetailsRepo extends JpaRepository<DispatchPlanDetailsVO, Long>{


	List<DispatchPlanDetailsVO> findByDispatchPlanVO(DispatchPlanVO dispatchPlanVO);


}
