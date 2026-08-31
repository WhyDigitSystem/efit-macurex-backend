package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineHistoryVO;
import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineHistoryRepo
        extends JpaRepository<MachineHistoryVO, Long> {

    List<MachineHistoryVO> findByMachineMasterVO(
            MachineMasterVO machineMasterVO);

}