package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterAttachmentVO;
import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineMasterAttachmentRepo
        extends JpaRepository<MachineMasterAttachmentVO, Long> {

    List<MachineMasterAttachmentVO> findByMachineMasterVO(
            MachineMasterVO machineMasterVO);

    void deleteByMachineMasterVO(
            MachineMasterVO machineMasterVO);
}