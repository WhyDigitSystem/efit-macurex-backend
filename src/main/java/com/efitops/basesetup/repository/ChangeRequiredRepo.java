package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ChangeRequiredVO;
import com.efitops.basesetup.entity.EngineeringChangeNoteVO;

@Repository
public interface ChangeRequiredRepo extends JpaRepository<ChangeRequiredVO, Long> {

	List<ChangeRequiredVO> findByEngineeringChangeNoteVO(EngineeringChangeNoteVO vo);

}
