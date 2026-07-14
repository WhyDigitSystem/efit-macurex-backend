package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineTechnicalInfoVO;

public interface MachineTechnicalInfoRepo extends JpaRepository<MachineTechnicalInfoVO, Long>{

	List<MachineTechnicalInfoVO> findByMachineMasterVO(MachineMasterVO machineMasterVO);

}
