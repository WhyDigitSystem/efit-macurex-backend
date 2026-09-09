package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EngineeringChangeNoteVO;
import com.efitops.basesetup.entity.RemarksVO;

@Repository
public interface RemarksRepo extends JpaRepository<RemarksVO, Long> {

	List<RemarksVO> findByEngineeringChangeNoteVO(EngineeringChangeNoteVO vo);

}
