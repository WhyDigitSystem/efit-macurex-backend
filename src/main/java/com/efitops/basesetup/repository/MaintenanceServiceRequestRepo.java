package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MaintenanceServiceRequestVO;

public interface MaintenanceServiceRequestRepo extends JpaRepository<MaintenanceServiceRequestVO, Long> {
	
	
	 List<MaintenanceServiceRequestVO> findByOrgIdAndCancelFalse(
	            Long orgId);

}
