package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ShiftBreakTimingDetailsVO;
import com.efitops.basesetup.entity.ShiftVO;
@Repository
public interface ShiftBreakTimingDetailsRepo extends JpaRepository<ShiftBreakTimingDetailsVO, Long>{

	List<ShiftBreakTimingDetailsVO> findByshiftVO(ShiftVO shiftVO);

}
