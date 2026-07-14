package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolRecieveFromCalibrationImagesVO;

@Repository
public interface ToolRecieveFromCalibrationImagesRepo extends JpaRepository<ToolRecieveFromCalibrationImagesVO, Long> {

}
