package com.efitops.basesetup.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DrawingMasterDetailsVO;
import com.efitops.basesetup.entity.DrawingMasterVO;

public interface DrawingMasterDetailsRepo extends JpaRepository<DrawingMasterDetailsVO, Long>{

	List<DrawingMasterDetailsVO> findByDrawingMasterVO(DrawingMasterVO drawingMasterVO);

}
