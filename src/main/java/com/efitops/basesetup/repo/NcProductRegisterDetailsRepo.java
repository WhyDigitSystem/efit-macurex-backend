package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NcProductRegisterDetailsVO;
import com.efitops.basesetup.entity.NcProductRegisterVO;

@Repository
public interface NcProductRegisterDetailsRepo extends JpaRepository<NcProductRegisterDetailsVO, Long> {

	List<NcProductRegisterDetailsVO> findByNcProductRegisterVO(NcProductRegisterVO ncProductRegisterVO);

}
