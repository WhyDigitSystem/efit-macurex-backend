package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TaxMasterDetailsVO;

public interface TaxMasterDetailsRepo extends JpaRepository<TaxMasterDetailsVO, Long>{

}
