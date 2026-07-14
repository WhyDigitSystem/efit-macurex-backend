package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.TdsMaster2VO;

@Repository
public interface TdsMaster2Repo extends JpaRepository<TdsMaster2VO, Long>{

}
