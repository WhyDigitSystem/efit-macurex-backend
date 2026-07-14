package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.NpdAttachmentVO;
import com.efitops.basesetup.entity.NpdVO;

public interface NpdAttachmentRepo extends JpaRepository<NpdAttachmentVO, Long> {

	List<NpdAttachmentVO> findByNpdVO(NpdVO npdVO);

}
