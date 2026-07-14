package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.GstDebitNoteVO;

public interface GstDebitNoteRepo extends JpaRepository<GstDebitNoteVO, Long>{

}
