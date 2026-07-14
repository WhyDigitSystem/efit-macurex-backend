package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PickListDetailsVO;
import com.efitops.basesetup.entity.PickListVO;

@Repository
public interface PickListDetailsRepo  extends JpaRepository<PickListDetailsVO, Long>{

	List<PickListDetailsVO> findByPickListVO(PickListVO pickListVO);

}
