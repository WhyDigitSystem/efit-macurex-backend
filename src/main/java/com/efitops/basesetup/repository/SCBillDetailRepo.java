package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SCBillDetailVO;

public interface SCBillDetailRepo extends JpaRepository<SCBillDetailVO, Long> {

}
