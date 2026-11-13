package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentsChangesVO;
import com.efitops.basesetup.entity.EngineeringChangeNoteVO;

@Repository
public interface DocumentsChangesRepo extends JpaRepository<DocumentsChangesVO, Long> {

	List<DocumentsChangesVO> findByEngineeringChangeNoteVO(EngineeringChangeNoteVO vo);

}
