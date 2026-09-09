package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentsVO;

@Repository
public interface DocumentsRepo extends JpaRepository<DocumentsVO, Long> {

}
