package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyMasterVO;

@Repository
public interface PartyMasterRepo extends JpaRepository< PartyMasterVO, Long>{

}
