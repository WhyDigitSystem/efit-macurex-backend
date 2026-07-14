package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NpdDetailsVO;
import com.efitops.basesetup.entity.NpdVO;

@Repository
public interface NpdDetailsRepo extends JpaRepository<NpdDetailsVO, Long> {

	List<NpdDetailsVO> findByNpdVO(NpdVO npdVO);

}
