package com.efitops.basesetup.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineCapacityVO;

public interface MachineCapacityRepo extends JpaRepository<MachineCapacityVO, Long>{

	List<MachineCapacityVO> findByMachineMasterVO(MachineMasterVO machineMasterVO);

}
