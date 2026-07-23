package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TransportMasterVO;

public interface TransportRepo extends JpaRepository<TransportMasterVO, Long>{

	boolean existsByTransportNameAndOrgId(String transportName, Long orgid);


}
