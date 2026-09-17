package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialTransferReturnNoteDetailsVO;
import com.efitops.basesetup.entity.MaterialTransferReturnNoteVO;

@Repository
public interface MaterialTransferReturnNoteDetailsRepository extends JpaRepository<MaterialTransferReturnNoteDetailsVO, Long> {
    
    List<MaterialTransferReturnNoteDetailsVO> findByMaterialTransferReturnNoteVO(MaterialTransferReturnNoteVO vo);
}