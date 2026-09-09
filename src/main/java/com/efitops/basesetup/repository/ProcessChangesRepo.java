package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProcessChangesVO;

@Repository
public interface ProcessChangesRepo extends JpaRepository<ProcessChangesVO, Long> {

}
