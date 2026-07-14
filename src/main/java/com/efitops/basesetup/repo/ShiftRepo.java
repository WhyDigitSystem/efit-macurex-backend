package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ShiftVO;

public interface ShiftRepo extends JpaRepository<ShiftVO, Long>  {

	@Query(nativeQuery = true, value = "select * from shiftmast where orgid=?1")
	 List<ShiftVO> getShiftByOrgId(Long orgId) ;
	@Query(nativeQuery = true, value = "select * from shiftmast where shiftmastid=?1")
	List<ShiftVO> getShiftById(Long id);
	
	boolean existsByShiftNameAndOrgId(String shiftCode, Long orgId);
	
	boolean existsByShiftCodeAndOrgId(String shiftCode, Long orgId);
}
