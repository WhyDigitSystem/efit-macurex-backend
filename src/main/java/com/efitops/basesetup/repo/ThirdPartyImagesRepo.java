package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ThirdPartyImagesVO;

@Repository
public interface ThirdPartyImagesRepo extends JpaRepository<ThirdPartyImagesVO, Long> {

}
