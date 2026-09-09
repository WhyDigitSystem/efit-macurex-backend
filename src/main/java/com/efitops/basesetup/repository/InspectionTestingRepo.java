package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InspectionTestingVO;

@Repository
public interface InspectionTestingRepo extends JpaRepository<InspectionTestingVO, Long> {

}
