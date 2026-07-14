package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RecieveFromCalibrationDetailsImagesVO;

@Repository
public interface RecieveFromCalibrationDetailsImagesRepo extends JpaRepository<RecieveFromCalibrationDetailsImagesVO, Long> {

}
