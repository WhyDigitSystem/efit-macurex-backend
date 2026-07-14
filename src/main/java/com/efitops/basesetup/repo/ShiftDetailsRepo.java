package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ShiftDetailsVO;
import com.efitops.basesetup.entity.ShiftVO;

@Repository
public interface ShiftDetailsRepo extends JpaRepository<ShiftDetailsVO, Long>
{

	List<ShiftDetailsVO> findByShiftVO(ShiftVO shiftVO);

}
