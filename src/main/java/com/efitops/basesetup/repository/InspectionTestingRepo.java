package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EngineeringChangeNoteVO;
import com.efitops.basesetup.entity.InspectionTestingVO;

@Repository
public interface InspectionTestingRepo extends JpaRepository<InspectionTestingVO, Long> {

	List<InspectionTestingVO> findByEngineeringChangeNoteVO(EngineeringChangeNoteVO vo);

}
