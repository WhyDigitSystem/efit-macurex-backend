package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ToolCategoryDetailVO;
import com.efitops.basesetup.entity.ToolCategoryVO;

public interface ToolCategoryDetailRepo extends JpaRepository<ToolCategoryDetailVO, Long> {

	List<ToolCategoryDetailVO> findByToolCategoryVO(ToolCategoryVO toolCategoryVO);

}
