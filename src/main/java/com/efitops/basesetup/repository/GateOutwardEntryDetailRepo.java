package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.GateOutwardEntryDetailVO;

public interface GateOutwardEntryDetailRepo extends JpaRepository<GateOutwardEntryDetailVO, Long> {

	Iterable<? extends GateOutwardEntryDetailVO> findByGateOutwardEntryVOId(Long id);

}
