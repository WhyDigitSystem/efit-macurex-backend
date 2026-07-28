package com.efitops.basesetup.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GlobalParameterVO;

@Repository
public interface GlobalParameterRepo extends JpaRepository<GlobalParameterVO, Long>{

	@Query("select a from GlobalParameterVO a where a.orgId=?1 and a.userid=?2")
	Optional<GlobalParameterVO> findGlobalParamByOrgIdAndUserName(Long orgid, Long userid);

}
